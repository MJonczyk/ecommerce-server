package com.ecommerceapp.ecommerceserver.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "product_detail")
public class ProductDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "product_detail_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id")
    @JsonIgnoreProperties("details")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "feature_id")
    private Feature feature;

    private String value;
}
