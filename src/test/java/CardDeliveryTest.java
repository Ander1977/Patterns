import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.*;
import static org.openqa.selenium.Keys.chord;

public class CardDeliveryTest {
    DateUtils currentDateTimePlusThreeDay = new DateUtils();
    public String currentDate = currentDateTimePlusThreeDay.localDateTime();
    public String selectAll = chord(Keys.CONTROL, "a");
    public Keys del = Keys.DELETE;



    @Test
    void shouldInputIsCorrect() {
        open("http://localhost:9999");
        $("[data-test-id='city'] input").setValue("Хабаровск");
        $("[data-test-id='date'] input").sendKeys(selectAll, del);
        $("[data-test-id='date'] input").setValue(currentDate);
        $("[data-test-id='name'] input").setValue("Копатилов Андрей");
        $("[data-test-id='phone'] input").setValue("+79098765432");
        $("[data-test-id=agreement]").click();
        $$(".form button").find(exactText("Забронировать")).click();
        $(withText("Успешно!")).waitUntil(visible, 15000);
        $("[data-test-id=notification]").shouldHave(text("Встреча успешно забронирована на"));
        $("[data-test-id=notification]").shouldHave(text(currentDate));
    }

    @Test
    void shouldPhoneNotCorrect() {
        open("http://localhost:9999");
        $("[data-test-id='city'] input").setValue("Хабаровск");
        $("[data-test-id='date'] input").sendKeys(selectAll, del);
        $("[data-test-id='date'] input").setValue(currentDate);
        $("[data-test-id='name'] input").setValue("Копатилов Андрей");
        $("[data-test-id='phone'] input").setValue("89098765432");
        $("[data-test-id=agreement]").click();
        $$("button").find(exactText("Забронировать")).click();
        $(".input_invalid[data-test-id='phone']").shouldHave(text("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
        $("[data-test-id='phone']").shouldHave(cssValue("color", "rgba(255, 92, 92, 1)"));
    }

    @Test
    void shouldNameNotCorrect() {
        open("http://localhost:9999");
        $("[data-test-id='city'] input").setValue("Хабаровск");
        $("[data-test-id='date'] input").sendKeys(selectAll, del);
        $("[data-test-id='date'] input").setValue(currentDate);
        $("[data-test-id='name'] input").setValue("Kopatilov Andrey");
        $("[data-test-id='phone'] input").setValue("+79098765432");
        $("[data-test-id=agreement]").click();
        $$("button").find(exactText("Забронировать")).click();
        $(".input_invalid[data-test-id='name']").shouldHave(text("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }

    @Test
    void shouldChecboxNotClick() {
        open("http://localhost:9999");
        $("[data-test-id='city'] input").setValue("Хабаровск");
        $("[data-test-id='date'] input").sendKeys(selectAll, del);
        $("[data-test-id='date'] input").setValue(currentDate);
        $("[data-test-id='name'] input").setValue("Копатилов Андрей");
        $("[data-test-id='phone'] input").setValue("+79098765432");
        $$("button").find(exactText("Забронировать")).click();
        $("[data-test-id=agreement]").shouldHave(text("Я соглашаюсь с условиями обработки и использования моих персональных данных"));
        $(".input_invalid[data-test-id=agreement]").shouldHave(cssValue("color", "rgba(255, 92, 92, 1)"));
    }

    @Test
    void shouldCityNotSelected() {
        open("http://localhost:9999");
        $("[data-test-id='date'] input").sendKeys(selectAll, del);
        $("[data-test-id='date'] input").setValue(currentDate);;
        $("[data-test-id='name'] input").setValue("Копатилов Андрей");
        $("[data-test-id='phone'] input").setValue("+79098765432");
        $("[data-test-id=agreement]").click();
        $$("button").find(exactText("Забронировать")).click();
        $(".input_invalid[data-test-id='city']").shouldHave(text("Поле обязательно для заполнения"));

    }

    @Test
    void shouldCityNotUnavailable() {
        open("http://localhost:9999");
        $("[data-test-id='city'] input").setValue("Бикин");
        $("[data-test-id='date'] input").sendKeys(selectAll, del);
        $("[data-test-id='date'] input").setValue(currentDate);
        $("[data-test-id='name'] input").setValue("Копатилов Андрей");
        $("[data-test-id='phone'] input").setValue("+79098765432");
        $("[data-test-id=agreement]").click();
        $$("button").find(exactText("Забронировать")).click();
        $(".input_invalid[data-test-id='city']").shouldHave(text("Доставка в выбранный город недоступна"));
    }

}
