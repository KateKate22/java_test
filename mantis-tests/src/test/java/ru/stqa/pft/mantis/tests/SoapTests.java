package ru.stqa.pft.mantis.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.mantis.model.Issue;
import ru.stqa.pft.mantis.model.Project;

import javax.xml.rpc.ServiceException;
import java.net.MalformedURLException;
import java.rmi.RemoteException;
import java.util.Set;

public class SoapTests extends TestBase {

  @Test
  public void testExample() throws MalformedURLException, ServiceException, RemoteException {
    skipIfNotFixed(1);
    System.out.println("Тест пройден");
  }

  @Test
  public void testGetProjects() throws MalformedURLException, ServiceException, RemoteException {
    Set<Project> projects = app.soapHelper().getProjects();
    System.out.println(projects.size());
    for (Project project : projects) {
      System.out.println(project.getName());
    }
    System.out.println("-----");
  }

  @Test
  public void testCreateIssue() throws MalformedURLException, ServiceException, RemoteException {
    Set<Project> projects = app.soapHelper().getProjects();
    Issue issue = new Issue().withSummary("Test issue")
            .withDescription("Test issue descripton").withProject(projects.iterator().next());
    Issue created = app.soapHelper().addIssue(issue);
    Assert.assertEquals(issue.getSummary(), created.getSummary());
  }
}
