package cucumber;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import helper.PropertiesManager;
import helper.locator.PageLocator;
import helper.locator.PageLocatorsParser;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Random;

@Slf4j
public class CustomerTest {

    private WebDriver driver;
    private PageLocator customerPageLocator;
    private PageLocator loginPageLocator;

    @Before
    public void before(){
        System.setProperty("webdriver.chrome.driver","/root/selenium/chromedriver");
        this.driver = new ChromeDriver();
        PropertiesManager.getInstance().loadProperties();
        PageLocatorsParser.getInstance().parsePageLocators();
        this.customerPageLocator = PageLocatorsParser.getInstance().getPageLocatorByName("Customer page");
        this.loginPageLocator = PageLocatorsParser.getInstance().getPageLocatorByName("Login page");
    }

    @After
    public void after(Scenario scenario){
        if(scenario.isFailed()){
            try {
                File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
                File targetFile = new File(  scenario.getName() + ".png");
                Files.copy(scrFile.toPath(), targetFile.toPath());
            } catch (IOException e) {
                log.error(e.getMessage(),e);
            }
        }
        this.driver.quit();
    }

    @Given("^user already connected$")
    public void user_already_connected() throws Throwable {
        this.driver.get(PropertiesManager.getInstance().getPropertyValue("login.url"));
        WebElement usernameInput = this.driver.findElement(loginPageLocator.dom("username").primaryBy());
        WebElement passwordInput = this.driver.findElement(loginPageLocator.dom("password").primaryBy());
        usernameInput.sendKeys(PropertiesManager.getInstance().getPropertyValue("login.username"));
        passwordInput.sendKeys(PropertiesManager.getInstance().getPropertyValue("login.password"));
        WebElement loginButton = this.driver.findElement(By.name("btnLogin"));
        loginButton.click();
    }

    @When("^user click on New Customer button$")
    public void user_navigate_to_page() throws Throwable {
        this.driver.findElement(customerPageLocator.dom("new_customer_button").primaryBy()).click();
    }

    @When("^user set customer name as \"([^\"]*)\"$")
    public void user_set_customer_name_as(String name) throws Throwable {
        this.driver.findElement(customerPageLocator.dom("customer_name").primaryBy()).sendKeys(name);
    }

    @When("^Gender as \"([^\"]*)\"$")
    public void gender_as(String gender) throws Throwable {
        this.driver.findElement(customerPageLocator.dom("gender").primaryBy()).sendKeys(gender);
    }

    @When("^Date of birth as \"([^\"]*)\"$")
    public void date_of_birth_as(String dob) throws Throwable {
        this.driver.findElement(customerPageLocator.dom("dob").primaryBy()).sendKeys(dob);
    }

    @When("^adress as \"([^\"]*)\"$")
    public void adress_as(String addr) throws Throwable {
        this.driver.findElement(customerPageLocator.dom("addr").primaryBy()).sendKeys(addr);
    }

    @When("^city as \"([^\"]*)\"$")
    public void city_as(String city) throws Throwable {
        this.driver.findElement(customerPageLocator.dom("city").primaryBy()).sendKeys(city);
    }

    @When("^state as \"([^\"]*)\"$")
    public void state_as(String state) throws Throwable {
        this.driver.findElement(customerPageLocator.dom("state").primaryBy()).sendKeys(state);
    }

    @When("^pin as \"([^\"]*)\"$")
    public void pin_as(String pin) throws Throwable {
        this.driver.findElement(customerPageLocator.dom("pin").primaryBy()).sendKeys(pin);
    }

    @When("^mobile number as \"([^\"]*)\"$")
    public void mobile_number_as(String mobile) throws Throwable {
        this.driver.findElement(customerPageLocator.dom("mobile").primaryBy()).sendKeys(mobile);
    }

    @When("^email as \"([^\"]*)\"$")
    public void email_as(String email) throws Throwable {
        this.driver.findElement(customerPageLocator.dom("email").primaryBy()).sendKeys(email+ new Random().nextLong());
    }

    @When("^password as \"([^\"]*)\"$")
    public void password_as(String password) throws Throwable {
        this.driver.findElement(customerPageLocator.dom("password").primaryBy()).sendKeys(password);
    }

    @When("^click on submit button$")
    public void click_on_submit_button() throws Throwable {
        this.driver.findElement(customerPageLocator.dom("submit_button").primaryBy()).click();
    }

    @Then("^customer details should be displayed$")
    public void customer_details_should_be_displayed() throws Throwable {
        this.driver.findElement(customerPageLocator.dom("registration_message").primaryBy()).getText().equalsIgnoreCase("Customer Registered Successfully!!!");
    }
}
