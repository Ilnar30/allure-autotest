package qa.guru.allure;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static io.qameta.allure.Allure.step;
import static org.openqa.selenium.By.linkText;

public class StepsTest {
    private static final String REPOSITORY = "Ilnar30/demoqa-autotest";
    private static final int ISSUE = 2;

    @BeforeAll
    static void configure() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @Test
    public void testIssueSearch() {
        open("https://github.com/");
        $("[placeholder='Search GitHub']").setValue("Ilnar30/demoqa-autotest").pressEnter();
        $(linkText("Ilnar30/demoqa-autotest")).click();
        $("#issues-tab").click();
        $(withText("#2")).should(exist);

    }

    @Test
    public void testIssueSearchWithLambda() {
        step("Открываем страницу", () -> {
            open("https://github.com/");
        });
        step("Ищем репозиторий" + REPOSITORY, () -> {
            $("[placeholder='Search GitHub']").setValue(REPOSITORY).pressEnter();
        });
        step("Кликаем по ссылке репозитория" + REPOSITORY, () -> {
            $(linkText(REPOSITORY)).click();
        });
        step("Открываем таб Issues", () -> {
            $("#issues-tab").click();
        });
        step("Проверяем наличие Issue c номером " + ISSUE, () -> {
            $(withText("#" + ISSUE)).should(exist);
        });
    }

    @Test
    public void testAnnotatedStep() {
        WebSteps steps = new WebSteps();
        steps.openMainPage();
        steps.searchForRepository(REPOSITORY);
        steps.clickOnRepositoryLink(REPOSITORY);
        steps.openIssuesTab();
        steps.shouldSeeIssueWithNumber(ISSUE);
    }
}
