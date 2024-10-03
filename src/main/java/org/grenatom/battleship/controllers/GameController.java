package org.grenatom.battleship.controllers;

import org.grenatom.battleship.utill.BattlefieldGenerator;
import org.grenatom.battleship.utill.BattlefieldModifier;

import java.util.Objects;

import static org.grenatom.battleship.utill.CoordinateConstants.*;

public class GameController {

    public String[][] allyBattlefield;
    public String[][] enemyBattlefield;
    public String[][] enemyBattlefieldToPrint;

    public void startGame() {
        allyBattlefield = BattlefieldGenerator.generateBattlefield(this);
        enemyBattlefield = BattlefieldGenerator.generateBattlefield(this);
        enemyBattlefieldToPrint = BattlefieldGenerator.createEmptyBattlefield();
        System.out.println("Игра началась");
    }

    public void takeTurn() {
        BattlefieldModifier.startTurns(this);
    }

    public void endGame() {
        printAllyAndEnemyBattlefields();
        System.out.println("Игра окончена");
        if (getShipCellsAmount(allyBattlefield) == 0) {
            System.out.println(RED + "Вы проиграли!");
        } else {
            System.out.println(GREEN + "Вы победили!");
        }
    }

    public void printAllyAndEnemyBattlefields() {
        printCoordinateLetters();
        for (int currentString = 0; currentString < BATTLEFIELD_SIZE; currentString++) {
            printBattlefield(this.allyBattlefield, currentString);
            printBattlefield(this.enemyBattlefieldToPrint, currentString);
            System.out.println();
        }
        System.out.println("Палуб кораблей на вашем поле: " + getShipCellsAmount(allyBattlefield) +
                "           Палуб кораблей на поле противника: " + getShipCellsAmount(enemyBattlefield));
    }

    private void printCoordinateLetters() {
        System.out.printf("%-50s", "        Ваше поле:");
        System.out.printf("%-50s\n", "Поле противника:");
        for (int j = 0; j < 2; j++) {
            System.out.print("  |");
            for (char i = 'А'; i <= 'Й'; i++) {
                System.out.print(i + " |");
            }
            System.out.printf("%-10s", "");
        }
        System.out.println();
    }

    private void printBattlefield(String[][] battlefield, int currentString) {
        System.out.print(currentString + " |");
        for (int currentColumn = 0; currentColumn < BATTLEFIELD_SIZE; currentColumn++) {
            if (Objects.equals(battlefield[currentString][currentColumn], SHIP_CELL)) {
                System.out.printf(BLUE + "%-2s|" + RESET, battlefield[currentString][currentColumn]);
            } else if (Objects.equals(battlefield[currentString][currentColumn], DESTROYED_CELL)) {
                System.out.printf(RED + "%-2s|" + RESET, battlefield[currentString][currentColumn]);
            } else if (Objects.equals(battlefield[currentString][currentColumn], EMPTY_CELL)) {
                System.out.printf(GREEN + "%-2s|" + RESET, battlefield[currentString][currentColumn]);
            } else {
                System.out.printf("%-2s|", battlefield[currentString][currentColumn]);
            }
        }
        System.out.printf("%-10s", "");
    }

    public int getShipCellsAmount(String[][] battlefieldToShoot) {
        int counter = 0;
        for (int i = 0; i < BATTLEFIELD_SIZE; i++) {
            for (int j = 0; j < BATTLEFIELD_SIZE; j++) {
                if (Objects.equals(battlefieldToShoot[i][j], SHIP_CELL)) {
                    counter++;
                }
            }
        }
        return counter;
    }


}
