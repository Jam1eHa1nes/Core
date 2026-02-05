package com.selenium.qa.automation.core.mailosaur;

public interface MailInterface {

    public boolean messageContains( String text );
    public boolean subjectContains( String text );
    // Latest
    public void getMessage(Enums.Criteria criteria);
    // After time
    public void getMessage(Enums.Criteria criteria, long after);
    public void getMessages(Enums.Criteria criteria);
    public void getMessages(Enums.Criteria criteria, long afer);

}
