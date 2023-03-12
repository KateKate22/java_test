package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.ArrayList;
import java.util.List;

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
  public void closeContactDeletionAlert() {
    wd.switchTo().alert().accept();
    click(By.linkText("home"));
  }

  public void selectContactToDelete(int index) {
    wd.findElements(By.name("selected[]")).get(index).click();
  }

  public void submitContactModification() {
    click(By.name("update"));
  }

  public void selectContactToEdit(int index) {
    wd.findElements(By.xpath("//img[@alt='Edit']")).get(index).click();
  }

  public boolean contactExists() { // метод проверяет есть ли в таличной части хотя бы 1 контакт
    return isElementPresent(By.name("selected[]"));
  }

  public void createContact(ContactData contactData) { // объединияем методы, относящиеся к созданию контакта
    initContactCreation();
    fillContactForm(contactData, true);
    submitContactCreation();
  }

  public List<ContactData> getContactList() {
    List<ContactData> contacts = new ArrayList<ContactData>();
    List<WebElement> elements = wd.findElements(By.xpath("//table/tbody/tr[@name='entry']"));
    for (WebElement element: elements) {
      List<WebElement> elementFields = element.findElements(By.tagName("td"));
      //List<WebElement> elementFields = element.findElements(By.xpath("td[not(@class)]"));
      int id = Integer.parseInt(elementFields.get(0).findElement(By.tagName("input")).getAttribute("id"));
      String surname = elementFields.get(1).getText();
      String name = elementFields.get(2).getText();
      contacts.add(new ContactData(id, name, surname, null, null, null, null, null, null, null, null, null, null));
    }
    return contacts;
  }
}
