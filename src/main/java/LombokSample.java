import lombok.*;
import lombok.experimental.Accessors;

//TODO: comments on what code is generated
public class LombokSample {
    @SneakyThrows
    public static void handleException() {
        throw new IndexOutOfBoundsException("Boom!");
    }

    public static void main(String[] args) {
        Person pers = new Person();
        pers.setName("Anna");
        pers.setAge(24);
        pers.setEmail("a@sberbank.ru");
        System.out.println(pers);

        Car car = new Car("ford", "fiesta");
        System.out.printf("Car is %s %s%n", car.brand, car.model);

        Book book = new Book("War and Piece", "Leo Tolstoy", 1928, 7400f);
        System.out.printf("Book is %s, %s, %d, %.1f%n", book.title, book.author, book.pages, book.price);

        House house = House.builder().price(24500.2f).rooms(3).area(23.4f).address("Kutuzovskiy, 32").build();
        System.out.println(house.address);

        Product product = new Product();
        product.setCategory("fruits");
        System.out.println(product.getCategory());

        Configuration configuration = new Configuration().setUrl("https://test.com").setLogin("admin").setPassword("admin");
        System.out.println(configuration);

        handleException();
    }

    @Data
    public static class Person {
        private String name;
        private Integer age;
        private String email;
    }

    @RequiredArgsConstructor
    public static class Car {
        private final String brand;
        private final String model;
    }

    @AllArgsConstructor
    public static class Book {
        private String title;
        private String author;
        private Integer pages;
        private Float price;
    }

    @Builder
    public static class House {
        private final String address;
        private final Integer rooms;
        private final Float area;
        private final Float price;
    }

    public static class Product {
        @Setter
        @Getter
        private String category;
        private String name;
        private Float price;
    }

    @Setter
    @ToString
    @Accessors(chain = true)
    public static class Configuration {
        private String url;
        private String password;
        private String login;
    }
}
