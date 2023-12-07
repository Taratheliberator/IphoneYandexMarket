package org.example.pageobjects;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Attachment;
import org.openqa.selenium.By;
import io.qameta.allure.Step;
import org.openqa.selenium.OutputType;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Condition.*;

/**
 * Page Object класс для взаимодействия со страницей Яндекс Маркет.
 */
public class YandexPage {

    private SelenideElement marketButton = $x("//span[contains(text(),'Каталог')]");
    private SelenideElement electronicsMenu = $x("(//span[contains(text(),'Электроника')])[2]");
    private SelenideElement smartphonesButton = $(By.linkText("Смартфоны"));
    private SelenideElement allFilters = $x("//span[contains(.,'Все фильтры')]");
    private SelenideElement resultsButton = $x("//a[contains(text(),'Показать')]");
    private SelenideElement showAs = $x("//button[contains(.,'Показывать по')]");
    private SelenideElement searchField = $(By.name("text"));

    private ElementsCollection noteList = $$x("//*[@data-autotest-id='offer-snippet' or @data-autotest-id='product-snippet']");

    /**
     * Переходит в раздел 'Каталог' на Яндекс Маркете.
     *
     * @return YandexPage объект, представляющий текущую страницу.
     */
    @Step("Переход в раздел 'Каталог'")
    public YandexPage goToMarket() {
        marketButton.click();
        return this;
    }

    /**
     * Наводит курсор на пункт меню 'Электроника'.
     *
     * @return YandexPage объект, представляющий текущую страницу.
     */
    @Step("Переход в раздел 'Электроника'")
    public YandexPage goToElectronics() {
        electronicsMenu.hover();
        return this;
    }

    /**
     * Кликает на ссылку 'Смартфоны'.
     *
     * @return YandexPage объект, представляющий текущую страницу.
     */
    @Step("Переход в раздел 'Смартфоны'")
    public YandexPage goSmartphones() {
        smartphonesButton.shouldBe(visible).click();
        return this;
    }

    /**
     * Кликает на кнопку 'Все фильтры'.
     *
     * @return YandexPage объект, представляющий текущую страницу.
     */
    @Step("Открытие фильтра")
    public YandexPage openFilter() {
        allFilters.click();
        return this;
    }

    /**
     * Кликает на кнопку 'Показать' для отображения результатов по фильтрам.
     *
     * @return YandexPage объект, представляющий текущую страницу.
     */
    @Step("Показ результатов")
    public YandexPage showResults() {
        resultsButton.click();
        return this;
    }

    /**
     * Устанавливает фильтр по имени производителя смартфона.
     *
     * @param name Имя производителя для фильтрации.
     * @return YandexPage объект, представляющий текущую страницу.
     */
    @Step("Установка имени производителя: {0}")
    public YandexPage setVendorName(String name) {
        $x(String.format("//label[contains(.,'%s')]", name)).click();
        return this;
    }

    /**
     * Получает список элементов смартфонов на странице.
     *
     * @return ElementsCollection коллекция элементов смартфонов.
     */
    @Step("Получение списка элементов")
    public ElementsCollection getList() {
        return noteList;
    }

    /**
     * Делает скриншот текущего состояния страницы.
     *
     * @return byte[] массив байтов скриншота.
     */
    @Attachment(value = "Скриншот списка смартфонов", type = "image/png")
    private byte[] takeScreenshot() {
        return Selenide.screenshot(OutputType.BYTES);
    }

    /**
     * Загружает все смартфоны, доступные на странице, путем нажатия на кнопку 'Показать еще'.
     * Повторяется до тех пор, пока кнопка доступна.
     *
     * @throws InterruptedException если поток исполнения прерван во время ожидания.
     */
    @Step("Загрузка смартфонов")
    public void loadAllSmartphones() throws InterruptedException {
        int loadedSmartphonesCount = 0;

        while (true) {

            ElementsCollection showMoreButtons = $$("button[data-auto='pager-more']").filter(Condition.visible);

            if (showMoreButtons.isEmpty()) {
                System.out.println("Достигнут конец списка. Всего загружено смартфонов: " + loadedSmartphonesCount);
                break;
            }

            showMoreButtons.first().click();
            Thread.sleep(1000);
            ElementsCollection noteList = $$x("//*[@data-autotest-id='offer-snippet' or @data-autotest-id='product-snippet']");
            loadedSmartphonesCount = noteList.size();
            System.out.println("Загружено смартфонов на текущий момент: " + loadedSmartphonesCount);
            takeScreenshot();

        }
    }

    /**
     * Проверяет, является ли название смартфона моделью iPhone.
     *
     * @param element Название элемента для проверки.
     * @return boolean true, если элемент содержит название 'iphone'.
     */
    public static boolean isSmartphoneValid(String element) {
        String lowerCaseElement = element.toLowerCase();
        return lowerCaseElement.contains("iphone");
    }
}


