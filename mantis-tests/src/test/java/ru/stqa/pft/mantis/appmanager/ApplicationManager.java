package ru.stqa.pft.mantis.appmanager;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.Browser;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

public class ApplicationManager {
  private final Properties properties;

  private WebDriver wd;
  private String browser;

  private RegistrationHelper registrationHelper;
  private FtpHelper ftpHelper;
  private MailHelper mailHelper;
  private JamesHelper jamesHelper;
  private AuthorizationHelper authorizationHelper;
  private NavigationHelper navigationHelper;
  private ControlHelper controlHelper;
  private DbHelper dbHelper;
  private SoapHelper soapHelper;

  public ApplicationManager(String browser) {
    this.browser = browser;
    properties = new Properties();
  }

  public void init() throws IOException {
    String target = System.getProperty("target", "local");
    properties.load(new FileReader(new File(String.format("src/test/resources/%s.properties", target))));
  }

  public void stop() {
    if (wd != null) {
      wd.quit();
    }
  }

  public HttpSession newSession() {
    return new HttpSession(this);
  }

  public String getProperty(String key) {
    return properties.getProperty(key);
  }

  public RegistrationHelper registrationHelper() {
    if (registrationHelper == null) {
      registrationHelper = new RegistrationHelper(this);
    }
    return registrationHelper;
  }

  public AuthorizationHelper authorizationHelper() {
    if (authorizationHelper == null) {
      authorizationHelper = new AuthorizationHelper(this);
    }
    return authorizationHelper;
  }

  public NavigationHelper navigationHelper() {
    if (navigationHelper == null) {
      navigationHelper = new NavigationHelper(this);
    }
    return navigationHelper;
  }
  
  public ControlHelper controlHelper() {
    if (controlHelper == null) {
      controlHelper = new ControlHelper(this);
    }
    return controlHelper;
  }

  public DbHelper dbHelper() {
    if (dbHelper == null) {
      dbHelper = new DbHelper(this);
    }
    return dbHelper;
  }
  
  public FtpHelper ftpHelper() {
    if (ftpHelper == null) {
      ftpHelper = new FtpHelper(this);
    }
    return ftpHelper;
  }

  public MailHelper mailHelper() {
    if (mailHelper == null) {
      mailHelper = new MailHelper(this);
    }
    return mailHelper;
  }

  public JamesHelper jamesHelper() {
    if (jamesHelper == null) {
      jamesHelper = new JamesHelper(this);
    }
    return jamesHelper;
  }

  public SoapHelper soapHelper() {
    if (soapHelper == null) {
      soapHelper = new SoapHelper(this);
    }
    return soapHelper;
  }


  public WebDriver getDriver() {
    if(wd == null) {
      if (browser.equals(Browser.CHROME.browserName())) {
        wd = new ChromeDriver();
      } else if (browser.equals(Browser.FIREFOX.browserName())) {
        wd = new FirefoxDriver();
      }
      wd.manage().timeouts().implicitlyWait(Duration.ofSeconds(0));
      wd.get(properties.getProperty("web.baseUrl"));
    }
    return wd;
  }
}
