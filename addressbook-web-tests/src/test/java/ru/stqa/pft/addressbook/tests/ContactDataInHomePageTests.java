package ru.stqa.pft.addressbook.tests;

import org.hamcrest.MatcherAssert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.GroupData;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.hamcrest.CoreMatchers.equalTo;

public class ContactDataInHomePageTests extends TestBase {
  String groupName; //  строковая переменная, хранящая имя группы, для последующей передачи в качестве параметра при создании контакта;
  @BeforeMethod
  public void ensurePreconditions() {
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
    app.goTo().homePage();
  }
  @Test
  public void testContactDataInHomePage() {
    ContactData contact = app.contact().all().iterator().next();
    ContactData contactInfoFromEditForm = app.contact().infoFromEditForm(contact);
    // проверяем совпадение адреса на главной странице и форме редактирования:
    MatcherAssert.assertThat(contact.getAddress(), equalTo(contactInfoFromEditForm.getAddress()));
    // проверяем совпадение эмейлов на главной странице и форме редактирования:
    MatcherAssert.assertThat(contact.getAllEmails(), equalTo(mergeEmails(contactInfoFromEditForm)));
    // проверяем совпадение телефонов на главной странице и форме редактирования:
    MatcherAssert.assertThat(contact.getAllPhones(), equalTo(mergePhones(contactInfoFromEditForm)));

  }
  public String mergePhones(ContactData contact) {
    return Stream.of(contact.getHome(), contact.getMobile(), contact.getWork()).filter((s) -> ! s.equals(""))
            .map(ContactDataInHomePageTests::cleaned)
            .collect(Collectors.joining("\n"));
  }

  public String mergeEmails(ContactData contact) {
    return Stream.of(contact.getEmail(), contact.getEmail2(), contact.getEmail3()).filter((s) -> ! s.equals(""))
            .collect(Collectors.joining("\n"));
  }

  public static String cleaned (String phone) {
    return phone.replaceAll("\\s", "").replaceAll("[-()]", "");
  }
}
