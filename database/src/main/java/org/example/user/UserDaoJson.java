package org.example.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import exceptions.NoSuchUserException;
import org.example.product.Product;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserDaoJson implements UserDao {
    private static final String PATH_TO_FILE = "E:\\AAA\\JavaGuru\\Temp\\MegaIgo" +
            "r1\\database\\src\\main\\files\\users.json";

    public Optional<User> findById(long id) throws NoSuchUserException {
        List<User> users = getUsers(PATH_TO_FILE);
        Optional<User> user;
        user = users.stream().filter(it -> it.getId() == id).findFirst();
        if (user.isEmpty()) {
            throw new NoSuchUserException("There are no such user in file");
        }
        return user;
    }

    public void AddNewUser(String path, User user) {
        if (path == null || user == null) {
            throw new NullPointerException("Path or User is null");
        }
        List<User> users = getUsers(PATH_TO_FILE);
        users.add(user);
        writeUsers(PATH_TO_FILE, users);
    }

    public void writeUsers(String path, List<User> users) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.writeValue(new File(path), users);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<User> getUsers(String path) {
        List<User> users = new ArrayList<>();
        try {
            File file = new File(path);
            JsonElement fileElement = JsonParser.parseReader(new FileReader(file));
            JsonArray jsonArrayOfUsers = fileElement.getAsJsonArray();
            for (JsonElement user : jsonArrayOfUsers) {
                JsonObject userJson = user.getAsJsonObject();
                long id = userJson.get("id").getAsLong();
                String name = userJson.get("name").getAsString();
                String password = userJson.get("password").getAsString();
                String email = userJson.get("email").getAsString();
                int age = userJson.get("age").getAsInt();
                List<Product> products = new ArrayList<>();
                JsonArray jsonArrayOfProducts = userJson.get("products").getAsJsonArray();
                for (JsonElement productJson : jsonArrayOfProducts) {
                    JsonObject productJsonObject = productJson.getAsJsonObject();
                    long idOfProduct = productJsonObject.get("id").getAsLong();
                    String nameOfProduct = productJsonObject.get("name").getAsString();
                    int count = productJsonObject.get("count").getAsInt();
                    int price = productJsonObject.get("price").getAsInt();
                    Product product = new Product(idOfProduct, nameOfProduct, count, price);
                    products.add(product);
                }
                users.add(new User(id, name, password, email, age, products));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return users;
    }

    public void changeNameById(long id, String newName) {
        List<User> users = getUsers(PATH_TO_FILE);
        try {
            if (users.stream().noneMatch(it -> it.getId() == id)) {
                throw new NoSuchUserException("There are no such user in file");
            }
        } catch (NoSuchUserException e) {
            e.printStackTrace();
        }
        for (User user : users) {
            if (user.getId() == id) {
                user.setName(newName);
                break;
            }
        }
        writeUsers(PATH_TO_FILE, users);
    }
}
