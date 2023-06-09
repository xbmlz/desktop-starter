package io.xbmlz.desktop;


import com.formdev.flatlaf.FlatIntelliJLaf;
import io.xbmlz.desktop.ui.form.MainFrame;

import javax.swing.*;
import java.awt.*;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
        FlatIntelliJLaf.setup();
        SwingUtilities.invokeLater(() -> {
            MainFrame frame = new MainFrame();
            frame.setPreferredSize(new Dimension(800, 600));
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}
