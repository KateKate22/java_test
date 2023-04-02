package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactDeleteFromGroupTests extends TestBase {
  ContactData contactToDeleteFromGroup; // контакт, с которым будут осуществляться манипуляции в тесте (удаление контакта из группы)
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

    contactToDeleteFromGroup = app.dbHelper().contacts().iterator().next(); // выбираем случайный контакт

    //если нет ни одной группы, то создаем ее и включаем контакт в группу (раз не было групп, значит контакт не вкючен ни в одну из них)
    if (app.dbHelper().groups().size() == 0) {
      app.goTo().groupPage();
      app.groupHelper().create(new GroupData().withName("test1").withHeader("header1").withFooter("footer1"));
      app.goTo().homePage();
      app.contactHelper().addInGroup(contactToDeleteFromGroup);
      contactToDeleteFromGroup = app.dbHelper().contact(contactToDeleteFromGroup.getId());
    }
    //если искомый контакт не состоит ни в одной из групп, и при этом есть хотя бы одна группа, включаем контакт в группу
    if (contactToDeleteFromGroup.getGroups().size() == 0 && app.dbHelper().groups().size() != 0) {
      app.goTo().homePage();
      app.contactHelper().addInGroup(contactToDeleteFromGroup);
      contactToDeleteFromGroup = app.dbHelper().contact(contactToDeleteFromGroup.getId());
    }
  }

  @Test
  public void testDeleteFromGroup() {
    app.goTo().homePage();
    // берем случайную группу, в которую входит искомый контакт, и присваиваем переменной идентификатор группы
    int groupIdToDeleteFrom = contactToDeleteFromGroup.getGroups().iterator().next().getId();
    // сохраняем в переменную исходный список групп, в которые входит контакт
    Groups contactGroupsBefore = contactToDeleteFromGroup.getGroups();
    // вызываем метод удаления контакта из группы, в качестве параметров передаем искомый контакт (объект)
    // и идентификатор грппы, из которой будем контакт удалять
    app.contactHelper().deleteFromGroup(contactToDeleteFromGroup, groupIdToDeleteFrom);
    // из БД получаем объект группы с искомым идентификатором (та самая группа, из которой контакт был удален)
    GroupData groupToDelete = app.dbHelper().group(groupIdToDeleteFrom);
    // из БД получаем искомый контакт по идентификатору (после его удаления из группы)
    ContactData cont = app.dbHelper().contact(contactToDeleteFromGroup.getId());
    Groups contactGroupsAfter = cont.getGroups(); // получаем список групп контакта (после его удаления из группы)
    assertThat(contactGroupsAfter.size(), equalTo(contactGroupsBefore.size() - 1)); // сравниваем по размерности списки групп (в которые входит контакт) до и после
    assertThat(contactGroupsAfter, equalTo(contactGroupsBefore.without(groupToDelete))); //сравниваем сами списки
  }
}
