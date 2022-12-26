package ru.netology;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.manager.ProductManager;
import ru.netology.repository.ProductRepository;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

public class ProductManagerAndRepositoryTest {


    private ProductRepository repository = new ProductRepository();
    private ProductManager manager = new ProductManager(repository);

    private Product book1 = new Book(1, "Война и Мир", 600, "Лев Толстой");
    private Product book2 = new Book(2, "Тихий Дон", 500, "Михаил Шолохов");
    private Product book3 = new Book(3, "Преступление и Наказание", 450, "Фёдор Достоевский");
    private Product book4 = new Book(4, "Идиот", 400, "Фёдор Достоевский");
    private Product book5 = new Book(5, "Идиот", 400, "Фёдор Достоевский");

    private Product smartphone1 = new Smartphone(6, "Note10Pro", 28000, "Redmi");
    private Product smartphone2 = new Smartphone(7, "Iphone13", 70000, "Apple");
    private Product smartphone3 = new Smartphone(8, "Honor 10", 25000, "Huawei");
    private Product smartphone4 = new Smartphone(9, "Honor 8", 20000, "Huawei");
    private Product smartphone5 = new Smartphone(10, "Honor 8", 20000, "Huawei");


    @BeforeEach
    public void setup() {
        manager.add(book1);
        manager.add(book2);
        manager.add(book3);
        manager.add(book4);
        manager.add(book5);
        manager.add(smartphone1);
        manager.add(smartphone2);
        manager.add(smartphone3);
        manager.add(smartphone4);
        manager.add(smartphone5);
    }

    @Test
    public void ShouldFindAll() {
        Product[] expected = {book1, book2, book3, book4, book5, smartphone1, smartphone2, smartphone3, smartphone4, smartphone5};
        Product[] actual = repository.findAll();
        Assertions.assertArrayEquals(expected, actual);
    }

    @Test
    public void shouldRemoveProductID() {
        repository.removeById(smartphone2.getId());
        Product[] expected = {book1, book2, book3, book4, book5, smartphone1, smartphone3, smartphone4, smartphone5};
        Product[] actual = repository.findAll();
        Assertions.assertArrayEquals(expected, actual);
    }

    @Test
    public void shouldSearchBookByName() {
        String name = "Война и Мир";
        Product[] expected = {book1};
        Product[] actual = manager.searchBy(name);
        Assertions.assertArrayEquals(expected, actual);
    }

    @Test
    public void shouldNotSearchBookByNameIfFalse() {
        String name = "Игра Престолов";
        Product[] expected = {};
        Product[] actual = manager.searchBy(name);
        assertArrayEquals(expected, actual);
    }

    @Test
    public void shouldSearchBookWithSameName() {
        String name = "Идиот";
        Product[] expected = {book4, book5};
        Product[] actual = manager.searchBy(name);
        Assertions.assertArrayEquals(expected, actual);
    }

    @Test
    public void shouldSearchBookByAuthorName() {
        String authorName = "Михаил Шолохов";
        Product[] expected = {book2};
        Product[] actual = manager.searchBy(authorName);
        Assertions.assertArrayEquals(expected, actual);
    }

    @Test
    public void shouldSearchBookByAuthorNameIfFalse() {
        String authorName = "Александр Пушкин";
        Product[] expected = {};
        Product[] actual = manager.searchBy(authorName);
        Assertions.assertArrayEquals(expected, actual);
    }

    @Test
    public void shouldSearchBooksWithSameAuthorName() {
        String authorName = "Фёдор Достоевский";
        Product[] expected = {book3, book4, book5};
        Product[] actual = manager.searchBy(authorName);
        Assertions.assertArrayEquals(expected, actual);
    }

    @Test
    public void shouldSearchSmartphoneByName() {
        String name = "Note10Pro";
        Product[] expected = {smartphone1};
        Product[] actual = manager.searchBy(name);
        Assertions.assertArrayEquals(expected, actual);
    }

    @Test
    public void shouldSearchSmartphoneByNameIfFalse() {
        String name = "Iphone 10";
        Product[] expected = {};
        Product[] actual = manager.searchBy(name);
        Assertions.assertArrayEquals(expected, actual);
    }

    @Test
    public void shouldSearchSmartphoneWithSameName() {
        String name = "Honor 8";
        Product[] expected = {smartphone4, smartphone5};
        Product[] actual = manager.searchBy(name);
        Assertions.assertArrayEquals(expected, actual);
    }

    @Test
    public void shouldSearchSmartphoneByBrand() {
        String brand = "Apple";
        Product[] expected = {smartphone2};
        Product[] actual = manager.searchBy(brand);
        Assertions.assertArrayEquals(expected, actual);
    }

    @Test
    public void shouldSearchSmartphoneByBrandIfFalse() {
        String brand = "Xiaomi";
        Product[] expected = {};
        Product[] actual = manager.searchBy(brand);
        Assertions.assertArrayEquals(expected, actual);
    }

    @Test
    public void shouldSearchSmartphonesWithSameBrand() {
        String brand = "Huawei";
        Product[] expected = {smartphone3, smartphone4, smartphone5};
        Product[] actual = manager.searchBy(brand);
        Assertions.assertArrayEquals(expected, actual);
    }


    //Исключительные ситуации и их обработка. Тестирование исключений

    @Test
    public void shouldRemoveById() {
        int id = 5;
        repository.removeById(id);
        Product[] expected = {book1, book2, book3, book4, smartphone1, smartphone2, smartphone3, smartphone4, smartphone5};
        Product[] actual = repository.findAll();
        Assertions.assertArrayEquals(expected, actual);
    }

    @Test
    public void shouldNotRemoveByIdIfFalse() {
        int id = 11;
        Assertions.assertThrows(NotFoundException.class, () -> repository.removeById(id));
    }

    @Test
    public void shouldReturnNull() {
        int id = 11;
        repository.findById(id);
        Product expected = null;
        Product actual = repository.findById(id);
        Assertions.assertEquals(expected, actual);
    }
}
