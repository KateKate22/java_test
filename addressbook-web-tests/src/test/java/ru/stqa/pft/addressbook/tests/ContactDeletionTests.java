package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.GroupData;

import java.util.List;

public class ContactDeletionTests extends TestBase {
  String groupName; //  строковая переменная, хранящая имя группы, для последующей передачи в качестве параметра при создании контакта;
  @Test
  public void testContactDeletion() throws InterruptedException {
    app.goTo().returnToHomePage();
    if (! app.getContactHelper().contactExists()) { // проверяем есть ли хотя бы один контакт в табличной части
      app.goTo().groupPage(); // переходим в группы для осуществления последующей проверки
      if (! app.group().groupExists()) { // проверяем есть ли хотя бы одна группа в списке; если нет, создаем ее
        app.group().create(new GroupData().withName("test1"));
      }
      groupName = app.group().groupName(); // записываем в строковую переменную имя созданной либо уже имеющейся группы
      app.getContactHelper().createContact(new ContactData("Petr", "Petrov", "Wee", "Flower", "88142555555", "+79110000000", "peterpetrov@yandex.ru", "peter11petrov@gmai.com", "6", "May", "1980", groupName));
      app.goTo().returnToHomePage();
    }
    List<ContactData> before = app.getContactHelper().getContactList();
    app.getContactHelper().selectContactToDelete(before.size()-1);
    app.getContactHelper().submitContactDeletion();
    app.getContactHelper().closeContactDeletionAlert();
    List<ContactData> after = app.getContactHelper().getContactList();
    Assert.assertEquals(after.size(), before.size()-1);
    before.remove(before.size()-1);
    Assert.assertEquals(after, before);
  }
}
