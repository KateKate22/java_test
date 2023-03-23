package ru.stqa.pft.addressbook.generators;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.ParameterException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.thoughtworks.xstream.XStream;
import ru.stqa.pft.addressbook.model.GroupData;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

public class GroupDataGenerator {

  @Parameter(names = "-c", description = "Group count")
  public int count;

  @Parameter(names = "-p", description = "File Path")
  public String filePath;

  @Parameter(names = "-f", description = "Saving format")
  public String format;

public static void main(String[] args) throws IOException {
  GroupDataGenerator generator = new GroupDataGenerator();
  JCommander jCommander = new JCommander(generator);
  try {
    jCommander.parse(args);
  } catch (ParameterException ex) {
    jCommander.usage();
    return;
  }
  generator.run();
  //int count = Integer.parseInt(args[0]);
  //File file = new File(args[1]);
}

  void run() throws IOException {
    List<GroupData> groups = makeListGroups(count);
    if (format.equals("csv")) {
      writeGroupsInFileCsv(groups, new File(filePath));
    } else if (format.equals("xml")) {
      writeGroupsInFileXml(groups, new File(filePath));
    } else if (format.equals("json")) {
      writeGroupsInFileJson(groups, new File(filePath));
    }
    else {
      System.out.println("Unrecognized format: " + format);
    }
  }

  private void writeGroupsInFileJson(List<GroupData> groups, File file) throws IOException {
    Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().setPrettyPrinting().create();
    String json = gson.toJson(groups);
    Writer writeInFile = new FileWriter(file);
    writeInFile.write(json);
    writeInFile.close();
  }

  private void writeGroupsInFileXml(List<GroupData> groups, File file) throws IOException {
    XStream xstream = new XStream();
    xstream.processAnnotations(GroupData.class);
    //xstream.alias("group", GroupData.class);
    String xml = xstream.toXML(groups);
    Writer writeInFile = new FileWriter(file);
    writeInFile.write(xml);
    writeInFile.close();
  }

  private void writeGroupsInFileCsv(List<GroupData> groups, File file) throws IOException {
    Writer writeInFile = new FileWriter(file);
    for (GroupData group: groups) {
    writeInFile.write(String.format("%s; %s; %s\n", group.getName(), group.getHeader(), group.getFooter()));
    }
    writeInFile.close();
  }

  private List<GroupData> makeListGroups(int count) {
  List<GroupData> groups = new ArrayList<>();
  for (int i = 0; i < count; i++) {
    groups.add(new GroupData().withName(String.format("test %s", i))
            .withHeader(String.format("header %s", i)).withFooter(String.format("footer %s", i)));
  }
  return groups;
  }

}
