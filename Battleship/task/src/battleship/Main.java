package battleship;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Main{
    static Scanner scanner = new Scanner(System.in);
//    static {
//        try {
//            scanner = new Scanner(new File("C:/Users/micbo/Desktop/inputCoordinates.txt"));
//        } catch (IOException e) {
//            System.out.println(e.getMessage());
//        }
//    }


    public static void main(String[] args) {
        Player player1 = new Player("Player 1");
        Player player2 = new Player("Player 2");

        checkIfBoatsPlacedCorrectly(player1);

        checkIfBoatsPlacedCorrectly(player2);

        System.out.println("The game starts!\n");

        int boatsNumber = player1.getBoatsFleet().size();

        while(boatsNumber > player1.getSumOfShotBoats() && boatsNumber > player2.getSumOfShotBoats()) {

            shoot(player1, player2);

            shoot(player2, player1);

            System.out.println("Player 1 shot boats: " + player1.getSumOfShotBoats() + "\n" +
                                "Player 2 shot boats: " + player2.getSumOfShotBoats() + "\n");
        }
        scanner.close();
    }

    public static boolean checkIfOpponentSankYourShip(Player player, Player opponent) {
        for (Boat boat : player.getBoatsFleet()) {
            if (isTakenDown(player, opponent, boat) && !boat.isSank()) {
                opponent.addToSumOfShotBoats();
                boat.setSank(true);
                return true;
            }
        }
        return false;
    }


    public static boolean isTakenDown(Player player, Player opponent, Boat boat) {
        int[] boatCoordinates = player.getBoatsPositions().get(boat);
        for (int i = boatCoordinates[0]; i <= boatCoordinates[2]; i++) {
            for (int j = boatCoordinates[1]; j <= boatCoordinates[3]; j++) {
                if (!opponent.getSingleFogField(i, j).equals("X")){
                    return false;
                }
            }
        }
        return true;
    }


    public static void shoot(Player player, Player opponent) {
        player.printFogField();
        System.out.println("---------------------");
        player.printBattleground();

        System.out.println(player.getName() + ", it's your turn:\n");
        String input = scanner.nextLine();
        String[] inputProcessedArray = input.trim().split("\\s+");
        int rowCoord;
        int columnCoord;
        if (inputProcessedArray.length == 1) {
            String oneElementInput = inputProcessedArray[0];
            rowCoord = processInputRow(oneElementInput.toUpperCase());
            columnCoord = processInputColumn(oneElementInput);
        } else {
            String connectedString = inputProcessedArray[0] + inputProcessedArray[1];
            rowCoord = processInputRow(connectedString.toUpperCase());
            columnCoord = processInputColumn(connectedString);
        }

        boolean isShootCorrect = false;
        while (!isShootCorrect) {
            if (validateInputRow(rowCoord) || validateInputColumn(columnCoord)) {
                System.out.println("Error! You entered the wrong coordinates! Try again:\n");
                isShootCorrect = false;
                continue;
            }
            isShootCorrect = true;
        }

        String sign = player.markFields(opponent, rowCoord, columnCoord);
        player.printFogField();
        System.out.println("---------------------");
        player.printBattleground();

        if (sign.equals("X")) {
            if(!checkIfOpponentSankYourShip(opponent, player)) {
                System.out.println("You hit a ship!\n");
            } else {
                if (player.getBoatsFleet().size() == player.getSumOfShotBoats()) {
                    System.out.println("You sank the last ship. You won. Congratulations!");
                    System.out.println("Game over!");
                    return;
                } else {
                    System.out.println("You sank a ship! Specify a new target\n");
                }
            }
        } else {
            System.out.println("You missed!\n");
        }
        pressEnterKey();
    }


//    public static boolean checkIfGameOver(Player player, Player opponent, String sign) {
//        if (!sign.equals("X")) {
//            System.out.println("You missed!\n");
//            return false;
//        }
//        if(checkIfOpponentSankYourShip(opponent, player)) {
//            System.out.println("You sank a ship! Specify a new target\n");
//            return false;
//        }
//        if (player.getBoatsFleet().size() == player.getSumOfShotBoats()) {
//            System.out.println("You sank the last ship. You won. Congratulations!");
//            System.out.println("Game over!");
//            return true;
//        }
//        System.out.println("You hit a ship!\n");
//        return false;
//    }


    public static void checkIfBoatsPlacedCorrectly(Player player)  {
        System.out.println(player.getName() + ", place your ships on the game field\n");
        player.printBattleground();
        for(Boat boat : player.getBoatsFleet()) {
            boolean isPlaced = false;
            while (!isPlaced) {
                if (shipPositioning(player, boat)) {
                    isPlaced = true;
                }
            }
        }
        pressEnterKey();
    }


    public static int processInputRow(String inputString){
        String rowString = inputString.substring(0, 1);
        return rowLetterToNumber(rowString);
    }


    public static int processInputColumn(String inputString){
        if (inputString.length() == 2) {
            return Integer.parseInt(inputString.substring(1, 2));
        }
        return Integer.parseInt(inputString.substring(1, 3));
    }


    public static boolean validateInputRow(int row){
        return row == -1;
    }


    public static boolean validateInputColumn(int column){
        return column < 1 || column > 10;
    }


    public static boolean shipPositioning(Player player, Boat boat){
        System.out.println("Enter the coordinates of the " + boat.getName() + " (" + boat.getLength() + " cells):\n");
        String input = scanner.nextLine();
        String[] inputArray = input.split("\\s+");
        String firstInput = inputArray[0];
        String secondInput = inputArray[1];

        int rowFirstCoord = processInputRow(firstInput.trim().toUpperCase());
        int columnFirstCoord = processInputColumn(firstInput.trim().toUpperCase());
        int rowSecondCoord = processInputRow(secondInput.trim().toUpperCase());
        int columnSecondCoord = processInputColumn(secondInput.trim().toUpperCase());

        if (validateInputRow(rowFirstCoord) || validateInputRow(rowSecondCoord)
                || validateInputColumn(columnFirstCoord) || validateInputColumn(columnSecondCoord)) {
            System.out.println("Error! You entered the wrong coordinates! Try again:\n");
            return false;
        }

        if (rowFirstCoord > rowSecondCoord) {
            int temp = rowFirstCoord;
            rowFirstCoord = rowSecondCoord;
            rowSecondCoord = temp;
        }
        if (columnFirstCoord > columnSecondCoord) {
            int temp = columnFirstCoord;
            columnFirstCoord = columnSecondCoord;
            columnSecondCoord = temp;
        }

        boolean horizontal = false;
        boolean vertical = false;
        if (rowSecondCoord - rowFirstCoord == 0) {
            horizontal = true;
        } else if (columnFirstCoord - columnSecondCoord == 0) {
            vertical = true;
        }

        if (rowFirstCoord != rowSecondCoord && columnFirstCoord != columnSecondCoord) {
            System.out.println("Error! Wrong ship location! Try again:\n");
            return false;
        } else if ((columnSecondCoord - columnFirstCoord != boat.getLength() - 1 && horizontal)
                || (rowSecondCoord - rowFirstCoord != boat.getLength() - 1 && vertical)) {
            System.out.println("Error! Wrong length of the " + boat.getName() + " Try again:\n");
            return false;
        }

        for (int i = rowFirstCoord; i <= rowSecondCoord; i++) {
            for (int j = columnFirstCoord; j <= columnSecondCoord; j++) {
                if (player.getSingleField(i, j).equals("O")
                        || (horizontal && (player.getSingleField(i, columnFirstCoord - 1).equals("O") || player.getSingleField(i - 1, j).equals("O") || player.getSingleField(i + 1, j).equals("O") || player.getSingleField(i, columnSecondCoord + 1).equals("O")))
                        || (vertical && (player.getSingleField(rowFirstCoord - 1, j).equals("O") || player.getSingleField(i, j - 1).equals("O") || player.getSingleField(i, j + 1).equals("O") || player.getSingleField(rowSecondCoord + 1, j).equals("O")))) {
                    System.out.println("Error! You placed it too close to another one. Try again:\n");
                    return false;
                } else {
                    player.setSingleField(i, j, "O");
                }
            }
        }

        player.setBoatPosition(boat, new int[]{rowFirstCoord, columnFirstCoord, rowSecondCoord, columnSecondCoord});

        player.printBattleground();
        return true;
    }

    public static void pressEnterKey() {
        System.out.println("Press Enter and pass the move to another player\n...");
        try{
            System.in.read();
            clearScreen();
        }catch(Exception e) {
            e.printStackTrace();
        }
    }

    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public static int rowLetterToNumber(String row) {
        switch (row) {
            case "A":
                return 1;
            case "B":
                return 2;
            case "C":
                return 3;
            case "D":
                return 4;
            case "E":
                return 5;
            case "F":
                return 6;
            case "G":
                return 7;
            case "H":
                return 8;
            case "I":
                return 9;
            case "J":
                return 10;
        }
        return -1;
    }

}