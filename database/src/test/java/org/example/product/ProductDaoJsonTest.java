package org.example.product;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class ProductDaoJsonTest {
    private static final String PATH_TO_FILE = "E:\\AAA\\JavaGuru\\Temp\\MegaIgor1\\database\\src\\main\\files\\products.json";
    private final ProductDao productDao = new ProductDaoJson();

    @Test
    public void testWriteProducts() {
        List<Product> products = new ArrayList<>();
        products.add(new Product(1L, "Fridge", 12, 14));
        products.add(new Product(2L, "Microwave", 10, 10));
        products.add(new Product(3L, "Coffee Machine", 12, 15));
        products.add(new Product(4L, "Oven", 9, 14));
        products.add(new Product(5L, "Table", 4, 6));
        products.add(new Product(6L, "Chair", 20, 3));
        productDao.writeProducts(PATH_TO_FILE, products);
        List<Product> productsFromDao = productDao.getProducts(PATH_TO_FILE);
        for (int i = 0; i < products.size(); i++) {
            assertEquals(products.get(i).getId(), productsFromDao.get(i).getId());
            assertEquals(products.get(i).getName(), productsFromDao.get(i).getName());
            assertEquals(products.get(i).getCount(), productsFromDao.get(i).getCount());
            assertEquals(products.get(i).getPrice(), productsFromDao.get(i).getPrice());
        }
    }
}