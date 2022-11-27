import java.util.Arrays;
import java.util.Scanner;

public class TicTacToe {

    final static String CHECK_VERTICAL = "VERTICAL";
    final static String CHECK_HORIZONTAL = "HORIZONTAL";
    final static String CHECK_DIAGONAL = "DIAGONAL";
    public static void main(String[] args) {
        Scanner myScanner = new Scanner(System.in); // Create a Scanner object
        boolean isCorrectSizeGiven = false;
        int boardSize = 0;

        while (!isCorrectSizeGiven) {
            System.out.println(
                    "Enter the total number of rows and columns you would like to have for your tic tac toe board. (Range can be from 3-26)");
            String inputText = myScanner.nextLine();

            try {
                boardSize = Integer.parseInt(inputText);

                // since boardsize is initialized as 0, if input given is not an integer, it is
                // still < 3 so it will trigger this message.
                if (boardSize < 3 || boardSize > 26)
                    System.out.println("Tic Tac Toe row and column size can only be an integer\n");
                else {
                    isCorrectSizeGiven = true;
                    System.out.println("Tic Tac Toe BoardSize set to " + boardSize + " X " + boardSize + "!\n");
                }
            } catch (Exception ex) {
                System.out.println("\nTic Tac Toe row and column size can only be an integer\n");
                // System.out.println(ex.getLocalizedMessage());
            }

        }

        boolean isCorrectConsecutiveMatchValue = false;
        int consecutiveValuesToWinMatch = 0;
        while (!isCorrectConsecutiveMatchValue) {
            System.out.println(String.format(
                    "Enter the total number of consecutive values needed to win the game (if value is 3, and you are 'X', then you need 3 X in a row vertically, horizontally, or diagonally to win). Minimum value must be 3, and maximum value cannot exceed the maximum size of the tic tac toe board (%s).",
                    boardSize));
            String inputText = myScanner.nextLine();

            try {

                consecutiveValuesToWinMatch = Integer.parseInt(inputText);

                if (consecutiveValuesToWinMatch >= 3 && consecutiveValuesToWinMatch <= boardSize)
                    isCorrectConsecutiveMatchValue = true;
                else
                    System.out.println(String.format(
                            "\nThe number entered does not match the minimum or maximum range for the consecuutive values needed to win. Minimum value must be 3, and maximum value cannot exceed the maximum size of the tic tac toe board (%s)\n",
                            boardSize));

            } catch (Exception e) {
                System.out.println(
                        "\nInput for The total number of consecutive values needed to win the game must be an integer. \n");
            }
        }

        var ticTacToeBoard = CreateTicTacToeBoard(boardSize);
        PrintTicTacToeBoard(ticTacToeBoard);
        /*
         * boolean isCorrectOpponent = false;
         * 
         * while (!isCorrectOpponent) {
         * System.out.println(
         * "Would you like to play against a local player or against the computer? Type P to play against Player and C to play against a computer!"
         * );
         * String inputText = myScanner.nextLine();
         * 
         * if (inputText.toUpperCase().equals("P") ||
         * inputText.toUpperCase().equals("C"))
         * isCorrectOpponent = true;
         * else
         * System.out.println(
         * "You entered a wrong input. Please type P to play against a Player or C to play against the computer.\n"
         * );
         * }
         */

        boolean isGameFinished = false;
        String player_turn = DetermineFirstTurn();
        boolean isPlayerOneTurn = player_turn.equals("Player 1");

        int number_of_turns = 0;
        while (!isGameFinished) {
            // First Turn of the game.
            if (number_of_turns == 0)
                System.out.println(player_turn
                        + " goes first: Enter the column alphabet and the row number where you would like to move (Ex: A1 or B3)");
            else
                System.out.println(player_turn
                        + " turn: Enter the column alphabet and the row number where you would like to move (Ex: A1 or B3)");

            String input_board_move = myScanner.nextLine();

            // First check if input is valid.
            if (isValidUserInput(input_board_move))
                if (isValidMove(input_board_move, ticTacToeBoard)) {
                    setMoveOnBoard(input_board_move, isPlayerOneTurn, ticTacToeBoard);
                    // print current move.
                    PrintTicTacToeBoard(ticTacToeBoard);

                    System.out.println(player_turn + " placed a '" + GetPlayerChar(isPlayerOneTurn) + "' at "
                            + input_board_move.toUpperCase() + "\n");
                    // Next determine if player has won?
                    if (!IsGameFinished(input_board_move, isPlayerOneTurn, ticTacToeBoard, number_of_turns, consecutiveValuesToWinMatch)) {
                        // If no, switch player turn.
                        isPlayerOneTurn = !isPlayerOneTurn;
                        if (player_turn.equals("Player 1"))
                            player_turn = "Player 2";
                        else
                            player_turn = "Player 1";

                        number_of_turns++;
                    } else {
                        isGameFinished = true;
                        number_of_turns++;
                        /*
                         * System.out.println("Congraluations, " + player_turn +
                         * " Won! Total turns to win: "
                         * + number_of_turns + "\n.");
                         */
                    }

                } else
                    System.out.println(player_turn
                            + " entered an invalid move. Either that place is already moved previously, or input is out of bounds of the board. Enter a value and try again.\n");
            else
                System.out.println(player_turn
                        + " entered an incorrect user input. User must enter the column alphabet and then the row number (Ex: A1 or B3)\n");

            // number_of_turns++;
        }
    }

    // Initialize 2D char array for our tic tac toe board.
    private static char[][] CreateTicTacToeBoard(int boardSize) {
        // Create and Initialize our TicTacToe Board 2d Array.
        char ticTacToeBoard[][] = new char[boardSize][boardSize];
        for (int row = 0; row < boardSize; row++) {
            for (int col = 0; col < boardSize; col++) {
                // Columns are alphabetical. Rows Numerical. So a value for array pos 0, 0 would
                // be A1.
                ticTacToeBoard[row][col] = '-';
            }
        }
        return ticTacToeBoard;
    }

    private static String DetermineFirstTurn() {
        // Use math.random which generates a random value from 0.0-1.0
        // if <= .5 then player 1's turn, otherwise player 2s
        if (Math.random() <= 0.5)
            return "Player 1";
        else
            return "Player 2";
    }

    private static boolean IsGameFinished(String input_board_move, boolean isPlayerOneTurn, char ticTacToeBoard[][],
            int number_of_turns, int consecutiveValuesToWinMatch) {
        
        boolean canGameHaveWinner = number_of_turns + 1 >= (consecutiveValuesToWinMatch * 2 - 1);
        //Not enough moves in the game have been made to determine a winner.
        //for a 3x3 tic tac toe board, a minimum of 5 turns must be made to determine a winner.
        // if (!canGameHaveWinner)
        //     return false;

        var input_move = GetInputBoardMove(input_board_move);
        var playerTicTacToeChar = GetPlayerChar(isPlayerOneTurn);
        // boolean verticalMatch = checkVerticalMatch(input_move, playerTicTacToeChar,
        // ticTacToeBoard, 3);
        boolean verticalMatch = checkLinearMatch(input_move, playerTicTacToeChar, ticTacToeBoard, consecutiveValuesToWinMatch, CHECK_VERTICAL);
        boolean horizontalMatch = checkLinearMatch(input_move, playerTicTacToeChar, ticTacToeBoard, consecutiveValuesToWinMatch,
                CHECK_HORIZONTAL);
        //boolean diagonalMatch = checkLinearMatch(input_move, playerTicTacToeChar, ticTacToeBoard, consecutiveValuesToWinMatch, CHECK_DIAGONAL);
        boolean leftDiagonalMatch = checkLeftDiagonalMatch(input_move, playerTicTacToeChar, ticTacToeBoard, consecutiveValuesToWinMatch, CHECK_DIAGONAL);
        boolean rightDiagonalMatch = checkDiagonalRightMatch(input_move, playerTicTacToeChar, ticTacToeBoard, consecutiveValuesToWinMatch, CHECK_DIAGONAL);
        var emptyBoardValue = '-';
        boolean hasWinner = (verticalMatch || horizontalMatch || leftDiagonalMatch || rightDiagonalMatch);
        // Draw if No matches (vertical, horizontal or diagnal foound and if there are
        // no empty spots left on the board).
        if (!hasWinner && !ValueExists(emptyBoardValue, ticTacToeBoard)) {
            System.out.println("Game ended in a Draw! There are no winners!\n");
            return true;
        }

        // Game won.
        if (hasWinner) {
            String player_turn = "";
            if (isPlayerOneTurn)
                player_turn = "Player 1";
            else
                player_turn = "Player 2";
            System.out.println("Congraluations, " + player_turn + " Won! Total turns to win: "
                    + (number_of_turns + 1) + "\n.");
            return true;
        }

        return false;
    }

    private static boolean checkLinearMatch(Pair<Integer, Integer> input_move, char charToCheck,
            char ticTacToeBoard[][], int consecutiveValuesToMatch, String checkPosition) {
        var input_row_num = input_move.pair_value_one;
        var input_col_num = input_move.pair_value_two;

        Integer input_val_to_use;
        if (checkPosition.toUpperCase().equals(CHECK_VERTICAL))
            input_val_to_use = input_row_num;
        else
            input_val_to_use = input_col_num;

        /*
         * if (isVertical)
         * input_val_to_use = input_row_num;
         * else
         * input_val_to_use = input_col_num;
         */

        int startRowValue = input_val_to_use - consecutiveValuesToMatch + 1;
        int endRowValue = input_val_to_use + consecutiveValuesToMatch;
        // int startRowValue = input_val_to_use - consecutiveValuesToMatch;
        // int endRowValue = input_val_to_use;
        // Must first check horizontally up from current move if ticTacToe match is
        // found.
        if (startRowValue < 0)
            startRowValue = 0;

        if (endRowValue > ticTacToeBoard.length)
            endRowValue = ticTacToeBoard.length;

        int sameCharacterFound = 0;
        // boolean horizontalLeftOrUpMatch = false;
        for (int i = startRowValue; i < endRowValue; i++) {
            char current_pos_char = 0; // 0 is null
            // vertical up
            if (checkPosition.toUpperCase().equals(CHECK_VERTICAL))
                current_pos_char = ticTacToeBoard[i][input_col_num];
            // horizontal left
            else if (checkPosition.toUpperCase().equals(CHECK_HORIZONTAL))
                current_pos_char = ticTacToeBoard[input_row_num][i];
            else // Diagonal left.
                current_pos_char = ticTacToeBoard[i][i];

            if (current_pos_char == charToCheck) {
                sameCharacterFound++;
                if (sameCharacterFound == consecutiveValuesToMatch)
                    return true;
            } else
                sameCharacterFound = 0;

        }

        // System.out.println(checkPosition.toUpperCase() + " CHECK - input_val_to_use = " + input_val_to_use
        //         + ". startRowValue = " + startRowValue +
        //         ". endRowValue = " + endRowValue + ". sameCharFoundUp = " +
        //         sameCharacterFound);

        return false;
    }

    private static boolean checkLeftDiagonalMatch(Pair<Integer, Integer> input_move, char charToCheck,
            char ticTacToeBoard[][], int consecutiveValuesToMatch, String checkPosition) {
                var input_row_num = input_move.pair_value_one;
                var input_col_num = input_move.pair_value_two;                

                //determine starting row value and col value of left diagnal.
                 //add 1 as we are also including the place the person marks the cell. So if 
                int xValue = input_row_num + 1;
                int yValue = input_col_num + 1;
                int counter = 0;
                //if currently at first row, do not subtract further. We subtract both x and y axis by consecutiveValuesToMatch
                //If tic tac toe board is a 5x5 and a player chose a cell E5 (last cell in the first row: indexes 0, 4) since we are at the first row, we cannot decrement starting column.
                //This implementation keeps track of the starting row value (ROW 0) and column value (COL 4). 
                while (counter != consecutiveValuesToMatch)
                {
                    if (xValue == 0 || yValue == 0)
                        break;                    
                    xValue--;
                    if (yValue > 0)
                        yValue--;
                    counter++;
                }
              
                int startRowValue = xValue;
                int startColValue = yValue;        

                int endRowValue = input_row_num + consecutiveValuesToMatch;
                
                if (endRowValue > ticTacToeBoard.length)
                    endRowValue = ticTacToeBoard.length;
                int endColValue = yValue;      
                
                int sameCharacterFound = 0;
                // boolean horizontalLeftOrUpMatch = false;
                System.out.println(checkPosition.toUpperCase() + " CHECK - input_row_num = " + input_row_num + " input_col_num = " + input_col_num
                + ". startRowValue = " + startRowValue +
                ". endRowValue = " + endRowValue + ". startColValue = " + startColValue + ". endColValue = " + endColValue + ". sameCharFoundUp = " +
                sameCharacterFound); 

                int y = startColValue;
                for (int x = startRowValue; x < endRowValue; x++) {
                    char current_pos_char = 0; // 0 is null

                        current_pos_char = ticTacToeBoard[x][y];
                        
                        if (current_pos_char == charToCheck) {
                            sameCharacterFound++;
                            if (sameCharacterFound == consecutiveValuesToMatch)
                                return true;
                        } else
                            sameCharacterFound = 0;

                        System.out.println(checkPosition.toUpperCase() + " CHECK - x = " + (x+1) + " y = " + (y+1)
                            + ". current_pos_char = " + current_pos_char +
                            "." + " sameCharFound = " +
                            sameCharacterFound);
                        
                        y++; //increment column. 
                        
                        //End of the tic tac to board reached.
                        if (y == ticTacToeBoard[x].length)
                            break;
                }
        
                // System.out.println(checkPosition.toUpperCase() + " CHECK - input_val_to_use = " + input_val_to_use
                //         + ". startRowValue = " + startRowValue +
                //         ". endRowValue = " + endRowValue + ". sameCharFoundUp = " +
                //         sameCharacterFound);
        
                return false;
            }

            private static boolean checkDiagonalRightMatch(Pair<Integer, Integer> input_move, char charToCheck,
            char ticTacToeBoard[][], int consecutiveValuesToMatch, String checkPosition) {
                var input_row_num = input_move.pair_value_one;
                var input_col_num = input_move.pair_value_two;                

                // int startRowValue = input_row_num - consecutiveValuesToMatch + 1;
                //int endRowValue = input_row_num + consecutiveValuesToMatch;

                //Get Diagnal Right Starting Point Pair.
                //Must include the cell player chose so have to increment or decrement by 1.


                 //add 1 as we are also including the place the person marks the cell.
                 int xValue = input_row_num - 1;
                 int yValue = input_col_num + 1;
                 int counter = 0;

                 //if currently at first row, do not subtract further. We subtract both x and y axis by consecutiveValuesToMatch
                 //If tic tac toe board is a 5x5 and a player chose a cell E5 (last cell in the first row: indexes 0, 4) since we are at the first row, we cannot decrement starting column.
                 //This implementation keeps track of the starting row value (ROW 0) and column value (COL 4). 
                 while (counter != consecutiveValuesToMatch)
                 {
                     if (xValue >= ticTacToeBoard.length - 1 || yValue == 0)
                         break;                    
                     xValue++;
                     yValue--;
                     counter++;
                 }
                 int diagnalRightStartingRow = xValue;
                 int diagnalRightStartingCol = yValue;
                 
                //Get Diagnal Right Ending Point pair
                //Must include the cell player chose so have to increment or decrement by 1.
                int diagnalRightEndingRow = input_row_num - consecutiveValuesToMatch + 1;
                // int diagnalRightEndingCol = input_col_num + consecutiveValuesToMatch - 1;

                if (diagnalRightEndingRow < 0)
                    diagnalRightEndingRow = 0;

                // if (diagnalRightEndingCol >= ticTacToeBoard.length)
                //     diagnalRightEndingCol = ticTacToeBoard.length - 1;

                int y = diagnalRightStartingCol;
                int sameCharacterFound = 0;
                for (int x = diagnalRightStartingRow; x >= diagnalRightEndingRow; x--) {
                    char current_pos_char = 0; // 0 is null

                    current_pos_char = ticTacToeBoard[x][y];
                    
                    if (current_pos_char == charToCheck) {
                        sameCharacterFound++;
                        if (sameCharacterFound == consecutiveValuesToMatch)
                            return true;
                    } else
                        sameCharacterFound = 0;

                    System.out.println(checkPosition.toUpperCase() + " CHECK DIAG2 - x = " + (x+1) + " y = " + (y+1)
                    + ". diagnalRightStartingRow = " + diagnalRightStartingRow
                    + ". diagnalRightEndingRow = " + diagnalRightEndingRow
                    + ". current_pos_char = " + current_pos_char +
                        "." + " sameCharFound = " +
                        sameCharacterFound);
                    
                    y++; //increment column. 
                    
                    //End of the tic tac to board reached.
                    if (y == ticTacToeBoard[x].length)
                        break;
                }
                
                return false;
            }


    private static boolean isValidMove(String input_board_move, char ticTacToeBoard[][]) {
        /*
         * char columnAlphabet = Character.toUpperCase(input_board_move.charAt(0));
         * //Since array index start at 0 subtract 1.
         * int row_number = Integer.parseInt(input_board_move.substring(1,
         * input_board_move.length())) - 1;
         * //Since array index start at 0 subtract 1.
         * int col_number = GetColIndexFromAlphabet(columnAlphabet) - 1;
         */
        var input_move = GetInputBoardMove(input_board_move);
        int row_number = input_move.pair_value_one;
        int col_number = input_move.pair_value_two;

        try {
            // If current place to move is not out of bounds of the array and does not have
            // an 'O' or an 'X', only then is it a valid move.
            // '-' is default value when nothing is placed in that part of the board.
            if (ticTacToeBoard[row_number][col_number] == '-')
                return true;
            else
                return false;
        } catch (Exception indexOutOfBounException) {
            return false;
        }
    }

    // Extracts the row index and col index from the inputted board move by the user
    // as a Pair.
    private static Pair<Integer, Integer> GetInputBoardMove(String input_board_move) {
        char columnAlphabet = Character.toUpperCase(input_board_move.charAt(0));
        // Since array index start at 0 subtract 1.
        int row_number = Integer.parseInt(input_board_move.substring(1, input_board_move.length())) - 1;
        // Since array index start at 0 subtract 1.
        int col_number = GetColIndexFromAlphabet(columnAlphabet) - 1;

        return new Pair<>(row_number, col_number);
    }

    // Since users will input column alphabet instead of an index when we are
    // required to move, this will convert
    // given alphabet to the necessary index.
    private static int GetColIndexFromAlphabet(char columnAlphabet) {
        return Math.abs(columnAlphabet - 'A') + 1;
    }

    // Can have letters A and double digits at maximum so at least 2 characters and
    // at most 3.
    private static boolean isValidUserInput(String input_board_move) {
        // Regexp - first character has to be capital alphabet. Can have up to 2 digits
        // afterwards.
        return input_board_move.matches("^[a-zA-Z]{1}[0-9]{1,2}$");
    }

    // Player 1 will always be O player 2 will always be X.
    private static void setMoveOnBoard(String input_board_move, boolean isPlayerOneTurn, char[][] ticTacToeBoard) {
        char player_board_value;
        if (isPlayerOneTurn)
            player_board_value = GetPlayerOneBoardChar();
        else
            player_board_value = GetPlayerTwoBoardChar();

        var input_move = GetInputBoardMove(input_board_move);
        int row_number = input_move.pair_value_one;
        int col_number = input_move.pair_value_two;

        ticTacToeBoard[row_number][col_number] = player_board_value;
    }

    private static boolean ValueExists(char value, char ticTacToeBoard[][]) {
        for (int row = 0; row < ticTacToeBoard.length; row++) {
            for (int col = 0; col < ticTacToeBoard[row].length; col++) {
                if (ticTacToeBoard[row][col] == value)
                    return true;
            }
        }
        return false;
    }

    private static char GetPlayerChar(boolean isPlayerOneTurn) {
        if (isPlayerOneTurn)
            return GetPlayerOneBoardChar();
        else
            return GetPlayerTwoBoardChar();
    }

    private static char GetPlayerOneBoardChar() {
        return 'O';
    }

    private static char GetPlayerTwoBoardChar() {
        return 'X';
    }

    private static void PrintValuesTicTacToeBoard(String[][] ticTacToeBoard) {
        StringBuilder sb = new StringBuilder();

        for (int row = 0; row < ticTacToeBoard.length; row++) {
            for (int col = 0; col < ticTacToeBoard[row].length; col++) {
                // System.out.print(ticTacToeBoard[row][col] + "\t");
                System.out.print("O" + "\t");
            }
            System.out.println();
        }
    }

    private static void PrintTicTacToeBoard(char[][] ticTacToeBoard) {
        StringBuilder sb = new StringBuilder();
        PrintBoardHeader(ticTacToeBoard[0].length);
        for (int row = 0; row < ticTacToeBoard.length; row++) {
            PrintBoardDivider(ticTacToeBoard[row].length);
            for (int col = 0; col < ticTacToeBoard[row].length; col++) {
                if (col == 0)
                    // We remove one extra space once row number to be displayed is double digits.
                    if (ticTacToeBoard.length < 10 || row < 9)
                        System.out.printf((row + 1) + " | %s ", ticTacToeBoard[row][col]);
                    else
                        System.out.printf((row + 1) + "| %s ", ticTacToeBoard[row][col]);
                else
                    System.out.printf("| %s ", ticTacToeBoard[row][col]);
            }
            System.out.println("|");
        }
        // Last bottom border to print.
        PrintBoardDivider(ticTacToeBoard[0].length);
        // print one line after board.
        System.out.println();
    }

    private static String PrintBoardHeader(int columnSize) {
        StringBuilder sb = new StringBuilder();
        // PrintBoardDivider(columnSize);
        var startColChar = 'A';
        for (int i = 1; i <= columnSize; i++) {
            // Add space so we can show row numbers.
            if (i == 1)
                System.out.printf("    %s", startColChar);
            else
                System.out.printf("   %s", startColChar);
            startColChar++;
        }
        // System.out.println("|");
        System.out.println();
        return "";
    }

    private static String PrintBoardDivider(int columnSize) {
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i <= columnSize; i++) {
            if (i == 1)
                sb.append("  +---");
            else
                sb.append("+---");
        }
        sb.append("+");
        System.out.println(sb.toString());
        return sb.toString();
    }
}
