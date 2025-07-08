package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.fail;

public class FormPage extends AbsBasePage {


    public FormPage(WebDriver driver) {
        super(driver, "");
    }

    @Step("Ввод имени пользователя: {name}")
    public void inputUsername(String name) {
        WebElement usenameInput = driver.findElement(By.cssSelector("input#username"));
        usenameInput.clear();
        usenameInput.sendKeys(name);
    }

    @Step("Ввод email: {email}")
    public void inputEmail(String email) {
        WebElement emailInput = driver.findElement(By.cssSelector("input#email"));
        emailInput.clear();
        emailInput.sendKeys(email);
    }

    @Step("Ввод пароля")
    public void inputPassword(String password) {
        WebElement passwordInput = driver.findElement(By.cssSelector("input#password"));
        passwordInput.clear();
        passwordInput.sendKeys(password);
    }

    @Step("Подтверждение пароля")
    public void confirmPassword(String password) {
        WebElement passwordInput = driver.findElement(By.cssSelector("input#confirm_password"));
        passwordInput.clear();
        passwordInput.sendKeys(password);
    }

    @Step("Проверка совпадения паролей")
    public void validatePasswords() {
        String password = driver.findElement(By.cssSelector("input#password")).getAttribute("value");
        String confirmPassword = driver.findElement(By.cssSelector("input#confirm_password")).getAttribute("value");
        assert password != null;
        assert password.equals(confirmPassword);
    }

    @Step("Ввод даты рождения: {date}")
    public void inputDate(String date) {
        WebElement nameInput = driver.findElement(By.cssSelector("#birthdate"));
        nameInput.clear();
        nameInput.sendKeys(date);
    }

    @Step("Выбор уровня языка: {name}")
    public void selectLanguageLevel(String name) {
        Select languageLevel = new Select(driver.findElement(By.cssSelector("#language_level")));
        languageLevel.selectByVisibleText(name);
    }

    @Step("Отправка формы")
    public void sendForm() {
        WebElement sendButton = driver.findElement(By.cssSelector("#registrationForm > input[type=submit]"));
        sendButton.click();
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("#output")));
        } catch (TimeoutException e) {
            fail();
        }
    }

    @Step("Проверка выходных данных")
    public void validateOutputData(String username, String email, String date, String languageLevel) {
        WebElement outputDiv = driver.findElement(By.cssSelector("#output"));
        String outputText = outputDiv.getText();

        WebElement languageLevelElement = driver.findElement(By.cssSelector("#language_level"));

        if (!outputText.contains("Имя пользователя: " + username) ||
                !outputText.contains("Электронная почта: " + email) ||
                !outputText.contains("Дата рождения: " + convertDate(date)) ||
                !outputText.contains("Уровень языка: " + getOptionValueByText(languageLevelElement, languageLevel))) {
            throw new AssertionError("One or more required fields are missing in output");
        }
    }

    private String convertDate(String originalDate) {
        DateTimeFormatter originalFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        DateTimeFormatter targetFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        LocalDate date = LocalDate.parse(originalDate, originalFormatter);

        return date.format(targetFormatter);
    }

    private String getOptionValueByText(WebElement selectElement, String optionText) {
        List<WebElement> options = selectElement.findElements(By.tagName("option"));

        for (WebElement option : options) {
            if (option.getText().equals(optionText)) {
                return option.getAttribute("value");
            }
        }
        throw new AssertionError("Option with text '" + optionText + "' not found");
    }
}
