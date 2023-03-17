package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.GroupData;

import java.util.Comparator;
import java.util.List;

public class ContactCreationTests extends TestBase {
  String groupName; //  строковая переменная, хранящая имя группы, для последующей передачи в качестве параметра при создании контакта;

  @Test
  public void testContactCreation() throws Exception {
    app.goTo().groupPage(); // переходим в группы для осуществления последующей проверки
    if (!app.group().groupExists()) { // проверяем есть ли хотя бы одна группа в списке; если нет, создаем ее
      app.group().create(new GroupData().withName("test1"));
    }
    groupName = app.group().groupName(); // записываем в строковую переменную имя созданной либо уже имеющейся группы
    app.goTo().returnToHomePage(); // переход на страницу с контактами
    List<ContactData> before = app.getContactHelper().getContactList();
    ContactData contactData = new ContactData("Petr", "Petrov", "Wee", "Flower", "88142555555", "+79110000000", "peterpetrov@yandex.ru", "peter11petrov@gmai.com", "6", "May", "1980", groupName);
    app.getContactHelper().createContact(contactData);
    app.goTo().returnToHomePage();
    List<ContactData> after = app.getContactHelper().getContactList();
    Assert.assertEquals(after.size(), before.size()+1);
    before.add(contactData);
    Comparator<? super ContactData> byId = (Comparator<ContactData>) (c1, c2) -> Integer.compare(c1.getId(), c2.getId());
    after.sort(byId);
    before.sort(byId);
    Assert.assertEquals(after, before);
  }
}
