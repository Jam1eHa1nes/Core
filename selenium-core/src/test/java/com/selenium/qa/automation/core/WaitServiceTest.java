package com.selenium.qa.automation.core;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class WaitServiceTest {

    @Mock
    static WebDriver driver;

    static WaitService waitService;

    @Mock
    private WebElement element;

    @BeforeAll
    static void setUp() {
        waitService = new WaitService(driver);
    }

    @Test
    void shouldNotThrowTimeoutForImmutableObject() {

        // given
        Integer number = 1;

        // then
        assertDoesNotThrow(() -> waitService.waitForStableValue(number, Integer::intValue, 5));
    }

    @Test
    void shouldNotThrowTimeoutWhenValueIsStable() {

        // given
        List<WebElement> webElements = List.of(element, element);
        when(element.findElements(any())).thenReturn(webElements);

        waitService.setNumberOfConsistentValuesRequired(2);
        By locator = By.id("testId");

        // then
        assertDoesNotThrow(() -> waitService.waitForStableValue(element, webElement -> webElement.findElements(locator), 3));
        verify(element, times(3)).findElements(locator);
    }

    @Test
    void shouldThrowTimeoutWhenValueIsNotStable() {

        // given
        List<WebElement> webElements = List.of(element, element);
        List<WebElement> otherWebElements = List.of(element, element, element);
        when(element.findElements(any())).thenReturn(webElements, otherWebElements, webElements, otherWebElements);

        waitService.setPollingIntervalMillis(500);
        waitService.setNumberOfConsistentValuesRequired(2);

        By locator = By.id("testId");

        // then
        assertThrows(TimeoutException.class, () -> waitService.waitForStableValue(element, webElement -> webElement.findElements(locator), 2));
        verify(element, times(5)).findElements(locator);
    }
}
