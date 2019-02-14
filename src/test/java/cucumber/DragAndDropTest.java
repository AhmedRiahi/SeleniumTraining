package cucumber;

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
import org.openqa.selenium.interactions.Actions;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class DragAndDropTest {

    private WebDriver driver;
    private String draggableItemName;
    private WebElement draggableElement;
    private WebElement targetDraggableElements;

    @Before
    public void before(){
        System.setProperty("webdriver.chrome.driver","C:\\soft\\chromedriver_win32\\chromedriver.exe");
        this.driver = new ChromeDriver();
        this.driver.get("https://www.seleniumeasy.com/test/drag-and-drop-demo.html");

    }

    @After
    public void after(){
        this.driver.quit();
    }

    @Given("^item \"([^\"]*)\"$")
    public void item(String draggableItemName) throws Throwable {
        this.draggableItemName = draggableItemName;
        this.draggableElement  =this.driver.findElements(By.cssSelector("#todrag > span")).stream().filter(element -> element.getText().equals(draggableItemName)).findAny().get();

    }

    @When("^user drag and drop the item$")
    public void user_drag_and_drop_the_item() throws Throwable {
        this.targetDraggableElements = this.driver.findElement(By.id("mydropzone"));
        Actions actions = new Actions(this.driver);
        actions.clickAndHold(this.draggableElement).build().perform();
        TimeUnit.SECONDS.sleep(5);
        actions.moveToElement(this.targetDraggableElements).build().perform();
        TimeUnit.SECONDS.sleep(5);
        actions.moveByOffset(-1, -1).build().perform();
        TimeUnit.SECONDS.sleep(5);
        actions.release().build().perform();
    }

    @Then("^item should be removed from draggable list$")
    public void item_should_be_removed_from_draggable_list() throws Throwable {
        List<String> draggableItemText =  this.driver.findElements(By.cssSelector("#todrag > span")).stream().map(WebElement::getText).collect(Collectors.toList());
        Assertions.assertThat(draggableItemText.stream().anyMatch(itemText -> itemText.equals(this.draggableItemName))).isFalse();
    }

    @Then("^item should be added to dropped item list$")
    public void item_should_be_added_to_dropped_item_list() throws Throwable {
        List<String> draggableItemText =  this.driver.findElements(By.cssSelector("#droppedlist > span")).stream().map(WebElement::getText).collect(Collectors.toList());
        Assertions.assertThat(draggableItemText.stream().anyMatch(itemText -> itemText.equals(this.draggableItemName))).isTrue();
    }
}
