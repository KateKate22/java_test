package ru.stqa.pft.mantis.tests;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.lanwen.verbalregex.VerbalExpression;
import ru.stqa.pft.mantis.model.MailMessage;
import ru.stqa.pft.mantis.model.User;

import javax.mail.MessagingException;
import java.io.IOException;

public class ResetPasswordTests extends TestBase{

  @BeforeMethod
  public void startMailServer(){
    app.mailHelper().start();
  }

  @Test
  public void TestResetPassword() throws MessagingException, IOException {
    long now = System.currentTimeMillis();
    String password = String.format("passwordEdited%s", now);
    app.authorizationHelper().authorize();
    app.navigationHelper().goToUserControl();
    // выбираем из БД пользователя для теста
    User user = app.dbHelper().getUsersFromDb().iterator().next();
    // запишем в переменную зашифрованный пароль пользователя
    String passwordBefore = user.getPasswordEncrypted();
    app.controlHelper().resetPassword(user.getUsername());
    MailMessage mess = app.mailHelper().waitForMailForUser(1, 1000, user.getEmail());
    String confirmationLink = findConfirmationLink(mess);
    app.registrationHelper().finish(confirmationLink, password);
    // из БД берем обновленного пользователя после всех манипуляций
    User userAfter = app.dbHelper().getUsersFromDb().stream().filter((m) -> m.getId() == user.getId()).findFirst().get();
    // запишем в переменную зашифрованный пароль пользователя после изменения
    String passwordAfter = userAfter.getPasswordEncrypted();

    // Проверка входа пользователя с новым паролем
    Assert.assertTrue(app.newSession().login(user.getUsername(), password));

    // Проверка, что в БД пароль был изменен (дополнительная проверка)
    Assert.assertFalse(passwordBefore.equals(passwordAfter));
  }

  private String findConfirmationLink(MailMessage mess) {
    VerbalExpression regex = VerbalExpression.regex().find("http://").nonSpace().oneOrMore().build();
    return regex.getText(mess.text);
  }

  @AfterMethod
  public void stopMailServer(){
    app.mailHelper().stop();
  }
}
