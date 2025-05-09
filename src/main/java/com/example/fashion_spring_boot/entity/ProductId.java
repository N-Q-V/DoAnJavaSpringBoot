package com.example.fashion_spring_boot.entity;

import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class ProductId implements Serializable {
    private Long id;
    private String language;

    // Constructors
    public ProductId() {
    }

    public ProductId(Long id, String language) {
        this.id = id;
        this.language = language;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        ProductId productId = (ProductId) o;
        return Objects.equals(id, productId.id) && Objects.equals(language, productId.language);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, language);
    }
}
