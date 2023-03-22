package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class GroupCreationTests extends TestBase {

  @DataProvider
  public Iterator<Object[]> validGroups() throws IOException {
    List<Object[]> list = new ArrayList<>();
    BufferedReader reader = new BufferedReader(new FileReader(new File("src\\test\\resources\\groups.csv")));
    String line = reader.readLine();
    while (line != null) {
      String[] attrs = line.split(";");
      list.add(new Object[]{new GroupData().withName(attrs[0]).withHeader(attrs[1]).withFooter(attrs[2])});
      line = reader.readLine();
    }
    /*list.add(new Object[]{new GroupData().withName("test1").withHeader("header1").withFooter("footer1")});
    list.add(new Object[]{new GroupData().withName("test2").withHeader("header2").withFooter("footer2")});
    list.add(new Object[]{new GroupData().withName("test3").withHeader("header3").withFooter("footer3")});*/
    return list.iterator();
  }

  @Test(dataProvider = "validGroups")
  public void testGroupCreation(GroupData group) throws Exception {
    //GroupData group = new GroupData();
    app.goTo().groupPage();
    Groups before = app.group().all();
    app.group().create(group);
    assertThat(app.group().count(), equalTo(before.size() + 1)); // хэширование (предварительная проверка при помощи более быстрой операции)
    Groups after = app.group().all();
    //group.withId(after.stream().mapToInt((g) -> g.getId()).max().getAsInt());
    //Comparator<? super GroupData> byId = (g1, g2) -> Integer.compare(g1.getId(), g2.getId());
    //before.sort(byId);
    //after.sort(byId);
    assertThat(after, equalTo(
            before.withAdded(group.withId(after.stream().mapToInt((g) -> g.getId()).max().getAsInt()))));
  }

  @Test
  public void testBadGroupCreation() throws Exception {
    app.goTo().groupPage();
    Groups before = app.group().all();
    GroupData group = new GroupData().withName("test2'");
    app.group().create(group);
    assertThat(app.group().count(), equalTo(before.size()));
    Groups after = app.group().all();
    assertThat(after, equalTo(before));
  }
}
