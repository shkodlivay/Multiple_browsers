package factory;

import exceptions.BrowserNotSupportedException;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;


public class WebDriverFactory {

    private static final String remoteUrl = System.getProperty("remote.url", "");
    private static final String browserName = System.getProperty("browser", "chrome");
    private static final String browserVersion = System.getProperty("browser.version");

    public static WebDriver getDriver(String webDriverName) throws MalformedURLException {
        return getDriver(webDriverName, null);
    }

    public static WebDriver getDriver(String webDriverName, Object options) throws MalformedURLException {

        if (!remoteUrl.isEmpty()) {
            MutableCapabilities mutableCapabilities = new DesiredCapabilities();
            mutableCapabilities.setCapability("browserName", browserName);
            mutableCapabilities.setCapability("browserVersion", browserVersion);
            return new RemoteWebDriver(new URL(remoteUrl), mutableCapabilities);
        }

        if (webDriverName == null || webDriverName.trim().isEmpty()) {
            throw new IllegalArgumentException("WebDriver name must be specified");
        }

        try {
            return switch (webDriverName.toLowerCase()) {
                case "chrome" -> options != null ?
                        new ChromeDriver((ChromeOptions) options) :
                        new ChromeDriver();
                case "firefox" -> options != null ?
                        new FirefoxDriver((FirefoxOptions) options) :
                        new FirefoxDriver();
                default -> throw new BrowserNotSupportedException(webDriverName);
            };
        } catch (ClassCastException e) {
            throw new IllegalArgumentException("Invalid options type for " + webDriverName, e);
        }
    }
}
