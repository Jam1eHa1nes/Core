package com.selenium.qa.automation.core.mailosaur;

import com.mailosaur.MailosaurClient;
import com.mailosaur.MailosaurException;
import com.mailosaur.models.Link;
import com.mailosaur.models.Message;
import com.mailosaur.models.MessageSearchParams;
import com.mailosaur.models.SearchCriteria;
import com.selenium.qa.automation.core.CommonPageObject;
import com.selenium.qa.automation.core.locators.Target;
import org.junit.Test;

import java.io.IOException;
import java.security.SecureRandom;
import java.util.List;

import static com.selenium.qa.automation.core.Enums.Tag.A;
import static com.selenium.qa.automation.core.mailosaur.Config.*;
import static org.junit.Assert.assertNotNull;


public class EmailPrototypeWithHyperlinks extends CommonPageObject {
    @Test
    public void testExample() throws IOException, MailosaurException {

        final Target INPUT_PASSWORD =   id("input-password");
        final Target CONFIRM_PASSWORD =  id("input-password-confirm");
        final Target ACTIVATE =     tagWithText(A, "Activate");
        final Target HEADING4 =     className("Heading4");


        String url = null;
        String password = null;

        MailosaurClient mailosaur = new MailosaurClient(apiKey);

        MessageSearchParams params = new MessageSearchParams();
        // See https://currentmillis.com/ to convert date/time to MS
        params.withServer(serverId).withReceivedAfter(1725363000000L);

        SearchCriteria criteria = new SearchCriteria();
        criteria.withSentTo("admin@" + serverDomain);
        Message message = mailosaur.messages().get(params, criteria);
        log("From      : "+ message.from());
        log("To        : "+ message.to());
        log("Subject   : "+ message.subject());
        log("Content   : "+ message.text());
        log("Links found  : "+message.html().links().size());
        List<Link> links = message.html().links();
        for(Link link : links ) {
            if (link.text() != null) {
                if (link.text().equals("this activation link")) {
                    url = link.href();
                    break;
                }
            }
        }

        assertNotNull(message);
        SecureRandom secureRandom = new SecureRandom();
        StringBuilder passwordBuilder = new StringBuilder();
        String alphanumeric = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        for (int i = 0; i < 10; i++) {
            int index = secureRandom.nextInt(alphanumeric.length());
            passwordBuilder.append(alphanumeric.charAt(index));
        }
        password = passwordBuilder.toString() + "!&%";
        open();
        go(url);
        focus(INPUT_PASSWORD).compose(password);
        focus(CONFIRM_PASSWORD).compose(password);
        focus(ACTIVATE).click();
        // Confirmation page
        focus(HEADING4).contains("activated");
        descend(tag(A)).click();

    }
}

