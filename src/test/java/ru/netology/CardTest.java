package ru.netology;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static org.openqa.selenium.Keys.BACK_SPACE;

public class CardTest {

    @BeforeEach
    public void setupTest() {
        open("http://localhost:9999");
    }

    String bookDate(int days) {
        return LocalDate.now().plusDays(days).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

    String meetingDateNearest = bookDate(3);

    @Test
    public void HappyPathTest() {
        $("[data-test-id='city'] input").setValue("Ростов-на-Дону");
        $("[data-test-id='date'] input").doubleClick().sendKeys(BACK_SPACE);
        $("[data-test-id='date'] input").setValue(meetingDateNearest);
        $("[data-test-id='name'] input").setValue("Иван Иванов");
        $("[data-test-id='phone'] input").setValue("+71234567891");
        $("[data-test-id='agreement'] .checkbox__box").click();
        $(byText("Забронировать")).click();
        $("[data-test-id='notification'] .notification__title")
                .shouldBe(Condition.appear, Duration.ofSeconds(15))
                .shouldHave((text("Успешно!")));
        $("[data-test-id='notification'] .notification__content")
                .shouldBe(Condition.visible)
                .shouldHave(text("Встреча успешно забронирована на " + meetingDateNearest));
    }
}

