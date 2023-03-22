package org.example.product;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import exceptions.NoSuchProductException;
import exceptions.NoUniqueProductException;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProductDaoJson implements ProductDao {
    private static final String PATH_TO_FILE = "E:\\AAA\\JavaGuru\\Temp\\MegaIgor1\\database\\src\\main\\files\\products.json";

    public List<Product> getProducts(String path) {
        List<Product> products = new ArrayList<>();
        try {
            File file = new File(path);
            JsonElement fileElement = JsonParser.parseReader(new FileReader(file));
            JsonArray jsonArrayOfProducts = fileElement.getAsJsonArray();
            for (JsonElement product : jsonArrayOfProducts) {
                JsonObject productJson = product.getAsJsonObject();
                long id = productJson.get("id").getAsLong();
                String name = productJson.get("name").getAsString();
                int count = productJson.get("count").getAsInt();
                int price = productJson.get("price").getAsInt();
                products.add(new Product(id, name, count, price));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return products;
    }

    @Override
    public void writeProducts(String path, List<Product> products) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.writeValue(new File(path), products);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Optional<Product> getProductById(long id) throws NoSuchProductException {
        List<Product> products = getProducts(PATH_TO_FILE);
        if (products.stream().filter(it -> it.getId() == id).count() != 1) {
            throw new NoSuchProductException("No such product");
        } else {
            return products.stream().filter(it -> it.getId() == id).findFirst();
        }
    }

    @Override
    public void AddNewProduct(Product product) throws NoUniqueProductException {
        if (product == null) {
            throw new NullPointerException("Product is null");
        }
        List<Product> products = getProducts(PATH_TO_FILE);
        if (products.stream().noneMatch(it -> it.getId() == product.getId())) {
            products.add(product);
            writeProducts(PATH_TO_FILE, products);
        } else {
            throw new NoUniqueProductException("There are such product in file");
        }
    }

    @Override
    public void addQuantityToProduct(Product product) {
        List<Product> products = getProducts(PATH_TO_FILE);
        if (products.stream().filter(it -> it.getId() == product.getId()).count() == 1) {
            Product productFromJson = products.stream().
                    filter(it -> it.getId() == product.getId()).findFirst().get();
            productFromJson.setCount(productFromJson.getCount() + product.getCount());
        }
        writeProducts(PATH_TO_FILE, products);
    }

    public void reduceQuantityOfProduct(Product product) {
        List<Product> products = getProducts(PATH_TO_FILE);
        if (products.stream().filter(it -> it.getId() == product.getId()).count() == 1) {
            Product productFromJson = products.stream().
                    filter(it -> it.getId() == product.getId()).findFirst().get();
            productFromJson.setCount(productFromJson.getCount() - product.getCount());
        }
        writeProducts(PATH_TO_FILE, products);
    }
}
