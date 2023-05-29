package com.example.homemarket.entities;

import com.example.homemarket.utils.EnumOrderStatus;
import com.example.homemarket.utils.EnumPaymentMethod;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;


@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "orders")
public class Order implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer orderID;
	@Temporal(TemporalType.DATE)
	private Date orderDate;
	private float totalValue;
	private String userName;
	private String address;
	private String phoneNumber;
    private EnumPaymentMethod paymentMethod;
	@Enumerated(EnumType.STRING)
	private EnumOrderStatus status;

	@OneToMany(mappedBy = "order")
	private List<OrderItem> orderItemList;

	@ManyToOne
	@JoinColumn(name = "userID")
	private User user;



}
