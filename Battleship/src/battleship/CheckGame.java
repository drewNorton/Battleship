package battleship;

import java.awt.Color;

public class CheckGame {

    //cpu ships
    private static int cCarrier = 5;
    private static int cBattleship = 4;
    private static int cCruiser = 3;
    private static int cSubmarine = 3;
    private static int cDestroyer = 2;
    private static int cAllShips = cCarrier + cBattleship + cCruiser + cSubmarine + cDestroyer;

    //player ships
    private static int pCarrier = 5;
    private static int pBattleship = 4;
    private static int pCruiser = 3;
    private static int pSubmarine = 3;
    private static int pDestroyer = 2;
    private static int pAllShips = pCarrier + pBattleship + pCruiser + pSubmarine + pDestroyer;

    public static boolean player(String ship) {

        if (ship.equals("carrier")) {
            cCarrier--;
            if (cCarrier == 0) {
                Gui.message("you sunk the carrier");
            }
        }
        if (ship.equals("battleship")) {
            cBattleship--;
            if (cBattleship == 0) {
                Gui.message("you sunk the battleship");
            }
        }
        if (ship.equals("cruiser")) {
            cCruiser--;
            if (cCruiser == 0) {
                Gui.message("you sunk the cruiser");
            }
        }
        if (ship.equals("you sunk the submarine")) {
            cSubmarine--;
            if (cSubmarine == 0) {
                Gui.message("submarine sunk              ");
            }
        }
        if (ship.equals("destroyer")) {
            cDestroyer--;
            if (cDestroyer == 0) {
                Gui.message("you sunk the destroyer");
            }
        }
        cAllShips--;
//        System.out.println(cpuLocations);
        if (cAllShips == 0) {
            //gameover
            Gui.message("YOU WIN");
            return true;
        } else {
            return false;
        }
    }

    public static boolean cpu(String ship, int index) {

        if (ship.equals("carrier")) {
            Gui.buttons[index].setBackground(Color.RED);
            pCarrier--;
            if (pCarrier == 0) {
                Guess.changeMode(0);
            }
        }
        if (ship.equals("battleship")) {
            Gui.buttons[index].setBackground(Color.RED);
            pBattleship--;
            if (pBattleship == 0) {
                Guess.changeMode(1);
            }
        }
        if (ship.equals("cruiser")) {
            Gui.buttons[index].setBackground(Color.RED);
            pCruiser--;
            if (pCruiser == 0) {
                Guess.changeMode(2);
            }
        }
        if (ship.equals("submarine")) {
            Gui.buttons[index].setBackground(Color.RED);
            pSubmarine--;
            if (pSubmarine == 0) {
                Guess.changeMode(3);
            }
        }
        if (ship.equals("destroyer")) {
            Gui.buttons[index].setBackground(Color.RED);
            pDestroyer--;
            if (pDestroyer == 0) {
                Guess.changeMode(4);
            }
        }
        pAllShips--;
        if (pAllShips == 0) {
            //gameover
            Gui.message("YOU LOSE");
            return true;
        } else {
            return false;
        }
    }

    public static int getMaxShip() {
        if (pCarrier > 0) {
            return 5;
        } else if (pBattleship > 0) {
            return 4;
        } else if (pCruiser > 0 || pSubmarine > 0) {
            return 3;
        } else {
            return 2;
        }
    }
}
