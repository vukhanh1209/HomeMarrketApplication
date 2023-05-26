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
	@Column(name = "user_id", nullable = false)
	private Integer id;

	@Column(name = "first_name")
	@NotBlank
	private String firstName;

	@Column(name = "last_name")
	@NotBlank
	private String lastName;

	@NotBlank
	@NotNull
	@Email(message = "Email is not valid")
	private String email;

	@NotBlank
	private String password;

	@Column(name = "phone_number")
	@Size(max = 10)
	private String phoneNumber;

	@Column(name="default_address")
	@NotBlank
	private String defaultAddress;

	@Column(name = "verification_code")
	private String verificationCode;

	@Enumerated(EnumType.STRING)
	private EnumRole roles;

	@Column(name = "is_active")
	private Boolean isActive = false;

	@OneToOne(mappedBy = "user",cascade = CascadeType.ALL)
	@JoinColumn(name = "cart_id")
	private Cart cart;

	@Column(name = "address")
	private String addresses;

	@OneToMany(mappedBy = "user")
	private List<Payment> payments;

	@OneToMany(mappedBy = "user")
	private  List<Order> orders;

}
