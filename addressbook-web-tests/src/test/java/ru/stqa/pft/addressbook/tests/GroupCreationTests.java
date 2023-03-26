package ru.stqa.pft.addressbook.tests;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.thoughtworks.xstream.XStream;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

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

public class GroupCreationTests extends TestBase {
  @DataProvider
  public Iterator<Object[]> validGroupsFromCsv() throws IOException {
    List<Object[]> list = new ArrayList<>();
    try (BufferedReader reader = new BufferedReader(new FileReader(new File("src\\test\\resources\\groups.csv"))))
    {
      String line = reader.readLine();
      while (line != null) {
        String[] attrs = line.split(";");
        list.add(new Object[]{new GroupData().withName(attrs[0]).withHeader(attrs[1]).withFooter(attrs[2])});
        line = reader.readLine();
      }
      //list.add(new Object[]{new GroupData().withName("test1").withHeader("header1").withFooter("footer1")});
      //list.add(new Object[]{new GroupData().withName("test2").withHeader("header2").withFooter("footer2")});
      //list.add(new Object[]{new GroupData().withName("test3").withHeader("header3").withFooter("footer3")});
      return list.iterator();
    }
  }

  @DataProvider
  public Iterator<Object[]> validGroupsFromXml() throws IOException {
    try (BufferedReader reader = new BufferedReader(new FileReader(new File("src\\test\\resources\\groups.xml")))) {
      String xml = "";
      String line = reader.readLine();
      while (line != null) {
        xml += line;
        line = reader.readLine();
      }
      XStream xstream = new XStream();
      xstream.processAnnotations(GroupData.class);
      xstream.allowTypes(new Class[]{GroupData.class});
      List<GroupData> groups = (List<GroupData>) xstream.fromXML(xml);
      return groups.stream().map((g) -> new Object[]{g}).collect(Collectors.toList()).iterator();
    }
  }

  @DataProvider
  public Iterator<Object[]> validGroupsFromJson() throws IOException {
    try (BufferedReader reader = new BufferedReader(new FileReader(new File("src\\test\\resources\\groups.json")))) {
      String json = "";
      String line = reader.readLine();
      while (line != null) {
        json += line;
        line = reader.readLine();
      }
      Gson gson = new Gson();
      List<GroupData> groups = gson.fromJson(json, new TypeToken<List<GroupData>>() {
      }.getType());
      return groups.stream().map((g) -> new Object[]{g}).collect(Collectors.toList()).iterator();
    }
  }

  @Test(dataProvider = "validGroupsFromJson")
  public void testGroupCreation(GroupData group) throws Exception {
    //GroupData group = new GroupData();
    app.goTo().groupPage();
    Groups before = app.dbHelper().groups();
    app.groupHelper().create(group);
    assertThat(app.groupHelper().count(), equalTo(before.size() + 1)); // хэширование (предварительная проверка при помощи более быстрой операции)
    Groups after = app.dbHelper().groups();
    //group.withId(after.stream().mapToInt((g) -> g.getId()).max().getAsInt());
    //Comparator<? super GroupData> byId = (g1, g2) -> Integer.compare(g1.getId(), g2.getId());
    //before.sort(byId);
    //after.sort(byId);
    assertThat(after, equalTo(
            before.withAdded(group.withId(after.stream().mapToInt((g) -> g.getId()).max().getAsInt()))));
  }

  @Test(enabled = false)
  public void testBadGroupCreation() throws Exception {
    app.goTo().groupPage();
    Groups before = app.dbHelper().groups();
    GroupData group = new GroupData().withName("test2'");
    app.groupHelper().create(group);
    assertThat(app.groupHelper().count(), equalTo(before.size()));
    Groups after = app.dbHelper().groups();
    assertThat(after, equalTo(before));
  }
}
