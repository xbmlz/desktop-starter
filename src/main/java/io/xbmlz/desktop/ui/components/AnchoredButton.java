package io.xbmlz.desktop.ui.components;

import com.formdev.flatlaf.util.UIScale;

import javax.swing.*;
import java.awt.*;

public class AnchoredButton extends JToggleButton {


    public AnchoredButton(String text) {
        super(text);
        setPreferredSize(new Dimension(100, 100));
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.rotate(-Math.PI / 2);
        g2d.translate(-getHeight(), 0);
        super.paintComponent(g2d);
        g2d.dispose();
    }

    @Override
    public Dimension getPreferredSize() {
        Dimension dimension = super.getPreferredSize();
        dimension.width = (int) (UIScale.scale(4) + dimension.width * 1.1f);
        dimension.height += UIScale.scale(2);
        System.out.println(dimension.width + " " + dimension.height);
        return new Dimension(dimension.height, dimension.width);
    }
}
