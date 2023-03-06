package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.GroupData;

public class GroupModificationTests extends TestBase {
  @Test
  public void testGroupModification() {
    app.getNavigationHelper().gotoGroupPage();
    if (! app.getGroupHelper().groupExists()) { // проверяем есть ли хотя бы одна группа в списке
      app.getGroupHelper().createGroup(new GroupData("test1", null, null));
    }
      app.getGroupHelper().selectGroup();
      app.getGroupHelper().editSelectedGroups();
      app.getGroupHelper().fillGroupForm(new GroupData("testEdited1", "test2", "testEdited3"));
      app.getGroupHelper().submitGroupModification();
      app.getGroupHelper().returnToGroupPage();
      app.getSessionHelper().logout();
  }
}
