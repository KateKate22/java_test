package ru.stqa.pft.addressbook.model;

import java.util.Objects;

public class ContactData {
  private int id;
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
    this.id = Integer.MAX_VALUE;
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

  public ContactData(int id, String name, String surname, String nickname, String company, String home, String mobile, String email, String email2, String bday, String bmonth, String byear, String group) {
    this.id = id;
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

  public ContactData setId(int id) {
    this.id = id;
    return this;
  }

  public int getId() {
    return id;
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

  @Override
  public String toString() {
    return "ContactData{" +
            "name='" + name + '\'' +
            ", surname='" + surname + '\'' +
            '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    ContactData that = (ContactData) o;
    return id == that.id && Objects.equals(name, that.name) && Objects.equals(surname, that.surname);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name, surname);
  }
}
