package be.surin.web;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.*;
import java.util.List;

import static be.surin.web.HyperPlanning2020Scraper.phantomJSPath;

public class HyperPlanning2020Collector {

    static final String url = "https://hplanning2020.umons.ac.be/invite";
    static final String geckoDriverPath = "src\\main\\resources\\geckodriver.exe";
    static final String chromeDriverPath = "src\\main\\resources\\chromedriver.exe";

    public static void main(String[] args) throws IOException {
        getCourse();
    }

    public static void getCourse() throws IOException {
        String BAB1MATH = "//*[@id=\"GInterface.Instances[1].Instances[1]_15\"]";
        String BAB2MATH = "//*[@id=\"GInterface.Instances[1].Instances[1]_39\"]";
        String BAB3MATH = "//*[@id=\"GInterface.Instances[1].Instances[1]_62\"]";

        String BAB1INFO = "//*[@id=\"GInterface.Instances[1].Instances[1]_14\"]";
        String BAB2INFO = "//*[@id=\"GInterface.Instances[1].Instances[1]_38\"]";
        String BAB3INFO = "//*[@id=\"GInterface.Instances[1].Instances[1]_61\"]";

        String[] optionArray = {
                //math
                "//*[@id=\"GInterface.Instances[1].Instances[1]_15\"]",
                "//*[@id=\"GInterface.Instances[1].Instances[1]_39\"]",
                "//*[@id=\"GInterface.Instances[1].Instances[1]_62\"]",
                //info
                "//*[@id=\"GInterface.Instances[1].Instances[1]_14\"]",
                "//*[@id=\"GInterface.Instances[1].Instances[1]_38\"]",
                "//*[@id=\"GInterface.Instances[1].Instances[1]_61\"]"
        };

        /*
        BAC1-Physique
-
BAC2-Physique
-
BAC3-Physique
-
BAC1-Biologie
-
BAC2-Biologie
-
BAC3-Biologie
-
BAC1-Chimie
-
BAC2-Chimie
-
BAC3-Chimie
         */
        String buttonEdit = "//*[@id=\"GInterface.Instances[1].Instances[1].bouton_Edit\"]";
        String scrollBar = "//*[@id=\"GInterface.Instances[1].Instances[1]_ContenuScroll\"]";
        String weekBar = "//*[@id=\"GInterface.Instances[1].Instances[4]_Calendrier\"]";
        String recap = "/html/body/div[3]/div[1]/table/tbody/tr[1]/td/div[3]/div[1]/ul/li[2]";

        String table = "/html/body/div[3]/div/table/tbody/tr[2]/td/table/tbody/tr[2]/td/div/div[2]/div/div[1]/table[1]/tbody"; //tbody
        String table2 = "/html/body/div[3]/div[1]/table/tbody/tr[2]/td/table/tbody/tr[2]/td/div/div[2]/div/div[1]/table[1]"; //table class
        String table3 = "/html/body/div[3]/div[1]/table/tbody/tr[2]/td/table/tbody/tr[2]/td/div/div[2]/div/div[1]/table[1]/tbody/tr[2]"; //cell

        System.setProperty("webdriver.gecko.driver", geckoDriverPath);

        FirefoxOptions fxOption = new FirefoxOptions();
        fxOption.addArguments("--headless", "--disable-gpu", "--window-size=1920,1200","--ignore-certificate-errors");
        FirefoxDriver driver = new FirefoxDriver(fxOption);

        WebDriverWait wait = new WebDriverWait(driver, 100);
        driver.navigate().to(url);

        //TODO only for BAB2 math for now, change i=1 to i=0 and uncomment optionArray.length for full loop over BaB1 to 3 math and info
        for(int i=0; i<optionArray.length; i++) {

            // Click the scrollbar (after it's loaded)
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath(buttonEdit)));
            driver.findElement(By.xpath(buttonEdit)).click();

            // Find the option button and click it
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(scrollBar)));
            driver.findElement(By.xpath(optionArray[i])).click();

            // Click to go to "récapitulatif des cours"
            wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath(recap)));
            driver.findElement(By.xpath(recap)).click();
            List<WebElement> rows = driver.findElements(By.xpath(table));
            FileUtils.writeStringToFile(new File("src\\main\\resources\\course"+i+".txt"), rows.get(0).getText());

        }
        driver.quit();




    }
}
