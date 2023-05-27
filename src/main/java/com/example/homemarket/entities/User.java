package com.example.homemarket.entities;

import com.example.homemarket.utils.EnumRole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user", uniqueConstraints = @UniqueConstraint(columnNames = "email"))
public class User implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "userID", nullable = false)
	private Integer userID;

	@Column(name = "firstName")
	@NotBlank
	private String firstName;

	@Column(name = "lastName")
	@NotBlank
	private String lastName;

	@NotBlank
	@NotNull
	@Email(message = "Email is not valid")
	private String email;

	@NotBlank
	private String password;

	@Column(name = "phone_number")
	@Size(max = 10, min = 10)
	private String phoneNumber;

	@Column(name="address")
	@NotBlank
	private String address;

	@Column(name = "verificationCode")
	private String verificationCode;

	@Enumerated(EnumType.STRING)
	private EnumRole roles;

	@Column(name = "is_active")
	private Boolean isActive = false;

	@OneToOne(mappedBy = "user",cascade = CascadeType.ALL)
	private Cart cart;

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private List<Payment> paymentList;

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private  List<Order> orderList;

}
