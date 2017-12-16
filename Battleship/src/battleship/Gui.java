package battleship;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class Gui extends JFrame implements ActionListener {

    public static final int GRID_DIMENSIONS = 10;
    public static final int NUM_BUTTONS = GRID_DIMENSIONS * GRID_DIMENSIONS;
    public static JButton buttons[] = new JButton[NUM_BUTTONS];
    private static JLabel text = new JLabel();
    private static JFrame f = new JFrame("Battleship");
    private static JPanel container = new JPanel();
    private static Gui gui = new Gui();

    public static void main(String[] args) {
        PlaceShips.cpu();
        createGui();
        f.setVisible(true);
    }

    private static void createGui() {
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setResizable(false);

        JPanel message = new JPanel();
        message.add(text);

        Gui.message("Place your aircraft carrier in the bottom grid - 5 squares");

        container.setLayout(null);
        container.setBorder(new EmptyBorder(20, 100, 20, 100));
        container.add(gui.createGrid(1)).setBounds(200, 10, 500, 470);
        container.add(message);
        message.setBounds(200, 475, 500, 20);
        container.add(gui.createGrid(2)).setBounds(200, 490, 500, 470);
        JButton reset = new JButton("reset");
        reset.addActionListener(e -> reset());
        container.add(reset).setBounds(50, 50, 100, 20);
        f.setContentPane(container);

        f.setSize(900, 1000);
    }

    private Container createGrid(int num) {
        JPanel grid = new JPanel(new GridLayout(GRID_DIMENSIONS, GRID_DIMENSIONS));

        for (int i = 0; i < NUM_BUTTONS; i++) {
            buttons[i] = new JButton(Integer.toString(i));
            if (PlaceShips.cLocations.contains(100 + i)) {
                buttons[i].setBackground(Color.gray); //change color to see cpu ships
            } else {
                buttons[i].setBackground(Color.gray);
            }
            buttons[i].setActionCommand((num * 100) + i + "");
            buttons[i].addActionListener(this);
            grid.add(buttons[i]);
            grid.setBorder(new EmptyBorder(15, 0, 15, 0));
            grid.setPreferredSize(new Dimension(550, 450));
        }
        return grid;
    }

    @Override
    public void actionPerformed(ActionEvent ae) {

        String className = getClassName(ae.getSource());

        if (className.equals("JButton")) {
            JButton button = (JButton) (ae.getSource());
            int bCoord = Integer.parseInt(button.getActionCommand());

            //code here to handle user clicking on grid
            if (bCoord < 200) {
                if (Guess.player(bCoord)) {
                    button.setBackground(Color.red);
                } else {
                    button.setBackground(Color.LIGHT_GRAY);
                }

            } else if (bCoord >= 200 && PlaceShips.pAddShips) {
                PlaceShips.player(bCoord);
                if (PlaceShips.getpLocations().contains(bCoord)) {
                    button.setBackground(Color.white);
                    button.setEnabled(false);
                    System.out.println(PlaceShips.getpLocations());
                }
            }
        }
    }

    private String getClassName(Object o) {
        String classString = o.getClass().getName();
        int dotIndex = classString.lastIndexOf(".");
        return classString.substring(dotIndex + 1);
    }

    public static void message(String text) {
        Gui.text.setText(text);

    }

    public static void reset() {
        PlaceShips.reset();
        Guess.reset();
        container.removeAll();
        PlaceShips.cpu();
        createGui();
    }
}
