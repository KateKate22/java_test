package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactModificationTests extends TestBase {
  String groupName; //  строковая переменная, хранящая имя группы, для последующей передачи в качестве значения параметра при создании контакта;

  @BeforeMethod
  public void ensurePreconditions() {
    app.goTo().homePage();
    if (app.contact().all().size() == 0) { // проверяем есть ли хотя бы один контакт в табличной части
      app.goTo().groupPage(); // переходим в группы для осуществления последующей проверки
      if (app.group().all().size() == 0) { // проверяем есть ли хотя бы одна группа в списке; если нет, создаем ее
        app.group().create(new GroupData().withName("test1"));
      }
      groupName = app.group().groupName(); // записываем в строковую переменную имя созданной либо уже имеющейся группы
      app.contact().create(new ContactData("Petr", "Petrov", "Wee", "Flower", "88142555555", "+79110000000", "peterpetrov@yandex.ru", "peter11petrov@gmai.com", "6", "May", "1980", groupName));
      app.goTo().homePage();
    }
  }
  @Test
  public void testContactModification() {
    Contacts before = app.contact().all();
    ContactData modifiedContact = before.iterator().next();
    ContactData contact = new ContactData(modifiedContact.getId(), "PetrEdited", "PetrovEdited", "WeeEdited", "FlowerEdited", "88142551111", "+79110001111", "peterpetrovEdited@yandex.ru", "peter11petrovEdited@gmai.com", "1", "May", "1981", null);
    app.contact().modify(contact);
    app.goTo().homePage();
    Contacts after = app.contact().all();
    //Assert.assertEquals(after.size(), before.size());
    assertThat(after.size(), equalTo(before.size()));
    assertThat(after, equalTo(before.without(modifiedContact).withAdded(contact)));
  }
}
