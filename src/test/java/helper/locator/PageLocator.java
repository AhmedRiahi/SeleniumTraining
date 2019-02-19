package helper.locator;

import lombok.Data;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@Data
@XmlRootElement
public class PageLocator {

    private String name;
    private List<DomElement> domElements;


    public DomElement getDomElementByName(String name){
        return this.domElements.stream().filter(domElement -> domElement.getName().equalsIgnoreCase(name)).findFirst().orElseThrow(() -> new RuntimeException("unknown Dom Element :"+name));
    }
}
