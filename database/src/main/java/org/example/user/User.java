package org.example.user;

import org.example.product.Product;

import java.util.List;

public class User {
    private long id;
    private String name;
    private String password;
    private String email;
    private int age;
    private List<Product> products;

    public List<Product> getProducts() {
        return products;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public int getAge() {
        return age;
    }

    public User(long id, String name, String password, String email, int age, List<Product> products) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.email = email;
        this.age = age;
        this.products = products;
    }

    public String getName() {
        return name;
    }

    public long getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
