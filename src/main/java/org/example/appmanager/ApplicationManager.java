package org.example.appmanager;

import com.codeborne.selenide.Configuration;
import org.example.pageobjects.YandexPage;
import static com.codeborne.selenide.Selenide.*;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

/**
 * Менеджер приложения для настройки и управления тестовым окружением.
 * Загружает конфигурации из внешних файлов и инициализирует объекты страниц.
 */
public class ApplicationManager {

    private final Properties properties;
    private YandexPage yandexPage;

    /**
     * Конструктор ApplicationManager. Инициализирует свойства для конфигурации.
     */
    public ApplicationManager() {
        properties = new Properties();
    }

    /**
     * Инициализирует тестовое окружение, включая настройки браузера и загрузку конфигураций.
     * @throws IOException если произойдет ошибка при чтении файла конфигурации.
     */
    public void init() throws IOException {
        properties.load(new FileReader(new File("src/test/resources/test.properties")));
        Configuration.baseUrl = properties.getProperty("web.baseUrl");
        Configuration.timeout = 30000;
        Configuration.browserSize = "1920x1080";
        open(Configuration.baseUrl);
        yandexPage = new YandexPage();
    }

    /**
     * Закрывает веб-драйвер и освобождает ресурсы, завершая тестовое окружение.
     */
    public void stop() {
        closeWebDriver();
    }

    /**
     * Возвращает объект YandexPage для взаимодействия со страницей Яндекс Маркета.
     * @return объект YandexPage.
     */
    public YandexPage yandex() {
        return yandexPage;
    }
}
