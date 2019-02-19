package helper.locator;

import helper.PropertiesManager;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class PageLocatorsParser {

    private final static PageLocatorsParser instance = new PageLocatorsParser();
    private Unmarshaller unmarshaller;
    private Map<String,PageLocator> pageLocatorMap;


    public static PageLocatorsParser getInstance(){
        return PageLocatorsParser.instance;
    }

    private PageLocatorsParser(){
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(PageLocator.class);
            this.unmarshaller = jaxbContext.createUnmarshaller();
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

    public void parsePageLocators(){
        List<File> locatorsFiles = this.loadLocatorsFiles();
        List<PageLocator> pageLocators = locatorsFiles.stream().map(file -> {
            try {
               return (PageLocator)this.unmarshaller.unmarshal(file);
            } catch (JAXBException e) {
                e.printStackTrace();
            }
            return null;
        }).collect(Collectors.toList());

        this. pageLocatorMap = pageLocators.stream().collect(Collectors.toMap(PageLocator::getName, Function.identity()));
    }

    private List<File> loadLocatorsFiles(){
        try {
            List<Path> locatorsPaths = Files.list(Paths.get(PropertiesManager.getInstance().getPropertyValue("locators.path"))).filter(file -> file.getFileName().toString().endsWith(".xml")).collect(Collectors.toList());
            return locatorsPaths.stream().map(Path::toFile).collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    public PageLocator getPageLocatorByName(String name){
        return this.pageLocatorMap.get(name);
    }
}
