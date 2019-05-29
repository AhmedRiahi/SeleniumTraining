import org.junit.Test;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Date;
import java.util.HashMap;

public class FakeTest {

    @Test
    public void fakeTest() throws IOException, InterruptedException {
        System.setProperty("webdriver.chrome.driver","/usr/bin/chromedriver");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("start-maximized"); // open Browser in maximized mode
        options.addArguments("disable-infobars"); // disabling infobars
        options.addArguments("--disable-extensions"); // disabling extensions
        options.addArguments("--disable-gpu"); // applicable to windows os only
        options.addArguments("--disable-dev-shm-usage"); // overcome limited resource problems
        options.addArguments("--no-sandbox"); // Bypass OS security model
        options.addArguments("--headless");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");

        String downloadFilepath = "/root/download/";

        options.addArguments("download.default_directory="+downloadFilepath);
        HashMap<String, Object> chromePrefs = new HashMap<String, Object>();
        chromePrefs.put("profile.default_content_settings.popups", 0);
        chromePrefs.put("download.default_directory", downloadFilepath);
        chromePrefs.put("download.prompt_for_download", false);
        chromePrefs.put("download.directory_upgrade", true);
        chromePrefs.put("safebrowsing.enabled", false);
        chromePrefs.put("safebrowsing.disable_download_protection", true);

        options.setExperimentalOption("prefs", chromePrefs);


        WebDriver driver = new ChromeDriver(options);
        driver.get("https://github.com/AhmedRiahi/SeleniumTraining/archive/master.zip");
        File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        File targetFile = new File(  "fake_test"+new Date() + ".png");
        Files.copy(scrFile.toPath(), targetFile.toPath());
        Thread.sleep(5*1000);
    }
}
