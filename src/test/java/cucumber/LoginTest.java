package cucumber;

import cucumber.api.PendingException;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.assertj.core.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class LoginTest {

    private WebDriver driver;
    private String username;
    private String password;

    @Before
    public void before(){
        System.setProperty("webdriver.chrome.driver","C:\\soft\\chromedriver_win32\\chromedriver.exe");
        this.driver = new ChromeDriver();
    }

    @After
    public void after(){
       this.driver.quit();
    }


    @Given("^username \"([^\"]*)\" and password \"([^\"]*)\"$")
    public void giver_username_and_password(String username,String password){
        this.username = username;
        this.password = password;
    }

    @When("^user navigate to \"([^\"]*)\"$")
    public void user_navigate_to(String url) throws Throwable {
        this.driver.get(url);
    }

    @When("^user types username and password$")
    public void user_types_username_and_password() throws Throwable {
        WebElement usernameInput = this.driver.findElement(By.name("uid"));
        WebElement passwordInput = this.driver.findElement(By.name("password"));

        usernameInput.sendKeys(this.username);
        passwordInput.sendKeys(this.password);
    }

    @When("^user click on Login button$")
    public void user_click_on_Login_button() throws Throwable {
        WebElement loginButton = this.driver.findElement(By.name("btnLogin"));
        loginButton.click();
    }

    @Then("^user user should be logged correctly$")
    public void user_user_should_be_logged_correctly() throws Throwable {
        Assertions.assertThat(driver.getCurrentUrl()).isEqualTo("http://www.demo.guru99.com/V4/manager/Managerhomepage.php");
    }
}
