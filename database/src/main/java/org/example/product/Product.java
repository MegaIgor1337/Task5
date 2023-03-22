package org.example.product;

public class Product {
    private long id;
    private String name;
    private int count;
    private int price;

    public Product(long id, String name, int count, int price) {
        this.name = name;
        this.count = count;
        this.id = id;
        this.price = price;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCount(int count) {
        this.count = count;
    }


    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public int getCount() {
        return count;
    }

}
