package base;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.util.LinkedList;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * GUI for LampModel.
 */
public class LampGUI {

    private final JFrame f = new JFrame("Lamps");
    private final ImageIcon offLamp = new ImageIcon(ClassLoader.getSystemResource("images/OffLamp.png")); // OFF LAMP
    private final ImageIcon onLamp = new ImageIcon(ClassLoader.getSystemResource("images/OnLamp.png")); // ON LAMP
    private static final String FONT = "SansSerif";
    private static final int MAX_LAMPS = 8;
    private int lampCount;
    private static final int FONT_SIZE = 24;

    /**
     * Constructor for the GUI.
     */
    public LampGUI() {

        final JPanel canvas = new JPanel();
        final JPanel buttons = new JPanel();
        final JPanel lamps = new JPanel();
        final List<Pair<LampModel, JPanel>> lampList = new LinkedList<>();

        final var newLamp = new MyButton("Create New Lamp");
        newLamp.setHorizontalAlignment(JButton.CENTER);
        newLamp.setFont(new Font(FONT, Font.BOLD, 32));
        newLamp.setBackground(new Color(255, 255, 0));
        newLamp.setHoverBackgroundColor(new Color(255, 255, 0));
        newLamp.setPressedBackgroundColor(new Color(200, 200, 0));
        newLamp.setFocusPainted(false);

        final var removeLastLamp = new MyButton("Remove Last Lamp");
        removeLastLamp.setHorizontalAlignment(JButton.CENTER);
        removeLastLamp.setFont(new Font(FONT, Font.BOLD, 32));
        removeLastLamp.setBackground(new Color(0, 0, 255));
        removeLastLamp.setHoverBackgroundColor(new Color(0, 0, 255));
        removeLastLamp.setPressedBackgroundColor(new Color(0, 0, 120));
        removeLastLamp.setFocusPainted(false);

        final var allOff = new MyButton("Turn Off All Lamps");
        allOff.setHorizontalAlignment(JButton.CENTER);
        allOff.setFont(new Font(FONT, Font.BOLD, 32));
        allOff.setBackground(new Color(255, 0, 0));
        allOff.setHoverBackgroundColor(new Color(255, 0, 0));
        allOff.setPressedBackgroundColor(new Color(120, 0, 0));
        allOff.setFocusPainted(false);

        final var allOn = new MyButton("Turn On All Lamps");
        allOn.setHorizontalAlignment(JButton.CENTER);
        allOn.setFont(new Font(FONT, Font.BOLD, 32));
        allOn.setBackground(new Color(0, 255, 0));
        allOn.setHoverBackgroundColor(new Color(0, 255, 0));
        allOn.setPressedBackgroundColor(new Color(0, 150, 0));
        allOn.setFocusPainted(false);

        canvas.setLayout(new GridLayout(1, 2));
        buttons.setLayout(new GridLayout(4, 1));
        lamps.setLayout(new FlowLayout());

        buttons.add(newLamp);
        buttons.add(removeLastLamp);
        buttons.add(allOn);
        buttons.add(allOff);
        canvas.add(buttons);

        // Utility Classes

        class SwitchLamp {
            void switchLamp(final LampModel lamp, final JPanel pan) {
                lamp.invertState();
                pan.remove(0);
                if (lamp.isOn()) {
                    pan.add(new JLabel(onLamp), 0);
                } else {
                    pan.add(new JLabel(offLamp), 0);
                }
                pan.validate();
            }

            void turnOn(final LampModel lamp, final JPanel pan) {
                if (!lamp.isOn()) {
                    lamp.switchOn();
                    pan.remove(0);
                    pan.add(new JLabel(onLamp), 0);
                    pan.validate();
                }
            }

            void turnOff(final LampModel lamp, final JPanel pan) {
                if (lamp.isOn()) {
                    lamp.switchOff();
                    pan.remove(0);
                    pan.add(new JLabel(offLamp), 0);
                    pan.validate();
                }
            }
        }

        // Button Operations

        newLamp.addActionListener(e -> {
            if (lampCount < MAX_LAMPS) {
                lampCount++;

                final JPanel lampLayout = new JPanel();
                final var lamp = new LampModel();
                lampLayout.setLayout(new GridLayout(2, 1));

                final var lampSwitch = new MyButton("Switch State");
                lampSwitch.setHorizontalAlignment(JButton.CENTER);
                lampSwitch.setFont(new Font(FONT, Font.BOLD, FONT_SIZE));
                lampSwitch.setBackground(new Color(255, 255, 0));
                lampSwitch.setHoverBackgroundColor(new Color(255, 255, 0));
                lampSwitch.setPressedBackgroundColor(new Color(200, 200, 0));
                lampSwitch.setFocusPainted(false);

                lampLayout.add(new JLabel(offLamp));
                lampLayout.add(lampSwitch);
                lamps.add(lampLayout);
                final Pair<LampModel, JPanel> pair = new Pair<>(lamp, lampLayout);
                lampList.add(pair);

                lampSwitch.addActionListener(a -> new SwitchLamp().switchLamp(lamp, lampLayout));

                lamps.validate();
            } else {
                JOptionPane.showMessageDialog(f, "Can't insert any more lamps!", "Too Many Lamps Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

        removeLastLamp.addActionListener(e -> {
            if (lampCount > 0) {
                lampCount--;
                lampList.remove(lampList.size() - 1);
                lamps.remove(lamps.getComponentCount() - 1);
                lamps.validate();
                lamps.repaint();
            } else {
                JOptionPane.showMessageDialog(f, "Can't remove any more lamps!", "Too Little Lamps Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

        allOn.addActionListener(
                e -> lampList.stream().forEach(pair -> new SwitchLamp().turnOn(pair.getFirst(), pair.getSecond())));

        allOff.addActionListener(
                e -> lampList.stream().forEach(pair -> new SwitchLamp().turnOff(pair.getFirst(), pair.getSecond())));

        // Frame Operations
        canvas.add(lamps);
        f.setContentPane(canvas);
        final Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        final int sw = (int) (screen.getWidth() / 1.5);
        final int sh = (int) (screen.getHeight() / 2);
        f.setSize(sw, sh);
        f.setLocationByPlatform(true);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void display() {
        f.setVisible(true);
    }

    /**
     * 
     * @param args
     */
    public static void main(final String[] args) {
        new LampGUI().display();
    }
}
