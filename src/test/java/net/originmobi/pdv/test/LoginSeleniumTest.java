package net.originmobi.pdv.test;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.WebDriverRunner;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selenide.*;

public class LoginSeleniumTest {

    @BeforeAll
    public static void setUpAll() {
        Configuration.browserSize = "1280x800";
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @BeforeEach
    public void setUp() {
        open("http://localhost:8080/");
    }

    @AfterEach
    public void tearDown() {
        closeWebDriver();
    }

    @Test
    public void loginValid() {
        $("input[id=user]").sendKeys("gerente");
        $("input[id=password]").sendKeys("123");
        $("button[type=submit]").click();
        System.out.println("");
        assert WebDriverRunner.getWebDriver().getCurrentUrl().equals("http://localhost:8080/");

    }

    @Test
    public void loginInvalidPassword() {
        $("input[id=user]").sendKeys("gerente");
        $("input[id=password]").sendKeys("saddasdas");
        $("button[type=submit]").click();
        System.out.println("");
        assert WebDriverRunner.getWebDriver().getCurrentUrl().equals("http://localhost:8080/login?error");
    }

    @Test
    public void loginInvalidLogin() {
        $("input[id=user]").sendKeys("acscxzczfa");
        $("input[id=password]").sendKeys("123");
        $("button[type=submit]").click();
        System.out.println("");
        assert WebDriverRunner.getWebDriver().getCurrentUrl().equals("http://localhost:8080/login?error");
    }
}
