package org.example.product;

import exceptions.NoSuchProductException;
import exceptions.NoUniqueProductException;

import java.util.List;
import java.util.Optional;

public interface ProductDao {
    List<Product> getProducts(String path);

    void writeProducts(String path, List<Product> products);

    Optional<Product> getProductById(long id) throws NoSuchProductException;

    void AddNewProduct(Product product) throws NoUniqueProductException;

    void addQuantityToProduct(Product product);

    void reduceQuantityOfProduct(Product product);
}
