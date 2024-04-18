package mantis.test;

import static org.junit.Assert.assertEquals;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import io.github.bonigarcia.wdm.WebDriverManager;
import mantis.ConfigReader;

public class LoginTest {
    private WebDriver driver;
    private ConfigReader configReader;

    @Before
    public void setUp() {
        // Config WebDriver
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        configReader = new ConfigReader();
    }

    @After
    public void tearDown() {
        // Close the browser after each test
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    public void testLogin() {
        String url = configReader.getUrl();
        String login = configReader.getLogin();
        String password = configReader.getPassword();

        driver.get(url);
        try {
            driver.findElement(By.id("username")).sendKeys(login);
            driver.findElement(By.className("width-40")).submit();
            driver.findElement(By.id("password")).sendKeys(password);
            driver.findElement(By.className("width-40")).submit();
        } catch(org.openqa.selenium.NoSuchElementException e){
            WebElement alertMessage = driver.findElement(By.className("alert"));
            assertEquals("Sua conta pode estar desativada ou bloqueada ou o nome de usuário e a senha que você digitou não estão corretos.", alertMessage.getText()); 
        }
    }
}