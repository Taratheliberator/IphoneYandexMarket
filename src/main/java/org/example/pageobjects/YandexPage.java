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


public class YandexPage {

    private SelenideElement marketButton = $x("//span[contains(text(),'Каталог')]");
    private SelenideElement electronicsMenu = $x("(//span[contains(text(),'Электроника')])[2]");
    private SelenideElement smartphonesButton = $(By.linkText("Смартфоны"));
    private SelenideElement allFilters = $x("//span[contains(.,'Все фильтры')]");
    private SelenideElement resultsButton = $x("//a[contains(text(),'Показать')]");
    private SelenideElement showAs = $x("//button[contains(.,'Показывать по')]");
    private SelenideElement searchField = $(By.name("text"));

    private ElementsCollection noteList = $$x("//*[@data-autotest-id='offer-snippet' or @data-autotest-id='product-snippet']");

    public YandexPage goToMarket() {
        marketButton.click();
        return this;
    }

    public YandexPage goToElectronics() {
        electronicsMenu.hover();
        return this;
    }

    public YandexPage goSmartphones() {
        smartphonesButton.shouldBe(visible).click();
        return this;
    }

    public YandexPage openFilter() {
        allFilters.click();
        return this;
    }

    public YandexPage showResults() {
        resultsButton.click();
        return this;
    }

    public YandexPage setVendorName(String name) {
        $x(String.format("//label[contains(.,'%s')]", name)).click();
        return this;
    }

    public boolean isTargetPresent(String name) {
        return $x(String.format("//span[contains(text(),'%s')]", name)).shouldBe(visible).isDisplayed();
    }

    public ElementsCollection getList() {
        return noteList;
    }
    @Attachment(value = "Скриншот списка смартфонов", type = "image/png")
    private byte[] takeScreenshot() {
        return Selenide.screenshot(OutputType.BYTES);
    }
    @Step("Загрузка смартфонов")
    public void loadAllSmartphones() throws InterruptedException {
        int loadedSmartphonesCount = 0;

        while (true) {
            // Проверяем наличие и видимость кнопки "Показать еще"
            ElementsCollection showMoreButtons = $$("button[data-auto='pager-more']").filter(Condition.visible);

            if (showMoreButtons.isEmpty()) {
                System.out.println("Достигнут конец списка. Всего загружено смартфонов: " + loadedSmartphonesCount);
                break;
            }

            // Нажимаем на кнопку "Показать еще"
            showMoreButtons.first().click();

            // Ждем, пока новые элементы загрузятся
            Thread.sleep(1000); // Это пример, лучше использовать явное ожидание.

            // Обновляем количество загруженных смартфонов
            ElementsCollection noteList = $$x("//*[@data-autotest-id='offer-snippet' or @data-autotest-id='product-snippet']");
            loadedSmartphonesCount = noteList.size();

            System.out.println("Загружено смартфонов на текущий момент: " + loadedSmartphonesCount);
            takeScreenshot();

        }
    }

    public static boolean isSmartphoneValid(String element) {
        String lowerCaseElement = element.toLowerCase();
        return lowerCaseElement.contains("iphone");
    }
}


