package ru.stqa.pft.mantis.appmanager;

import ru.stqa.pft.mantis.model.User;

import java.sql.*;
import java.util.*;

public class DbHelper {

  private final ApplicationManager app;
  public DbHelper(ApplicationManager app) {
    this.app = app;
  }

  public List<User> getUsersFromDb() {
    Connection conn = null;
    List<User> users = new ArrayList<User>();
    try {
      conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/bugtracker?user=root&password=");
      Statement st = conn.createStatement();
      // выбираем из БД юзеров без админских прав
      ResultSet rs = st.executeQuery("select id, username, email, password from mantis_user_table where access_level=25");
      while (rs.next()) {
        users.add(new User(rs.getInt("id"), rs.getString("username"), rs.getString("email"), rs.getString("password")));
      }
      rs.close();
      st.close();
      conn.close();

    } catch (SQLException ex) {
      System.out.println("SQLException: " + ex.getMessage());
      System.out.println("SQLState: " + ex.getSQLState());
      System.out.println("VendorError: " + ex.getErrorCode());
    }
    return users;
  }
}
