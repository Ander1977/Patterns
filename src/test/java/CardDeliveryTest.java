import com.github.javafaker.Faker;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.util.Locale;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.*;
import static java.lang.String.valueOf;
import static org.openqa.selenium.Keys.chord;

public class CardDeliveryTest {
    Faker faker = new Faker(new Locale("RU"));
    DataGenerator dataGenerator = new DataGenerator();
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
        $("[data-test-id='name'] input").setValue(valueOf(faker.name().fullName()));
        $("[data-test-id='phone'] input").setValue(valueOf(faker.phoneNumber().phoneNumber()));
        $("[data-test-id=agreement]").click();
        $$(".form button").find(exactText("Запланировать")).click();
        $(withText("Успешно!")).waitUntil(visible, 15000);
        $("[data-test-id=success-notification]").shouldHave(text("Встреча успешно запланирована на"));
        $("[data-test-id=success-notification]").shouldHave(text(currentDate));
    }

    @Test
    void shouldInputIsCorrectRepeatedly() {
        RegistrationByNamePhone registrationData = DataGenerator.Registration.generateByNamePhone("RU");
        open("http://localhost:9999");
        $("[data-test-id='city'] input").setValue("Хабаровск");
        $("[data-test-id='date'] input").sendKeys(selectAll, del);
        $("[data-test-id='date'] input").setValue(currentDate);
        $("[data-test-id='name'] input").setValue(registrationData.getFullName());
        $("[data-test-id='phone'] input").setValue(registrationData.getPhoneNumber());
        $("[data-test-id=agreement]").click();
        $$(".form button").find(exactText("Запланировать")).click();
        $(withText("Успешно!")).waitUntil(visible, 15000);
        $("[data-test-id=success-notification]").shouldHave(text("Встреча успешно запланирована на"));
        $("[data-test-id=success-notification]").shouldHave(text(currentDate));
        $("[data-test-id='date'] input").sendKeys(selectAll, del);
        $("[data-test-id='date'] input").setValue(currentDate + 1);
        $$(".form button").find(exactText("Запланировать")).click();
        $(withText("Необходимо подтверждение")).waitUntil(visible, 15000);
        $("[data-test-id=replan-notification]").shouldHave(text("У вас уже запланирована встреча на другую дату. Перепланировать?"));
        $$("div.notification__content > button").find(exactText("Перепланировать")).click();
        $(withText("Успешно!")).waitUntil(visible, 15000);
        $("[data-test-id=success-notification]").shouldHave(text("Встреча успешно запланирована на"));
        $("[data-test-id=success-notification]").shouldHave(text(currentDate));
    }


    @Test
    void shouldPhoneNotInput() {
        open("http://localhost:9999");
        $("[data-test-id='city'] input").setValue("Хабаровск");
        $("[data-test-id='date'] input").sendKeys(selectAll, del);
        $("[data-test-id='date'] input").setValue(currentDate);
        $("[data-test-id='name'] input").setValue(valueOf(faker.name().fullName()));
        $("[data-test-id=agreement]").click();
        $$("button").find(exactText("Запланировать")).click();
        $(".input_invalid[data-test-id='phone']").shouldHave(text("Поле обязательно для заполнения"));
        $("[data-test-id='phone']").shouldHave(cssValue("color", "rgba(255, 92, 92, 1)"));
    }

    @Test
    void shouldNameNotCorrect() {
        open("http://localhost:9999");
        $("[data-test-id='city'] input").setValue("Хабаровск");
        $("[data-test-id='date'] input").sendKeys(selectAll, del);
        $("[data-test-id='date'] input").setValue(currentDate);
        $("[data-test-id='name'] input").setValue(valueOf(faker.name()));
        $("[data-test-id='phone'] input").setValue(valueOf(faker.phoneNumber().phoneNumber()));
        $("[data-test-id=agreement]").click();
        $$("button").find(exactText("Запланировать")).click();
        $(".input_invalid[data-test-id='name']").shouldHave(text("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }

    @Test
    void shouldChecboxNotClick() {
        open("http://localhost:9999");
        $("[data-test-id='city'] input").setValue("Хабаровск");
        $("[data-test-id='date'] input").sendKeys(selectAll, del);
        $("[data-test-id='date'] input").setValue(currentDate);
        $("[data-test-id='name'] input").setValue(valueOf(faker.name().fullName()));
        $("[data-test-id='phone'] input").setValue(valueOf(faker.phoneNumber().phoneNumber()));
        $$("button").find(exactText("Запланировать")).click();
        $("[data-test-id=agreement]").shouldHave(text("Я соглашаюсь с условиями обработки и использования моих персональных данных"));
        $(".input_invalid[data-test-id=agreement]").shouldHave(cssValue("color", "rgba(255, 92, 92, 1)"));
    }

    @Test
    void shouldCityNotSelected() {
        open("http://localhost:9999");
        $("[data-test-id='date'] input").sendKeys(selectAll, del);
        $("[data-test-id='date'] input").setValue(currentDate);;
        $("[data-test-id='name'] input").setValue(valueOf(faker.name().fullName()));
        $("[data-test-id='phone'] input").setValue(valueOf(faker.phoneNumber().phoneNumber()));
        $("[data-test-id=agreement]").click();
        $$("button").find(exactText("Запланировать")).click();
        $(".input_invalid[data-test-id='city']").shouldHave(text("Поле обязательно для заполнения"));

    }

    @Test
    void shouldCityNotUnavailable() {
        open("http://localhost:9999");
        $("[data-test-id='city'] input").setValue("Бикин");
        $("[data-test-id='date'] input").sendKeys(selectAll, del);
        $("[data-test-id='date'] input").setValue(currentDate);
        $("[data-test-id='name'] input").setValue(valueOf(faker.name().fullName()));
        $("[data-test-id='phone'] input").setValue(valueOf(faker.phoneNumber().phoneNumber()));
        $("[data-test-id=agreement]").click();
        $$("button").find(exactText("Запланировать")).click();
        $(".input_invalid[data-test-id='city']").shouldHave(text("Доставка в выбранный город недоступна"));
    }

}
