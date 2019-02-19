package helper.locator;

import lombok.Data;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import javax.xml.bind.annotation.XmlRootElement;

@Data
@XmlRootElement
public class DomElementLocator {

    private Locators type;
    private String value;
    private Integer priority;


    public By toSeleniumLocator(){
        switch (this.type){
            case ID: return By.id(this.value);
            case NAME: return By.name(this.value);
            default: throw new RuntimeException("Unknow selenium locator type :"+this.type);
        }
    }
}
