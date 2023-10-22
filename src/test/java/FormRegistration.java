import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import helpers.Attach;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.remote.DesiredCapabilities;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.logevents.SelenideLogger.step;


public class FormRegistration {
    @BeforeAll
    static void beforeall()
    {
        Configuration.baseUrl="https://demoqa.com";
        Configuration.pageLoadStrategy="eager";
        Configuration.browserSize = "1920x1080";
        //Configuration.holdBrowserOpen=true;
        Configuration.remote = "https://user1:1234@selenoid.autotests.cloud/wd/hub";
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("enableVNC", true);
        capabilities.setCapability("enableVideo", true);
        Configuration.browserCapabilities = capabilities;

    }
    @BeforeEach
    void addListener() {
        SelenideLogger.addListener("AllureSelenide", new AllureSelenide());
    }

    @AfterEach
    void addAttachments() {
        Attach.screenshotAs("Last screenshot");
        Attach.pageSource();
        Attach.browserConsoleLogs();
        Attach.addVideo();
    }
    @Test
    void enterFormTest()
    {
        step("Открываем страницу регистрации", ()-> {
        open("/automation-practice-form");
        executeJavaScript("$('#fixedban').remove()");
        executeJavaScript("$('footer').remove()");
        });

        step("Заполняем имя",()->{
            $("#firstName").setValue("Daria");
        });

        step("Заполняем фамилию",()->{
            $("#lastName").setValue("Kuteynikova");
        });

        step("Заполняем почту",()->{
            $("#userEmail").setValue("kolohmatova@yandex.ru");
        });

        step("Выбираем пол",()->{
            $("#genterWrapper").$(byText("Female")).click();
        });

        step("Заполняем номер",()->{
            $("#userNumber").setValue("9002172121");
        });

        step("Заполняем дату рождения",()->{
            $("#dateOfBirthInput").click();
            $(".react-datepicker__month-select").selectOption("January");
            $(".react-datepicker__year-select").selectOption("1997");
            $(".react-datepicker__day--006").click();
        });

        step("Заполняем предмет",()->{
            $("#subjectsInput").setValue("Maths").pressEnter();
        });

        step("Выбираем хобби",()->{
            $("#hobbiesWrapper").$(byText("Sports")).click();
            $("#hobbiesWrapper").$(byText("Reading")).click();
        });

        step("Загружаем аватарку",()->{
            $("#uploadPicture").uploadFromClasspath("cat.jpg");
        });

        step("Заполняем адрес",()->{
            $("#currentAddress").setValue("Moskow");
        });

        step("Выбираем штат и город",()->{
            $("#state").click();
            $("#stateCity-wrapper").$(byText("Haryana")).click();
            $("#city").click();
            $("#stateCity-wrapper").$(byText("Karnal")).click();
        });

        step("Нажимаем на кнопку регистрации",()->{
            $("#submit").click();
        });


        step("Проверяем соответствие введённых данных ",()->{
            $(".table-responsive").shouldHave(text("Daria Kuteynikova"));
            $(".table-responsive").shouldHave(text("kolohmatova@yandex.ru"));
            $(".table-responsive").shouldHave(text("Female"));
            $(".table-responsive").shouldHave(text("9002172121"));
            $(".table-responsive").shouldHave(text("6 January,1997"));
            $(".table-responsive").shouldHave(text("Maths"));
            $(".table-responsive").shouldHave(text("Sports, Reading"));
            $(".table-responsive").shouldHave(text("cat.jpg"));
            $(".table-responsive").shouldHave(text("Moskow"));
            $(".table-responsive").shouldHave(text("Haryana Karnal"));
        });

    }

}
