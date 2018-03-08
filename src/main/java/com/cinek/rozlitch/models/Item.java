package com.cinek.rozlitch.models;

import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;

/**
 * Created by Cinek on 14.11.2017.
 */
@Entity
public class Item {
    @Id
    @GeneratedValue
    private Long id;
    @NotBlank
    private String category;
    @DecimalMin("0.0")
    private Double price;
    @NotBlank
    private String name;
    @Min(1)
    private Integer quantity;


    public double getValue() {return price*quantity;}
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
