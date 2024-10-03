package org.grenatom.battleship.models;

public class Ship {
    private final int length;
    private final boolean vertical;

    public Ship(int length) {
        this.length = length;
        int willBeHorizontal = (int) Math.round(Math.random());
        this.vertical = willBeHorizontal == 0;
    }

    public int getLength() {
        return length;
    }

    public boolean isVertical() {
        return vertical;
    }

    @Override
    public String toString() {
        return "Ship{" +
                "length=" + length +
                ", vertical=" + vertical +
                '}';
    }
}
