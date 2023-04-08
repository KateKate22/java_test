package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactAddInGroupTests extends TestBase {
  ContactData contactInGroup; // контакт, с которым будут осуществляться манипуляции в тесте (добавление контакта в группу)

  @BeforeMethod
  public void ensurePrecondions() {
    //проверяем есть ли хотя бы один контакт
    if (app.dbHelper().contacts().size() == 0) { // проверяем есть ли хотя бы один контакт в БД
      app.contactHelper().create(new ContactData().setName("Petr").setSurname("Petrov").setNickname("Wee")
              .setCompany("Flower").setAddress("Lenina street 33-8").setHome("88142555555").setMobile("+79110000000").
              setWork("555555").setEmail("peterpetrov@yandex.ru").setEmail2("peter11petrov@gmail.com").
              setEmail3("peter1234petrov@gmail.com").setBday("6").setBmonth("May").setByear("1980"));
      app.goTo().homePage();
    }

    contactInGroup = app.dbHelper().contacts().iterator().next(); // выбираем случайный контакт

    // если нет ни одной группы или искомый контакт состоит во всех существующих группах, то создадим еще одну группу
    if (contactInGroup.getGroups().size() >= app.dbHelper().groups().size() || app.dbHelper().groups().size() == 0) {
      app.goTo().groupPage();
      app.groupHelper().create(new GroupData().withName("test1").withHeader("header1").withFooter("footer1"));
    }
  }

  @Test
  public void testContactAddInGroup() {
    app.goTo().homePage();
    Groups contactGroupsBefore = contactInGroup.getGroups();
    int idGroup = app.contactHelper().addInGroup(contactInGroup, contactGroupsBefore); // вызываем метод добавления контакта в группу, который на выходе возвращает идентификатор группы, в которую был добавлен контакт
    GroupData groupToAdd = app.dbHelper().group(idGroup); // из БД получаем объект группы с искомым идентификатором
    ContactData cont = app.dbHelper().contact(contactInGroup.getId()); // из БД получаем искомый контакт по идентификатору
    Groups contactGroupsAfter = cont.getGroups(); // получаем список групп контакта после его добавления в группу
    assertThat(contactGroupsAfter.size(), equalTo(contactGroupsBefore.size() + 1)); // сравниваем по размерности списки групп (в которые входит контакт) до и после
    assertThat(contactGroupsAfter, equalTo(contactGroupsBefore.withAdded(groupToAdd))); //сравниваем сами списки
    //Set<ContactData> conts = groupToAdd.getContacts();
    //Boolean bool = conts.contains(contactInGroup);
  }
}
