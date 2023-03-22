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

import java.util.*;
import java.util.stream.Collectors;

public class Service {
    private final String PATH_TO_FILE_USERS = "E:\\AAA\\JavaGuru\\Temp\\MegaIgor1\\database\\src\\main\\files\\users.json";
    private final String PATH_TO_FILE_PRODUCTS = "E:\\AAA\\JavaGuru\\Temp\\MegaIgor1\\database\\src\\main\\files\\products.json";
    private final UserDao userDao = new UserDaoJson();
    private final ProductDao productDao = new ProductDaoJson();

    public List<ProductDto> getProductsByIdForUserDto(long id) {
        return userDao.getUsers(PATH_TO_FILE_USERS).stream().
                filter(it -> it.getId() == id).findFirst().
                get().getProducts().stream().map(it -> new ProductDto(it.getId(),
                        it.getName(), it.getCount(), it.getPrice())).collect(Collectors.toList());
    }

    public List<Product> getProductsForUser(UserDto userDto) {
        return userDto.getProducts().stream().map(it -> new Product(it.getId(),
                it.getName(), it.getCount(), it.getPrice())).collect(Collectors.toList());
    }

    public void writeUsers(String pathToFile, List<UserDto> users) {
        List<User> userList = users.stream().map(it -> new User(it.getId(),
                it.getName(), it.getPassword(), it.getEmail(), it.getAge(),
                getProductsForUser(it))).toList();
        userDao.writeUsers(pathToFile, userList);
    }

    public Optional<UserDto> getUser(long id) throws NoSuchUserException {
        return userDao.findById(id).map(it -> new UserDto(it.getId(), it.getName(),
                it.getPassword(), it.getEmail(), it.getAge(), getProductsByIdForUserDto(it.getId())));
    }

    public List<UserDto> getUsers() {
        return userDao.getUsers(PATH_TO_FILE_USERS).
                stream().map(it -> new UserDto(it.getId(), it.getName(), it.getPassword(),
                        it.getEmail(), it.getAge(), getProductsByIdForUserDto(it.getId()))).toList();
    }

    public void changeName(long id, String newName) throws NoSuchUserException {
        userDao.changeNameById(id, newName);
    }


    public UserDto findUser(String name, String password) {
        if (getUsers().stream().filter(it -> it.getName().equals(name)
                && it.getPassword().equals(password)).count() != 1) {
            return null;
        } else {
            return getUsers().stream().filter(it -> it.getName().equals(name)
                    && it.getPassword().equals(password)).findFirst().get();
        }
    }

    public UserDto findUser(String name) {
        if (getUsers().stream().filter(it -> it.getName().equals(name)).count() != 1) {
            return null;
        } else {
            return getUsers().stream().filter(it -> it.getName().equals(name)).findFirst().get();
        }
    }

    public void addNewUser(String name, String password, String email, int age) {
        List<User> users = userDao.getUsers(PATH_TO_FILE_USERS);
        users.add(new User(findLastId() + 1, name, password, email, age, new ArrayList<>()));
        userDao.writeUsers(PATH_TO_FILE_USERS, users);
    }

    public void replaceUser(UserDto newUser) {
        List<UserDto> users = getUsers();
        users.stream().map(it -> {
            if (it.getId() == newUser.getId()) {
                it.setName(newUser.getName());
                it.setPassword(newUser.getPassword());
                it.setEmail(newUser.getEmail());
                it.setAge(newUser.getAge());
            }
            return null;
        }).collect(Collectors.toList());
        writeUsers(PATH_TO_FILE_USERS, users);
    }

    public long findLastId() {
        List<User> users = userDao.getUsers(PATH_TO_FILE_USERS);
        return users.get(users.size() - 1).getId();
    }

    public boolean validateName(String newName) {
        List<UserDto> users = getUsers();
        return users.stream().noneMatch(it -> it.getName().equals(newName));
    }

    public boolean validatePassword(String password) {
        String passwordRegex = "^(?=.*[A-Z])(?=.*\\d)[A-Za-z\\d]{8,}$";
        return password.matches(passwordRegex);
    }

    public List<ProductDto> getProducts() {
        return productDao.getProducts(PATH_TO_FILE_PRODUCTS).stream().
                map(it -> new ProductDto(it.getId(), it.getName(),
                        it.getCount(), it.getPrice())).toList();
    }


    public boolean validateProduct(String name, int count) {
        return getProducts().stream().filter(it -> it.getName().
                equalsIgnoreCase(name) && it.getCount() >= count).count() == 1;
    }

    public void addProductToUser(UserDto user, ProductDto product) {
        List<UserDto> users = getUsers();
        productDao.reduceQuantityOfProduct(new Product(product.getId(),
                product.getName(), product.getCount(), product.getPrice()));
        users.stream().filter(it -> it.getId() == user.getId()).
                map(it -> it.getProducts().add(product)).collect(Collectors.toList());
        writeUsers(PATH_TO_FILE_USERS, users);
        user.getProducts().add(product);
    }

    public ProductDto getProductForUser(String name, int count) {
        if (validateProduct(name, count)) {
            ProductDto product = getProducts().stream().filter(it -> it.getName().equalsIgnoreCase(name)
                    && it.getCount() >= count).findFirst().get();
            return new ProductDto(product.getId(), name, count, product.getPrice());
        } else {
            return null;
        }
    }

    public int getPrice(String name, int count) {
        return getProducts().stream().
                filter(it -> it.getName().equalsIgnoreCase(name)).findFirst().get().getPrice() * count;
    }
}
