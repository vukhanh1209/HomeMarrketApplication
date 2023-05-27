package com.example.homemarket.service.cart;

import com.example.homemarket.dtos.CartDTO;
import com.example.homemarket.dtos.request.ItemRequestDTO;
import com.example.homemarket.dtos.response.BaseResponse;
import com.example.homemarket.entities.Cart;
import com.example.homemarket.entities.CartItem;
import com.example.homemarket.entities.Product;
import com.example.homemarket.entities.User;
import com.example.homemarket.exceptions.NotFoundException;
import com.example.homemarket.repositories.CartItemRepository;
import com.example.homemarket.repositories.CartRepository;
import com.example.homemarket.repositories.ProductRepository;
import com.example.homemarket.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

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
//    @Autowired
//    OrderRepository orderRepository;
//    @Autowired
//    OrderDetailRepository orderDetailRepository;
//    @Autowired
//    PaymentRepository paymentRepository;
    @Transactional(readOnly = true)
    @Override
    public CartDTO getCart(Integer user_id) {
        Cart cart = cartRepository.findByUserId(user_id);
        List<CartItem> item = itemRepository.findByCartId(cart.getCartID());
        if (cart == null){
            throw new NotFoundException("Do not have products in your cart");
        }
        if (item.stream().count() == 0){
            throw new NotFoundException("Do not have products in your cart");
        }
        else{
            cart.setItems(item);
        }
        return new CartDTO(cart);
    }
//    @Override
//    @Transactional
//    public BaseResponse buyNow(CheckoutRequestBuyNowDTO checkoutRequestBuyNowDTO) {
//        User user = userRepository.findById(checkoutRequestBuyNowDTO.getUserId()).
//                orElseThrow(()-> new NotFoundException(String.format("User with id %d not found", checkoutRequestBuyNowDTO.getUserId())));
//
//        Order order = new Order();
//        order.setPhoneNumber(checkoutRequestBuyNowDTO.getPhone());
//        order.setTotal(checkoutRequestBuyNowDTO.getTotalPrice());
//        order.setStatus("Pending");
//        order.setUser(user);
//        orderRepository.save(order);
//
//        Item item = new Item();
//        item.setItemName(checkoutRequestBuyNowDTO.getItemDetail().getItemName());
//        item.setPrice(checkoutRequestBuyNowDTO.getItemDetail().getPrice());
//        item.setQuantity(checkoutRequestBuyNowDTO.getItemDetail().getQuantity());
//        item.setThumbnail(checkoutRequestBuyNowDTO.getItemDetail().getThumbnailPath());
//        item.setStatusCheckout(true);
//
//        Product product = productRepository.findById(checkoutRequestBuyNowDTO.getItemDetail().getProductId()).
//                orElseThrow(() -> new NotFoundException(String.format("Product with id %d not found", checkoutRequestBuyNowDTO.getItemDetail().getProductId())));
//        product.setQuantity(product.getQuantity() - checkoutRequestBuyNowDTO.getItemDetail().getQuantity());
//        item.setProduct(product);
//        itemRepository.save(item);
//        productRepository.save(product);
//
//        OrderDetail orderDetail = new OrderDetail();
//        orderDetail.setPrice(checkoutRequestBuyNowDTO.getItemDetail().getPrice());
//        orderDetail.setQuantity(checkoutRequestBuyNowDTO.getItemDetail().getQuantity());
//        orderDetail.setItem(item);
//        orderDetail.setOrder(order);
//        orderDetailRepository.save(orderDetail);
//
//        Payment payment = new Payment();
//        payment.setPaymentMethod(checkoutRequestBuyNowDTO.getPaymentMethod());
//        payment.setPaymentPrice(checkoutRequestBuyNowDTO.getTotalPrice());
//        payment.setUser(user);
//        payment.setOrder(order);
//        paymentRepository.save(payment);
//        return new BaseResponse(true, "Checkout successfully");
//    }

    //    @Override
//    public CheckoutDTO getCheckoutInfo(Integer userId) {
//        Optional<User> user = userRepository.findById(userId);
//        if (!user.isPresent()){
//            throw new NotFoundException(String.format("User with id %d not found", userId));
//        }
//        CheckoutDTO checkoutDTO = new CheckoutDTO();
//        checkoutDTO.setUserId(userId);
//        checkoutDTO.setUserName(user.get().getFirstName() + user.get().getLastName());
//        checkoutDTO.setAddress(user.get().getDefaultAddress());
//        checkoutDTO.setPhone(user.get().getPhoneNumber());
//        return checkoutDTO;
//    }
//
//    @Override
//    @Transactional
//    public BaseResponse checkout(CheckoutRequestDTO checkoutRequestDTO) {
//        Optional<User> user = userRepository.findById(checkoutRequestDTO.getUserId());
//        if (!user.isPresent()){
//            throw new NotFoundException(String.format("User with id %d not found", checkoutRequestDTO.getUserId()));
//        }
//        Order order = new Order();
//        order.setPhoneNumber(checkoutRequestDTO.getPhone());
//        order.setTotal(checkoutRequestDTO.getTotalPrice());
//        order.setStatus("Pending");
//        order.setUser(user.get());
//        orderRepository.save(order);
//
//        List<Item> items = new ArrayList<>();
//        List<OrderDetail> orderDetails = new ArrayList<>();
//
//        for (ItemDTO itemDTO : checkoutRequestDTO.getItemDTOS()){
//            Item item = new Item();
//            item.setId(itemDTO.getId());
//            item.setStatusCheckout(true);
//            item.setItemName(itemDTO.getItemName());
//            item.setPrice(itemDTO.getPrice());
//            item.setQuantity(itemDTO.getQuantity());
//
//            Optional<Cart> cart = cartRepository.findById(itemDTO.getCartId());
//            if (!cart.isPresent()){
//                throw new NotFoundException(String.format("Cart with id %d not found", itemDTO.getCartId()));
//            }
//            item.setCart(cart.get());
//
//            Optional<Product> product = productRepository.findById(itemDTO.getProductId());
//            if (!product.isPresent()){
//                throw new NotFoundException(String.format("Product with id %d not found",itemDTO.getProductId()));
//            }
//            product.get().setQuantity(product.get().getQuantity() - itemDTO.getQuantity());
//            item.setProduct(product.get());
//            item.setThumbnail(itemDTO.getThumbnailPath());
//            items.add(item);
//
//            OrderDetail orderDetail = new OrderDetail();
//            orderDetail.setPrice(itemDTO.getPrice());
//            orderDetail.setQuantity(itemDTO.getQuantity());
//            orderDetail.setItem(item);
//            orderDetail.setOrder(order);
//            orderDetails.add(orderDetail);
//        }
//
//        itemRepository.saveAll(items);
//        orderDetailRepository.saveAll(orderDetails);
//        List<Product> products = items.stream()
//                .filter(item -> item.getProduct() != null)
//                .map(Item::getProduct)
//                .collect(Collectors.toList());
//        productRepository.saveAll(products);
//
//        Payment payment = new Payment();
//        payment.setPaymentMethod(checkoutRequestDTO.getPaymentMethod());
//        payment.setPaymentPrice(checkoutRequestDTO.getTotalPrice());
//        payment.setOrder(order);
//        payment.setUser(user.get());
//        paymentRepository.save(payment);
//        return new BaseResponse(true, "Checkout successfully");
//    }
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
            } else throw new RuntimeException("Quantity of product is larger than in warehouse");
        } else {
            int totalQuantity = itemOptional.get().getQuantity() + itemRequestDTO.getQuantity();
            if (totalQuantity <= quantityProduct) {
                item = itemOptional.get();
                item.setQuantity(totalQuantity);
            } else throw new RuntimeException("Quantity of item no more than product quantity");
        }
        itemRepository.save(item);
        return new BaseResponse(true, "Adding product to cart successfully");
    }

    @Override
    public BaseResponse deleteItem(Integer itemId) {
        CartItem item = itemRepository.findById(itemId).orElseThrow(()->
                new NotFoundException(String.format("Item with id %d not found", itemId)));
        itemRepository.delete(item);

        return new BaseResponse(true, "Delete successfully");
    }
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

