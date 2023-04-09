package ru.stqa.pft.mantis.appmanager;

import org.openqa.selenium.By;

public class NavigationHelper extends HelperBase {

  public NavigationHelper(ApplicationManager app) {
    super(app);
  }

  public void goToUserControl() {
    click(By.linkText("Управление"));
    click(By.linkText("Управление пользователями"));
  }
}
