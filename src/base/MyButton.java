package base;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JButton;

class MyButton extends JButton {

    /**
     * 
     */
    private static final long serialVersionUID = 6119882536884210329L;
    private Color hoverBackgroundColor;
    private Color pressedBackgroundColor;

    MyButton() {
        this(null);
    }

    @SuppressWarnings("PMD.ConstructorCallsOverridableMethod")
    MyButton(final String text) {
        super(text);
        super.setContentAreaFilled(false);
    }

    @Override
    protected void paintComponent(final Graphics g) {
        if (getModel().isPressed()) {
            g.setColor(pressedBackgroundColor);
        } else if (getModel().isRollover()) {
            g.setColor(hoverBackgroundColor);
        } else {
            g.setColor(getBackground());
        }
        g.fillRect(0, 0, getWidth(), getHeight());
        super.paintComponent(g);
    }

    @Override
    public void setContentAreaFilled(final boolean b) {
    }

    public Color getHoverBackgroundColor() {
        return hoverBackgroundColor;
    }

    public void setHoverBackgroundColor(final Color hoverBackgroundColor) {
        this.hoverBackgroundColor = hoverBackgroundColor;
    }

    public Color getPressedBackgroundColor() {
        return pressedBackgroundColor;
    }

    public void setPressedBackgroundColor(final Color pressedBackgroundColor) {
        this.pressedBackgroundColor = pressedBackgroundColor;
    }
}
