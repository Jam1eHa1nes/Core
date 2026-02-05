package com.selenium.qa.automation.core.mailosaur;

import org.junit.Test;

import static com.selenium.qa.automation.core.mailosaur.Config.*;
import static org.junit.Assert.*;

import com.mailosaur.MailosaurClient;
import com.mailosaur.MailosaurException;
import com.mailosaur.models.*;
import java.io.IOException;

public class EmailPrototypeSubjectCheck {
    @Test
    public void testExample() throws IOException, MailosaurException {


        MailosaurClient mailosaur = new MailosaurClient(apiKey);

        MessageSearchParams params = new MessageSearchParams();
        params.withServer(serverId);

        SearchCriteria criteria = new SearchCriteria();
        criteria.withSentTo("anybody@" + serverDomain);

        Message message = mailosaur.messages().get(params, criteria);

        assertNotNull(message);
        assertEquals("Test ggsgpd44.mailosaur.net", message.subject());
    }
}

