package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactDeletionTests extends TestBase {

  String groupName; //  строковая переменная, хранящая имя группы, для последующей передачи в качестве параметра при создании контакта;
  @BeforeMethod
  public void ensurePreconditions() {
    if (app.dbHelper().contacts().size() == 0) { // проверяем есть ли хотя бы один контакт в табличной части
      app.goTo().groupPage(); // переходим в группы для осуществления последующей проверки
      if (app.dbHelper().groups().size() == 0) { // проверяем есть ли хотя бы одна группа в списке; если нет, создаем ее
        app.groupHelper().create(new GroupData().withName("test1"));
      }
      groupName = app.groupHelper().groupName(); // записываем в строковую переменную имя созданной либо уже имеющейся группы
      app.contactHelper().create(new ContactData().setName("Petr").setSurname("Petrov").setNickname("Wee")
              .setCompany("Flower").setAddress("Lenina street 33-8").setHome("88142555555").setMobile("+79110000000").
              setWork("555555").setEmail("peterpetrov@yandex.ru").setEmail2("peter11petrov@gmail.com").
              setEmail3("peter1234petrov@gmail.com").setBday("6").setBmonth("May").setByear("1980").setGroup(groupName));
      //app.contact().create(new ContactData("Petr", "Petrov", "Wee", "Flower", "88142555555", "+79110000000", "555555", "peterpetrov@yandex.ru", "peter11petrov@gmai.com", "6", "May", "1980", groupName));
      app.goTo().homePage();
    }
    app.goTo().homePage();
  }

  @Test
  public void testContactDeletion() throws InterruptedException {
    Contacts before = app.dbHelper().contacts();
    ContactData deletedContact = before.iterator().next(); // берем случайный элелемент из множества
    app.contactHelper().delete(deletedContact);
    Contacts after = app.dbHelper().contacts();
    assertThat(after.size(), equalTo(before.size()-1));
    assertThat(after, equalTo(before.without(deletedContact)));
  }
}
