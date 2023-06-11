package io.xbmlz.desktop.ui.form;

import io.xbmlz.desktop.ui.components.FileTree;
import io.xbmlz.desktop.ui.components.ShellPanel;
import io.xbmlz.desktop.ui.components.ToolWindow;

import javax.swing.*;

import org.fife.ui.rtextarea.RTextArea;
import org.fife.ui.rtextarea.RTextScrollPane;

import java.awt.*;

public class MainFrame extends JFrame {

    public MainFrame() {
        initComponents();
    }

    private void initComponents() {
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.add("FileTree", new JScrollPane(new FileTree()));
        tabbedPane.add("ToolWindow", new ToolWindow());
        tabbedPane.add("Edit", new RTextScrollPane(new RTextArea()));
        tabbedPane.add("Terminal", new ShellPanel().createTerminalWidget());
        setTitle("DesktopStarter");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());
        contentPane.add(tabbedPane, BorderLayout.CENTER);
    }
}
