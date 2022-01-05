package base;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.List;

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * Operations for Lamps.
 */
public final class LampOperations {

    private LampOperations() {
    }

    static void switchLamp(final LampModel lamp, final JPanel pan, final ImageIcon on, final ImageIcon off) {
        if (lamp.isOn()) {
            LampOperations.turnOff(lamp, pan, off);
        } else {
            LampOperations.turnOn(lamp, pan, on);
        }
    }

    static void turnOn(final LampModel lamp, final JPanel pan, final ImageIcon on) {
        lamp.switchOn();
        pan.remove(0);
        pan.add(new JLabel(on), 0);
        pan.revalidate();
    }

    static void turnOff(final LampModel lamp, final JPanel pan, final ImageIcon off) {
        lamp.switchOff();
        pan.remove(0);
        pan.add(new JLabel(off), 0);
        pan.revalidate();
    }

    static void removeAtIndex(final List<Integer> indexVector, final List<Pair<LampModel, JPanel>> lampList,
            final JPanel lamps, final JComboBox<Integer> indexSelector, final int index) {
        final int count = LampOperations.getLampCount(lamps);
        if (count > 0) {
            if (index <= LampOperations.getLastLamp(lamps)
                    && ((JComponent) lamps.getComponent(index)).getComponents().length != 0) {

                indexVector.remove(Integer.valueOf(index + 1));

                lampList.remove(LampOperations.getLampCount(lamps) - 1);
                final Dimension size = lamps.getComponent(index).getSize();
                lamps.remove(index);
                lamps.add(Box.createRigidArea(size), index);
                indexSelector.revalidate();
                indexSelector
                        .setSelectedIndex(indexSelector.getSelectedIndex() == LampOperations.getLastLamp(lamps) ? -1
                                : indexSelector.getSelectedIndex());
                LampOperations.rewriteButtons(lamps);
                lamps.revalidate();
                lamps.repaint();
            } else {
                JOptionPane.showMessageDialog(lamps.getParent(), "Invalid Index!", "Invalid Index Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(lamps.getParent(), "Can't remove any more lamps!", "Too Few Lamps Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    static void addLamp(final List<Integer> indexVector, final int maxLamps, final String font, final int fontSize,
            final ImageIcon on, final ImageIcon off, final JPanel lamps, final List<Pair<LampModel, JPanel>> lampList,
            final JComboBox<Integer> indexSelector) {
        final int count = LampOperations.getLampCount(lamps);
        if (count < maxLamps) {
            for (int i = 0; i < lamps.getComponentCount(); i++) {
                if (((JComponent) lamps.getComponent(i)).getComponents().length == 0) {
                    indexVector.add(i, i + 1);

                    final JPanel lampContainer = new JPanel();
                    lamps.remove(i);
                    lamps.add(lampContainer, i);
                    final var lamp = new LampModel();
                    lampContainer.setLayout(new GridLayout(2, 1));

                    final var lampSwitch = new MyButton("Switch State" + " [" + (i + 1) + "]",
                            new Font(font, Font.BOLD, fontSize), JButton.CENTER, new Color(255, 255, 0));
                    lampSwitch.setPressedBackgroundColor(new Color(200, 200, 0));

                    lampContainer.add(new JLabel(off));
                    lampContainer.add(lampSwitch);
                    final Pair<LampModel, JPanel> pair = new Pair<>(lamp, lampContainer);
                    lampList.add(pair);

                    lampSwitch.addActionListener(a -> LampOperations.switchLamp(lamp, lampContainer, on, off));

                    lampContainer.setVisible(true);
                    indexSelector.revalidate();
                    lamps.revalidate();
                    lamps.repaint();
                    break;
                }
            }
        } else {
            JOptionPane.showMessageDialog(lamps.getParent(), "Can't insert any more lamps!", "Too Many Lamps Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    @SuppressWarnings("PMD.CollapsibleIfStatements")
    static int getLampCount(final JPanel lamps) {
        int count = 0;
        for (int i = 0; i < lamps.getComponentCount(); i++) {
            if (((JComponent) lamps.getComponent(i)).getComponents().length != 0) {
                    count++;
            }
        }
        return count;
    }

    @SuppressWarnings("PMD.CollapsibleIfStatements")
    static int getLastLamp(final JPanel lamps) {
        int index = 0;
        for (int i = 0; i < lamps.getComponentCount(); i++) {
            if (((JComponent) lamps.getComponent(i)).getComponents().length != 0) {
                    index = i;
            }
        }
        return index;
    }

    @SuppressWarnings("PMD.CollapsibleIfStatements")
    private static void rewriteButtons(final JPanel lamps) {
        for (int i = 0; i < lamps.getComponentCount(); i++) {
            if (((JComponent) lamps.getComponent(i)).getComponents().length != 0) {
                    ((JButton) ((JPanel) lamps.getComponent(i)).getComponent(1))
                            .setText("Switch State" + " [" + (i + 1) + "]");
            }

        }
    }
}
