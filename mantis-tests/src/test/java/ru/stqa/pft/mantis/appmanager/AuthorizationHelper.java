package ru.stqa.pft.mantis.appmanager;

import org.openqa.selenium.By;

public class AuthorizationHelper extends HelperBase {
  public AuthorizationHelper(ApplicationManager app) {
    super(app);
  }

  public void authorize() {
    wd.get(app.getProperty("web.baseUrl") + "/login_page.php");
    type(By.name("username"), "administrator");
    click(By.xpath("//input[@type='submit']"));
    type(By.name("password"), "root");
    click(By.xpath("//input[@type='submit']"));

  }
}
