package com.example.musicplate.models;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "plates")
public class Plate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "preview")
    private String preview;

    @Column(name = "description")
    private String description;

    @Column(name = "price")
    private int price;

    @ManyToMany(mappedBy = "plates")
    private List<Cart> carts = new ArrayList<>();
    public Plate() {
    }

    public Plate(Long id, String name, String preview, String description, int price) {
        this.id = id;
        this.name = name;
        this.preview = preview;
        this.description = description;
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPreview() {
        return preview;
    }

    public void setPreview(String preview) {
        this.preview = preview;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}


