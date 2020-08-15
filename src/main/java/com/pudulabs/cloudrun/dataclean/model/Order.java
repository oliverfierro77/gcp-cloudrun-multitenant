package com.pudulabs.cloudrun.dataclean.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.util.Date;

@AllArgsConstructor
@Table(name="order"
        ,schema="public"
)
@Entity
public class Order implements java.io.Serializable {

    public Order(){
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name="order_number")
    private String orderNumber;

    @Column(name="order_created_date")
    private Date orderCreatedDate;

    @Column(name="country_description")
    private Date orderCountryDescription;

}