package ru.stqa.pft.mantis.appmanager;

import org.openqa.selenium.By;

public class ControlHelper extends HelperBase{
  public ControlHelper(ApplicationManager app) {
    super(app);
  }


  public void resetPassword(String username) {
    click(By.linkText(username));
    click(By.xpath("//input[@value='Сбросить пароль']"));
  }
}
