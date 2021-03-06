package battleship;

import java.util.ArrayList;

//rename shipLocations?
public class PlaceShips {

    //for CPU
    public static final int CARRIER = 5;
    public static final int BATTLESHIP = 4;
    public static final int CRUISER = 3;
    public static final int SUBMARINE = 3;
    public static final int DESTROYER = 2;
    public static ArrayList<Integer> cLocations = new ArrayList<>();
    //for Player
    public static boolean pAddShips = false; //determines when player can add their ships
    private static boolean firstSpot = true;
    private static boolean secondSpot = false;
    private static boolean pVertical;
    private static ArrayList<Integer> pLocations = new ArrayList<>();

    public static ArrayList<Integer> getpLocations() {
        return pLocations;
    }

    //rules for cpu placement of ships
    private static boolean shipOrientation(int startPoint, int ship) { //return true if vertical
        int gridMax = 199;
        if (startPoint > gridMax - (ship * Gui.GRID_DIMENSIONS) + Gui.GRID_DIMENSIONS) {
            return false;
        } else if ((startPoint - Gui.NUM_BUTTONS) % Gui.GRID_DIMENSIONS > (Gui.GRID_DIMENSIONS - 1 - ship)) {
            return true;
        } else {
            int random = Helper.randomNumber(0, 1);
            return random == 0;
        }
    }

    private static void addShip(int ship) {
        int min = 100;
        int max = 197;
        boolean valid = true;
        int startPoint = Helper.randomNumber(min, max);
        boolean vertical = shipOrientation(startPoint, ship);
        int[] checkValid = new int[ship]; //stores points in temp array to check if overlap with previous

        if (vertical) {
            if (startPoint - Gui.GRID_DIMENSIONS + (ship * Gui.GRID_DIMENSIONS) > 199) {
                valid = false;
            }
            for (int i = 0; i < ship; i++) {
                checkValid[i] = startPoint;
                if (cLocations.contains(startPoint)) {
                    valid = false;
                    break;
                }
                startPoint += 10;
            }

        } else { //horizontal
            if ((startPoint - 100) % Gui.GRID_DIMENSIONS > Gui.GRID_DIMENSIONS - 1 - ship) {
                valid = false;
            }
            for (int i = 0; i < ship; i++) {
                checkValid[i] = startPoint;
                if (cLocations.contains(startPoint)) {
                    valid = false;
                    break;
                }
                startPoint++;
            }
        }

        if (valid) { //no overlap, adds temp array to locations arraylist
            for (int i : checkValid) {
                cLocations.add(i);
            }
        } else {
            addShip(ship); //failed a test, retry with a new point
        }
    }

    public static void cpu() {
        addShip(CARRIER);
        addShip(BATTLESHIP);
        addShip(CRUISER);
        addShip(SUBMARINE);
        addShip(DESTROYER);
        System.out.println(cLocations);
        pAddShips = true;
    }

    //rules for player placement of ships
    public static boolean player(int button) {

        int ship = 0;
        int size = pLocations.size();
        int lDifference = 0;
        if (pLocations != null && !pLocations.isEmpty()) {
            lDifference = button - pLocations.get(pLocations.size() - 1);
        }
        if (size == 17) {
            pAddShips = false;
        }
        if (size < 5) {
            if (size == 4) {
                Gui.message("Place your battleship - 4 squares");
            }
            ship = CARRIER;
        }
        if (size >= 5 && size < 9) {
            if (size == 8) {
                Gui.message("Place your cruiser - 3 squares");
            }
            ship = BATTLESHIP;
        }
        if (size >= 9 && size < 12) {
            if (size == 11) {
                Gui.message("Place your submarine - 3 squares");
            }
            ship = CRUISER;
        }
        if (size >= 12 && size < 15) {
            if (size == 14) {
                Gui.message("Place your destroyer - 2 squares");
            }
            ship = SUBMARINE;
        }
        if (size >= 15 && size < 17) {
            if (size == 16) {
                Gui.message("Start by guessing in the grid above");
            }
            ship = DESTROYER;
        }
        //first spot for each ship
        if (firstSpot && (checkValidVertical(button, ship) || checkValidHorizontal(button, ship))) {
            System.out.println(checkValidVertical(button, ship));
            System.out.println(checkValidHorizontal(button, ship));
            pLocations.add(button);
            firstSpot = false;
            secondSpot = true;
            return true;
        } else if (secondSpot) {
            if ((lDifference == 10 || lDifference == -10) && checkValidVertical(button, ship - 2)) {
                pVertical = true;
                pLocations.add(button);
                secondSpot = false;
                return true;
            } else if ((lDifference == 1 || lDifference == -1) && checkValidHorizontal(button, ship - 2)) {
                pVertical = false;
                pLocations.add(button);
                secondSpot = false;
                return true;
            } else {
                return false;
            }

        } else {
            if (pVertical) {
                if (lDifference == 10 || lDifference == -10) {
                    pLocations.add(button);
                    if (pLocations.size() == 5 || pLocations.size() == 9 || pLocations.size() == 12 || pLocations.size() == 15 || pLocations.size() == 17) {
                        firstSpot = true;
                    }
                    return true;
                } else {
                    return false;
                }
                //need to prevent wrap around placement
            } else if (!pVertical) {
                if (lDifference == 1 || lDifference == -1) {
                    pLocations.add(button);
                    if (pLocations.size() == 5 || pLocations.size() == 9 || pLocations.size() == 12 || pLocations.size() == 15 || pLocations.size() == 17) {
                        firstSpot = true;
                    }
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        }
    }

    private static boolean checkValidVertical(int bCoord, int ship) {
        System.out.println("north = " + Helper.checkDirection("north", bCoord, ship, pLocations));
        System.out.println("south = " + Helper.checkDirection("south", bCoord, ship, pLocations));
        return Helper.checkDirection("north", bCoord, ship, pLocations) + Helper.checkDirection("south", bCoord, ship, pLocations) >= ship;
    }

    private static boolean checkValidHorizontal(int bCoord, int ship) {
        System.out.println("east = " + Helper.checkDirection("east", bCoord, ship, pLocations));
        System.out.println("west = " + Helper.checkDirection("west", bCoord, ship, pLocations));
        return Helper.checkDirection("east", bCoord, ship, pLocations) + Helper.checkDirection("west", bCoord, ship, pLocations) >= ship;
    }

    public static void reset() {
        cLocations.clear();
        pLocations.clear();

    }
}
