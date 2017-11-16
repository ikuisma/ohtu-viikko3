package ohtu;

import org.omg.CORBA.TIMEOUT;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

public class Tester {

    public static void main(String[] args) {
        WebDriver driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
        driver.get("http://localhost:4567");
        createNewUser(driver);
        driver.quit();
    }

    private static void createNewUser(WebDriver driver) {
        FrontPage frontPage = new FrontPage(driver);
        RegisterUserPage registerUserPage = new RegisterUserPage(driver);
        frontPage.clickRegisterNewUser();
        sleep(2);
        registerUserPage.inputUsername("paavo");
        registerUserPage.inputPassword("password1");
        registerUserPage.inputPasswordConfirmation("password1");
        registerUserPage.submitRegistrationForm();
        sleep(2);
    }

    private static void loginWithNonExistingUser(WebDriver driver) {
        FrontPage frontPage = new FrontPage(driver);
        LoginPage loginPage = new LoginPage(driver);
        frontPage.clickLogin();
        sleep(2);
        loginPage.inputPassword("password1");
        loginPage.inputUsername("paavo");
        loginPage.submitLogin();
        sleep(2);
    }

    private static void wrongPassword(WebDriver driver) {
        FrontPage frontPage = new FrontPage(driver);
        LoginPage loginPage = new LoginPage(driver);
        frontPage.clickLogin();
        sleep(2);
        loginPage.inputUsername("pekka");
        loginPage.inputPassword("akkep123");
        loginPage.submitLogin();
        sleep(2);
    }

    private static void successfulLogin(WebDriver driver) {
        FrontPage frontPage = new FrontPage(driver);
        LoginPage loginPage = new LoginPage(driver);
        frontPage.clickLogin();
        sleep(2);
        loginPage.inputUsername("pekka");
        loginPage.inputPassword("akkep");
        sleep(2);
        loginPage.submitLogin();
        sleep(3);
    }

    private static void sleep(int n){
        try{
            Thread.sleep(n*1000);
        } catch(Exception e){}
    }

}

class FrontPage {

    private WebDriver driver;

    FrontPage(WebDriver driver) {
        this.driver = driver;
    }

    public void clickLogin() {
        WebElement element = driver.findElement(By.linkText("login"));
        element.click();
    }

    public void clickRegisterNewUser() {
        WebElement element = driver.findElement(By.linkText("register new user"));
        element.click();
    }

}

class LoginPage {

    private WebDriver driver;

    LoginPage(WebDriver driver) {
        this.driver = driver;
    }

    public void inputUsername(String text) {
        WebElement element = driver.findElement(By.name("username"));
        element.sendKeys(text);
    }

    public void inputPassword(String text) {
        WebElement element = driver.findElement(By.name("password"));
        element.sendKeys(text);
    }

    public void submitLogin() {
        WebElement element = driver.findElement(By.name("login"));
        element.submit();
    }

}

class RegisterUserPage {

    private WebDriver driver;

    RegisterUserPage(WebDriver driver) {
        this.driver = driver;
    }

    public void inputUsername(String text) {
        WebElement element = driver.findElement(By.name("username"));
        element.sendKeys(text);
    }

    public void inputPassword(String text) {
        WebElement element = driver.findElement(By.name("password"));
        element.sendKeys(text);
    }

    public void inputPasswordConfirmation(String text) {
        WebElement element = driver.findElement(By.name("passwordConfirmation"));
        element.sendKeys(text);
    }

    public void submitRegistrationForm() {
        WebElement element = driver.findElement(By.name("signup"));
        element.submit();
    }

}
