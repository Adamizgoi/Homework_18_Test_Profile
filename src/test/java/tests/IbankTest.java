package tests;

import com.codeborne.selenide.Configuration;
import data.RegistrationInfo;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;
import static utils.RegistrationInfoGenerator.RegistrationGenerator.generateUser;
import static utils.RegistratorUtil.Registrator.registrateUser;
import static utils.RegistratorUtil.Registrator.setUpWebApi;

public class IbankTest {

    // тексты ошибок при регистрации
    String errorText = "Ошибка!";
    String errorTextLogin = "Неверно указан логин или пароль";
    String errorTextEmptyField = "Поле обязательно для заполнения";
    String errorTextPassword = "Неверно указан логин или пароль";

    // поля для информации о пользователях, занесенных в систему (по их логину и паролю можно войти в систему)
    // пользователи генерируются рандомно, они уникальны для каждого прогона тестов
    static RegistrationInfo randomUserLoginInfo1;
    static RegistrationInfo randomUserLoginInfo2;
    static RegistrationInfo randomUserLoginInfo3;
    static RegistrationInfo randomBlockedUserLoginInfo1;

    @BeforeAll
    static void registrationOfUsers() {
        // изменить настройки для rest-assured можно в классе RegistrationUtil
        setUpWebApi();
        randomUserLoginInfo1 = generateUser("active");
        randomUserLoginInfo2 = generateUser("active");
        randomUserLoginInfo3 = generateUser("active");
        randomBlockedUserLoginInfo1 = generateUser("blocked");
        registrateUser(randomUserLoginInfo1);
        registrateUser(randomUserLoginInfo2);
        registrateUser(randomUserLoginInfo3);
        registrateUser(randomBlockedUserLoginInfo1);
    }

    @BeforeEach
    void setUp() {
        open("http://localhost:9999/");
    }

    @Test
    void shouldLoginIfUserInfoCorrect() {
        $("[data-test-id = 'login'] input").setValue(randomUserLoginInfo1.getLogin());
        $("[data-test-id = 'password'] input").setValue(randomUserLoginInfo1.getPassword());
        $("[data-test-id = 'action-login']").click();
        $(byText("Личный кабинет")).shouldBe(visible);
    }

    @Test
    void shouldNotLoginIfUserNameIsIncorrect() {
        $("[data-test-id = 'login'] input").setValue(randomUserLoginInfo1.getLogin() + "error");
        $("[data-test-id = 'password'] input").setValue(randomUserLoginInfo1.getPassword());
        $("[data-test-id = 'action-login']").click();
        $(byText(errorTextLogin)).shouldBe(visible);
    }

    @Test
    void shouldNotLoginIfUserNameIsEmpty() {
        $("[data-test-id = 'password'] input").setValue(randomUserLoginInfo2.getPassword());
        $("[data-test-id = 'action-login']").click();
        $("[data-test-id = 'login'] .input__sub").shouldHave(text(errorTextEmptyField)).shouldBe(visible);
    }

    @Test
    void shouldNotLoginIfUserPasswordIsIncorrect() {
        $("[data-test-id = 'login'] input").setValue(randomUserLoginInfo1.getLogin());
        $("[data-test-id = 'password'] input").setValue(randomUserLoginInfo1.getPassword() + "error");
        $("[data-test-id = 'action-login']").click();
        $(byText(errorTextPassword)).shouldBe(visible);
    }

    @Test
    void shouldNotLoginIfUserPasswordIsEmpty() {
        $("[data-test-id = 'login'] input").setValue(randomUserLoginInfo3.getLogin());
        $("[data-test-id = 'action-login']").click();
        $("[data-test-id = 'password'] .input__sub").shouldHave(text(errorTextEmptyField)).shouldBe(visible);
    }
}
