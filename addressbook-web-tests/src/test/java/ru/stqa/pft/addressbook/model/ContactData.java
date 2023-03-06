package ru.stqa.pft.addressbook.model;

public class ContactData {
  private final String name;
  private final String surname;
  private final String nickname;
  private final String company;
  private final String home;
  private final String mobile;
  private final String email;
  private final String email2;
  private final String bday;
  private final String bmonth;
  private final String byear;
  private final String group;

  public ContactData(String name, String surname, String nickname, String company, String home, String mobile, String email, String email2, String bday, String bmonth, String byear, String group) {
    this.name = name;
    this.surname = surname;
    this.nickname = nickname;
    this.company = company;
    this.home = home;
    this.mobile = mobile;
    this.email = email;
    this.email2 = email2;
    this.bday = bday;
    this.bmonth = bmonth;
    this.byear = byear;
    this.group = group;
  }

  public String getName() {
    return name;
  }

  public String getSurname() {
    return surname;
  }

  public String getNickname() {
    return nickname;
  }

  public String getCompany() {
    return company;
  }

  public String getHome() {
    return home;
  }

  public String getMobile() {
    return mobile;
  }

  public String getEmail() {
    return email;
  }

  public String getEmail2() {
    return email2;
  }

  public String getBday() {
    return bday;
  }

  public String getBmonth() {
    return bmonth;
  }

  public String getByear() {
    return byear;
  }

  public String getGroup() { return group; }
}
