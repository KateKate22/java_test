package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.GroupData;

public class ContactCreationTests extends TestBase {

  @Test
  public void testContactCreation() throws Exception {
    if (! app.getGroupHelper().groupExistsTest1()) { // проверяем есть ли группа с наименованием test1, если нет, создаем ее
      app.getGroupHelper().createGroup(new GroupData("test1", null, null));
    }
    app.getContactHelper().createContact(new ContactData("Petr", "Petrov", "Wee", "Flower", "88142555555", "+79110000000", "peterpetrov@yandex.ru", "peter11petrov@gmai.com", "6", "May", "1980", "test1"), true);
    app.getNavigationHelper().returnToHomePage();
    app.getSessionHelper().logout();
  }
}
