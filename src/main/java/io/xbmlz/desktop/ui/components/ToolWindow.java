package io.xbmlz.desktop.ui.components;

import javax.swing.*;
import java.awt.*;

public class ToolWindow extends JToolBar {


    public ToolWindow() {
        initComponents();
    }


    private void initComponents() {
        setFloatable(false);
        StripeButton button = new StripeButton("Test");
        AnchoredButton button2 = new AnchoredButton("button2");
        add(button);
        add(button2);
    }
}
