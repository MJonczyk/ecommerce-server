package com.ecommerceapp.ecommerceserver.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "feature")
public class Feature {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "feature_id")
    private Long id;

    @Column(unique = true)
    private String name;

    @ManyToMany(mappedBy = "features")
    @JsonIgnoreProperties("features")
    private List<Category> categories;
}
