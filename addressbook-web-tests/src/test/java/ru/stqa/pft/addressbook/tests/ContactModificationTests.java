package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

public class ContactModificationTests extends TestBase {
  @Test
  public void testContactModification() {
    app.getContactHelper().returnToHomePage();
    app.getContactHelper().selectContactToEdit();
    app.getContactHelper().fillContactForm(new ContactData("PetrEdited", "PetrovEdited", "WeeEdited", "FlowerEdited", "88142551111", "+79110001111", "peterpetrovEdited@yandex.ru", "peter11petrovEdited@gmai.com", "1", "May", "1981"));
    app.getContactHelper().submitContactModification();
    app.getContactHelper().returnToHomePage();
    app.getSessionHelper().logout();
  }
}
