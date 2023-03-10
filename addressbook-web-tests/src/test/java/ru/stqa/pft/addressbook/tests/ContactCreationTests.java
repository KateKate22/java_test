package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.GroupData;

public class ContactCreationTests extends TestBase {
  String groupName; //  строковая переменная, хранящая имя группы, для последующей передачи в качестве параметра при создании контакта;

  @Test
  public void testContactCreation() throws Exception {
    app.getNavigationHelper().gotoGroupPage(); // переходим в группы для осуществления последующей проверки
    if (!app.getGroupHelper().groupExists()) { // проверяем есть ли хотя бы одна группа в списке; если нет, создаем ее
      app.getGroupHelper().createGroup(new GroupData("test1", null, null));
    }
    groupName = app.getGroupHelper().groupName(); // записываем в строковую переменную имя созданной либо уже имеющейся группы
    app.getContactHelper().createContact(new ContactData("Petr", "Petrov", "Wee", "Flower", "88142555555", "+79110000000", "peterpetrov@yandex.ru", "peter11petrov@gmai.com", "6", "May", "1980", groupName));
    app.getNavigationHelper().returnToHomePage();
    app.getSessionHelper().logout();
  }
}
