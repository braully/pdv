package net.originmobi.pdv.test;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.WebDriverRunner;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selenide.*;

public class ProductsTest {

    @BeforeAll
    public static void setUpAll() {
        Configuration.browserSize = "1280x800";
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @BeforeEach
    public void setUp() {
        open("http://localhost:8080/");
        $("input[id=user]").sendKeys("gerente");
        $("input[id=password]").sendKeys("123");
        $("button[type=submit]").click();
        $$("img").findBy(Condition.attribute("alt", "Produto")).click();
        $$("a").findBy(Condition.href("/produto")).click();
    }

    @AfterEach
    public void tearDown() {
        closeWebDriver();
    }

    @Test
    public void logout() {
        $("button[type=submit]").click();
        assert WebDriverRunner.getWebDriver().getCurrentUrl().equals("http://localhost:8080/login");
    }

    @Test
    public void createProduto() {
        $$("a").findBy(Condition.text("Novo")).click();
        assert WebDriverRunner.getWebDriver().getCurrentUrl().equals("http://localhost:8080/produto/form");
        $("#descricao").sendKeys("teste");
        $("#valorCusto").sendKeys("1000");
        $("#validade").sendKeys("16/09/2022");
        $("#valorVenda").sendKeys("1500");
        $("#unidade").sendKeys("10");
        $("input[name=enviar]").click();
        assert $$("span").findBy(Condition.text("Produdo cadastrado com sucesso")).isDisplayed();
    }
}
