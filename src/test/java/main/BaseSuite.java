package main;

import factory.WebDriverFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.net.MalformedURLException;

public abstract class BaseSuite {
    protected WebDriver driver;

    @BeforeEach
    public void init() throws MalformedURLException {
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments(
                "--start-fullscreen",
                "--disable-infobars",
                "--no-sandbox"
        );
        driver = WebDriverFactory.getDriver("chrome", chromeOptions);
    }

    @AfterEach
    public void close() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }

}