package com.example.homemarket.entities;

import com.example.homemarket.utils.EnumPaymentMethod;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;
import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "payment")
public class Payment implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "paymentID", nullable = false)
    private Integer paymentID;

    private Float totalValue;

    private Date paymentDate;

    @Enumerated(EnumType.STRING)
    private EnumPaymentMethod paymentMethod;

    @ManyToOne
    @JoinColumn(name = "userID")
    private User user;


    @ManyToMany
    @JoinTable(name = "productOnPayment",
            joinColumns = @JoinColumn(name = "paymentID"),
            inverseJoinColumns = @JoinColumn(name = "productID"))
    private List<Product> productList;
}
