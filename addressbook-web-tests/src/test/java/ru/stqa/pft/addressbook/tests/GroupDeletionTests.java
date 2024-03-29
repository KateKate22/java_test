package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class GroupDeletionTests extends TestBase {
  @BeforeMethod
  public void ensurePrecondions() {
    app.goTo().groupPage();
    if (app.groupHelper().all().size() == 0) { // проверяем есть ли хотя бы одна группа в списке
      app.groupHelper().create(new GroupData().withName("test1"));
    }
  }
  @Test
  public void testGroupDeletion() throws Exception {
    Groups before = app.dbHelper().groups();
    GroupData deletedGroup = before.iterator().next();
    app.groupHelper().delete(deletedGroup);
    assertThat(app.groupHelper().count(), equalTo(before.size()-1));
    Groups after = app.dbHelper().groups();
    assertThat(after, equalTo(before.without(deletedGroup)));
    verifyGroupListInUI();
  }
}
