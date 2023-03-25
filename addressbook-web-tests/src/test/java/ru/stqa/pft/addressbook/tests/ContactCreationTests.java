package ru.stqa.pft.addressbook.tests;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.thoughtworks.xstream.XStream;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactCreationTests extends TestBase {
  //String groupName; //  строковая переменная, хранящая имя группы, для последующей передачи в качестве параметра при создании контакта;

  @DataProvider
  public Iterator<Object[]> contactsDataTestXml() throws IOException {
    List<ContactData> list = new ArrayList<>();
    try (BufferedReader reader = new BufferedReader(new FileReader(new File("src\\test\\resources\\contacts.xml")))) {
      String line = reader.readLine();
      String xmlData = "";
      while (line != null) {
        xmlData += line;
        line = reader.readLine();
      }
      XStream xstream = new XStream();
      xstream.processAnnotations(ContactData.class);
      xstream.allowTypes(new Class[]{ContactData.class});
      List<ContactData> contacts = (List<ContactData>) xstream.fromXML(xmlData);
      return contacts.stream().map((g) -> new Object[]{g}).collect(Collectors.toList()).iterator();
    }
  }

  @DataProvider
  public Iterator<Object[]> contactsDataTestJson() throws IOException {
    List<ContactData> list = new ArrayList<>();
    try (BufferedReader reader = new BufferedReader(new FileReader(new File("src\\test\\resources\\contacts.json"))))
    {
      String line = reader.readLine();
      String jsonData = "";
      while (line != null) {
        jsonData += line;
        line = reader.readLine();
      }
      Gson gson = new Gson();
      List<ContactData> contacts = gson.fromJson(jsonData, new TypeToken<List<ContactData>>() {
      }.getType());
      return contacts.stream().map((g) -> new Object[]{g}).collect(Collectors.toList()).iterator();
    }
  }

  @BeforeMethod
  public void ensurePreconditions() {
    app.goTo().groupPage(); // переходим в группы для осуществления последующей проверки
    if (app.group().all().size() == 0) { // проверяем есть ли хотя бы одна группа в списке; если нет, создаем ее
      app.group().create(new GroupData().withName("test1"));
    }
    //groupName = app.group().groupName(); // записываем в строковую переменную имя созданной либо уже имеющейся группы
    app.goTo().homePage(); // переход на страницу с контактами
  }

  @Test(dataProvider = "contactsDataTestXml")
  public void testContactCreation(ContactData contact) throws Exception {
    Contacts before = app.contact().all();
    app.contact().create(contact);
    app.goTo().homePage();
    Contacts after = app.contact().all();
    assertThat(after.size(), equalTo(before.size() + 1));
    assertThat(after, equalTo(
            before.withAdded(contact.setId(after.stream().mapToInt((g) -> g.getId()).max().getAsInt()))));
  }
}
