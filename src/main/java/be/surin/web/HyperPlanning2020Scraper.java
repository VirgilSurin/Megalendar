package be.surin.web;

import be.surin.engine.Event;
import be.surin.engine.HourMin;
import be.surin.engine.TAG;
import be.surin.gui.AppLauncher;
import be.surin.gui.CalendarScene;
import javafx.application.Application;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HyperPlanning2020Scraper {
    static final String url = "https://hplanning2020.umons.ac.be/invite";
    static final String geckoDriverPath = "src\\main\\resources\\geckodriver.exe";
    static final String phantomJSPath = "src\\main\\resources\\phantomjs-2.1.1-windows\\bin\\phantomjs.exe";
    static final String chromeDriverPath = "src\\main\\resources\\chromedriver.exe";

    /* DO NOT ERASE THIS CODE

    public static void main(String[] args) throws IOException, IllegalArgumentException, InterruptedException {
        ArrayList<Event> events = method(LocalDate.of(2020, 8, 18));
        for (Event e : events)
            System.out.println(e.toString());
    }

    public static ArrayList<Event> method(LocalDate date) throws IllegalArgumentException, InterruptedException {

        // As a part of preparing the workflow, collect the relevant XPaths
        String buttonEdit = "//*[@id=\"GInterface.Instances[1].Instances[1].bouton_Edit\"]";
        String scrollBar = "//*[@id=\"GInterface.Instances[1].Instances[1]_ContenuScroll\"]";
        String BAB2INFO = "//*[@id=\"GInterface.Instances[1].Instances[1]_38\"]";
        String BAB2MATH = "//*[@id=\"GInterface.Instances[1].Instances[1]_39\"]";
        String[] allCourses = new String[270];
        for (int i = 0; i < allCourses.length; i++)
            allCourses[i] = "//*[@id=\"GInterface.Instances[1].Instances[1]_" + i + "\"]";

        String gridCourses = "//*[@id=\"GInterface.Instances[1].Instances[8]_Grille_Elements\"]";

        // Open a new browser window
        System.setProperty("webdriver.gecko.driver", "..\\geckodriver.exe");
        FirefoxDriver driver = new FirefoxDriver();
        WebDriverWait wait = new WebDriverWait(driver, 100);
        driver.navigate().to(url);

        // Click the scrollbar (after it's loaded)
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(buttonEdit)));
        driver.findElement(By.xpath(buttonEdit)).click();

        // Find the BAB2 Info button and click it
        Actions actions = new Actions(driver);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(scrollBar)));
        WebElement button = driver.findElement(By.xpath(BAB2MATH));
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

     */

    public static void main(String[] args) throws IOException, InterruptedException {
        ArrayList<Event> arrayList = importEvents();
        for (Event e : arrayList)
            System.out.println(e.toString());
    }

    public static void refreshEvents() {
        ArrayList<Event> importedEvents = importEvents();
        cleanEvents();
        AppLauncher.currentProfile.getAgenda().getEventList().addAll(importedEvents);
        CalendarScene.getEventView().refresh();
    }

    private static void cleanEvents() {
        ArrayList<Event> dirtyList = AppLauncher.currentProfile.getAgenda().getEventList();
        dirtyList.removeIf(event -> (event.getTag().equals(TAG.BLUE)));
    }

    private static ArrayList<Event> importEvents() {

        // Relevant XPaths used
        String buttonEdit = "//*[@id=\"GInterface.Instances[1].Instances[1].bouton_Edit\"]";
        String scrollBar = "//*[@id=\"GInterface.Instances[1].Instances[1]_ContenuScroll\"]";
        String BAB2INFO = "//*[@id=\"GInterface.Instances[1].Instances[1]_38\"]";
        String weekBar = "//*[@id=\"GInterface.Instances[1].Instances[4]_Calendrier\"]";

        // Open a new browser window
        System.setProperty("webdriver.gecko.driver", geckoDriverPath);

        //phantom driver
        /*
        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setJavascriptEnabled(true);
        caps.setCapability("takesScreenshot", true);
        caps.setCapability(
                PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY,
                phantomJSPath);
        WebDriver driver = new  PhantomJSDriver(caps);
        */
        //chrome headless driver
        /*
        System.setProperty("webdriver.chrome.driver", chromeDriverPath);
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless", "--disable-gpu", "--window-size=1920,1200","--ignore-certificate-errors");
        WebDriver driver = new ChromeDriver(options);
         */
        FirefoxOptions fxOption = new FirefoxOptions();
        fxOption.addArguments("--headless");
        FirefoxDriver driver = new FirefoxDriver(fxOption);
        WebDriverWait wait = new WebDriverWait(driver, 100);
        driver.navigate().to(url);


        // Click the scrollbar (after it's loaded)
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(buttonEdit)));
        driver.findElement(By.xpath(buttonEdit)).click();

        // Find the BAB2 Info button and click it
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(scrollBar)));
        driver.findElement(By.xpath(BAB2INFO)).click();

        // Get the courses of every week
        ArrayList<Event> arrayList = new ArrayList<>();
        for (int week = 1; week <= 52; week++) {
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(weekBar)));
            String weekPath = "//*[@id=\"GInterface.Instances[1].Instances[4]_j_" + week + "\"]";
            WebElement weekButton = driver.findElement(By.xpath(weekPath));
            String cls = weekButton.getAttribute("class");
            // To avoid the blocked weeks or weeks without courses
            if (cls.contains("Calendrier_Jour_AvecContenu")) {
                driver.findElement(By.xpath(weekPath)).click();
                arrayList.addAll(getCoursesOfWeek(wait, driver));
            }
        }

        // Close the browser window
        driver.close();

        return arrayList;
    }

    public static ArrayList<Event> getCoursesOfWeek(WebDriverWait wait, WebDriver driver) {

        // Relevant XPaths used
        String gridCourses = "//*[@id=\"GInterface.Instances[1].Instances[8]_Grille_Elements\"]";

        // Count the number of events in the week (to avoid NoSuchElementExceptions later)
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

        // Get the first day of the week
        String firstDayPath = "/html/body/div[3]/div[1]/table/tbody/tr[2]/td/div/table/tbody/tr[2]/td/div[1]/div[1]/div[1]/div[2]/div/div/div[1]/div";
        String firstDayStr = driver.findElement(By.xpath(firstDayPath)).getText();
        String[] firstDayTab = firstDayStr.split(" ");
        int dayOfMonth = Integer.parseInt(firstDayTab[1]);
        int month = 9;
        int year = 2021;
        switch (firstDayTab[2]) {
            case "septembre" :
                month = 9;
                year = 2020;
                break;
            case "octobre" :
                month = 10;
                year = 2020;
                break;
            case "novembre" :
                month = 11;
                year = 2020;
                break;
            case "décembre" :
                month = 12;
                year = 2020;
                break;
            case "janvier" :
                month = 1;
                break;
            case "février" :
                month = 2;
                break;
            case "mars" :
                month = 3;
                break;
            case "avril" :
                month = 4;
                break;
            case "mai" :
                month = 5;
                break;
            case "juin" :
                month = 6;
                break;
            case "juillet" :
                month = 7;
                break;
            case "août" :
                month = 8;
                break;
        }
        LocalDate firstDayOfWeek = LocalDate.of(year, month, dayOfMonth);

        // Get the width in pixel of a day by looking at the top
        String dayStyle = driver.findElement(By.xpath("//*[@id=\"id_67_titreTranche0\"]")).getAttribute("style");
        Pattern pattern = Pattern.compile("[0-9]+");
        Matcher matcher = pattern.matcher(dayStyle);
        int widthOfADay = 0;
        if (matcher.find()) {
            widthOfADay = Integer.parseInt(matcher.group());
        }

        // Get the courses of the week one by one and add them into an ArrayList
        // It also converts the elements from the HTML page into an usable event
        ArrayList<Event> arrayList = new ArrayList<>();
        for (int i = 1; i <= cnt; i++) {

            // Get the left margin of the day (to know in witch day column it is)
            // Since we know the width of an unique column
            String style = driver.findElement(By.xpath("//*[@id=\"id_70_cours_" + (i-1) + "\"]")).getAttribute("style");
            Pattern stylePattern = Pattern.compile("left: -?[0-9]+px");
            Matcher styleMatcher = stylePattern.matcher(style);
            String styleStr = "";
            if (styleMatcher.find()) {
                styleStr = styleMatcher.group();
            }
            Pattern pixelPattern = Pattern.compile("[0-9]+");
            Matcher pixelMatcher = pixelPattern.matcher(styleStr);
            int leftPixel = 0;
            if (pixelMatcher.find()) {
                leftPixel = Integer.parseInt(pixelMatcher.group());
            }

            // Get the fromFate and toDate of the event
            LocalDate fromDate = firstDayOfWeek.plusDays(Math.round(((float) leftPixel)/ widthOfADay));
            LocalDate toDate = firstDayOfWeek.plusDays(Math.round(((float) leftPixel)/ widthOfADay));

            // Get the fromHour and the toHour of the event
            String hourStr = driver.findElement(By.xpath("//*[@id=\"id_70_coursInt_" + (i-1) + "\"]")).getAttribute("title");
            String[] hourSplit = hourStr.split(" ");
            String fromHourStr = hourSplit[1];
            String[] fromHourSplit = fromHourStr.split("h");
            HourMin fromHour = new HourMin(Integer.parseInt(fromHourSplit[0]), Integer.parseInt(fromHourSplit[1]));
            String toHourStr = hourSplit[3];
            String[] toHourSplit = toHourStr.split("h");
            HourMin toHour = new HourMin(Integer.parseInt(toHourSplit[0]), Integer.parseInt(toHourSplit[1]));

            // Increments the toDate if the event is on more than 1 day
            // This implementation only works because we suppose an event last less than 24 hours
            if (toHour.compareTo(fromHour) < 0)
                toDate = toDate.plusDays(1);

            // Get the name of the event
            String name;
            try {
                String pathName = "/html/body/div[3]/div[1]/table/tbody/tr[2]/td/div/table/tbody/tr[2]/td";
                pathName += ("/div[1]/div[1]/div[2]/div[2]/div/div/div[1]/div[4]/div[" + i + "]/div/table/tbody/tr/td/div[1]");
                name = driver.findElement(By.xpath(pathName)).getText();
            }
            catch (org.openqa.selenium.NoSuchElementException e) {
                name = "";
            }

            // Split the name on 2 lines (for comfort)
            String[] nameSplit = name.split(" - ");
            if (nameSplit.length != 0)
                name = nameSplit[0];
            if (nameSplit.length > 1)
                name += ("\n" + nameSplit[1]);
            for (int k = 2; k < nameSplit.length; k++)
                name += (" - " + (nameSplit[k]));

            // Get the description of the event
            String description = "";
            int j = 2;
            do {
                try {
                    String pathDesc = "/html/body/div[3]/div[1]/table/tbody/tr[2]/td/div/table/tbody/tr[2]/td";
                    pathDesc += ("/div[1]/div[1]/div[2]/div[2]/div/div/div[1]/div[4]/div[" + i + "]/div/table/tbody/tr/td/div[" + j + "]");
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
            // An arbitrary choice of tag, for now TAG.BLUE indicates an event from HyperPlanning
            event.setTag(TAG.BLUE);
            System.out.println(event);
            arrayList.add(event);
        }
        return arrayList;
    }
}
