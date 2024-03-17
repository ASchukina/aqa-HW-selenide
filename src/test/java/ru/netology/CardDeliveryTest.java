package ru.netology;

import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selectors;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;
import static org.openqa.selenium.Keys.BACK_SPACE;

public class CardDeliveryTest {
    private WebDriver driver;

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
        $(byText("Забронировать")).click();
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
        $(byText("Забронировать")).click();
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
        $("[data-test-id='name'] input").setValue("Петров-Иванов Петя");
        $("[data-test-id='phone'] input").setValue("+79102671142");
        $("[data-test-id='agreement'] .checkbox__box").click();
        $(byText("Забронировать")).click();
        $("[data-test-id='notification']")
                .shouldHave(Condition.text("Успешно! Встреча успешно забронирована на " + neededDate),
                        Duration.ofSeconds(15));

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
        $(byText("Забронировать")).click();
        $("[data-test-id='city'].input_invalid .input__sub")
                .shouldBe(visible)
                .shouldHave(Condition.text("Поле обязательно для заполнения"));

    }

    @Test
    void shouldNotSendRequestWithIncorrectCity() {
        open("http://localhost:9999");
        // переменная для того, чтобы ввести дату +3 дня от текущей
        String neededDate = LocalDate.now().plusDays(3).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $("[data-test-id='city'] input").setValue("Нью-Йорк");
        $("[data-test-id='date'] input").doubleClick().sendKeys(BACK_SPACE); // очищаем поле
        $("[data-test-id='date'] input").setValue(neededDate); // вводим нужную дату
        $("[data-test-id='name'] input").setValue("Петров Петя");
        $("[data-test-id='phone'] input").setValue("+79102671142");
        $("[data-test-id='agreement'] .checkbox__box").click();
        $(byText("Забронировать")).click();
        $("[data-test-id='city'].input_invalid .input__sub")
                .shouldBe(visible)
                .shouldHave(Condition.text("Доставка в выбранный город недоступна"));

    }

    @Test
    void shouldNotSendRequestWithoutDate() {
        open("http://localhost:9999");
        // переменная для того, чтобы ввести дату +3 дня от текущей
        String neededDate = LocalDate.now().plusDays(3).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $("[data-test-id='city'] input").setValue("Орёл");
        $("[data-test-id='date'] input").doubleClick().sendKeys(BACK_SPACE); // очищаем поле
        $("[data-test-id='name'] input").setValue("Петров Петя");
        $("[data-test-id='phone'] input").setValue("+79102671142");
        $("[data-test-id='agreement'] .checkbox__box").click();
        $(byText("Забронировать")).click();
        $("[data-test-id='date'] .input_invalid .input__sub")
                .shouldBe(visible)
                .shouldHave(text("Неверно введена дата"));

    }

    @Test
    void shouldNotSendRequestWithIncorrectDate() {
        open("http://localhost:9999");
        // переменная для того, чтобы ввести дату +3 дня от текущей
        String neededDate = LocalDate.now().plusDays(-1).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $("[data-test-id='city'] input").setValue("Орёл");
        $("[data-test-id='date'] input").doubleClick().sendKeys(BACK_SPACE); // очищаем поле
        $("[data-test-id='date'] input").setValue(neededDate); // вводим нужную дату
        $("[data-test-id='name'] input").setValue("Петров Петя");
        $("[data-test-id='phone'] input").setValue("+79102671142");
        $("[data-test-id='agreement'] .checkbox__box").click();
        $(byText("Забронировать")).click();
        $("[data-test-id='date'] .input_invalid .input__sub")
                .shouldBe(visible)
                .shouldHave(text("Заказ на выбранную дату невозможен"));

    }

    @Test
    void shouldNotSendRequestWithoutName() {
        open("http://localhost:9999");
        // переменная для того, чтобы ввести дату +3 дня от текущей
        String neededDate = LocalDate.now().plusDays(3).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $("[data-test-id='city'] input").setValue("Орёл");
        $("[data-test-id='date'] input").doubleClick().sendKeys(BACK_SPACE); // очищаем поле
        $("[data-test-id='date'] input").setValue(neededDate); // вводим нужную дату
        $("[data-test-id='name'] input").setValue("");
        $("[data-test-id='phone'] input").setValue("+79102671142");
        $("[data-test-id='agreement'] .checkbox__box").click();
        $(byText("Забронировать")).click();
        $("[data-test-id='name'] .input__inner .input__sub")
                .shouldBe(visible)
                .shouldHave(text("Поле обязательно для заполнения"));

    }

    @Test
    void shouldNotSendRequestWithIncorrectName() {
        open("http://localhost:9999");
        // переменная для того, чтобы ввести дату +3 дня от текущей
        String neededDate = LocalDate.now().plusDays(3).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $("[data-test-id='city'] input").setValue("Орёл");
        $("[data-test-id='date'] input").doubleClick().sendKeys(BACK_SPACE); // очищаем поле
        $("[data-test-id='date'] input").setValue(neededDate); // вводим нужную дату
        $("[data-test-id='name'] input").setValue("Petrov Petya");
        $("[data-test-id='phone'] input").setValue("+79102671142");
        $("[data-test-id='agreement'] .checkbox__box").click();
        $(byText("Забронировать")).click();
        $("[data-test-id='name'] .input__inner .input__sub")
                .shouldBe(visible)
                .shouldHave(text("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));

    }

    @Test
    void shouldNotSendRequestWithoutPhone() {
        open("http://localhost:9999");
        // переменная для того, чтобы ввести дату +3 дня от текущей
        String neededDate = LocalDate.now().plusDays(3).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $("[data-test-id='city'] input").setValue("Орёл");
        $("[data-test-id='date'] input").doubleClick().sendKeys(BACK_SPACE); // очищаем поле
        $("[data-test-id='date'] input").setValue(neededDate); // вводим нужную дату
        $("[data-test-id='name'] input").setValue("Петров Петя");
        $("[data-test-id='phone'] input").setValue("");
        $("[data-test-id='agreement'] .checkbox__box").click();
        $(byText("Забронировать")).click();
        $("[data-test-id='phone'] .input__inner .input__sub")
                .shouldBe(visible)
                .shouldHave(text("Поле обязательно для заполнения"));

    }

    @Test
    void shouldNotSendRequestWithIncorrectPhone() {
        open("http://localhost:9999");
        // переменная для того, чтобы ввести дату +3 дня от текущей
        String neededDate = LocalDate.now().plusDays(3).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $("[data-test-id='city'] input").setValue("Орёл");
        $("[data-test-id='date'] input").doubleClick().sendKeys(BACK_SPACE); // очищаем поле
        $("[data-test-id='date'] input").setValue(neededDate); // вводим нужную дату
        $("[data-test-id='name'] input").setValue("Петров Петя");
        $("[data-test-id='phone'] input").setValue("+7-910-267-11-42");
        $("[data-test-id='agreement'] .checkbox__box").click();
        $(byText("Забронировать")).click();
        $("[data-test-id='phone'] .input__inner .input__sub")
                .shouldBe(visible)
                .shouldHave(text("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));

    }

    @Test
    void shouldNotSendRequestWithoutCheckBox() {
        open("http://localhost:9999");
        // переменная для того, чтобы ввести дату +3 дня от текущей
        String neededDate = LocalDate.now().plusDays(3).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $("[data-test-id='city'] input").setValue("Орёл");
        $("[data-test-id='date'] input").doubleClick().sendKeys(BACK_SPACE); // очищаем поле
        $("[data-test-id='date'] input").setValue(neededDate); // вводим нужную дату
        $("[data-test-id='name'] input").setValue("Петров Петя");
        $("[data-test-id='phone'] input").setValue("+79102671142");
        $(byText("Забронировать")).click();
        $("[data-test-id='agreement'] .checkbox__text")
                .shouldBe(visible)
                .shouldHave(text("Я соглашаюсь с условиями обработки и использования моих персональных данных"),
                        Condition.cssValue("user-select", "none"));

    }

    @Test
    void shouldSendSuccessfulRequestWithCitySearch() {
        open("http://localhost:9999");
        // переменная для того, чтобы ввести дату +3 дня от текущей
        String neededDate = LocalDate.now().plusDays(3).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $("[data-test-id='city'] input").setValue("Ор");
        $$(".menu-item").find(exactText("Орёл")).click();
        $("[data-test-id='date'] input").doubleClick().sendKeys(BACK_SPACE); // очищаем поле
        $("[data-test-id='date'] input").setValue(neededDate); // вводим нужную дату
        $("[data-test-id='name'] input").setValue("Петров Петя");
        $("[data-test-id='phone'] input").setValue("+79102671142");
        $("[data-test-id='agreement'] .checkbox__box").click();
        $(byText("Забронировать")).click();
        $("[data-test-id='notification']")
                .shouldHave(Condition.text("Успешно! Встреча успешно забронирована на " + neededDate),
                        Duration.ofSeconds(15));

    }
}
