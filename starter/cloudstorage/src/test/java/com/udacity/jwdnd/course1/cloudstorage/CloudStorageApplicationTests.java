package com.udacity.jwdnd.course1.cloudstorage;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import java.io.File;
import java.time.Duration;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CloudStorageApplicationTests {

	@LocalServerPort
	private int port;

	final Duration timeout = Duration.ofSeconds(2);

	private WebDriver driver;

	@BeforeAll
	static void beforeAll() {
		WebDriverManager.chromedriver().setup();
	}

	@BeforeEach
	public void beforeEach() {
		this.driver = new ChromeDriver();
	}

	@AfterEach
	public void afterEach() {
		if (this.driver != null) {
			driver.quit();
		}
	}

	@Test
	public void getLoginPage() {
		driver.get("http://localhost:" + this.port + "/login");
		Assertions.assertEquals("Login", driver.getTitle());
	}

	/**
	 * PLEASE DO NOT DELETE THIS method.
	 * Helper method for Udacity-supplied sanity checks.
	 **/
	private void doMockSignUp(String firstName, String lastName, String userName, String password){
		// Create a dummy account for logging in later.

		// Visit the sign-up page.
		WebDriverWait webDriverWait = new WebDriverWait(driver, timeout);
		driver.get("http://localhost:" + this.port + "/signup");
		webDriverWait.until(ExpectedConditions.titleContains("Sign Up"));

		// Fill out credentials
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputFirstName")));
		WebElement inputFirstName = driver.findElement(By.id("inputFirstName"));
		inputFirstName.click();
		inputFirstName.sendKeys(firstName);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputLastName")));
		WebElement inputLastName = driver.findElement(By.id("inputLastName"));
		inputLastName.click();
		inputLastName.sendKeys(lastName);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputUsername")));
		WebElement inputUsername = driver.findElement(By.id("inputUsername"));
		inputUsername.click();
		inputUsername.sendKeys(userName);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputPassword")));
		WebElement inputPassword = driver.findElement(By.id("inputPassword"));
		inputPassword.click();
		inputPassword.sendKeys(password);

		// Attempt to sign up.
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("buttonSignUp")));
		WebElement buttonSignUp = driver.findElement(By.id("buttonSignUp"));
		buttonSignUp.click();

		/* Check that the sign up was successful.
		// You may have to modify the element "success-msg" and the sign-up
		// success message below depening on the rest of your code.
		*/
		Assertions.assertTrue(driver.findElement(By.id("success-msg")).getText().contains("You successfully signed up!"));
	}



	/**
	 * PLEASE DO NOT DELETE THIS method.
	 * Helper method for Udacity-supplied sanity checks.
	 **/
	private void doLogIn(String userName, String password)
	{
		// Log in to our dummy account.
		driver.get("http://localhost:" + this.port + "/login");
		WebDriverWait webDriverWait = new WebDriverWait(driver, timeout);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputUsername")));
		WebElement loginUserName = driver.findElement(By.id("inputUsername"));
		loginUserName.click();
		loginUserName.sendKeys(userName);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputPassword")));
		WebElement loginPassword = driver.findElement(By.id("inputPassword"));
		loginPassword.click();
		loginPassword.sendKeys(password);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("login-button")));
		WebElement loginButton = driver.findElement(By.id("login-button"));
		loginButton.click();

		webDriverWait.until(ExpectedConditions.titleContains("Home"));

	}

	/**
	 * PLEASE DO NOT DELETE THIS TEST. You may modify this test to work with the
	 * rest of your code.
	 * This test is provided by Udacity to perform some basic sanity testing of
	 * your code to ensure that it meets certain rubric criteria.
	 *
	 * If this test is failing, please ensure that you are handling redirecting users
	 * back to the login page after a succesful sign up.
	 * Read more about the requirement in the rubric:
	 * https://review.udacity.com/#!/rubrics/2724/view
	 */
	@Test
	public void testRedirection() {
		// Create a test account
		doMockSignUp("Redirection","Test","RT","123");

		// Check if we have been redirected to the log in page.
		Assertions.assertEquals("http://localhost:" + this.port + "/login", driver.getCurrentUrl());
	}

	/**
	 * PLEASE DO NOT DELETE THIS TEST. You may modify this test to work with the
	 * rest of your code.
	 * This test is provided by Udacity to perform some basic sanity testing of
	 * your code to ensure that it meets certain rubric criteria.
	 *
	 * If this test is failing, please ensure that you are handling bad URLs
	 * gracefully, for example with a custom error page.
	 *
	 * Read more about custom error pages at:
	 * https://attacomsian.com/blog/spring-boot-custom-error-page#displaying-custom-error-page
	 */
	@Test
	public void testBadUrl() {
		// Create a test account
		doMockSignUp("URL","Test","UT","123");
		doLogIn("UT", "123");

		// Try to access a random made-up URL.
		driver.get("http://localhost:" + this.port + "/some-random-page");
		Assertions.assertFalse(driver.getPageSource().contains("Whitelabel Error Page"));
	}


	/**
	 * PLEASE DO NOT DELETE THIS TEST. You may modify this test to work with the
	 * rest of your code.
	 * This test is provided by Udacity to perform some basic sanity testing of
	 * your code to ensure that it meets certain rubric criteria.
	 *
	 * If this test is failing, please ensure that you are handling uploading large files (>1MB),
	 * gracefully in your code.
	 *
	 * Read more about file size limits here:
	 * https://spring.io/guides/gs/uploading-files/ under the "Tuning File Upload Limits" section.
	 */
	@Test
	public void testLargeUpload() {
		// Create a test account
		doMockSignUp("Large File","Test","LFT","123");
		doLogIn("LFT", "123");

		// Try to upload an arbitrary large file
		WebDriverWait webDriverWait = new WebDriverWait(driver, timeout);
		String fileName = "upload5m.zip";

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("fileUpload")));
		WebElement fileSelectButton = driver.findElement(By.id("fileUpload"));
		fileSelectButton.sendKeys(new File(fileName).getAbsolutePath());

		WebElement uploadButton = driver.findElement(By.id("uploadButton"));
		uploadButton.click();
		try {
			webDriverWait.until(ExpectedConditions.presenceOfElementLocated(By.id("success")));
		} catch (org.openqa.selenium.TimeoutException e) {
			System.out.println("Large File upload failed");
		}
		Assertions.assertFalse(driver.getPageSource().contains("HTTP Status 403 – Forbidden"));

	}

	@Test
	public void unauthorizedAccessIsRedirectedToLoginPage() {
		driver.get("http://localhost:" + this.port + "/home");
		Assertions.assertEquals("Login", driver.getTitle());
	}

	@Test
	public void unauthorizedUserCanAccessSignupPage() {
		driver.get("http://localhost:" + this.port + "/signup");
		Assertions.assertEquals("Sign Up", driver.getTitle());
	}

	@Test
	public void authedUserCanCreateNote(){
		beforeNotesTest();
		WebDriverWait wait = new WebDriverWait(driver, timeout);

		var launchDialogButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("add-note-button")));
		launchDialogButton.click();

		var noteTitleInput = wait.until(ExpectedConditions.elementToBeClickable(By.id("note-title")));
		noteTitleInput.clear();
		noteTitleInput.sendKeys("Test note title.");

		var noteDescriptionInput = wait.until(ExpectedConditions.elementToBeClickable(By.id("note-description")));
		noteDescriptionInput.clear();
		noteDescriptionInput.sendKeys("Test note description.");

		var saveNoteButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("save-note")));
		saveNoteButton.click();

		wait.until(ExpectedConditions.presenceOfElementLocated(By.id("success")));
		driver.get("http://localhost:" + this.port + "/home");
		wait = new WebDriverWait(driver, timeout);
		wait.until(ExpectedConditions.titleIs("Home"));

		driver.findElement(By.id("nav-notes-tab")).click();

		var found = wait.until(ExpectedConditions.textToBe(
				By.xpath("//*[@id='userTable']/tbody/tr/th"),
				"Selenium new note.")
		);

		Assertions.assertTrue(found);

	}

	@Test
	public void authedUserCanEditNote(){
		WebDriverWait wait = new WebDriverWait(driver, timeout);
		authedUserCanCreateNote();
		var notesTable = driver.findElement(By.xpath("//*[@id='userTable']/tbody"));
		var editNoteButton = wait.until(
				ExpectedConditions.elementToBeClickable(
						notesTable.findElement(By.tagName("td"))
								.findElement(By.tagName("button")))
		);

		editNoteButton.click();

		var noteTitleInput = wait.until(ExpectedConditions.elementToBeClickable(By.id("note-title")));
		noteTitleInput.clear();
		noteTitleInput.sendKeys("Selenium edited...");

		var noteDescriptionInput = wait.until(ExpectedConditions.elementToBeClickable(By.id("note-description")));
		noteDescriptionInput.clear();
		noteDescriptionInput.sendKeys("This is just another exercise.");

		var saveNoteButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("save-note")));
		saveNoteButton.click();

		wait.until(ExpectedConditions.presenceOfElementLocated(By.id("success")));
		driver.get("http://localhost:" + this.port + "/home");
		wait = new WebDriverWait(driver, timeout);
		wait.until(ExpectedConditions.titleIs("Home"));

		driver.findElement(By.id("nav-notes-tab")).click();

		var found = wait.until(ExpectedConditions.textToBe(
				By.xpath("//*[@id='userTable']/tbody/tr/th"),
				"Selenium edited...")
		);

		Assertions.assertTrue(found);
	}
	@Test
	public void authedUserCanDeleteNote(){
		WebDriverWait wait = new WebDriverWait(driver, timeout);
		authedUserCanCreateNote();
		var notesTable = driver.findElement(By.xpath("//*[@id='userTable']/tbody"));
		var initialNotesNumber = notesTable.findElements(By.tagName("tr")).size();

		var removeNoteButton = wait.until(
				ExpectedConditions.elementToBeClickable(
						notesTable.findElement(By.tagName("td"))
								.findElement(By.tagName("a"))
				)
		);

		removeNoteButton.click();
		wait.until(ExpectedConditions.presenceOfElementLocated(By.id("success")));
		driver.get("http://localhost:" + this.port + "/home");
		wait = new WebDriverWait(driver, timeout);
		wait.until(ExpectedConditions.titleIs("Home"));

		driver.findElement(By.id("nav-notes-tab")).click();
		wait.until(
				ExpectedConditions.numberOfElementsToBe(
						By.xpath("//*[@id='userTable']/tbody/tr"),
						initialNotesNumber - 1
				)
		);
	}

	private void beforeNotesTest(){
		doMockSignUp("Large File","Test","LFT","123");
		doLogIn("LFT", "123");
		driver.get("http://localhost:" + this.port + "/home");
		WebDriverWait wait = new WebDriverWait(driver, timeout);
		wait.until(ExpectedConditions.titleIs("Home"));

		driver.findElement(By.id("nav-notes-tab")).click();
	}

	private void beforeCredentialTest(){
		doMockSignUp("Large File","Test","LFT","123");
		doLogIn("LFT", "123");
		driver.get("http://localhost:" + this.port + "/home");
		WebDriverWait wait = new WebDriverWait(driver, timeout);
		wait.until(ExpectedConditions.titleIs("Home"));

		driver.findElement(By.id("nav-credentials-tab")).click();
	}

	@Test
	public void authedUserCanCreateCredential(){
		beforeCredentialTest();
		WebDriverWait wait = new WebDriverWait(driver, timeout);

		var launchDialogButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("add-credential")));
		launchDialogButton.click();

		var credentialUrlInput = wait.until(ExpectedConditions.elementToBeClickable(By.id("credential-url")));
		credentialUrlInput.clear();
		credentialUrlInput.sendKeys("https://udacity.com");

		var credentialUsernameInput = wait.until(ExpectedConditions.elementToBeClickable(By.id("credential-username")));
		credentialUsernameInput.clear();
		credentialUsernameInput.sendKeys("username");

		var credentialPasswordInput = wait.until(ExpectedConditions.elementToBeClickable(By.id("credential-password")));
		credentialPasswordInput.clear();
		credentialPasswordInput.sendKeys("password");

		var saveCredentialButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("save-credential")));
		saveCredentialButton.click();

		wait.until(ExpectedConditions.presenceOfElementLocated(By.id("success")));
		driver.get("http://localhost:" + this.port + "/home");
		wait = new WebDriverWait(driver, timeout);
		wait.until(ExpectedConditions.titleIs("Home"));

		driver.findElement(By.id("nav-credentials-tab")).click();

		var found = wait.until(ExpectedConditions.textToBe(
				By.xpath("//*[@id='credentialTable']/tbody/tr/th"),
				"https://udacity.com")
		);

		Assertions.assertTrue(found);

	}

	@Test
	public void authedUserCanEditCredential(){
		authedUserCanCreateCredential();
		WebDriverWait wait = new WebDriverWait(driver, timeout);
		var creTable = driver.findElement(By.xpath("//*[@id='credentialTable']/tbody"));
		var editCreButton = wait.until(
				ExpectedConditions.elementToBeClickable(
						creTable.findElement(By.tagName("td"))
								.findElement(By.tagName("button")))
		);

		editCreButton.click();

		var credentialPasswordInput = wait.until(ExpectedConditions.elementToBeClickable(By.id("credential-password")));
		Assertions.assertEquals("password", credentialPasswordInput.getAttribute("value"));
		credentialPasswordInput.clear();
		credentialPasswordInput.sendKeys("password1");

		var credentialUrlInput = wait.until(ExpectedConditions.elementToBeClickable(By.id("credential-url")));
		credentialUrlInput.clear();
		credentialUrlInput.sendKeys("https://udacity.com-edit");

		var credentialUsernameInput = wait.until(ExpectedConditions.elementToBeClickable(By.id("credential-username")));
		credentialUsernameInput.clear();
		credentialUsernameInput.sendKeys("something");

		var saveCredentialButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("save-credential")));
		saveCredentialButton.click();

		wait.until(ExpectedConditions.presenceOfElementLocated(By.id("success")));
		driver.get("http://localhost:" + this.port + "/home");
		wait = new WebDriverWait(driver, timeout);
		wait.until(ExpectedConditions.titleIs("Home"));

		driver.findElement(By.id("nav-credentials-tab")).click();

		var found = wait.until(ExpectedConditions.textToBe(
				By.xpath("//*[@id='credentialTable']/tbody/tr/th"),
				"https://udacity.com-edit")
		);

		Assertions.assertTrue(found);

	}
	@Test
	public void authedUserCanDeleteCredential(){
		authedUserCanCreateCredential();
		WebDriverWait wait = new WebDriverWait(driver, timeout);
		var credentialsTable = driver.findElement(By.xpath("//*[@id='credentialTable']/tbody"));
		var initialNotesNumber = credentialsTable.findElements(By.tagName("tr")).size();

		var removeCredentialButton = wait.until(
				ExpectedConditions.elementToBeClickable(
						credentialsTable
								.findElement(By.tagName("td"))
								.findElement(By.tagName("a"))
				)
		);

		removeCredentialButton.click();
		wait.until(ExpectedConditions.presenceOfElementLocated(By.id("success")));
		driver.get("http://localhost:" + this.port + "/home");
		wait = new WebDriverWait(driver, timeout);
		wait.until(ExpectedConditions.titleIs("Home"));

		driver.findElement(By.id("nav-credentials-tab")).click();

		wait.until(
				ExpectedConditions.numberOfElementsToBe(
						By.xpath("//*[@id='credentialTable']/tbody/tr"),
						initialNotesNumber - 1
				)
		);
	}

}
