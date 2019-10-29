import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.List;

public class Main {

    public static final String WEB_DRIVER_ID = "webdriver.chrome.driver";
    public static final String WEB_DRIVER_PATH = "D:/Big Tiger/dev/chromedriver_win32/chromedriver.exe";
    public static final String BASE_URL = "https://swexpertacademy.com/main/sst/intro.do";

    public static void main(String[] args) {

        System.setProperty(WEB_DRIVER_ID, WEB_DRIVER_PATH);
        ChromeOptions options = new ChromeOptions();
        options.setCapability("ignoreProtectedModeSettings", true);
        WebDriver driver = new ChromeDriver(options);

        try {
            driver.get(BASE_URL);

            List<WebElement> btn_apply_test = driver.findElements(By.className("btn_center"));
            btn_apply_test.get(3).click();

            WebElement input_id = driver.findElement(By.id("id"));
            input_id.sendKeys("kimaro45@naver.com");
            WebElement input_pwd = driver.findElement(By.id("pwd"));
            input_pwd.sendKeys("rlatjrgh09!");

            WebElement btn_login = driver.findElement(By.className("btn_login"));
            btn_login.click();

            btn_apply_test = driver.findElements(By.className("btn_center"));
            btn_apply_test.get(3).click();

            WebElement btn_next_month = driver.findElement(By.className("ui-datepicker-next"));
            btn_next_month.click();
            List<WebElement> ableDay = driver.findElements(By.className("ableDaySaturday"));
            ableDay.get(0).click();

            while(true) {
                WebElement selectedDay = driver.findElement(By.className("selectedDay"));
                selectedDay.click();

                List<WebElement> certi_time = driver.findElements(By.className("certi-time"));
                certi_time.get(0).findElement(By.tagName("a")).click();

                List<WebElement> btn_complete = driver.findElements(By.className("btn-complete"));
                //System.out.println(btn_complete.size());

                if(btn_complete.size() < 2) {
                    // push message
                    System.out.println("자리있다!");
                    break;
                }
                else {
                    boolean isEnd = false;
                    for(WebElement el : btn_complete) {
                        if(el.getText().equals("마감") == false) {
                            isEnd = true;
                            break;
                        }
                    }
                    if(isEnd) {
                        // push message
                        System.out.println("자리있다!");
                        break;
                    }
                }

                Thread.sleep(2000);
            }
        } catch (Exception e) {

        } finally {
            driver.close();
        }


    }
}
