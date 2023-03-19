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
      app.contact().create(new ContactData().setName("Petr").setSurname("Petrov").setNickname("Wee")
              .setCompany("Flower").setAddress("Lenina street 33-8").setHome("88142555555").setMobile("+79110000000").
              setWork("555555").setEmail("peterpetrov@yandex.ru").setEmail2("peter11petrov@gmail.com").
              setEmail3("peter1234petrov@gmail.com").setBday("6").setBmonth("May").setByear("1980").setGroup(groupName));
      app.goTo().homePage();
    }
  }
  @Test
  public void testContactModification() {
    Contacts before = app.contact().all();
    ContactData modifiedContact = before.iterator().next();
    ContactData contact = new ContactData().setId(modifiedContact.getId()).setName("PetrEdited").setSurname("PetrovEdited").setNickname("WeeEdited")
            .setCompany("FlowerEdited").setAddress("Lenina street 33-8").setHome("88142551111").setMobile("+79110001111").
            setWork("555555").setEmail("peterpetrovEdited@yandex.ru").setEmail2("peter11petrovEdited@gmai.com").
            setEmail3("peter1234petrovEdited@gmail.com").setBday("1").setBmonth("May").setByear("1980");
    app.contact().modify(contact);
    app.goTo().homePage();
    Contacts after = app.contact().all();
    //Assert.assertEquals(after.size(), before.size());
    assertThat(after.size(), equalTo(before.size()));
    assertThat(after, equalTo(before.without(modifiedContact).withAdded(contact)));
  }
}
