package ru.netology;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selectors.byText;
import static org.openqa.selenium.Keys.BACK_SPACE;
import static com.codeborne.selenide.Selenide.*;

public class CardDeliveryTest {
    private WebDriver driver;

    @BeforeAll
    static void setUpAll() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--headless");
        driver = new ChromeDriver(options);
        driver.get("http://localhost:9999");
    }

    @AfterEach
    void tearDown() {
        driver.quit();
        driver = null;
    }

    @Test
    void shouldSendSuccessfulRequest() {
        open("http://localhost:9999");
        // переменная для того, чтобы ввести дату +3 дня от текущей
        String neededDate = LocalDate.now().plusDays(3).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $("[data-test-id='city'] input").setValue("Орёл");
        $("[data-test-id='date'] input").doubleClick().sendKeys(BACK_SPACE); // очищаем поле
        $("[data-test-id='date'] input").setValue(neededDate); // вводим нужную дату
        $("[data-test-id='name'] input").setValue("Петров Петя");
        $("[data-test-id='phone'] input").setValue("+79102671142");
        $("[data-test-id='agreement'] .checkbox__box").click();
        $("button").click();
        $("[data-test-id='notification']")
                .shouldHave(Condition.text("Успешно! Встреча успешно забронирована на " + neededDate),
                        Duration.ofSeconds(15));

    }

    @Test
    void shouldSendSuccessfulRequestWithDoubleLastName() {
        open("http://localhost:9999");
        // переменная для того, чтобы ввести дату +3 дня от текущей
        String neededDate = LocalDate.now().plusDays(3).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $("[data-test-id='city'] input").setValue("Орёл");
        $("[data-test-id='date'] input").doubleClick().sendKeys(BACK_SPACE); // очищаем поле
        $("[data-test-id='date'] input").setValue(neededDate); // вводим нужную дату
        $("[data-test-id='name'] input").setValue("Петров-Иванов Петя");
        $("[data-test-id='phone'] input").setValue("+79102671142");
        $("[data-test-id='agreement'] .checkbox__box").click();
        $("button").click();
        $("[data-test-id='notification']")
                .shouldHave(Condition.text("Успешно! Встреча успешно забронирована на " + neededDate),
                        Duration.ofSeconds(15));

    }

    @Test
    void shouldSendSuccessfulRequestWithManyDays() {
        open("http://localhost:9999");
        // переменная для того, чтобы ввести дату +3 дня от текущей
        String neededDate = LocalDate.now().plusDays(365).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $("[data-test-id='city'] input").setValue("Орёл");
        $("[data-test-id='date'] input").doubleClick().sendKeys(BACK_SPACE); // очищаем поле
        $("[data-test-id='date'] input").setValue(neededDate); // вводим нужную дату
        $("[data-test-id='name'] input").setValue("Петров Петя");
        $("[data-test-id='phone'] input").setValue("+79102671142");
        $("[data-test-id='agreement'] .checkbox__box").click();
        $("button").click();
        $("[data-test-id='city'].input_invalid .input__sub")
                .shouldBe(Condition.visible)
                .shouldHave(Condition.text("Поле обязательно для заполнения"));

    }

    @Test
    void shouldNotSendRequestWithoutCity() {
        open("http://localhost:9999");
        // переменная для того, чтобы ввести дату +3 дня от текущей
        String neededDate = LocalDate.now().plusDays(3).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $("[data-test-id='date'] input").doubleClick().sendKeys(BACK_SPACE); // очищаем поле
        $("[data-test-id='date'] input").setValue(neededDate); // вводим нужную дату
        $("[data-test-id='name'] input").setValue("Петров Петя");
        $("[data-test-id='phone'] input").setValue("+79102671142");
        $("[data-test-id='agreement'] .checkbox__box").click();
        $("button").click();
        $("[data-test-id='city'].input_invalid .input__sub")
                .shouldBe(Condition.visible)
                .shouldHave(Condition.text("Доставка в выбранный город недоступна"));

    }

    @Test
    void shouldNotSendRequestWithoutDate() {
        open("http://localhost:9999");
        // переменная для того, чтобы ввести дату +3 дня от текущей
        String neededDate = LocalDate.now().plusDays(3).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $("[data-test-id='date'] input").doubleClick().sendKeys(BACK_SPACE); // очищаем поле
        $("[data-test-id='name'] input").setValue("Петров Петя");
        $("[data-test-id='phone'] input").setValue("+79102671142");
        $("[data-test-id='agreement'] .checkbox__box").click();
        $("button").click();
        $("[data-test-id='date'].input_invalid .input__sub")
                .shouldBe(Condition.visible)
                .shouldHave(Condition.text("Неверно введена дата"));

    }

    @Test
    void shouldNotSendRequestWithIncorrectDate() {
        open("http://localhost:9999");
        // переменная для того, чтобы ввести дату +3 дня от текущей
        String neededDate = LocalDate.now().plusDays(-1).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $("[data-test-id='date'] input").doubleClick().sendKeys(BACK_SPACE); // очищаем поле
        $("[data-test-id='date'] input").setValue(neededDate); // вводим нужную дату
        $("[data-test-id='name'] input").setValue("Петров Петя");
        $("[data-test-id='phone'] input").setValue("+79102671142");
        $("[data-test-id='agreement'] .checkbox__box").click();
        $("button").click();
        $("[data-test-id='date'].input_invalid .input__sub")
                .shouldBe(Condition.visible)
                .shouldHave(Condition.text("Заказ на выбранную дату невозможен"));

    }

    @Test
    void shouldNotSendRequestWithoutName() {
        open("http://localhost:9999");
        // переменная для того, чтобы ввести дату +3 дня от текущей
        String neededDate = LocalDate.now().plusDays(3).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $("[data-test-id='date'] input").doubleClick().sendKeys(BACK_SPACE); // очищаем поле
        $("[data-test-id='date'] input").setValue(neededDate); // вводим нужную дату
        $("[data-test-id='phone'] input").setValue("+79102671142");
        $("[data-test-id='agreement'] .checkbox__box").click();
        $("button").click();
        $("[data-test-id='name'].input_invalid .input__sub")
                .shouldBe(Condition.visible)
                .shouldHave(Condition.text("Поле обязательно для заполнения"));

    }

    @Test
    void shouldNotSendRequestWithIncorrectName() {
        open("http://localhost:9999");
        // переменная для того, чтобы ввести дату +3 дня от текущей
        String neededDate = LocalDate.now().plusDays(3).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $("[data-test-id='date'] input").doubleClick().sendKeys(BACK_SPACE); // очищаем поле
        $("[data-test-id='date'] input").setValue(neededDate); // вводим нужную дату
        $("[data-test-id='name'] input").setValue("Petrov Petya");
        $("[data-test-id='phone'] input").setValue("+79102671142");
        $("[data-test-id='agreement'] .checkbox__box").click();
        $("button").click();
        $("[data-test-id='name'].input_invalid .input__sub")
                .shouldBe(Condition.visible)
                .shouldHave(Condition.text("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));

    }

    @Test
    void shouldNotSendRequestWithoutPhone() {
        open("http://localhost:9999");
        // переменная для того, чтобы ввести дату +3 дня от текущей
        String neededDate = LocalDate.now().plusDays(3).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $("[data-test-id='date'] input").doubleClick().sendKeys(BACK_SPACE); // очищаем поле
        $("[data-test-id='date'] input").setValue(neededDate); // вводим нужную дату
        $("[data-test-id='name'] input").setValue("Петров Петя");
        $("[data-test-id='agreement'] .checkbox__box").click();
        $("button").click();
        $("[data-test-id='phone'].input_invalid .input__sub")
                .shouldBe(Condition.visible)
                .shouldHave(Condition.text("Поле обязательно для заполнения"));

    }

    @Test
    void shouldNotSendRequestWithIncorrectPhone() {
        open("http://localhost:9999");
        // переменная для того, чтобы ввести дату +3 дня от текущей
        String neededDate = LocalDate.now().plusDays(3).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $("[data-test-id='date'] input").doubleClick().sendKeys(BACK_SPACE); // очищаем поле
        $("[data-test-id='date'] input").setValue(neededDate); // вводим нужную дату
        $("[data-test-id='name'] input").setValue("Петров Петя");
        $("[data-test-id='phone'] input").setValue("+7-910-267-11-42");
        $("[data-test-id='agreement'] .checkbox__box").click();
        $("button").click();
        $("[data-test-id='phone'].input_invalid .input__sub")
                .shouldBe(Condition.visible)
                .shouldHave(Condition.text("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));

    }

    @Test
    void shouldNotSendRequestWithoutCheckBox() {
        open("http://localhost:9999");
        // переменная для того, чтобы ввести дату +3 дня от текущей
        String neededDate = LocalDate.now().plusDays(3).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $("[data-test-id='date'] input").doubleClick().sendKeys(BACK_SPACE); // очищаем поле
        $("[data-test-id='date'] input").setValue(neededDate); // вводим нужную дату
        $("[data-test-id='name'] input").setValue("Петров Петя");
        $("[data-test-id='phone'] input").setValue("+79102671142");
        $("button").click();
        $("[data-test-id='agreement'].input_invalid .checkbox__text")
                .shouldBe(Condition.visible)
                .shouldHave(Condition.text("Я соглашаюсь с условиями обработки и использования моих персональных данных"));

    }
}