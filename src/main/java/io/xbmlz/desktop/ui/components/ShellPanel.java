package io.xbmlz.desktop.ui.components;

import java.io.IOException;
import java.io.PipedReader;
import java.io.PipedWriter;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import com.jediterm.pty.PtyProcessTtyConnector;
import com.jediterm.terminal.TtyConnector;
import com.jediterm.terminal.ui.JediTermWidget;
import com.jediterm.terminal.ui.UIUtil;
import com.jediterm.terminal.ui.settings.DefaultSettingsProvider;

public class ShellPanel {

    private static final char ESC = 27;

    private static TtyConnector createTtyConnector() {
        try {
            Map<String, String> envs = System.getenv();
            String[] command;
            if (UIUtil.isWindows) {
                command = new String[] { "cmd.exe" };
            } else {
                command = new String[] { "/bin/bash", "--login" };
                envs = new HashMap<>(System.getenv());
                envs.put("TERM", "xterm-256color");
            }
            PtyProcess process = new PtyProcessBuilder().setCommand(command).setEnvironment(envs).start();
            return new PtyProcessTtyConnector(process, StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    public JediTermWidget createTerminalWidget() {
        JediTermWidget widget = new JediTermWidget(80, 24, new DefaultSettingsProvider());
        PipedWriter terminalWriter = new PipedWriter();
        widget.setTtyConnector(new ExampleTtyConnector(terminalWriter));
        widget.start();
        try {
            writeTerminalCommands(terminalWriter);
            terminalWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return widget;
    }

    private static class ExampleTtyConnector implements TtyConnector {

        private final PipedReader myReader;

        public ExampleTtyConnector(PipedWriter writer) {
            try {
                myReader = new PipedReader(writer);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        @Override
        public void close() {
        }

        @Override
        public String getName() {
            return null;
        }

        @Override
        public int read(char[] buf, int offset, int length) throws IOException {
            return myReader.read(buf, offset, length);
        }

        @Override
        public void write(byte[] bytes) {
        }

        @Override
        public boolean isConnected() {
            return true;
        }

        @Override
        public void write(String string) {
        }

        @Override
        public int waitFor() {
            return 0;
        }

        @Override
        public boolean ready() throws IOException {
            return myReader.ready();
        }

    }
}
