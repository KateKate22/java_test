package ru.stqa.pft.rest.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.rest.model.Issue;

import java.io.IOException;
import java.util.Set;

public class RestTests extends TestBase {

  @Test
  public void testExample() throws IOException {
    skipIfNotFixed(203);
    System.out.println("Тест пройден");
  }

  @Test
  public void testCreateIssue() throws IOException {
    Set<Issue> oldIssues = app.restHelper().getIssue();
    Issue newIssue = new Issue().withSubject("test issueKKK").withDescription("New test issueKKK");
    int issueId = app.restHelper().createIssue(newIssue);
    Set<Issue> newIssues = app.restHelper().getIssue();
    oldIssues.add(newIssue.withId(issueId));
    Assert.assertEquals(oldIssues, newIssues);
  }
}
