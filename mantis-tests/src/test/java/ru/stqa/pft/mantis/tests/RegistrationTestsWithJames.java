package ru.stqa.pft.mantis.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.lanwen.verbalregex.VerbalExpression;
import ru.stqa.pft.mantis.model.MailMessage;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.List;

public class RegistrationTestsWithJames extends TestBase {

  @Test
  public void testRegistration() throws MessagingException, IOException {
    long now = System.currentTimeMillis();
    String email = String.format("user%s@localhost", now);
    String user = String.format("user%s", now);
    String password = "password";
    app.jamesHelper().createUser(user, password);
    app.registrationHelper().start(user, email);
    List<MailMessage> mailMessages = app.jamesHelper().waitForMail(user, password, 60000);
    String confirmationLink = findConfirmationLink(mailMessages, email);
    app.registrationHelper().finish(confirmationLink, password);
    Assert.assertTrue(app.newSession().login(user, password));
  }

  private String findConfirmationLink(List<MailMessage> mailMessages, String email) {
    MailMessage mailMessage = mailMessages.stream().filter((m) -> m.to.equals(email)).findFirst().get();
    VerbalExpression regex = VerbalExpression.regex().find("http://").nonSpace().oneOrMore().build();
    return regex.getText(mailMessage.text);
  }
}
