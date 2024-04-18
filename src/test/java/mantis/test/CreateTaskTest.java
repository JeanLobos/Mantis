package mantis.test;

import static org.junit.Assert.assertEquals;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import io.github.bonigarcia.wdm.WebDriverManager;
import mantis.ConfigReader;

public class CreateTaskTest {
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
    public void createTask() {
        String url = configReader.getUrl();
        String login = configReader.getLogin();
        String password = configReader.getPassword();
        String summary = configReader.getSummary();
        String description = configReader.getDescription();

        // Access login page
        driver.get(url);

        // Login
        driver.findElement(By.id("username")).sendKeys(login);
        driver.findElement(By.className("width-40")).submit();
        driver.findElement(By.id("password")).sendKeys(password);
        driver.findElement(By.className("width-40")).submit();

        // Waiting button "Criar Tarefa"
        WebElement createTaskButton = driver.findElement(By.className("btn-sm"));
        createTaskButton.click();

        // Select project
        WebElement selectProject = driver.findElement(By.id("select-project-id"));
        Select findProject = new Select(selectProject);
        findProject.selectByVisibleText("Teste");
        driver.findElement(By.className("btn-round")).click();

        // Select other options (Frequency, severity, priority)

        WebElement selectFrequency = driver.findElement(By.id("reproducibility"));
        WebElement selectSevetiry = driver.findElement(By.id("severity"));
        WebElement selectPriority = driver.findElement(By.id("priority"));

        Select findFrequency = new Select(selectFrequency);
        Select findSeverity = new Select(selectSevetiry);
        Select findPriority = new Select(selectPriority);

        findFrequency.selectByVisibleText("sempre");     
        findSeverity.selectByVisibleText("trivial");      
        findPriority.selectByVisibleText("urgente");

        // Summary and description
        driver.findElement(By.id("summary")).sendKeys(summary);
        driver.findElement(By.id("description")).sendKeys(description);

        // Create task
        driver.findElement(By.className("btn-round")).click();

        // Waiting task details
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("bug-project")));

        // Validate task details
        assertEquals("Teste", driver.findElement(By.className("bug-project")).getText());
        assertEquals(login, driver.findElement(By.className("bug-reporter")).getText());
        assertEquals(summary, driver.findElement(By.className("bug-summary")).getText());
        assertEquals(description, driver.findElement(By.className("bug-description")).getText());
    }
}
