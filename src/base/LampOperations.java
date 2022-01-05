package base;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
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

    static int removeAtIndex(final List<Integer> indexVector, final List<Pair<LampModel, JPanel>> lampList,
            final JPanel lamps, final JComboBox<Integer> indexSelector, final int index, final int maxLamps,
            final int lampCount, final JFrame f) {
        int count = lampCount;
        if (lampCount > 0) {
            if (index < maxLamps) {
                indexVector.remove(--count);

                lampList.remove(index);
                lamps.remove(index);
                indexSelector.revalidate();
                indexSelector.setSelectedIndex(
                        indexSelector.getSelectedIndex() == lampCount ? -1 : indexSelector.getSelectedIndex());
                for (int i = 0; i < lamps.getComponentCount(); i++) {
                    ((JButton) ((JPanel) lamps.getComponent(i)).getComponent(1))
                            .setText("Switch State" + " [" + (i + 1) + "]");
                }
                lamps.revalidate();
                lamps.repaint();
            } else {
                JOptionPane.showMessageDialog(f, "Invalid Index!", "Invalid Index Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(f, "Can't remove any more lamps!", "Too Few Lamps Error",
                    JOptionPane.ERROR_MESSAGE);
        }
        return count;
    }

    static int addLamp(final List<Integer> indexVector, final int lampCount, final int maxLamps, final String font,
            final int fontSize, final ImageIcon on, final ImageIcon off, final JPanel lamps,
            final List<Pair<LampModel, JPanel>> lampList, final JComboBox<Integer> indexSelector, final JFrame f) {
        int count = lampCount;
        if (lampCount < maxLamps) {
            indexVector.add(++count);

            final JPanel lampLayout = new JPanel();
            final var lamp = new LampModel();
            lampLayout.setLayout(new GridLayout(2, 1));

            final var lampSwitch = new MyButton("Switch State" + " [" + count + "]",
                    new Font(font, Font.BOLD, fontSize), JButton.CENTER, new Color(255, 255, 0));
            lampSwitch.setPressedBackgroundColor(new Color(200, 200, 0));

            lampLayout.add(new JLabel(off));
            lampLayout.add(lampSwitch);
            lamps.add(lampLayout);
            final Pair<LampModel, JPanel> pair = new Pair<>(lamp, lampLayout);
            lampList.add(pair);

            lampSwitch.addActionListener(a -> LampOperations.switchLamp(lamp, lampLayout, on, off));

            indexSelector.revalidate();
            lamps.revalidate();
            lamps.repaint();
        } else {
            JOptionPane.showMessageDialog(f, "Can't insert any more lamps!", "Too Many Lamps Error",
                    JOptionPane.ERROR_MESSAGE);
        }
        return count;
    }
}
