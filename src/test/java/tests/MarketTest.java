package tests;

import com.codeborne.selenide.ElementsCollection;
import io.qameta.allure.Step;
import org.example.pageobjects.YandexPage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.openqa.selenium.WebElement;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static org.example.helpers.Assertions.assertTrue;

/**
 * Класс для тестирования функциональности фильтрации смартфонов на Яндекс Маркете.
 */
public class MarketTest extends TestBase {

    private YandexPage yandexPage;

    /**
     * Подготавливает окружение перед каждым тестом.
     * Инициализирует страницу Яндекс Маркета.
     */
    @BeforeEach
    public void setUp() throws Exception {
        super.setUp();
        yandexPage = app.yandex();
    }

    /**
     * Тестирование фильтрации смартфонов по заданным производителям.
     * @param vendors Список производителей для фильтрации.
     * @throws InterruptedException В случае прерывания во время ожидания загрузки элементов.
     */
    @ParameterizedTest
    @MethodSource("provideDataForMarketTest")
    @Step("Тестирование фильтрации смартфонов с параметрами: производители - {vendors}")
    public void pageMarketTest(List<String> vendors) throws InterruptedException {
        yandexPage.goToMarket()
                .goToElectronics()
                .goSmartphones()
                .openFilter();

        for (String vendor : vendors) {
            yandexPage.setVendorName(vendor);
        }
        yandexPage.showResults()
                .loadAllSmartphones();
        ElementsCollection allSmartphones = yandexPage.getList();

        for (WebElement smartphone : allSmartphones) {
            String smartphoneInfo = smartphone.getText();
            assertTrue(yandexPage.isSmartphoneValid(smartphoneInfo), "\nСмартфон не удовлетворяет условиям фильтра: \n" + smartphoneInfo);
        }
    }

    /**
     * Предоставляет данные для параметризованного теста.
     * @return Поток аргументов, содержащих списки производителей.
     */
    private static Stream<Arguments> provideDataForMarketTest() {
        return Stream.of(
                Arguments.of(Arrays.asList("Apple"))
        );
    }
}
