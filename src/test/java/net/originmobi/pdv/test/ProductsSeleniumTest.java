package net.originmobi.pdv.test;

import com.codeborne.selenide.*;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.Assert;
import org.junit.jupiter.api.*;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;

public class ProductsSeleniumTest {
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
        $$("a").findBy(Condition.href("/produto")).click();
    }

    @AfterEach
    public void tearDown() {
        closeWebDriver();
    }

    @Test
    public void logout() {
        $("button[type=submit]").click();
        assert WebDriverRunner.getWebDriver().getCurrentUrl().equals(BASE_URL + "/login");
    }

    @Test
    public void createProdutoValid() {
        int amountBefore = $("tbody").findAll("tr").size();
        $$("a").findBy(Condition.text("Novo")).click();
        assert WebDriverRunner.getWebDriver().getCurrentUrl().equals(BASE_URL + "/produto/form");
        $("#descricao").sendKeys("teste");
        $("#valorCusto").sendKeys("1000");
        $("#validade").sendKeys("16/09/2022");
        $("#valorVenda").sendKeys("1500");
        $("#unidade").sendKeys("10");
        $("input[name=enviar]").click();
        assert $$("span").findBy(Condition.text("Produdo cadastrado com sucesso")).isDisplayed();
        $(byText("Listar")).click();
        assert $("tbody").findAll("tr").size() > amountBefore;
    }

    @Test
    public void createProdutoInvalid() {
        int amountBefore = $("tbody").findAll("tr").size();
        $$("a").findBy(Condition.text("Novo")).click();
        assert WebDriverRunner.getWebDriver().getCurrentUrl().equals(BASE_URL + "/produto/form");
        $("#descricao").sendKeys("Detergente");
        $("#balanca").sendKeys("SIM");
        $("#valorCusto").sendKeys("-5");
        $("#validade").sendKeys("20/10/1800");
        $("#valorVenda").sendKeys("-8");
        $("#unidade").sendKeys("-10");
        $("input[name=enviar]").click();
        Assertions.assertFalse($$("span").findBy(Condition.text("Produdo cadastrado com sucesso")).isDisplayed());
        $(byText("Listar")).click();
        assert $("tbody").findAll("tr").size() == amountBefore;
    }

    @Test
    public void deactivateProduto() {
        ElementsCollection productEntry = $("tbody").findAll("tr");
        int len = productEntry.size();
        int found = 0;
        for (int i = 0; i < len; i++) {
            SelenideElement product = $("tbody").findAll("tr").get(i);
            product.find("a").click();
            if ($("#ativo").text().equals("ATIVO")) {
                found = 1;
                break;
            }
            open(BASE_URL + "/produto");
        }
        if (found == 0) {
            $$("a").findBy(Condition.text("Novo")).click();
            $("#descricao").sendKeys("teste inativar");
            $("#valorCusto").sendKeys("1000");
            $("#validade").sendKeys("16/09/2022");
            $("#valorVenda").sendKeys("1500");
            $("#unidade").sendKeys("10");
            $("input[name=enviar]").click();
            ElementsCollection bodyList = $("tbody").findAll("tr");
            bodyList.get(bodyList.size()-1).find("a").click();
        }
        $("#ativo").sendKeys("INATIVO");
        $("input[name=enviar]").click();
        assert $("#ativo").text().equals("INATIVO");
    }

    @Test
    public void activateProduto() {
        ElementsCollection productEntry = $("tbody").findAll("tr");
        int len = productEntry.size();
        int found = 0;
        for (int i = 0; i < len; i++) {
            SelenideElement product = $("tbody").findAll("tr").get(i);
            product.find("a").click();
            if ($("#ativo").text().equals("INATIVO")) {
                found = 1;
                break;
            }
            open(BASE_URL + "/produto");
        }
        if (found == 0) {
            $$("a").findBy(Condition.text("Novo")).click();
            $("#descricao").sendKeys("teste inativar");
            $("#valorCusto").sendKeys("1000");
            $("#validade").sendKeys("16/09/2022");
            $("#valorVenda").sendKeys("1500");
            $("#unidade").sendKeys("10");
            $("#ativo").sendKeys("INATIVO");
            $("input[name=enviar]").click();
            ElementsCollection bodyList = $("tbody").findAll("tr");
            bodyList.get(bodyList.size()-1).find("a").click();
            assert $("#ativo").text().equals("INATIVO");
        }
        $("#ativo").sendKeys("ATIVO");
        $("input[name=enviar]").click();
        assert $("#ativo").text().equals("ATIVO");
    }
}
