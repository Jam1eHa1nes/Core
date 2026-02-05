package com.selenium.qa.automation.core.mailosaur;

public class Config {

    protected static String ORGADMIN = "OrgAdmin";
    protected static String apiKey = "LI233hD1j5gb4uFAC57Yp1cMaBpEWOnS";
    protected static String serverId = "ggsgpd44";
    protected static String serverDomain = "ggsgpd44.mailosaur.net";
    private static int emailSuffix = 0;

    protected String getUserName() {
        return ORGADMIN+emailSuffix++;
    }

}
