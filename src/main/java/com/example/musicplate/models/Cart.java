package com.example.musicplate.models;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "carts")
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name="user_id")
    private Long userId;
    @ManyToMany

    @JoinTable(name = "carts_plates",
            joinColumns = @JoinColumn(name = "cart_id"),
            inverseJoinColumns = @JoinColumn(name = "plate_id"))
    private List<Plate> plates = new ArrayList<>();

    public Cart() {
    }
    public Cart(Long userId, Long plateId) {
        this.userId = userId;
        this.plates = new ArrayList<>();
        Plate plate = new Plate();
        plate.setId(plateId);
        this.plates.add(plate);
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }



    public List<Plate> getPlates() {
        return plates;
    }

    public void setPlates(List<Plate> plates) {
        this.plates = plates;
    }

    @Override
    public String toString() {
        return "Cart{" +
                "id=" + id +
                ", user=" + userId +
                ", plates=" + plates +
                '}';
    }
}
