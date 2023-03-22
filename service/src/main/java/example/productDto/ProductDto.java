package example.productDto;

public class ProductDto {
    private long id;
    private String name;
    private int count;
    private int price;

    public ProductDto(long id, String name, int count, int price) {
        this.name = name;
        this.count = count;
        this.id = id;
        this.price = price;
    }

    public int getPrice() {
        return price;
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


    public void setPrice(int price) {
        this.price = price;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getCount() {
        return count;
    }


    @Override
    public String toString() {
        return "Product: {" +
                "id=" + id +
                ", name='" + name + "\n" +
                ", count=" + count + "\n" +
                ", paid=" + price * count + "\n" +
                '}';
    }


}
