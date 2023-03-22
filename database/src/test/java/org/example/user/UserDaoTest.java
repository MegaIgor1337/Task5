package org.example.user;

import org.example.product.Product;
import org.example.user.User;
import org.example.user.UserDao;
import org.example.user.UserDaoJson;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class UserDaoTest {
    UserDao userDao = new UserDaoJson();

    @Test
    public void testWriteToJson() {
        List<User> users = new ArrayList<>();
        List<Product> products = List.of(new Product(1L, "Fridge", 2, 14));
        users.add(new User(1L, "Игорь", "e5535", "erer@mail.ru", 12, products));
        users.add(new User(2L, "Андрей", "erere", "gfg@gmail.com", 34, new ArrayList<>()));
        users.add(new User(3L, "Аня", "gfg", "ere@mail.ru", 35, new ArrayList<>()));
        users.add(new User(4L, "Maxim", "45353", "dgdd@mail.ru", 11, new ArrayList<>()));
        users.add(new User(5L, "Влад", "rtrte", "gfgd@mail.ru", 23, new ArrayList<>()));
        users.add(new User(6L, "Инна", "dgdgd", "kfksfk@mail.ru", 22, new ArrayList<>()));
        users.add(new User(7L, "Владий", "gdgd", "dgdg@mail.ru", 42, new ArrayList<>()));
        users.add(new User(8L, "Иннокентий", "gddg", "dfdgd", 11, new ArrayList<>()));
        int length = users.size();
        userDao.writeUsers("D:\\JAVA\\enterpriseCourse\\Hm2\\Task2\\" +
                "database\\src\\main\\files\\users.json", users);
        List<User> usersFromJson = userDao.getUsers("D:\\JAVA\\enterpriseCourse\\Hm2\\" +
                "Task2\\database\\src\\main\\files\\users.json");
        for (int i = 0; i < length; i++) {
            assertEquals(users.get(i).getId(), usersFromJson.get(i).getId());
            assertEquals(users.get(i).getName(), usersFromJson.get(i).getName());
        }
    }
}