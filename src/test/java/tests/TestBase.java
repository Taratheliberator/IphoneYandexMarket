package tests;

import org.junit.jupiter.api.AfterEach;
import org.example.appmanager.ApplicationManager;
import org.junit.jupiter.api.BeforeEach;

/**
 * Базовый класс для всех тестовых классов.
 * Предоставляет методы для инициализации и завершения тестового окружения.
 */
public class TestBase {

    // Экземпляр ApplicationManager для управления тестовым окружением
    protected static final ApplicationManager app = new ApplicationManager();

    /**
     * Выполняется перед каждым тестом.
     * Инициализирует тестовое окружение.
     *
     * @throws Exception в случае ошибок при инициализации.
     */
    @BeforeEach
    public void setUp() throws Exception {
        app.init();
    }

    /**
     * Выполняется после каждого теста.
     * Завершает тестовое окружение и освобождает ресурсы.
     */
    @AfterEach
    public void tearDown() {
        app.stop();
    }
}

