package com.selenium.qa.automation.core.mailosaur;

import com.mailosaur.MailosaurClient;
import com.mailosaur.MailosaurException;
import com.mailosaur.models.Message;
import com.mailosaur.models.MessageSearchParams;
import com.mailosaur.models.SearchCriteria;
import org.junit.Test;

import java.io.IOException;

import static com.selenium.qa.automation.core.mailosaur.Config.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class EmailPrototypeAfterTestStart {
    @Test
    public void testExample() throws IOException, MailosaurException {

        long testStart = System.currentTimeMillis();

        MailosaurClient mailosaur = new MailosaurClient(apiKey);

        MessageSearchParams params = new MessageSearchParams();
        params.withServer(serverId).withReceivedAfter(testStart);

        SearchCriteria criteria = new SearchCriteria();
        criteria.withSentTo("anybody@" + serverDomain);

        Message message = mailosaur.messages().get(params, criteria);

        assertNotNull(message);
        assertEquals("Test ggsgpd44.mailosaur.net", message.subject());
    }
}

