package ru.stqa.pft.rest.appmanager;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import org.apache.http.client.fluent.Executor;
import org.apache.http.client.fluent.Request;
import org.apache.http.message.BasicNameValuePair;
import ru.stqa.pft.rest.model.Issue;

import java.io.IOException;
import java.util.Set;

public class RestHelper {

  public Set<Issue> getIssue() throws IOException {
    String json = getExecutor().execute(Request.Get("https://bugify.stqa.ru/api/issues.json?pageSize=500"))
            .returnContent()
            .asString();
    JsonElement parsed = new JsonParser().parse(json);
    JsonElement issues = parsed.getAsJsonObject().get("issues");
    return new Gson().fromJson(issues, new TypeToken<Set<Issue>>() {
    }.getType());
  }

  private Executor getExecutor() {
    return Executor.newInstance().auth("b31e382ca8445202e66b03aaf31508a3", "");
  }

  public int createIssue(Issue newIssue) throws IOException {
    String json = getExecutor().execute(Request.Post("https://bugify.stqa.ru/api/issues.json")
                    .bodyForm(new BasicNameValuePair("subject", newIssue.getSubject()), new BasicNameValuePair("description", newIssue.getDescription())))
            .returnContent().asString();
    JsonElement parsed = new JsonParser().parse(json);
    return parsed.getAsJsonObject().get("issue_id").getAsInt();
  }

  public boolean BugIsNotFixed(int issueId) throws IOException {
    String json = getExecutor().execute(Request.Get(String.format("https://bugify.stqa.ru/api/issues/%d.json", issueId)))
            .returnContent()
            .asString();
    JsonElement parsed = new JsonParser().parse(json);
    JsonElement issuesJson = parsed.getAsJsonObject().get("issues");
    Set<Issue> issues = new Gson().fromJson(issuesJson, new TypeToken<Set<Issue>>() {
    }.getType());
    Issue issue = issues.stream().filter((m) -> m.getId() == issueId).findFirst().get();
    if (!issue.getState_name().equals("Resolved") && !issue.getState_name().equals("Closed")) {
      return true;
    } else {
      return false;
    }
  }
}
