package ru.stqa.pft.addressbook.generators;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.ParameterException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.thoughtworks.xstream.XStream;
import ru.stqa.pft.addressbook.model.ContactData;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

public class ContactDataGenerator {
  @Parameter(names = "-c", description = "Num of contacts")
  public int count;

  @Parameter (names = "-p", description = "File Path")
  public String filePath;

  @Parameter (names = "-f", description = "Saving format")
  public String format;
  public static void main(String[] args) throws IOException {
    ContactDataGenerator generator = new ContactDataGenerator();
    JCommander jCommander = new JCommander(generator);
    try {
      jCommander.parse(args);
    } catch (ParameterException ex) {
      jCommander.usage();
      return;
    }
    generator.run();
  }
  private void run() throws IOException {
    List<ContactData> contacts = generateContacts(count);
    if (format.equals("xml")) {
      writeDataInFileXml(contacts, new File(filePath));
    } else if (format.equals("json")) {
      writeDataInFileJson(contacts, new File(filePath));
    } else {
      System.out.println("Unknown format: " + format);
    }
  }

  private void writeDataInFileJson(List<ContactData> contacts, File file) throws IOException {
    Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().setPrettyPrinting().create();
    String json = gson.toJson(contacts);
    try (Writer writeInFile = new FileWriter(file)) { //реализуем автоматическое закрытие файла
      writeInFile.write(json);
    }
    //writeInFile.close();
  }

  private void writeDataInFileXml(List<ContactData> contacts, File file) throws IOException {
    XStream xstream = new XStream();
    xstream.processAnnotations(ContactData.class);
    String xml = xstream.toXML(contacts);
    try (Writer writeInFile = new FileWriter(file))
    {
      writeInFile.write(xml);
    }
  }

  private List<ContactData> generateContacts(int count) {
    List<ContactData> contacts = new ArrayList<>();
    for (int i=0; i < count; i++) {
      contacts.add(new ContactData().setName(String.format("Petr%s", i+1)).setSurname(String.format("Petrov%s", i+1))
              .setMobile(String.format("+7911444112%s", i+1)).setHome(String.format("77-55-1%s", i+1))
              .setAddress(String.format("Lenina street %s", i+1)).setEmail(String.format("petrov%s@gmail.com", i+1))
              .setEmail2(String.format("petrov%s@yandex.ru", i+1)).setBday(String.format("%s", i+1)).setBmonth("May")
              .setGroup(String.format("test%s", i+1)));
    }
    return contacts;
  }
}
