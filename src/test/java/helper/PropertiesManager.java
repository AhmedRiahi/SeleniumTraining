package helper;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertiesManager {

    private static PropertiesManager instance;
    private Properties properties = new Properties();
    private final String propertiesFilePath = "technical.properties";

    private PropertiesManager(){ }

    public static PropertiesManager getInstance(){
        if(PropertiesManager.instance == null){
            PropertiesManager.instance = new PropertiesManager();
            PropertiesManager.instance.loadProperties();
        }
        return PropertiesManager.instance;
    }

    public void loadProperties(){
        try {
            this.properties.load(new FileInputStream(PropertiesManager.instance.propertiesFilePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public String getPropertyValue(String propertyName){
        return this.properties.getProperty(propertyName);
    }


}
