package ru.stqa.pft.rest.appmanager;

import java.io.IOException;

public class ApplicationManager {
  private RestHelper restHelper;


  public void init() throws IOException {
    restHelper = new RestHelper();
  }

  public void stop() {
  }

  public RestHelper restHelper() {
    return restHelper;
  }
}
