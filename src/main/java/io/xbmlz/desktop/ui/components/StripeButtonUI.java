package io.xbmlz.desktop.ui.components;

import com.formdev.flatlaf.ui.FlatToggleButtonUI;
import com.formdev.flatlaf.util.UIScale;

import javax.swing.*;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicGraphicsUtils;
import javax.swing.plaf.metal.MetalToggleButtonUI;
import java.awt.*;

public class StripeButtonUI extends FlatToggleButtonUI {

    private Insets ourViewInsets = new Insets(0, 0, 0, 0);

    private Rectangle iconRect = new Rectangle();

    private Rectangle textRect = new Rectangle();

    private Rectangle viewRect = new Rectangle();

    protected StripeButtonUI(boolean shared) {
        super(shared);
    }

    @Override
    public Dimension getPreferredSize(JComponent c) {
        StripeButton button = (StripeButton) c;
        Dimension dimension = super.getPreferredSize(button);
        dimension.width = (int) (UIScale.scale(4) + dimension.width * 1.1f);
        dimension.height += UIScale.scale(2);
        return new Dimension(dimension.height, dimension.width);
    }

    @Override
    public void update(Graphics g, JComponent c) {
        StripeButton button = (StripeButton) c;
        String text = button.getText();
        Icon icon = button.getIcon();
        FontMetrics fm = button.getFontMetrics(button.getFont());
        ourViewInsets = c.getInsets(ourViewInsets);
        viewRect.x = ourViewInsets.left;
        viewRect.y = ourViewInsets.top;

        viewRect.height = c.getWidth() - (ourViewInsets.left + ourViewInsets.right);
        viewRect.width = c.getHeight() - (ourViewInsets.top + ourViewInsets.bottom);

        iconRect.height = 0;
        iconRect.width = iconRect.height;
        iconRect.y = iconRect.width;
        iconRect.x = iconRect.y;
        textRect.height = 0;
        textRect.width = textRect.height;
        textRect.y = textRect.width;
        textRect.x = textRect.y;
        String clippedText = SwingUtilities.layoutCompoundLabel(
                c, fm, text, icon,
                button.getVerticalAlignment(), button.getHorizontalAlignment(),
                button.getVerticalTextPosition(), button.getHorizontalTextPosition(),
                viewRect, iconRect, textRect, 0);
        Graphics2D g2 = (Graphics2D) g.create();
        try {
            ButtonModel model = button.getModel();
            int off = UIScale.scale(1);
            iconRect.x -= UIScale.scale(2);
            textRect.x -= UIScale.scale(2);
            if (model.isArmed() && model.isPressed() || model.isSelected() || model.isRollover()) {
                g2.translate(-off, 0);
                g2.setColor(Color.red);
                g2.fillRect(0, 0, button.getWidth(), button.getHeight());
                g2.translate(off, 0);
            }
            g2.rotate(-Math.PI / 2);
            g2.translate(-c.getHeight(), 0);
            BasicGraphicsUtils.drawString(g2, clippedText, button.getMnemonic(), textRect.x, textRect.y + fm.getAscent());
        } finally {
            g2.dispose();
        }
    }
}
