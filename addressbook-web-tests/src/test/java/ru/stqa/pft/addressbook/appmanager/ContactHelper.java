package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import ru.stqa.pft.addressbook.model.ContactData;

public class ContactHelper extends HelperBase {

  public ContactHelper(WebDriver wd) {
    super(wd);
  }

  public void submitContactCreation() {
    click(By.xpath("//div[@id='content']/form/input[21]"));
  }

  public void fillContactForm(ContactData contactData, boolean creation) {
    type(By.name("firstname"), contactData.getName());
    type(By.name("lastname"), contactData.getSurname());
    type(By.name("nickname"), contactData.getNickname());
    type(By.name("company"), contactData.getCompany());
    type(By.name("home"), contactData.getHome());
    type(By.name("mobile"), contactData.getMobile());
    type(By.name("email"), contactData.getEmail());
    type(By.name("email2"), contactData.getEmail2());
    select(By.name("bday"), contactData.getBday());
    select(By.name("bmonth"), contactData.getBmonth());
    type(By.name("byear"), contactData.getByear());
    if (creation) {
      select(By.name("new_group"), contactData.getGroup());
    } else {
      Assert.assertFalse(isElementPresent(By.name("new_group")));
    }
  }

  public void initContactCreation() {
    click(By.linkText("add new"));
  }
  public void submitContactDeletion() {
    click(By.xpath("//input[@value='Delete']"));
  }
  public void closeContactDeletionAlert() { wd.switchTo().alert().accept(); }

  public void selectContactToDelete() {
    click(By.name("selected[]"));
  }

  public void submitContactModification() {
    click(By.name("update"));
  }

  public void selectContactToEdit() {
    click(By.xpath("//img[@alt='Edit']"));
  }

  public boolean contactExists() { // метод проверяет есть ли в таличной части хотя бы 1 контакт
    return isElementPresent(By.name("selected[]"));
  }

  public void createContact(ContactData contactData) { // объединияем методы, относящиеся к созданию контакта
    initContactCreation();
    fillContactForm(contactData, true);
    submitContactCreation();
  }
}
