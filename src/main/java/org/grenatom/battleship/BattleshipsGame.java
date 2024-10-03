package org.grenatom.battleship;

import org.grenatom.battleship.controllers.GameController;

public class BattleshipsGame {

    public static void main(String[] args) {
        GameController game = new GameController();
        game.startGame();
        game.takeTurn();
        game.endGame();
    }
}
