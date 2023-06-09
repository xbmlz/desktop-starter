package io.xbmlz.desktop.ui.components;

import javax.swing.*;
import java.awt.*;

public class StripeButton extends JToggleButton {


    /**
     * Creates an unselected toggle button with the specified text.
     *
     * @param text the string displayed on the toggle button
     */
    public StripeButton(String text) {
        super(text);
        setFocusable(false);
        setBorder(BorderFactory.createEmptyBorder(5, 5, 0, 5));
//        putClientProperty("DisableMnemonicProcessing", Boolean.TRUE);
        setRolloverEnabled(false);
        setOpaque(false);
        enableEvents(AWTEvent.MOUSE_EVENT_MASK);
    }

    /**
     * https://github.com/JetBrains/intellij-community/blob/master/platform/platform-impl/src/com/intellij/toolWindow/StripeButton.kt
     */
    public StripeButton() {
        setFocusable(false);
        setBorder(BorderFactory.createEmptyBorder(5, 5, 0, 5));
//        putClientProperty("DisableMnemonicProcessing", Boolean.TRUE);
        setRolloverEnabled(false);
        setOpaque(false);
        enableEvents(AWTEvent.MOUSE_EVENT_MASK);
    }


    @Override
    public void updateUI() {
        setUI(new StripeButtonUI(false));
    }
}
