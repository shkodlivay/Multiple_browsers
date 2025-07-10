package pages;

import org.openqa.selenium.WebDriver;

public abstract class AbsBasePage {

    protected WebDriver driver = null;
    private String path = "";

    public AbsBasePage(WebDriver driver, String path) {
        this.driver = driver;
        this.path = path;
    }

    private final String baseUrl = System.getProperty("base.url");

    public void open() {
        driver.get(baseUrl + path);
    }
}
