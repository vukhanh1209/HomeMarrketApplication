package com.example.homemarket.service.cart;

import com.example.homemarket.dtos.*;
import com.example.homemarket.dtos.request.ItemEditRequestDTO;
import com.example.homemarket.dtos.request.ItemRequestDTO;
import com.example.homemarket.dtos.request.PlaceOrderRequestDTO;
import com.example.homemarket.dtos.response.BaseResponse;
import com.example.homemarket.dtos.response.CheckoutDTO;
import com.example.homemarket.dtos.response.ItemCheckResultDTO;
import com.example.homemarket.entities.*;
import com.example.homemarket.exceptions.NotFoundException;
import com.example.homemarket.repositories.*;
import com.example.homemarket.utils.EnumOrderStatus;
import com.example.homemarket.utils.EnumPaymentMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CartServiceImpl implements CartService {
    @Autowired
    ProductRepository productRepository;
    @Autowired
    CartItemRepository itemRepository;
    @Autowired
    CartRepository cartRepository;
    @Autowired
    UserRepository userRepository;
    //    @Autowired
//    ProductImageRepository productImageRepository;
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    OrderDetailRepository orderDetailRepository;
    @Autowired
    PaymentRepository paymentRepository;
    @Transactional(readOnly = true)
    @Override
    public CartDTO getCart(Integer user_id) {
        Optional<Cart> cart = Optional.ofNullable(cartRepository.findByUserId(user_id));
        if (!cart.isPresent()) {
            throw new RuntimeException("User not found with id: " + user_id);
        }

        List<CartItem> items = itemRepository.findByCartId(cart.get().getCartID());
        if (items.isEmpty()) {
            throw new RuntimeException("No items found in the cart with id: " + cart.get().getCartID());
        }

        cart.get().setItems(items);
        return new CartDTO(cart.get());
    }
        @Override
    public CheckoutDTO getCheckoutInfo(Integer userId) {
        Optional<User> user = userRepository.findById(userId);
        if (!user.isPresent()){
            throw new NotFoundException(String.format("User with id %d not found", userId));
        }
        CheckoutDTO checkoutDTO = new CheckoutDTO();
        checkoutDTO.setUserId(userId);
        checkoutDTO.setUserName(user.get().getFirstName() + user.get().getLastName());
        checkoutDTO.setAddress(user.get().getAddress());
        checkoutDTO.setPhone(user.get().getPhoneNumber());
        return checkoutDTO;
    }
    @Override
    @Transactional
    public BaseResponse checkout(Integer cart_id) {
        Optional<Cart> cartOptional = cartRepository.findById(cart_id);
        if (!cartOptional.isPresent()) {
            throw new NotFoundException(String.format("Không tìm thấy giỏ hàng với id %d", cart_id));
        }

        Cart cart = cartOptional.get();
        List<CartItem> cartItems = cart.getItems();
        List<ItemCheckResultDTO> itemCheckResults = new ArrayList<>();

        boolean allItemsAvailable = true;

        for (CartItem cartItem : cartItems) {
            int requestedQuantity = cartItem.getQuantity();
            int availableQuantity = cartItem.getProduct().getQuantity();

            if (availableQuantity < requestedQuantity) {
                ItemCheckResultDTO itemCheckResult = new ItemCheckResultDTO(cartItem.getProduct().getProductName(), requestedQuantity, availableQuantity, false);
                itemCheckResults.add(itemCheckResult);
                allItemsAvailable = false;
            }
        }

        if (allItemsAvailable) {
            return new BaseResponse(true, "Mua hàng thành công");
        } else {
            return new BaseResponse(false, "Sản phẩm trong kho không đủ", itemCheckResults);
        }
    }
    @Override
    @Transactional
    public BaseResponse placeorder(PlaceOrderRequestDTO orderRequestDTO) {
        try {
            Optional<Cart> cartOptional = cartRepository.findById(orderRequestDTO.getCartID());
            if (!cartOptional.isPresent()) {
                throw new NotFoundException(String.format("Không tìm thấy giỏ hàng với id %d", orderRequestDTO.getCartID()));
            }

            User user = userRepository.findById(cartOptional.get().getUser().getUserID())
                    .orElseThrow(() -> new NotFoundException(String.format("Không tìm thấy khách hàng với id %d", cartOptional.get().getUser().getUserID())));

            Order order = new Order();
            order.setPhoneNumber(orderRequestDTO.getPhone());
            order.setAddress(orderRequestDTO.getAddress());
            order.setStatus(EnumOrderStatus.WAITING);
            order.setPaymentMethod(orderRequestDTO.getPaymentMethod());
            order.setUser(user);
            order.setUserName(orderRequestDTO.getFirstName() + " " + orderRequestDTO.getLastName());
            order.setOrderDate(orderRequestDTO.getOrderDate());

            List<ItemCheckResultDTO> itemCheckResults = new ArrayList<>();
            List<OrderItem> orderDetails = new ArrayList<>();
            float totalValue = 0.0f;
            List<CartItem> cartItems = cartOptional.get().getItems();
            for (CartItem itemDTO : cartItems) {
                Optional<Product> productOptional = productRepository.findById(itemDTO.getProduct().getProductID());
                if (!productOptional.isPresent()) {
                    throw new NotFoundException(String.format("Không tìm thấy sản phẩm với id %d", itemDTO.getProduct().getProductID()));
                }
                Product product = productOptional.get();

                if (product.getQuantity() < itemDTO.getQuantity()) {
                    ItemCheckResultDTO itemCheckResult = new ItemCheckResultDTO(product.getProductName(), itemDTO.getQuantity(), product.getQuantity(), false);
                    itemCheckResults.add(itemCheckResult);
                } else {
                    product.setQuantity(product.getQuantity() - itemDTO.getQuantity());
                    productRepository.save(product);
                    float itemTotal = itemDTO.getProduct().getPrice() * itemDTO.getQuantity();
                    totalValue += itemTotal;

                    OrderItem orderDetail = new OrderItem();
                    orderDetail.setProduct(itemDTO.getProduct());
                    orderDetail.setQuantity(itemDTO.getQuantity());
                    orderDetail.setOrder(order);
                    orderDetails.add(orderDetail);
                }
            }
            order.setTotalValue(totalValue);
            orderDetailRepository.saveAll(orderDetails);
            order.setOrderItemList(orderDetails);
            orderRepository.save(order);
            if (!itemCheckResults.isEmpty()) {
                return new BaseResponse(false, "Sản phẩm trong kho không đủ", itemCheckResults);
            }
            return new BaseResponse(true, "Đặt hàng thành công");
        } catch (NotFoundException ex) {
            // Xử lý ngoại lệ NotFoundException
            return new BaseResponse(false, ex.getMessage());
        } catch (Exception ex) {
            // Xử lý các ngoại lệ khác
            return new BaseResponse(false, "An error occurred during order placement");
        }
    }
    @Override
    @Transactional
    public BaseResponse createItem(ItemRequestDTO itemRequestDTO) {
        Optional<Cart> cart = Optional.ofNullable(cartRepository.findByUserId(itemRequestDTO.getUser_id()));
        Optional<User> user = userRepository.findById(itemRequestDTO.getUser_id());
//        ProductImage productImage = productImageRepository.getThumbnail(itemRequestDTO.getProduct_id());
        if (!user.isPresent()) {
            throw new NotFoundException(String.format("User with id %d not found.", itemRequestDTO.getUser_id()));
        }
        if (!cart.isPresent()) {
            Cart newCart = new Cart();
            newCart.setUser(user.get());
            cartRepository.save(newCart);
            cart = Optional.of(newCart);
        }
//        if (productImage == null){
//            throw new NotFoundException("Not found product image");
//        }

        Optional<Product> product = productRepository.findById(itemRequestDTO.getProduct_id());
        if (!product.isPresent()) {
            throw new NotFoundException(String.format("Product with id %d not found.", itemRequestDTO.getProduct_id()));
        }
        int quantityProduct = product.get().getQuantity();
        Optional<CartItem> itemOptional = Optional.ofNullable(itemRepository.findByProductIdAndCartId(itemRequestDTO.getProduct_id(), cart.get().getCartID()));
        CartItem item = new CartItem();
        if (!itemOptional.isPresent()) {
            if (itemRequestDTO.getQuantity() <= quantityProduct) {
                item.setProduct(product.get());
//                item.setThumbnail(productImage.getPath());
                item.setQuantity(itemRequestDTO.getQuantity());
                item.setCart(cart.get());
            } else throw new RuntimeException("Sản phẩm vượt quá số lượng trong kho");
        } else {
            int totalQuantity = itemOptional.get().getQuantity() + itemRequestDTO.getQuantity();
            if (totalQuantity <= quantityProduct) {
                item = itemOptional.get();
                item.setQuantity(totalQuantity);
            } else throw new RuntimeException("Sản phẩm vượt quá số lượng trong kho");
        }
        itemRepository.save(item);
        return new BaseResponse(true, "Thêm vào giỏ hàng thành công");
    }

    @Override
    public BaseResponse deleteItem(Integer itemId) {
        CartItem item = itemRepository.findById(itemId).orElseThrow(()->
                new NotFoundException(String.format("Item with id %d not found", itemId)));
        itemRepository.delete(item);

        return new BaseResponse(true, "Đã xóa sản phẩm khỏi giỏ hàng");
    }
    @Override
    public BaseResponse updateItemQuantity(ItemEditRequestDTO itemEditRequestDTO) {
        Optional<CartItem> itemOptional = itemRepository.findById(itemEditRequestDTO.getItem_id());
        CartItem item = itemOptional.get();
        int quantityProduct = item.getProduct().getQuantity();
        int newQuantity = itemEditRequestDTO.getQuantity();
        String notification = "Chỉ còn" + String.valueOf(quantityProduct) + "sản phẩm trong kho";

        if (newQuantity > quantityProduct) {
            throw new RuntimeException(notification);
        }

        item.setQuantity(newQuantity);
        itemRepository.save(item);

        return new BaseResponse(true, "Item quantity updated successfully");
    }
//    @Override
//    public UserDTO getOrder(Integer id) {
//        Optional<User> user = userRepository.findById(id);
//        if(!user.isPresent()){
//            throw new RuntimeException("User not found with id: "+id);
//        }
//        return user.get().getOrderList();
//    }
}








//    @Override
//    public CartDTO createItem(ItemRequestDTO itemRequestDTO) {
//        // Tìm cart của user
//        Cart cart = cartRepository.findByUserId(itemRequestDTO.getUser_id()).orElseGet(() -> {
//            User user = userRepository.findById(itemRequestDTO.getUser_id())
//                    .orElseThrow(() -> new NotFoundException(String.format("User with id %d not found.", itemRequestDTO.getUser_id())));
//            Cart newCart = new Cart();
//            newCart.setUser(user);
//            return cartRepository.save(newCart);
//        });
//
//        // Tìm sản phẩm
//        Product product = productRepository.findById(itemRequestDTO.getProduct_id())
//                .orElseThrow(() -> new NotFoundException(String.format("Product with id %d not found.", itemRequestDTO.getProduct_id())));
//
//        // Kiểm tra số lượng sản phẩm trong kho
//        int quantityProduct = product.getQuantity();
//        int requestQuantity = itemRequestDTO.getQuantity();
//        if (requestQuantity > quantityProduct) {
//            throw new RuntimeException("Quantity of product is larger than in warehouse");
//        }
//
//        // Tìm item trong cart (nếu có)
//        Item item = itemRepository.findByProductIdAndCartId(itemRequestDTO.getProduct_id(), cart.getId())
//                .orElseGet(() -> {
//                    Item newItem = new Item();
//                    newItem.setProduct(product);
//                    newItem.setItemName(product.getProductName());
//                    newItem.setPrice(product.getPrice());
//                    newItem.setCart(cart);
//                    return newItem;
//                });
//
//        // Cập nhật số lượng item
//        int totalQuantity = item.getQuantity() + requestQuantity;
//        if (totalQuantity > quantityProduct) {
//            throw new RuntimeException("Quantity of item no more than product quantity");
//        }
//        item.setQuantity(totalQuantity);
//        itemRepository.save(item);
//
//        // Tạo danh sách item để trả về
//        List<Item> itemList = Collections.singletonList(item);
//        CartDTO cartDTO = new CartDTO(cart);
//        cartDTO.setItems(itemList);
//        return cartDTO;
//    }

