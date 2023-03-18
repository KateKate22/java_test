package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactCreationTests extends TestBase {
  String groupName; //  строковая переменная, хранящая имя группы, для последующей передачи в качестве параметра при создании контакта;

  @BeforeMethod
  public void ensurePreconditions() {
    app.goTo().groupPage(); // переходим в группы для осуществления последующей проверки
    if (app.group().all().size() == 0) { // проверяем есть ли хотя бы одна группа в списке; если нет, создаем ее
      app.group().create(new GroupData().withName("test1"));
    }
    groupName = app.group().groupName(); // записываем в строковую переменную имя созданной либо уже имеющейся группы
    app.goTo().homePage(); // переход на страницу с контактами
  }

  @Test
  public void testContactCreation() throws Exception {
    Contacts before = app.contact().all();
    ContactData contact = new ContactData("Petr", "Petrov", "Wee", "Flower", "88142555555", "+79110000000", "peterpetrov@yandex.ru", "peter11petrov@gmai.com", "6", "May", "1980", groupName);
    app.contact().create(contact);
    app.goTo().homePage();
    Contacts after = app.contact().all();
    assertThat(after.size(), equalTo(before.size()+1));
    assertThat(after, equalTo(
            before.withAdded(contact.setId(after.stream().mapToInt((g) -> g.getId()).max().getAsInt()))));
  }
}
