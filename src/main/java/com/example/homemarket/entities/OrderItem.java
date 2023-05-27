package com.example.homemarket.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;


@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "orderItem")
public class OrderItem implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long orderItemID;
	private int quantity;

	@ManyToOne
	@JoinColumn(name = "productID")
	private Product product;
	
	@ManyToOne
	@JoinColumn(name = "orderID")
	private Order order;

}
