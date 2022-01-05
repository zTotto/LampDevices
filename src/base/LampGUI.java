package base;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 * GUI for LampModel.
 */
public class LampGUI {

    private final JFrame f = new JFrame("Lamps");
    private final ImageIcon offLamp = new ImageIcon(ClassLoader.getSystemResource("images/OffLamp.png")); // OFF LAMP
    private final ImageIcon onLamp = new ImageIcon(ClassLoader.getSystemResource("images/OnLamp.png")); // ON LAMP
    private static final String FONT = "SansSerif";
    private static final int MAX_LAMPS = 8;
    private static final int FONT_SIZE = 24;
    private static final Font FONT_AND_SIZE = new Font(FONT, Font.BOLD, 32);

    /**
     * Constructor for the GUI.
     */
    @SuppressWarnings("PMD.ReplaceVectorWithList")
    public LampGUI() {

        final JPanel canvas = new JPanel();
        final JPanel buttons = new JPanel();
        final JPanel lamps = new JPanel();
        final List<Pair<LampModel, JPanel>> lampList = new LinkedList<>();
        @SuppressWarnings("PMD.UseArrayListInsteadOfVector")
        final Vector<Integer> index = new Vector<>();

        final var newLamp = new MyButton("Create New Lamp", FONT_AND_SIZE, JButton.CENTER, new Color(255, 255, 0));
        newLamp.setPressedBackgroundColor(new Color(200, 200, 0));

        final var removeLastLamp = new MyButton("Remove Last Lamp", FONT_AND_SIZE, JButton.CENTER,
                new Color(0, 181, 222));
        removeLastLamp.setPressedBackgroundColor(new Color(0, 140, 171));

        final var removeSelectedLamp = new MyButton("Remove At Selected Index", FONT_AND_SIZE, JButton.LEFT,
                new Color(0, 181, 222));
        removeSelectedLamp.setPressedBackgroundColor(new Color(0, 140, 171));
        removeSelectedLamp.setLayout(new GridLayout(1, 2));
        removeSelectedLamp.add(new JPanel());
        removeSelectedLamp.getComponent(0).setVisible(false);

        final var indexSelector = new JComboBox<Integer>(index);
        indexSelector.setFont(new Font(FONT, Font.BOLD, 54));
        ((JLabel) indexSelector.getRenderer()).setHorizontalAlignment(SwingConstants.CENTER);
        indexSelector.setFocusable(false);
        indexSelector.setBackground(new Color(0, 181, 222));
        removeSelectedLamp.add(indexSelector);

        final var allOff = new MyButton("Turn Off All Lamps", FONT_AND_SIZE, JButton.CENTER, new Color(255, 0, 0));
        allOff.setPressedBackgroundColor(new Color(120, 0, 0));

        final var allOn = new MyButton("Turn On All Lamps", FONT_AND_SIZE, JButton.CENTER, new Color(0, 255, 0));
        allOn.setPressedBackgroundColor(new Color(0, 150, 0));

        canvas.setLayout(new GridLayout(1, 2));
        buttons.setLayout(new GridLayout(5, 1));
        lamps.setLayout(new FlowLayout(MAX_LAMPS));
        for (int i = 0; i < MAX_LAMPS; i++) {
            lamps.add(new JPanel());
            lamps.getComponent(i).setVisible(false);
        }

        buttons.add(newLamp);
        buttons.add(removeLastLamp);
        buttons.add(removeSelectedLamp);
        buttons.add(allOn);
        buttons.add(allOff);
        canvas.add(buttons);

        // Button Operations

        newLamp.addActionListener(e -> LampOperations.addLamp(index, MAX_LAMPS, FONT, FONT_SIZE, onLamp, offLamp, lamps,
                lampList, indexSelector));

        removeLastLamp.addActionListener(e -> LampOperations.removeAtIndex(index, lampList, lamps, indexSelector,
                LampOperations.getLastLamp(lamps)));

        removeSelectedLamp.addActionListener(e -> LampOperations.removeAtIndex(index, lampList, lamps, indexSelector,
                indexSelector.getSelectedItem() == null ? LampOperations.getLastLamp(lamps) : ((int) indexSelector.getSelectedItem() - 1) >= 0 ? (int) indexSelector.getSelectedItem() - 1
                        : LampOperations.getLastLamp(lamps)));

        allOn.addActionListener(e -> lampList.stream()
                .forEach(pair -> LampOperations.turnOn(pair.getFirst(), pair.getSecond(), onLamp)));

        allOff.addActionListener(e -> lampList.stream()
                .forEach(pair -> LampOperations.turnOff(pair.getFirst(), pair.getSecond(), offLamp)));

        // Frame Operations
        canvas.add(lamps);
        f.setContentPane(canvas);
        final Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        final int sw = (int) (screen.getWidth() / 1.4);
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
