package main;

import jdk.jfr.Description;
import org.junit.jupiter.api.Test;

import pages.FormPage;

public class FormTest extends BaseSuite {

    @Test
    @Description("Тест заполнения формы регистрации")
    public void fillRegistrationForm() {
        String username = System.getProperty("testLoginUsername");
        String password = System.getProperty("getLoginPassword");
        String email = "testuser@testmail.ru";
        String date = "12-12-2012";
        String languageLevel = "Продвинутый";

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

    }
}
