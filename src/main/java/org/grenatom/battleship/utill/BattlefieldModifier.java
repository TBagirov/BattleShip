package org.grenatom.battleship.utill;

import org.grenatom.battleship.controllers.GameController;

import java.awt.Point;
import java.util.Objects;
import java.util.Scanner;

import static org.grenatom.battleship.utill.CoordinateConstants.*;

public class BattlefieldModifier {


    public static void startTurns(GameController game) {
        while (hasAnyShips(game)) {
            game.printAllyAndEnemyBattlefields();
            try {
                Point coordinate = getCoordinateToShoot(getCoordinateInput());
                if (isAbleToShoot(game.enemyBattlefield, coordinate)) {
                    shootByPlayer(game, coordinate);
                } else {
                    throw new IllegalArgumentException("Клетка недоступна для выстрела");
                }
            } catch (IllegalArgumentException e) {
                System.err.println(e.getMessage());
                System.err.flush();
            }
        }
    }

    public static String getCoordinateInput() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите координату для выстрела: ");
        return scanner.nextLine();
    }

    public static Point getCoordinateToShoot(String coordinate) throws IllegalArgumentException {
        try {
            return new Point(coordinate.toUpperCase().charAt(0) - 'А', coordinate.charAt(1) - '0');
        } catch (StringIndexOutOfBoundsException e) {
            throw new IllegalArgumentException("Неверно введена точка для выстрела");
        }
    }

    public static boolean isAbleToShoot(String[][] battlefield, Point coordinate) {
        if (coordinate.x < 0 || coordinate.x > BATTLEFIELD_SIZE) {
            return false;
        } else if (coordinate.y < 0 || coordinate.y > BATTLEFIELD_SIZE) {
            return false;
        } else if (Objects.equals(battlefield[coordinate.y][coordinate.x], DESTROYED_CELL)) {
            return false;
        } else if (Objects.equals(battlefield[coordinate.y][coordinate.x], SHOT_CELL)) {
            return false;
        }
        return true;
    }

    public static void shootByPlayer(GameController game, Point coordinate) {
        if (Objects.equals(game.enemyBattlefield[coordinate.y][coordinate.x], SHIP_CELL)) {
            game.enemyBattlefield[coordinate.y][coordinate.x] = DESTROYED_CELL;
            game.enemyBattlefieldToPrint[coordinate.y][coordinate.x] = DESTROYED_CELL;
            System.out.println(GREEN + "Есть попадание!" + RESET);
        } else {
            game.enemyBattlefield[coordinate.y][coordinate.x] = SHOT_CELL;
            game.enemyBattlefieldToPrint[coordinate.y][coordinate.x] = SHOT_CELL;
            System.out.println(RED +"Промах!" + RESET);
            waitAfterShot();
            shootByComputer(game);
        }
    }

    public static  void shootByComputer(GameController game) {
        for (int i = 0; i < BATTLEFIELD_SIZE; i++) {
            for (int j = 0; j < BATTLEFIELD_SIZE; j++) {
                if (j > 0 && Objects.equals(game.allyBattlefield[i][j - 1], DESTROYED_CELL) &&
                        Objects.equals(game.allyBattlefield[i][j], EMPTY_CELL)) {
                    continue;
                }
                Point coordinate = new Point(j, i);
                if (isAbleToShoot(game.allyBattlefield, coordinate)) {
                    if (Objects.equals(game.allyBattlefield[coordinate.y][coordinate.x], SHIP_CELL)) {
                        game.allyBattlefield[coordinate.y][coordinate.x] = DESTROYED_CELL;
                        System.out.println(BLUE + "Компьютер:" + RED +" попал в корабль в точке "
                                + (char) ((int) 'А' + coordinate.x) + coordinate.y + RESET);
                        waitAfterShot();
                    } else {
                        game.allyBattlefield[coordinate.y][coordinate.x] = SHOT_CELL;
                        System.out.println(BLUE + "Компьютер: " + GREEN +  "промахнулся, выстрелив в точку " +
                                (char) ((int) 'А' + coordinate.x) + coordinate.y + RESET);
                        waitAfterShot();
                        return;
                    }
                }
            }
        }
    }

    private static void waitAfterShot() {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean hasAnyShips(GameController game) {
        return game.getShipCellsAmount(game.allyBattlefield) > 0 && game.getShipCellsAmount(game.enemyBattlefield) > 0;
    }
}
