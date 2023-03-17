package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import ru.stqa.pft.addressbook.model.GroupData;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class GroupHelper extends HelperBase {

  public GroupHelper(WebDriver wd) {
    super(wd);
  }

  public void returnToGroupPage() {
    click(By.linkText("group page"));
  }

  public void submitGroupCreation() {
    click(By.name("submit"));
  }

  public void fillGroupForm(GroupData groupData) {
    type(By.name("group_name"), groupData.getName());
    type(By.name("group_header"), groupData.getHeader());
    type(By.name("group_footer"), groupData.getFooter());
  }

  public void initGroupCreation() {
    click(By.name("new"));
  }

  public void deleteSelectedGroups() {
    click(By.name("delete"));
  }

  public void selectGroup(int index) {
    wd.findElements(By.name("selected[]")).get(index).click();
    //click(By.name("selected[]"));
  }

  private void selectGroupById(int id) {
    wd.findElement(By.cssSelector("input[value='" + id + "']")).click();
  }

  public void editSelectedGroups() {
    click(By.name("edit"));
  }

  public void submitGroupModification() {
    click(By.name("update"));
  }

  public boolean groupExists() { // метод проверяет есть ли в списке хотя бы одна группа в списке
    return isElementPresent(By.name("selected[]"));
  }

  public void create(GroupData group) { // объединияем методы, относящиеся к созданию группы
    initGroupCreation();
    fillGroupForm(group);
    submitGroupCreation();
    returnToGroupPage();
  }
  public void modify(GroupData group) {
    selectGroupById(group.getId());
    editSelectedGroups();
    fillGroupForm(group);
    submitGroupModification();
    returnToGroupPage();
  }

  /*public void delete(int index) {
    selectGroup(index);
    deleteSelectedGroups();
    returnToGroupPage();
  }*/

  public void delete(GroupData group) {
    selectGroupById(group.getId());
    deleteSelectedGroups();
    returnToGroupPage();
  }

  public String groupName() { //метод, который возвращает название первой попавшейся группы в списке
    String groupName = wd.findElement(By.name("selected[]")).getAttribute("title");
    return groupName.substring(8, groupName.length() - 1);
  }
  public int getGroupCount() {
    return wd.findElements(By.xpath("//span[@class='group']")).size();
  }

  /*public List<GroupData> list() {
    List<GroupData> groups = new ArrayList<GroupData>(); // cоздаем список объектов класса GroupData
    List<WebElement> elements = wd.findElements(By.cssSelector("span.group")); //создаем список веб-элементов
    for (WebElement element: elements) { // перебираем все элементы
      String name = element.getText(); // в строковую перемененную записываем навание группы
      int id = Integer.parseInt(element.findElement(By.tagName("input")).getAttribute("value"));
      //GroupData group = new GroupData().withId(id).withName(name); // создаем объект класса GroupData, в конструкторе передаем искомое название группы первым параметром
      groups.add(new GroupData().withId(id).withName(name)); // добавляем объект класса GroupData в список
    }
    return groups;
  }*/

  public Set<GroupData> all() {
    Set<GroupData> groups = new HashSet<>(); // cоздаем список объектов класса GroupData
    List<WebElement> elements = wd.findElements(By.cssSelector("span.group")); //создаем список веб-элементов
    for (WebElement element: elements) { // перебираем все элементы
      String name = element.getText(); // в строковую перемененную записываем навание группы
      int id = Integer.parseInt(element.findElement(By.tagName("input")).getAttribute("value"));
      //GroupData group = new GroupData().withId(id).withName(name); // создаем объект класса GroupData, в конструкторе передаем искомое название группы первым параметром
      groups.add(new GroupData().withId(id).withName(name)); // добавляем объект класса GroupData в список
    }
    return groups;
  }
}