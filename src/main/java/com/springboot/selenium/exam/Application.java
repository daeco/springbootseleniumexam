package com.springboot.selenium.exam;

import com.springboot.selenium.exam.util.MyBot;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import java.util.List;

@SpringBootApplication
public class Application {
    private static final Logger LOGGER = LogManager.getLogger(Application.class);

    public static final String WEB_DRIVER_ID = "webdriver.chrome.driver";
    public static final String WEB_DRIVER_PATH = "C:/dev/chromedriver_win32/chromedriver.exe";
    public static final String BASE_URL = "https://swexpertacademy.com/main/sst/intro.do";

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);

        ApiContextInitializer.init();
        TelegramBotsApi botsApi = new TelegramBotsApi();
        MyBot myBot = new MyBot();
        try {
            botsApi.registerBot(myBot);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }

        System.setProperty(WEB_DRIVER_ID, WEB_DRIVER_PATH);
        ChromeOptions options = new ChromeOptions();
        options.setCapability("ignoreProtectedModeSettings", true);
        options.addArguments("headless");
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

            JavascriptExecutor executor = (JavascriptExecutor)driver;
            executor.executeScript("arguments[0].click();", ableDay.get(0));

            while(true) {
                WebElement selectedDay = driver.findElement(By.className("selectedDay"));
                executor.executeScript("arguments[0].click();", selectedDay);


                List<WebElement> certi_time = driver.findElements(By.className("certi-time"));
                certi_time.get(0).findElement(By.tagName("a")).click();

                List<WebElement> btn_complete = driver.findElements(By.className("btn-complete"));

                if(btn_complete.size() < 2) {
                    // push message
                    SendMessage sn = new SendMessage();
                    sn.setChatId("1042595863");
                    sn.setText("SW 역량테스트 A형 신청 가능!");
                    myBot.execute(sn);
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
                        SendMessage sn = new SendMessage();
                        sn.setChatId("1042595863");
                        sn.setText("SW 역량테스트 A형 신청 가능!");
                        myBot.execute(sn);
                        break;
                    }
                }

                Thread.sleep(2000);
            }
        } catch (Exception e) {
            LOGGER.error("Unexpected problem encountered", e);
            SendMessage sn = new SendMessage();
            sn.setChatId("1042595863");
            sn.setText("오류로 인한 프로그램 종료");
            try {
                myBot.execute(sn);
            } catch (TelegramApiException te) {
                te.printStackTrace();
            }

        } finally {
            driver.close();
        }
    }
}
