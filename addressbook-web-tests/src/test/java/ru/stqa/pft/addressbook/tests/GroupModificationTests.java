package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class GroupModificationTests extends TestBase {

  @BeforeMethod
  public void ensurePrecondions() {
    app.goTo().groupPage();
    if (app.dbHelper().groups().size() == 0) {
      app.groupHelper().create(new GroupData().withName("test1"));
    }
    /*if (app.group().all().size() == 0) { // проверяем есть ли хотя бы одна группа в списке
      app.group().create(new GroupData().withName("test1"));
    }*/
  }
  @Test
  public void testGroupModification() {
    //Groups before = app.groupHelper().all();
    Groups before = app.dbHelper().groups();
    GroupData modifiedGroup = before.iterator().next();
    GroupData group = new GroupData()
            .withId(modifiedGroup.getId()).withName("testEdited1").withHeader("testEdited2").withFooter("testEdited3");
    app.groupHelper().modify(group);
    assertThat(app.groupHelper().count(), equalTo(before.size()));
    //Groups after = app.groupHelper().all();
    Groups after = app.dbHelper().groups();
    assertThat(after, equalTo(before.without(modifiedGroup).withAdded(group)));
    //Assert.assertEquals(new HashSet<Object>(before), new HashSet<Object>(after));
    verifyGroupListInUI();
  }
}
