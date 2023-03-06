package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class NavigationHelper extends HelperBase {

 // private WebDriver wd;

  public NavigationHelper(WebDriver wd) {
    super(wd);
  }

  public void gotoGroupPage() {
    //boolean a1 = isElementPresent(By.tagName("h1"));
    //boolean a2 = isElementPresent(By.name("new"));
    //boolean a3 = wd.findElement(By.tagName("h1")).getText().equals("Groups");

    if (isElementPresent(By.tagName("h1"))
            && isElementPresent(By.name("new"))
            && wd.findElement(By.tagName("h1")).getText().equals("Groups")) {
        return;
      }
    click(By.linkText("groups"));
  }
  public void returnToHomePage() {
    if (isElementPresent(By.id("maintable"))) {
      return;
    }
    click(By.linkText("home"));
  }
}
