package main;

import jdk.jfr.Description;
import org.junit.jupiter.api.Test;

import pages.FormPage;

public class FormTest extends BaseSuite {

    @Test
    @Description("Тест заполнения формы регистрации")
    public void fillRegistrationForm() {
        String username = TestCredentials.getCredential("testLoginUsername");
        String password = TestCredentials.getLoginPassword();
        String email = "testuser@testmail.ru";
        String date = "12-12-2012";
        String languageLevel = "Продвинутый";

        try {
            FormPage formPage = new FormPage(driver);
            formPage.open();
            formPage.inputUsername(username);
            formPage.inputEmail(email);
            formPage.inputPassword(password);
            formPage.confirmPassword(password);
            formPage.validatePasswords();
            formPage.inputDate(date);

            formPage.selectLanguageLevel(languageLevel);

            formPage.sendForm();

            formPage.validateOutputData(username, email, date, languageLevel);

        } catch (AssertionError e) {
            throw e;
        } catch (Exception e) {
            throw e;
        } finally {
            if (driver != null) {
                driver.quit();
            }
        }
    }
}
