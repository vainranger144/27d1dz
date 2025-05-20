import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class LoginDDTTest {

    private WebDriver driver;

    @BeforeEach
    public void setUp() {
        driver = new ChromeDriver();
        driver.get("https://guest:welcome2qauto@qauto.forstudy.space/");
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @ParameterizedTest
    @CsvSource({
            "test@hillel.ua, 1111",
            "test@hillel.ua, 1234"
    })
    public void testInvalidLoginDisplaysErrorMessage(String email, String password) throws InterruptedException {
        // Натискаємо кнопку Sign In
        driver.findElement(By.xpath("//button[text()='Sign In']")).click();

        // Вводимо email і пароль
        WebElement emailInput = driver.findElement(By.name("email"));
        WebElement passwordInput = driver.findElement(By.name("password"));

        emailInput.sendKeys(email);
        passwordInput.sendKeys(password);

        // Натискаємо кнопку Login
        driver.findElement(By.xpath("//button[text()='Login']")).click();

        // Зачекаємо кілька секунд на появу повідомлення (можна замінити на WebDriverWait)
        Thread.sleep(1000);

        // Перевіряємо повідомлення про помилку
        WebElement errorMessage = driver.findElement(By.className("invalid-feedback"));

        assertTrue(errorMessage.isDisplayed(), "Повідомлення про помилку не відображається");
        assertEquals("Wrong email or password", errorMessage.getText().trim());
    }
}
