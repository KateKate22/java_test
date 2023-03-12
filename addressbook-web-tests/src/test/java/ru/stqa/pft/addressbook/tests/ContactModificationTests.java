package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.GroupData;

import java.util.Comparator;
import java.util.List;

public class ContactModificationTests extends TestBase {
  String groupName; //  строковая переменная, хранящая имя группы, для последующей передачи в качестве значения параметра при создании контакта;

  @Test
  public void testContactModification() {
    app.getNavigationHelper().returnToHomePage();
    if (!app.getContactHelper().contactExists()) { // проверяем есть ли хотя бы один контакт в табличной части
      app.getNavigationHelper().gotoGroupPage(); // переходим в группы для осуществления последующей проверки
      if (!app.getGroupHelper().groupExists()) { // проверяем есть ли хотя бы одна группа в списке; если нет, создаем ее
        app.getGroupHelper().createGroup(new GroupData("test1", null, null));
      }
      groupName = app.getGroupHelper().groupName(); // записываем в строковую переменную имя созданной либо уже имеющейся группы
      app.getContactHelper().createContact(new ContactData("Petr", "Petrov", "Wee", "Flower", "88142555555", "+79110000000", "peterpetrov@yandex.ru", "peter11petrov@gmai.com", "6", "May", "1980", groupName));
      app.getNavigationHelper().returnToHomePage();
    }
    List<ContactData> before = app.getContactHelper().getContactList();
    app.getContactHelper().selectContactToEdit(before.size() - 1);
    ContactData contactData = new ContactData(before.get(before.size() - 1).getId(), "PetrEdited", "PetrovEdited", "WeeEdited", "FlowerEdited", "88142551111", "+79110001111", "peterpetrovEdited@yandex.ru", "peter11petrovEdited@gmai.com", "1", "May", "1981", null);
    app.getContactHelper().fillContactForm(contactData, false);
    app.getContactHelper().submitContactModification();
    app.getNavigationHelper().returnToHomePage();
    List<ContactData> after = app.getContactHelper().getContactList();
    Assert.assertEquals(after.size(), before.size());
    before.remove(before.size() - 1);
    before.add(contactData);
    Comparator<? super ContactData> byId = (Comparator<ContactData>) (c1, c2) -> Integer.compare(c1.getId(), c2.getId());
    after.sort(byId);
    before.sort(byId);
    Assert.assertEquals(after, before);
    app.getSessionHelper().logout();
  }
}
