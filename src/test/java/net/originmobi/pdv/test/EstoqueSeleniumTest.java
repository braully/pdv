package net.originmobi.pdv.test;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selectors.byXpath;
import static com.codeborne.selenide.Selenide.*;

public class EstoqueSeleniumTest {
    private final String BASE_URL = "http://localhost:8080";
    @BeforeAll
    public static void setUpAll() {
        Configuration.browserSize = "1280x800";
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @BeforeEach
    public void setUp() {
        open(BASE_URL);
        $("input[id=user]").sendKeys("gerente");
        $("input[id=password]").sendKeys("123");
        $("button[type=submit]").click();
        $$("img").findBy(Condition.attribute("alt", "Produto")).click();
        $$("a").findBy(Condition.href("/ajustes")).click();
    }

    @AfterEach
    public void tearDown() {
        closeWebDriver();
    }

    // TODO fix alert input
    @Test
    public void createAjusteValid(){
        $$("a").findBy(Condition.text("Novo")).click();
        $(byXpath("//select[@id='codigoProduto']//..//button")).click();
        $(byXpath("//select[@id='codigoProduto']//..//input")).sendKeys("Picolé", Keys.ENTER);
        $$("a").findBy(Condition.text("Inserir")).click();
        try {
            wait(30);
        } catch (Exception e) {
            e.printStackTrace();
        }
        switchTo().alert().sendKeys("30");
        confirm();
        $$("a").findBy(Condition.text("Processar")).click();
        confirm();
        confirm();
        $$("a").findBy(Condition.text("Cancelar")).click();
        $(byText("Listar")).click();
    }

    // TODO fix alert input
    @Test
    public void createAjusteInvalid(){
        $$("a").findBy(Condition.text("Novo")).click();
        $(byXpath("//select[@id='codigoProduto']//..//button")).click();
        $(byXpath("//select[@id='codigoProduto']//..//input")).sendKeys("Picolé", Keys.ENTER);
        $$("a").findBy(Condition.text("Inserir")).click();
        try {
            wait(30);
        } catch (Exception e) {
            e.printStackTrace();
        }
        switchTo().alert().sendKeys("-9.5");
        confirm();
        $$("a").findBy(Condition.text("Processar")).click();
        confirm();
        confirm();
        $$("a").findBy(Condition.text("Cancelar")).click();
        $(byText("Listar")).click();
    }
}
