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
    if (app.group().all().size() == 0) { // проверяем есть ли хотя бы одна группа в списке
      app.group().create(new GroupData().withName("test1"));
    }
  }
  @Test
  public void testGroupModification() {
    Groups before = app.group().all();
    GroupData modifiedGroup = before.iterator().next();
    GroupData group = new GroupData()
            .withId(modifiedGroup.getId()).withName("testEdited1").withHeader("testEdited2").withFooter("testEdited3");
    app.group().modify(group);
    Groups after = app.group().all();
    assertThat(after.size(), equalTo(before.size()));
    assertThat(after, equalTo(before.without(modifiedGroup).withAdded(group)));
    //Assert.assertEquals(new HashSet<Object>(before), new HashSet<Object>(after));
  }
}
