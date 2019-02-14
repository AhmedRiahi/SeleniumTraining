package cucumber;

import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.assertj.core.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class SeleniumEasyExample {

    private WebDriver driver;
    private String inputMessage;


    @Before
    public void before(){
        System.setProperty("webdriver.chrome.driver","C:\\soft\\chromedriver_win32\\chromedriver.exe");
        this.driver = new ChromeDriver();
    }

    @After
    public void after(){
        this.driver.quit();
    }

    @Given("^an input message \"([^\"]*)\"$")
    public void an_input_message(String input) throws Throwable {
        this.driver.get("https://www.seleniumeasy.com/test/basic-first-form-demo.html");
        this.inputMessage = input;
        this.driver.findElement(By.id("user-message")).sendKeys(input);
    }

    @When("^user click on <show message> button$")
    public void user_click_on_show_message_button() throws Throwable {
        this.driver.findElement(By.cssSelector(".btn:nth-child(2)")).click();
    }

    @Then("^input message should be displayed$")
    public void input_message_should_be_displayed() throws Throwable {
        String displayText = this.driver.findElement(By.id("display")).getText();
        Assertions.assertThat(displayText).isEqualTo(this.inputMessage);
    }
}
