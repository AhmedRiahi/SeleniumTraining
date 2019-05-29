import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.Test;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;


import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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

        chromePrefs.put("cmd", "Page.setDownloadBehavior");
        chromePrefs.put("behavior", "allow");

        options.setExperimentalOption("prefs", chromePrefs);


        WebDriver driver = this.enableDownload(options,downloadFilepath);

        driver.get("https://github.com/AhmedRiahi/SeleniumTraining/archive/master.zip");
        File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        File targetFile = new File(  "fake_test"+new Date() + ".png");
        Files.copy(scrFile.toPath(), targetFile.toPath());
        Thread.sleep(5*1000);
    }


    public ChromeDriver enableDownload(ChromeOptions options,String path) throws IOException {
        ChromeDriverService driverService = ChromeDriverService.createDefaultService();

        ChromeDriver driver = new ChromeDriver(driverService, options);

        Map<String, Object> commandParams = new HashMap<>();
        commandParams.put("cmd", "Page.setDownloadBehavior");

        Map<String, String> params = new HashMap<>();
        params.put("behavior", "allow");
        params.put("downloadPath", path);
        commandParams.put("params", params);

        ObjectMapper objectMapper = new ObjectMapper();
        HttpClient httpClient = HttpClientBuilder.create().build();

        String command = objectMapper.writeValueAsString(commandParams);

        String u = driverService.getUrl().toString() + "/session/" + driver.getSessionId() + "/chromium/send_command";
        System.out.println(u);
        HttpPost request = new HttpPost(u);
        request.addHeader("content-type", "application/json");
        request.setEntity(new StringEntity(command));
        HttpResponse response = httpClient.execute(request);
        System.out.println(response.toString());
        return driver;
    }
}
