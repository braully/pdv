package net.originmobi.pdv.test;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.FixMethodOrder;
import org.junit.jupiter.api.*;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Selenide.*;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CaixaSeleniumTest {
    private static final String BASE_URL = "http://localhost:8080";
    @BeforeAll
    public static void setUpAll() {
        Configuration.browserSize = "1280x800";
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @BeforeAll
    static void setUp() {
        open(BASE_URL);
        $("input[id=user]").sendKeys("gerente");
        $("input[id=password]").sendKeys("123");
        $("button[type=submit]").click();
        $$("img").findBy(Condition.attribute("alt", "Caixa")).click();
    }

    @AfterAll
    static void tearDown() {
        closeWebDriver();
    }

    @Test
    public void aopenCaixa() {
        $$("a").findBy(Condition.text("Abrir Novo")).click();
        $("#descricao").sendKeys("Caixa preferencial");
        $("#caixatipo").sendKeys("CAIXA");
        $("#valorAbertura").sendKeys("25000");
        $$("a").findBy(Condition.text("Abrir")).click();
        assert $$("tr[class=success] td").find(Condition.text("R$ 250")).should(exist).exists();
    }

    // TODO test wait issue
    @Test
    public void bretirarCaixa() {
        $("#btnSangria").click();
        $("#idvl").sendKeys("15000");
        $("#idobs").sendKeys("Despesa com funcionário");
        confirm();
        SelenideElement el = $$("tr[class=success] td").find(Condition.text("Despesa com funcionário"));
        assert el.exists();
        assert el.find(By.xpath("//..//td[contains(text(), 'R$ -150,00')]")).exists();
    }
}
