package ru.stqa.pft.mantis.model;

public class User {

  private int id;
  private String username;
  private String email;

  private String passwordEncrypted;

  public User(int id, String username, String email, String passwordEncrypted) {
    this.id = id;
    this.username = username;
    this.email = email;
    this.passwordEncrypted = passwordEncrypted;
  }

  public int getId() {
    return id;
  }

  public String getUsername() {
    return username;
  }

  public String getEmail() {
    return email;
  }

  public String getPasswordEncrypted() {
    return passwordEncrypted;
  }
}
