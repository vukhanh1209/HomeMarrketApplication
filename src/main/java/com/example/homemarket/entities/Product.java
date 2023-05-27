package com.example.homemarket.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "products")
public class Product implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer productID;
	private String productName;
	private int quantity;
	private float price;
	private String weight;
	private String productImageURL;

	@ManyToOne
	@JoinColumn(name = "categoryID")
	private Category category;


	@OneToOne(mappedBy = "product")
	private CartItem cartItem;

	@OneToMany(mappedBy = "product")
	private List<OrderItem> orderItemList;

}
