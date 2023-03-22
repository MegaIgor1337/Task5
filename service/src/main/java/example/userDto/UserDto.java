package example.userDto;

import example.productDto.ProductDto;

import java.util.List;

public class UserDto {
    private final long id;
    private String name;

    private String password;
    private String email;
    private int age;

    public UserDto(long id, String name, String password, String email, int age, List<ProductDto> products) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.email = email;
        this.age = age;
        this.products = products;
    }

    private List<ProductDto> products;

    public void setAge(int age) {
        this.age = age;
    }

    public List<ProductDto> getProducts() {
        return products;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
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
        return "UserDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
