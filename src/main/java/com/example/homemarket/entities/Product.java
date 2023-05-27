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
@Table(name = "product")
public class Product implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "product_id", nullable = false)
	private Integer productID;
	private String productName;
	private int quantity;
	private float price;
	private String weight;

	@Column(name = "product_img_url", nullable = false)
	private String productImageURL;
	private String weight;
	@ManyToOne
	@JoinColumn(name = "category_id")
	private Category category;


	@OneToOne(mappedBy = "product")
	private CartItem cartItem;

	@OneToMany(mappedBy = "product")
	private List<OrderItem> orderItemList;

}
