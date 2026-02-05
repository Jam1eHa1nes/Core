package com.selenium.qa.automation.core.mailosaur;

public class MailServices implements MailInterface {
    @Override
    public boolean messageContains(String text) {
        return false;
    }

    @Override
    public boolean subjectContains(String text) {
        return false;
    }

    @Override
    public void getMessage(Enums.Criteria criteria) {

    }

    @Override
    public void getMessage(Enums.Criteria criteria, long after) {

    }

    @Override
    public void getMessages(Enums.Criteria criteria) {

    }

    @Override
    public void getMessages(Enums.Criteria criteria, long afer) {

    }
}
