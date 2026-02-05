package com.core.qa.automation.common;

/**
 * Service Provider Interface for PageActions implementations.
 * Implementations of this interface are discovered via Java ServiceLoader.
 * <p>
 * To register an implementation, create a file:
 * <code>META-INF/services/com.core.qa.automation.common.PageActionsProvider</code>
 * containing the fully qualified class name of the provider implementation.
 */
public interface PageActionsProvider {

    /**
     * Gets the framework this provider supports.
     *
     * @return the framework type
     */
    PageFactory.Framework getFramework();

    /**
     * Creates a new PageActions instance.
     *
     * @return a new PageActions instance
     */
    PageActions create();
}
