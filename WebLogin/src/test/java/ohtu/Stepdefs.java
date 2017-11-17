package ohtu;

import cucumber.api.java.After;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import static org.junit.Assert.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class Stepdefs {
    WebDriver driver = new ChromeDriver();
    String baseUrl = "http://localhost:4567";
    FrontPage frontPage = new FrontPage(driver);
    RegisterUserPage registerUserPage = new RegisterUserPage(driver);
    FirstTimeLoginPage firstTimeLoginPage = new FirstTimeLoginPage(driver);
    MainPage mainPage = new MainPage(driver);
    
    @Given("^login is selected$")
    public void login_selected() throws Throwable {
        driver.get(baseUrl);
        WebElement element = driver.findElement(By.linkText("login"));       
        element.click();          
    }

    @Given("^command new user is selected$")
    public void command_new_user_is_selected() throws Throwable {
        driver.get(baseUrl);
        frontPage.clickRegisterNewUser();
    }

    @When("^a valid username \"([^\"]*)\" and password \"([^\"]*)\" and matching password confirmation are entered$")
    public void a_valid_username_and_password_and_matching_password_confirmation_are_entered(String username, String password) throws Throwable {
        registerUserPage.fillAndSubmitRegistrationForm(username, password);
    }

    @Then("^a new user is created$")
    public void a_new_user_is_created() throws Throwable {
        boolean firstTimeLoginPageIsOpen = firstTimeLoginPage.continueToMainPageLink().isDisplayed();
        assertTrue(firstTimeLoginPageIsOpen);
    }

    @When("^username \"([^\"]*)\" and password \"([^\"]*)\" are given$")
    public void username_and_password_are_given(String username, String password) throws Throwable {
        WebElement element = driver.findElement(By.name("username"));
        element.sendKeys(username);
        element = driver.findElement(By.name("password"));
        element.sendKeys(password);
        element = driver.findElement(By.name("login"));
        element.submit();  
    }

    @Then("^system will respond \"([^\"]*)\"$")
    public void system_will_respond(String pageContent) throws Throwable {
        assertTrue(driver.getPageSource().contains(pageContent));
    }
    
    @When("^correct username \"([^\"]*)\" and password \"([^\"]*)\" are given$")
    public void username_correct_and_password_are_given(String username, String password) throws Throwable {
        logInWith(username, password);
    }

    @When("^correct username \"([^\"]*)\" and incorrect password \"([^\"]*)\" are given$")
    public void username_and_incorrect_password_are_given(String username, String password) throws Throwable {
        logInWith(username, password);
    }

    @When("^nonexistent username \"([^\"]*)\" and the password \"([^\"]*)\" are given$")
    public void nonexistent_username_and_the_password_are_given(String username, String password) throws Throwable {
        logInWith(username, password);
    }

    @Then("^user is logged in$")
    public void user_is_logged_in() throws Throwable {
        pageHasContent("Ohtu Application main page");
    }
    
    @Then("^user is not logged in and error message is given$")
    public void user_is_not_logged_in_and_error_message_is_given() throws Throwable {
        pageHasContent("invalid username or password");
        pageHasContent("Give your credentials to login");
    }

    @When("^a short username \"([^\"]*)\" and a valid password \"([^\"]*)\" are entered$")
    public void a_short_username_and_a_valid_password_are_entered(String username, String password) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        registerUserPage.fillAndSubmitRegistrationForm(username, password);
    }

    @When("^a valid username \"([^\"]*)\" and a password \"([^\"]*)\" that is too short are entered$")
    public void a_valid_username_and_a_password_that_is_too_short_are_entered(String username, String password) throws Throwable {
        registerUserPage.fillAndSubmitRegistrationForm(username, password);
    }

    @When("^an already existing username \"([^\"]*)\" and a valid password \"([^\"]*)\" are entered$")
    public void an_already_existing_username_and_a_valid_password_are_entered(String username, String password) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        registerUserPage.fillAndSubmitRegistrationForm(username, password);
    }

    @When("^a valid username \"([^\"]*)\" and password \"([^\"]*)\" but a nonmatching confirmation \"([^\"]*)\" are entered$")
    public void a_valid_username_and_password_but_a_nonmatching_confirmation_are_entered(String username, String password, String passwordConfirmation) throws Throwable {
        registerUserPage.fillAndSubmitRegistrationForm(username, password, passwordConfirmation);
    }

    @Given("^user with username \"([^\"]*)\" with password \"([^\"]*)\" is successfully created$")
    public void user_with_username_with_password_is_successfully_created(String username, String password) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        driver.get(baseUrl);
        frontPage.clickRegisterNewUser();
        registerUserPage.fillAndSubmitRegistrationForm(username, password);
        firstTimeLoginPage.clickContinueToMainPage();
        mainPage.clickLogout();
    }

    @Given("^user with username \"([^\"]*)\" and password \"([^\"]*)\" is tried to be created$")
    public void user_with_username_and_password_is_tried_to_be_created(String username, String password) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        driver.get(baseUrl);
        frontPage.clickRegisterNewUser();
        registerUserPage.fillAndSubmitRegistrationForm(username, password);
    }

    @When("^username \"([^\"]*)\" that was not created tries to login with password \"([^\"]*)\"$")
    public void username_that_was_not_created_tries_to_login_with_password(String username, String password) throws Throwable {
        logInWith(username, password);
    }

    @When("^created user with username \"([^\"]*)\" and password \"([^\"]*)\" logs in$")
    public void created_user_with_username_and_password_logs_in(String username, String password) throws Throwable {
        logInWith(username, password);
    }


    @Then("^user is not created and error \"([^\"]*)\" is reported$")
    public void user_is_not_created_and_error_is_reported(String errorMessage) throws Throwable {
        assertTrue(driver.getPageSource().contains(errorMessage));
    }

    @After
    public void tearDown(){
        driver.quit();
    }
        
    /* helper methods */
 
    private void pageHasContent(String content) {
        assertTrue(driver.getPageSource().contains(content));
    }
        
    private void logInWith(String username, String password) {
        assertTrue(driver.getPageSource().contains("Give your credentials to login"));
        WebElement element = driver.findElement(By.name("username"));
        element.sendKeys(username);
        element = driver.findElement(By.name("password"));
        element.sendKeys(password);
        element = driver.findElement(By.name("login"));
        element.submit();  
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

    public void fillAndSubmitRegistrationForm(String username, String password, String passwordConfirmation) {
        inputUsername(username);
        inputPassword(password);
        inputPasswordConfirmation(passwordConfirmation);
        submitRegistrationForm();
    }

    public void fillAndSubmitRegistrationForm(String username, String password) {
        inputUsername(username);
        inputPassword(password);
        inputPasswordConfirmation(password);
        submitRegistrationForm();
    }


}

class FirstTimeLoginPage {

    private WebDriver driver;

    FirstTimeLoginPage(WebDriver driver) {
        this.driver = driver;
    }

    public void clickContinueToMainPage() {
        continueToMainPageLink().click();
    }

    public WebElement continueToMainPageLink() {
        return driver.findElement(By.linkText("continue to application mainpage"));
    }

}

class MainPage {

    private WebDriver driver;

    MainPage(WebDriver driver) {
        this.driver = driver;
    }

    public void clickLogout() {
        WebElement element = driver.findElement(By.linkText("logout"));
        element.click();
    }
}
