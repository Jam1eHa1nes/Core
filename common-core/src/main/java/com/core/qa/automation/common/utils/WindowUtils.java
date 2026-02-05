package com.core.qa.automation.common.utils;

import java.awt.*;

/**
 * Utility class for window-related operations, such as retrieving screen positions.
 * <p>
 * <b>Example usage:</b>
 * <pre>
 *     int[] pos = WindowUtils.getPositionOfSelectedWindow(0);
 *     System.out.println("X: " + pos[0] + ", Y: " + pos[1]);
 * </pre>
 */
public class WindowUtils {

    private WindowUtils() {
        // Utility class
    }

    /**
     * Gets the position of a screen by index.
     *
     * @param screenIndex the screen index (0-based)
     * @return an array containing [x, y] coordinates
     */
    public static int[] getPositionOfSelectedWindow(int screenIndex) {
        GraphicsEnvironment graphicsEnvironment = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice[] screenDevices = graphicsEnvironment.getScreenDevices();

        GraphicsDevice selectedScreen;

        if (screenIndex < 0 || screenIndex >= screenDevices.length) {
            selectedScreen = graphicsEnvironment.getDefaultScreenDevice();
        } else {
            selectedScreen = screenDevices[screenIndex];
        }

        Rectangle selectedScreenBounds = selectedScreen.getDefaultConfiguration().getBounds();

        return new int[]{selectedScreenBounds.x, selectedScreenBounds.y};
    }

    /**
     * Gets the number of available screens.
     *
     * @return the number of screens
     */
    public static int getScreenCount() {
        GraphicsEnvironment graphicsEnvironment = GraphicsEnvironment.getLocalGraphicsEnvironment();
        return graphicsEnvironment.getScreenDevices().length;
    }

    /**
     * Gets the dimensions of a screen by index.
     *
     * @param screenIndex the screen index (0-based)
     * @return an array containing [width, height]
     */
    public static int[] getScreenDimensions(int screenIndex) {
        GraphicsEnvironment graphicsEnvironment = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice[] screenDevices = graphicsEnvironment.getScreenDevices();

        GraphicsDevice selectedScreen;

        if (screenIndex < 0 || screenIndex >= screenDevices.length) {
            selectedScreen = graphicsEnvironment.getDefaultScreenDevice();
        } else {
            selectedScreen = screenDevices[screenIndex];
        }

        Rectangle bounds = selectedScreen.getDefaultConfiguration().getBounds();

        return new int[]{bounds.width, bounds.height};
    }

    /**
     * Gets the bounds of the default screen.
     *
     * @return the screen bounds as a Rectangle
     */
    public static Rectangle getDefaultScreenBounds() {
        GraphicsEnvironment graphicsEnvironment = GraphicsEnvironment.getLocalGraphicsEnvironment();
        return graphicsEnvironment.getDefaultScreenDevice().getDefaultConfiguration().getBounds();
    }
}

