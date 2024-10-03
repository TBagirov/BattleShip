package org.grenatom.battleship.utill;

import org.grenatom.battleship.controllers.GameController;
import org.grenatom.battleship.models.Ship;

import java.util.*;
import java.awt.Point;


public class BattlefieldGenerator {

    private static String[][] battlefield;

    public static String[][] generateBattlefield(GameController game) {
        do {
            battlefield = createEmptyBattlefield();
            placeAllShips();
        } while (game.getShipCellsAmount(battlefield) < 20);
        return battlefield;
    }


    public static  String[][] createEmptyBattlefield() {
        String[][] emptyBattlefield = new String[CoordinateConstants.BATTLEFIELD_SIZE][CoordinateConstants.BATTLEFIELD_SIZE];
        for (int i = 0; i < CoordinateConstants.BATTLEFIELD_SIZE; i++) {
            for (int j = 0; j < CoordinateConstants.BATTLEFIELD_SIZE; j++) {
                emptyBattlefield[i][j] = CoordinateConstants.EMPTY_CELL;
            }
            Arrays.fill(emptyBattlefield[i], CoordinateConstants.EMPTY_CELL);
        }
        return emptyBattlefield;
    }

    private static  void placeAllShips() {
        List<Ship> ships = new ArrayList<>();
        for (int j = 0; j < 4; j++) {
            for (int i = 0; i < 4 - j; i++) {
                ships.add(new Ship(j + 1));
            }
        }
        Collections.shuffle(ships);
        for (Ship ship : ships) {
            placeShip(ship);
        }
    }

    private static  void placeShip(Ship ship) {
        for (int startVert = 0; startVert < CoordinateConstants.BATTLEFIELD_SIZE; startVert++) {
            for (int startHoriz = 0; startHoriz < CoordinateConstants.BATTLEFIELD_SIZE; startHoriz += 4) {
                if (startHoriz == 0) {
                    startHoriz += (int) Math.round(Math.random());
                }
                Point coordinate = new Point(startVert, startHoriz);
                if (isValidForPlacing(coordinate, ship)) {
                    if (ship.isVertical()) {
                        for (int i = startVert; i < startVert + ship.getLength(); i++) {
                            battlefield[i][startHoriz] = CoordinateConstants.SHIP_CELL;
                        }
                    } else {
                        for (int i = startHoriz; i < startHoriz + ship.getLength(); i++) {
                            battlefield[startVert][i] = CoordinateConstants.SHIP_CELL;
                        }
                    }
                    return;
                }
            }
        }
    }

    private static  boolean isValidForPlacing(Point coordinate, Ship ship) {
        if (ship.isVertical()) {
            for (int i = coordinate.x; i < coordinate.x + ship.getLength(); i++) {
                if (i > 9 || hasShipNear(new Point(i, coordinate.y))) {
                    return false;
                }
            }
        } else {
            for (int i = coordinate.y; i < coordinate.y + ship.getLength(); i++) {
                if (i > 9 || hasShipNear(new Point(coordinate.x, i))) {
                    return false;
                }
            }
        }

        return true;
    }

    private static  boolean hasShipNear(Point coordinate) throws IndexOutOfBoundsException {
        // current
        if (Objects.equals(battlefield[coordinate.x][coordinate.y], CoordinateConstants.SHIP_CELL)) {
            return true;
        }
        //  right
        else if (coordinate.x < CoordinateConstants.BATTLEFIELD_SIZE - 1 && Objects.equals(battlefield[coordinate.x + 1][coordinate.y], CoordinateConstants.SHIP_CELL)) {
            return true;
        } //left
        else if (coordinate.x > 0 && Objects.equals(battlefield[coordinate.x - 1][coordinate.y], CoordinateConstants.SHIP_CELL)) {
            return true;
        }
        // top
        else if (coordinate.y < CoordinateConstants.BATTLEFIELD_SIZE - 1 && Objects.equals(battlefield[coordinate.x][coordinate.y + 1], CoordinateConstants.SHIP_CELL)) {
            return true;
        } // bot
        else if (coordinate.y > 0 && Objects.equals(battlefield[coordinate.x][coordinate.y - 1], CoordinateConstants.SHIP_CELL)) {
            return true;
        } // top right
        else if (coordinate.y < CoordinateConstants.BATTLEFIELD_SIZE - 1 && coordinate.x < CoordinateConstants.BATTLEFIELD_SIZE - 1 && Objects.equals(battlefield[coordinate.x + 1][coordinate.y + 1], CoordinateConstants.SHIP_CELL)) {
            return true;
        } // bot right
        else if (coordinate.x < CoordinateConstants.BATTLEFIELD_SIZE - 1 && coordinate.y > 0 && Objects.equals(battlefield[coordinate.x + 1][coordinate.y - 1], CoordinateConstants.SHIP_CELL)) {
            return true;
        } // top left
        else if (coordinate.x > 0 && coordinate.y < CoordinateConstants.BATTLEFIELD_SIZE - 1 && Objects.equals(battlefield[coordinate.x - 1][coordinate.y + 1], CoordinateConstants.SHIP_CELL)) {
            return true;
        } //  bot left
        else
            return coordinate.x > 0 && coordinate.y > 0 && Objects.equals(battlefield[coordinate.x - 1][coordinate.y - 1], CoordinateConstants.SHIP_CELL);
    }

}
