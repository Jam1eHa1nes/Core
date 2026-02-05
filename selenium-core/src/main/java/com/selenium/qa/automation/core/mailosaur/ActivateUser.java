package com.selenium.qa.automation.core.mailosaur;

import com.mailosaur.MailosaurClient;
import com.mailosaur.MailosaurException;
import com.mailosaur.models.Link;
import com.mailosaur.models.Message;
import com.mailosaur.models.MessageSearchParams;
import com.mailosaur.models.SearchCriteria;
import com.selenium.qa.automation.core.locators.Target;
import com.selenium.qa.automation.core.performable.Performable;
import org.apache.commons.lang3.RandomStringUtils;

import java.io.IOException;
import java.util.List;

import static com.selenium.qa.automation.core.Enums.Tag.A;
import static com.selenium.qa.automation.core.mailosaur.Config.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ActivateUser extends Performable {

    private String userName;

    public ActivateUser(String userName) {
        this.userName = userName;
    }

    final Target INPUT_PASSWORD =  null;//data_ci_id("input-password");
    final Target CONFIRM_PASSWORD =  null; //data_ci_id("input-password-confirm");
    final Target ACTIVATE =     tagWithText(A, "Activate");
    final Target HEADING4 =     className("Heading4");

    @Override
    public void run() {

        String url = null;
        String password = null;

        MailosaurClient mailosaur = new MailosaurClient(apiKey);

        MessageSearchParams params = new MessageSearchParams();
        // See https://currentmillis.com/ to convert date/time to MS
        params.withServer(serverId);

        SearchCriteria criteria = new SearchCriteria();
        criteria.withSentTo("admin@" + serverDomain);
        Message message = null;
        try {
            message = mailosaur.messages().get(params, criteria);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (MailosaurException e) {
            throw new RuntimeException(e);
        }
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
//            log("Hypertext : "+link.text());
//            log("Hyperlink : "+link.href());

        assertNotNull(message);
        password =  RandomStringUtils.randomAlphanumeric(10) + "!&%";
        open();
        go(url);
        focus(INPUT_PASSWORD).compose(password);
        focus(CONFIRM_PASSWORD).compose(password);
        focus(ACTIVATE).click();
        // Confirmation page
        focus(HEADING4).contains("activated");
        descend(tag(A)).click();

    }

    @Override
    public String description() {
        return "Activating User : "+userName;
    }

}
