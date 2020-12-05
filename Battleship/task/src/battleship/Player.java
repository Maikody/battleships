package battleship;

import java.util.*;

public class Player {
    private final String name;
    private int sumOfShotBoats;
    private final List<Boat> boatsFleet;
    private final String[][] battleground;
    private final String[][] fogField;
    private final Map<Boat, int[]> boatsPositions;

    public Player(String name) {
        this.name = name;
        this.sumOfShotBoats = 0;
        this.boatsFleet = new ArrayList<>();
        this.boatsFleet.add(new Boat("Aircraft Carrier", 5));
        this.boatsFleet.add(new Boat("Battleship", 4));
        this.boatsFleet.add(new Boat("Submarine", 3));
        this.boatsFleet.add(new Boat("Cruiser", 3));
        this.boatsFleet.add(new Boat("Destroyer", 2));
        battleground = new String[12][12];
        fogField = new String[12][12];
        boatsPositions = new HashMap<>();
        generateNewBattlegrounds();
    }

    public String getName() {
        return name;
    }

    public int getSumOfShotBoats() {
        return sumOfShotBoats;
    }

    public void addToSumOfShotBoats() {
        this.sumOfShotBoats++;
    }

    public List<Boat> getBoatsFleet() {
        return Collections.unmodifiableList(boatsFleet);
    }

    public String getSingleField(int row, int column) {
        return battleground[row][column];
    }

    public void setSingleField(int row, int column, String mark) {
        this.battleground[row][column] = mark;
    }

    public String getSingleFogField(int row, int column) {
        return fogField[row][column];
    }

    public Map<Boat, int[]> getBoatsPositions() {
        return boatsPositions;
    }

    public void setBoatPosition(Boat boat, int[] coordinates) {
        boatsPositions.put(boat, coordinates);
    }

    public void generateNewBattlegrounds() {
        char letter = 'A';
        for (int i = 0; i <= 11; i++) {
            for (int j = 0; j <= 11; j++) {
                if (i == 0 && j == 0) {
                    battleground[i][j] = " ";
                    fogField[i][j] = " ";
                } else if (i == 0) {
                    battleground[i][j] = String.valueOf(j);
                    fogField[i][j] = String.valueOf(j);
                } else if (j == 0) {
                    battleground[i][j] = String.valueOf(letter);
                    fogField[i][j] = String.valueOf(letter);
                    letter++;
                } else {
                    battleground[i][j] = String.valueOf('\u007E');
                    fogField[i][j] = String.valueOf('\u007E');
                }
            }
        }
    }

    public String markFields(Player opponent, int rowCoord, int columnCoord) {
        String sign;
        if (opponent.battleground[rowCoord][columnCoord].equals("O")
                || opponent.battleground[rowCoord][columnCoord].equals("X")) {
            sign = "X";
        } else {
            sign = "M";
        }
        fogField[rowCoord][columnCoord] = sign;
        opponent.battleground[rowCoord][columnCoord] = sign;
        return sign;
    }

    public void printBattleground() {
        for (int i = 0; i <= 10; i++) {
            for (int j = 0; j <= 10; j++) {
                System.out.print(battleground[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    public void printFogField() {
        for (int i = 0; i <= 10; i++) {
            for (int j = 0; j <= 10; j++) {
                System.out.print(fogField[i][j] + " ");
            }
            System.out.println();
        }
    }

}
