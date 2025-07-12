package hw02;

import lombok.*;
import lombok.experimental.Accessors;

public class LombokSample {
    @SneakyThrows
    public static void handleException() {
        throw new IndexOutOfBoundsException("Boom!");

//        generated code:
//        try {
//            throw new IndexOutOfBoundsException("Boom!");
//        } catch (Throwable t) {
//            throw lombok.Lombok.sneakyThrow(t);
//        }
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

//        generated code:
//        public Person(String name, Integer age, String email) {
//            this.name = name;
//            this.age = age;
//            this.email = email;
//        }
//        public String getName() {
//            return name;
//        }
//        public void setName(String name) {
//            this.name = name;
//        }
//        public Integer getAge() {
//            return age;
//        }
//        public void setAge(Integer age) {
//            this.age = age;
//        }
//        public String getEmail() {
//            return email;
//        }
//        public void setEmail(String email) {
//            this.email = email;
//        }
//        public String toString() {
//            return "Person(name=" + this.name + ", age=" + this.age + ", email=" + this.email + ")";
//        }
    }

    @RequiredArgsConstructor
    public static class Car {
        private final String brand;
        private final String model;
        private Integer year;

//        generated code:
//        public Car(String brand, String model) {
//            this.brand = brand;
//            this.model = model;
//        }
    }

    @AllArgsConstructor
    public static class Book {
        private String title;
        private String author;
        private Integer pages;
        private Float price;

//        generated code:
//        public Book(String title, String author, Integer pages, Float price) {
//            this.title = title;
//            this.author = author;
//            this.pages = pages;
//            this.price = price;
//        }
    }

    @Builder
    public static class House {
        private final String address;
        private final Integer rooms;
        private final Float area;
        private final Float price;
    }

//    generated code:
//    public static class HouseBuilder {
//        private String address;
//        private Integer rooms;
//        private Float area;
//        private Float price;
//
//        public HouseBuilder address(String address) {
//            this.address = address;
//            return this;
//        }
//        public HouseBuilder rooms(Integer rooms) {
//            this.rooms = rooms;
//            return this;
//        }
//        public HouseBuilder area(Float area) {
//            this.area = area;
//            return this;
//        }
//        public HouseBuilder price(Float price) {
//            this.price = price;
//            return this;
//        }
//        public House build() {
//            return new House(address, rooms, area, price);
//        }
//    }

    public static class Product {
        @Setter
        @Getter
        private String category;
        private String name;
        private Float price;

//        generated code:
//        public String getCategory() {
//            return category;
//        }
//        public void setCategory(String category) {
//            this.category = category;
//        }
    }

    @Setter
    @ToString
    @Accessors(chain = true)
    public static class Configuration {
        private String url;
        private String password;
        private String login;

//        generated code:
//        public Configuration setUrl(String url) {
//            this.url = url;
//            return this;
//        }
//        public Configuration setPassword(String password) {
//            this.password = password;
//            return this;
//        }
//        public Configuration setLogin(String login) {
//            this.login = login;
//            return this;
//        }
    }
}
