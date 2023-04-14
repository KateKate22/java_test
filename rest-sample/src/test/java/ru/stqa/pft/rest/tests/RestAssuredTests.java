package ru.stqa.pft.rest.tests;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.jayway.restassured.RestAssured;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.stqa.pft.rest.model.Issue;

import java.io.IOException;
import java.util.Set;

public class RestAssuredTests {

  @BeforeClass
  public void init() {
    RestAssured.authentication = RestAssured.basic("b31e382ca8445202e66b03aaf31508a3", "");
  }
  @Test
  public void testCreateIssue() throws IOException {
    Set<Issue> oldIssues = getIssue();
    Issue newIssue = new Issue().withSubject("test issueKKK").withDescription("New test issueKKK");
    int issueId = createIssue(newIssue);
    Set<Issue> newIssues = getIssue();
    oldIssues.add(newIssue.withId(issueId));
    Assert.assertEquals(oldIssues, newIssues);
  }

  private Set<Issue> getIssue() throws IOException {
    String json = RestAssured.get("https://bugify.stqa.ru/api/issues.json?pageSize=500").asString();
    JsonElement parsed = new JsonParser().parse(json);
    JsonElement issues = parsed.getAsJsonObject().get("issues");
    return new Gson().fromJson(issues, new TypeToken<Set<Issue>>(){}.getType());
  }

  private int createIssue(Issue newIssue) throws IOException {
    String json = RestAssured.given()
            .parameter("subject", newIssue.getSubject())
            .parameter("description", newIssue.getDescription())
            .post("https://bugify.stqa.ru/api/issues.json").asString();
    JsonElement parsed = new JsonParser().parse(json);
    return parsed.getAsJsonObject().get("issue_id").getAsInt();
  }


}
