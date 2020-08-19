package be.surin.web;

import be.surin.engine.Event;
import be.surin.engine.HourMin;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

public class HyperPlanning2020Scraper {
    static final String url = "https://hplanning2020.umons.ac.be/invite";

    public static void main(String[] args) throws IOException, IllegalArgumentException, InterruptedException {
        ArrayList<Event> events = method(LocalDate.of(2020, 8, 18));
        for (Event e : events)
            System.out.println(e.toString());
    }

    public static ArrayList<Event> method(LocalDate date) throws IllegalArgumentException, InterruptedException {

        // As a part of preparing the workflow, collect the relevant XPaths
        String buttonEdit = "//*[@id=\"GInterface.Instances[1].Instances[1].bouton_Edit\"]";
        String scrollBar = "//*[@id=\"GInterface.Instances[1].Instances[1]_ContenuScroll\"]";
        String BAB2INFO = "//*[@id=\"GInterface.Instances[1].Instances[1]_37\"]";
        String BAB2MATH = "//*[@id=\"GInterface.Instances[1].Instances[1]_38\"]";
        String[] allCourses = new String[270];
        for (int i = 0; i < allCourses.length; i++)
            allCourses[i] = "//*[@id=\"GInterface.Instances[1].Instances[1]_" + i + "\"]";

        String gridCourses = "//*[@id=\"GInterface.Instances[1].Instances[8]_Grille_Elements\"]";

        // Open a new browser window
        System.setProperty("webdriver.gecko.driver", "C:\\Users\\Maximilien\\Desktop\\geckodriver.exe");
        FirefoxDriver driver = new FirefoxDriver();
        WebDriverWait wait = new WebDriverWait(driver, 100);
        driver.navigate().to(url);

        // Click the scrollbar (after it's loaded)
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(buttonEdit)));
        driver.findElement(By.xpath(buttonEdit)).click();

        // Find the BAB2 Info button and click it
        Actions actions = new Actions(driver);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(scrollBar)));
        WebElement button = driver.findElement(By.xpath(BAB2INFO));
        button.click();

        // Count the number of events in the 1st week
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(gridCourses)));
        WebElement course;
        int cnt = 0;
        do {
            try {
                course = driver.findElement(By.xpath("//*[@id=\"id_70_cours_" + cnt + "\"]"));
                cnt++;
            }
            catch (org.openqa.selenium.NoSuchElementException e) {
                break;
            }
        } while (true);

        // Transform the courses into events
        ArrayList<Event> array = new ArrayList<>();

        for (int i = 1; i <= cnt; i++) {
            LocalDate fromDate = date;
            LocalDate toDate = date;
            HourMin fromHour = new HourMin(10, 30);
            HourMin toHour = new HourMin(12, 30);

            String name;
            try {
                String pathName = "/html/body/div[3]/div[1]/table/tbody/tr[2]/td/div/table/tbody/tr[2]/td/div[1]/div[1]/div[2]/div[2]/div/div/div[1]/div[4]/div[" + i + "]/div/table/tbody/tr/td/div[1]";
                name = driver.findElement(By.xpath(pathName)).getText();
            }
            catch (org.openqa.selenium.NoSuchElementException e) {
                name = "";
            }

            String description = "";
            int j = 2;
            do {
                try {
                    String pathDesc = "/html/body/div[3]/div[1]/table/tbody/tr[2]/td/div/table/tbody/tr[2]/td/div[1]/div[1]/div[2]/div[2]/div/div/div[1]/div[4]/div[" + i + "]/div/table/tbody/tr/td/div[" + j + "]";;
                    if (description != "")
                        description += "\n";
                    description += driver.findElement(By.xpath(pathDesc)).getText();
                    j++;
                }
                catch (org.openqa.selenium.NoSuchElementException e) {
                    break;
                }
            } while (true);

            Event event = new Event(fromDate, toDate, fromHour, toHour, name, description);
            array.add(event);
        }

        // Research of the name of a course
        // /html/body/div[3]/div[1]/table/tbody/tr[2]/td/div/table/tbody/tr[2]/td/div[1]/div[1]/div[2]/div[2]/div/div/div[1]/div[4]/div[8]/div/table/tbody/tr/td/div[1]
        // /html/body/div[3]/div[1]/table/tbody/tr[2]/td/div/table/tbody/tr[2]/td/div[1]/div[1]/div[2]/div[2]/div/div/div[1]/div[4]/div[4]/div/table/tbody/tr/td/div[1]
        // /html/body/div[3]/div[1]/table/tbody/tr[2]/td/div/table/tbody/tr[2]/td/div[1]/div[1]/div[2]/div[2]/div/div/div[1]/div[4]/div[4]/div/table/tbody/tr/td/div[1]
        // /html/body/div[3]/div[1]/table/tbody/tr[2]/td/div/table/tbody/tr[2]/td/div[1]/div[1]/div[2]/div[2]/div/div/div[1]/div[4]/div[12]/div/table/tbody/tr/td/div[1]
        // /html/body/div[3]/div[1]/table/tbody/tr[2]/td/div/table/tbody/tr[2]/td/div[1]/div[1]/div[2]/div[2]/div/div/div[1]/div[4]/div[13]/div/table/tbody/tr/td/div[1]

        // Observe the victory !
        Thread.sleep(7000);

        // Close the browser window
        driver.close();

        return array;
    }
}
