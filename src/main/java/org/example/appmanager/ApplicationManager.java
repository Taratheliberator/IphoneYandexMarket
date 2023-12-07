package org.example.appmanager;

import com.codeborne.selenide.Configuration;
import org.example.pageobjects.YandexPage;
import static com.codeborne.selenide.Selenide.*;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class ApplicationManager {

    private final Properties properties;
    private YandexPage yandexPage;

    public ApplicationManager() {
        properties = new Properties();
    }

    public void init() throws IOException {
        properties.load(new FileReader(new File("src/test/resources/test.properties")));
        Configuration.baseUrl = properties.getProperty("web.baseUrl");
        Configuration.timeout = 30000;
        Configuration.browserSize = "1920x1080";
        open(Configuration.baseUrl);
        yandexPage = new YandexPage();
    }
    public void stop() {
        closeWebDriver();
    }
    public YandexPage yandex() {
        return yandexPage;
    }
}

