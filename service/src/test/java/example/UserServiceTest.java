package example;

import example.productDto.ProductDto;
import example.userDto.UserDto;
import exceptions.NoSuchUserException;
import org.example.product.Product;
import org.example.product.ProductDao;
import org.example.product.ProductDaoJson;
import org.example.user.User;
import org.example.user.UserDao;
import org.example.user.UserDaoJson;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

public class UserServiceTest {

    private static final String PATH_TO_FILE_USERS = "E:\\AAA\\JavaGuru\\Temp\\MegaIgor1\\database\\src\\main\\files\\users.json";
    private static final String PATH_TO_FILE_PRODUCTS = "E:\\AAA\\JavaGuru\\Temp\\MegaIgor1\\database\\src\\main\\files\\products.json";
    private Service userService = new Service();
    private UserDao userDao = new UserDaoJson();
    private ProductDao productDao = new ProductDaoJson();

    @Test
    public void testGetUser() throws NoSuchUserException {
        Optional<UserDto> user = userService.getUser(2L);
        assertEquals("Андрей", user.get().getName());
    }

    @Test
    public void testGetUsers() {
        List<UserDto> users = userService.getUsers();
        List<User> usersFromJson = userDao.getUsers(PATH_TO_FILE_USERS);
        for (int i = 0; i < users.size(); i++) {
            assertEquals(users.get(i).getId(), usersFromJson.get(i).getId());
            assertEquals(users.get(i).getName(), usersFromJson.get(i).getName());
            assertEquals(users.get(i).getPassword(), usersFromJson.get(i).getPassword());
            assertEquals(users.get(i).getEmail(), usersFromJson.get(i).getEmail());
            assertEquals(users.get(i).getAge(), usersFromJson.get(i).getAge());
        }
    }

    @Test
    public void testChangeName() throws NoSuchUserException {
        userService.changeName(3L, "Валерий");
        List<User> users = userDao.getUsers(PATH_TO_FILE_USERS);
        assertEquals("Валерий", users.get(2).getName());
    }

    @Test
    public void testAddNewUser() {
        userService.addNewUser("djfjdf", "jgfjgjgj", "djf@mail.ru", 23);
        List<User> users = userDao.getUsers(PATH_TO_FILE_USERS);
        assertEquals(9, users.get(users.size() - 1).getId());
        assertEquals("djfjdf", users.get(users.size() - 1).getName());
        assertEquals("jgfjgjgj", users.get(users.size() - 1).getPassword());
        assertEquals("djf@mail.ru", users.get(users.size() - 1).getEmail());
        assertEquals(23, users.get(users.size() - 1).getAge());
    }

    @Test
    public void testWriteUsers() {
        List<UserDto> users = userService.getUsers();
        userService.writeUsers(PATH_TO_FILE_USERS, users);
        List<UserDto> usersDto = userService.getUsers();
        for (int i = 0; i < users.size(); i++) {
            assertEquals(users.get(i).getId(), usersDto.get(i).getId());
            assertEquals(users.get(i).getName(), usersDto.get(i).getName());
            assertEquals(users.get(i).getPassword(), usersDto.get(i).getPassword());
            assertEquals(users.get(i).getEmail(), usersDto.get(i).getEmail());
            assertEquals(users.get(i).getAge(), usersDto.get(i).getAge());
        }
    }

    @Test
    public void testReplaceUser() {
        UserDto newUser = new UserDto(1L, "Геннадий",
                "dfdgd", "gdgkd@mail.ru", 33, new ArrayList<>());
        userService.replaceUser(newUser);
        List<UserDto> users = userService.getUsers();
        assertEquals(1L, users.get(0).getId());

    }

    @Test
    public void testGetProducts() {
        List<ProductDto> products = userService.getProducts();
        List<Product> productsFromJson = productDao.getProducts(PATH_TO_FILE_PRODUCTS);
        for (int i = 0; i < productsFromJson.size(); i++) {
            assertEquals(productsFromJson.get(i).getId(), products.get(i).getId());
            assertEquals(productsFromJson.get(i).getName(), products.get(i).getName());
            assertEquals(productsFromJson.get(i).getCount(), products.get(i).getCount());
            assertEquals(productsFromJson.get(i).getPrice(), products.get(i).getPrice());
        }
    }

    @Test
    public void testValidateProduct() {
        assertTrue(userService.validateProduct("Fridge", 1));
    }

}