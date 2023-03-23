package ru.stqa.pft.addressbook.model;

import com.google.gson.annotations.Expose;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

import java.io.File;
import java.util.Objects;

@XStreamAlias("contact")
public class ContactData {
  @XStreamOmitField
  private int id;
  @Expose
  private String name;
  @Expose
  private String surname;
  private String nickname;
  private String company;
  @Expose
  private String home;
  @Expose
  private String mobile;
  private String work;
  @Expose
  private String email;
  @Expose
  private String email2;

  private String email3;
  private String bday;
  private String bmonth;
  private String byear;
  private String group;

  private String allPhones;

  private String allEmails;
  @Expose
  private String address;

  private File photo;

  public File getPhoto() {
    return photo;
  }

  public ContactData setPhoto(File photo) {
    this.photo = photo;
    return this;
  }

  public ContactData() {}

  public ContactData(String name, String surname, String nickname, String company, String home, String mobile, String work, String email, String email2, String bday, String bmonth, String byear, String group) {
    this.id = Integer.MAX_VALUE;
    this.name = name;
    this.surname = surname;
    this.nickname = nickname;
    this.company = company;
    this.home = home;
    this.mobile = mobile;
    this.work = work;
    this.email = email;
    this.email2 = email2;
    this.bday = bday;
    this.bmonth = bmonth;
    this.byear = byear;
    this.group = group;
  }

  public ContactData(int id, String name, String surname, String nickname, String company, String home, String mobile, String work, String email, String email2, String bday, String bmonth, String byear, String group) {
    this.id = id;
    this.name = name;
    this.surname = surname;
    this.nickname = nickname;
    this.company = company;
    this.home = home;
    this.mobile = mobile;
    this.work = work;
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

  public ContactData setName(String name) {
    this.name = name;
    return this;
  }

  public ContactData setSurname(String surname) {
    this.surname = surname;
    return this;
  }

  public ContactData setNickname(String nickname) {
    this.nickname = nickname;
    return this;
  }

  public ContactData setCompany(String company) {
    this.company = company;
    return this;
  }

  public ContactData setHome(String home) {
    this.home = home;
    return this;
  }

  public ContactData setMobile(String mobile) {
    this.mobile = mobile;
    return this;
  }

  public ContactData setWork(String work) {
    this.work = work;
    return this;
  }

  public ContactData setEmail(String email) {
    this.email = email;
    return this;
  }

  public ContactData setEmail2(String email2) {
    this.email2 = email2;
    return this;
  }

  public ContactData setEmail3(String email3) {
    this.email3 = email3;
    return this;
  }
  public ContactData setBday(String bday) {
    this.bday = bday;
    return this;
  }

  public ContactData setBmonth(String bmonth) {
    this.bmonth = bmonth;
    return this;
  }

  public ContactData setByear(String byear) {
    this.byear = byear;
    return this;
  }

  public ContactData setGroup(String group) {
    this.group = group;
    return this;
  }

  public ContactData setAddress(String address) {
    this.address = address;
    return this;
  }

  public ContactData setAllPhones(String allPhones) {
    this.allPhones = allPhones;
    return this;
  }

  public ContactData setAllEmails(String allEmails) {
    this.allEmails = allEmails;
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

  public String getEmail3() {
    return email3;
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

  public String getWork() { return work; }

  public String getAddress() {
    return address;
  }

  public String getAllPhones() {
    return allPhones;
  }

  public String getAllEmails() {
    return allEmails;
  }

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
