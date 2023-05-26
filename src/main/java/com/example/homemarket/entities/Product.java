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
	private Integer id;
	private String productName;
	private int quantity;
	private float price;
	private String productImage;
	private String description;

	@ManyToOne
	@JoinColumn(name = "categoryId")
	private Category category;


	@OneToMany(mappedBy = "product")
	private List<CartItem> items;

}
