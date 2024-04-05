package com.projeto_estoque.model;

import java.math.BigDecimal;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Sale {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String productName;
    private String categories;
    private Integer quantity;
    private BigDecimal costs;

    public Sale() {
    }

    public Sale(String productName, String categories, Integer quantity, BigDecimal costs) {
        this.productName = productName;
        this.categories = categories;
        this.quantity = quantity;
        this.costs = costs;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getProductName() {
        return productName;
    }
    public void setProductName(String productName) {
        this.productName = productName;
    }
    public String getCategories() {
        return categories;
    }
    public void setCategories(String categories) {
        this.categories = categories;
    }
    public Integer getQuantity() {
        return quantity;
    }
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
    public BigDecimal getCosts() {
        return costs;
    }
    public void setCosts(BigDecimal costs) {
        this.costs = costs;
    }

    @Override
    public String toString() {
        return "Sale [id=" + id + ", productName=" + productName + ", categories=" + categories + ", quantity="
                + quantity + ", costs=" + costs + "]";
    }
 
}
