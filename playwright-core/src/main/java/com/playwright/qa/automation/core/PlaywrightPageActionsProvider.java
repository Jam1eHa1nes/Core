package com.playwright.qa.automation.core;

import com.core.qa.automation.common.PageActions;
import com.core.qa.automation.common.PageActionsProvider;
import com.core.qa.automation.common.PageFactory;

/**
 * ServiceLoader provider for Playwright PageActions implementation.
 */
public class PlaywrightPageActionsProvider implements PageActionsProvider {

    @Override
    public PageFactory.Framework getFramework() {
        return PageFactory.Framework.PLAYWRIGHT;
    }

    @Override
    public PageActions create() {
        return new PlaywrightPageActions();
    }
}
