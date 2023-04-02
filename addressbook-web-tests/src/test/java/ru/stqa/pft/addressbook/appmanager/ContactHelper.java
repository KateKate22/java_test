package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

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
    attach(By.name("photo"), contactData.getPhoto());
    type(By.name("company"), contactData.getCompany());
    type(By.name("address"), contactData.getAddress());
    type(By.name("home"), contactData.getHome());
    type(By.name("mobile"), contactData.getMobile());
    type(By.name("work"), contactData.getWork());
    type(By.name("email"), contactData.getEmail());
    type(By.name("email2"), contactData.getEmail2());
    type(By.name("email3"), contactData.getEmail3());
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

  private void selectContactById(int id) {
    wd.findElement(By.cssSelector("input[value='" + id + "']")).click();
  }

  public void submitContactModification() {
    click(By.name("update"));
  }

  public void selectContactToEdit(int index) {
    wd.findElements(By.xpath("//img[@alt='Edit']")).get(index).click();
  }

  public void selectContactToEditById(int id) {
    //WebElement checkbox = wd.findElement(By.cssSelector(String.format("input[value='%s']", id)));
    //WebElement row = checkbox.findElement(By.xpath("./../.."));
    //List<WebElement> cells = row.findElements(By.tagName("td"));
    //cells.get(7).findElement(By.tagName("a")).click();*/
    wd.findElement(By.cssSelector(String.format("a[href='edit.php?id=%s']", id))).click();
  }

  public boolean contactExists() { // метод проверяет есть ли в таличной части хотя бы 1 контакт
    return isElementPresent(By.name("selected[]"));
  }

  public void create(ContactData contactData) { // объединияем методы, относящиеся к созданию контакта
    initContactCreation();
    fillContactForm(contactData, true);
    submitContactCreation();
  }

  public void delete(ContactData contact) {
    selectContactById(contact.getId());
    submitContactDeletion();
    closeContactDeletionAlert();
  }

  public void modify(ContactData contact) {
    selectContactToEditById(contact.getId());
    //selectContactToEdit(contact.getId());
    fillContactForm(contact, false);
    submitContactModification();
  }

  /*public List<ContactData> list() {
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
  }*/

  public Contacts all() {
    Contacts contacts = new Contacts();
    List<WebElement> elements = wd.findElements(By.xpath("//table/tbody/tr[@name='entry']"));
    for (WebElement element : elements) {
      List<WebElement> elementFields = element.findElements(By.tagName("td"));
      //List<WebElement> elementFields = element.findElements(By.xpath("td[not(@class)]"));
      int id = Integer.parseInt(elementFields.get(0).findElement(By.tagName("input")).getAttribute("id"));
      String surname = elementFields.get(1).getText();
      String name = elementFields.get(2).getText();
      String address = elementFields.get(3).getText();
      //String[] phones = elementFields.get(5).getText().split("\n");
      String allPhones = elementFields.get(5).getText();
      String allEmails = elementFields.get(4).getText();
      contacts.add(new ContactData().setId(id).setName(name).setSurname(surname).setAddress(address).setAllPhones(allPhones).setAllEmails(allEmails));
    }
    return contacts;
  }

  public ContactData infoFromEditForm(ContactData contact) {
    selectContactToEditById(contact.getId());
    String firstname = wd.findElement(By.name("firstname")).getAttribute("value");
    String lastname = wd.findElement(By.name("lastname")).getAttribute("value");
    String address = wd.findElement(By.name("address")).getAttribute("value");
    String home = wd.findElement(By.name("home")).getAttribute("value");
    String mobile = wd.findElement(By.name("mobile")).getAttribute("value");
    String work = wd.findElement(By.name("work")).getAttribute("value");
    String email = wd.findElement(By.name("email")).getAttribute("value");
    String email2 = wd.findElement(By.name("email2")).getAttribute("value");
    String email3 = wd.findElement(By.name("email3")).getAttribute("value");
    wd.navigate().back();
    return new ContactData().setName(firstname).setSurname(lastname).setAddress(address).setHome(home).setMobile(mobile).setWork(work).setEmail(email).setEmail2(email2).setEmail3(email3);
  }

  public int addInGroup(ContactData contact, Groups groupsList) { //метод принимает на вход контакт для включения в группу и исходный список групп этого контакта
    int idGroup = 0; // переменная, которая будет хранить идентификатор выбранной группы, проинициализируем значением 0
    List<WebElement> options = wd.findElements(By.xpath("//select[@name = 'to_group']/option"));

    selectContactById(contact.getId());
    click(By.name("to_group"));

    for (WebElement option : options) { // здесь в выпадающем списке будем искать группу, в которую еще не включен искомый контакт
      // как только встретим подходящую опцию, выберем ее (контакт будет включен в группу, в которой ранее не состоял)
      boolean isInGroup = false; // булевская переменная, если контакт уже включен в группу из выпадающего списка, то true, иначе false
      for (GroupData group : groupsList) {
        if (Integer.parseInt(option.getAttribute("value")) == group.getId()) {
          isInGroup = true; // контакт уже включен в данную группу, значит присваиваем true булевской переменной
        }
      }
      if (!isInGroup) { // если false, значит мы нашли группу, в которую искомый контакт не входит, и можем включать контакт в эту группу
        idGroup = Integer.parseInt(option.getAttribute("value"));
        new Select(wd.findElement(By.name("to_group"))).selectByVisibleText(option.getText());
        click(By.name("add"));
        break; // выходим из цикла, т.к. нашли нужную группу
      }
    }
    return idGroup;
  }

  public void addInGroup(ContactData contact) { //упрощенный метод добавления контакта в группах, используемый в предпроверках
    WebElement option = wd.findElement(By.xpath("//select[@name = 'to_group']/option[1]"));
    selectContactById(contact.getId());
    click(By.name("to_group"));
    new Select(wd.findElement(By.name("to_group"))).selectByVisibleText(option.getText());
    click(By.name("add"));
  }

  public void deleteFromGroup(ContactData contact, int groupId) {
    click(By.name("group"));
    WebElement option = wd.findElement(By.xpath(String.format("//select[@name='group']/option[@value=%s]", groupId)));
    new Select(wd.findElement(By.name("group"))).selectByVisibleText(option.getText());
    selectContactById(contact.getId());
    click(By.name("remove"));
  }
}
