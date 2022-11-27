public class test {
    public static void main(String[] args) {
        String input_board_move = "d222";
        char columnAlphabet = Character.toUpperCase(input_board_move.charAt(0));
        int row_number = Integer.parseInt(input_board_move.substring(1, input_board_move.length()));
        int col = Math.abs(columnAlphabet - 'A') + 1;
        //int col = columnAlphabet;
        System.out.println("columnalphabet= " + columnAlphabet);
        System.out.println("columnpos= " + col);
        System.out.println("rownumber= " + row_number);
        int player_one_counter = 0;
        int player_two_counter = 0;
        var test = new Pair(1, 4);
        System.out.println("pairvalue1=" +test.pair_value_one);
        System.out.println("pairvalue2=" +test.pair_value_two);


        String baseString = "Apple";
        String stringToSearchFor = "le";
        System.out.println("Base String: " + baseString + " - stringToSearchFor: " + stringToSearchFor + " - Index returned: " + myOwnIndexOfFunction(baseString, stringToSearchFor));
        
        /*for (int i = 0; i < 10000; i++)
        {
            String test = DetermineFirstTurn();
            if (test.equals("Player 1"))
                player_one_counter++;
            else
                player_two_counter++;
            System.out.println(test);
        }
        System.out.println("player1 total = " + player_one_counter);
        System.out.println("player2 total = " + player_two_counter);*/
 
        
    }

    private static String DetermineFirstTurn() {
        // Use math.random which generates a random value from 0.0-1.0
        // if <= .5 then player 1's turn, otherwise player 2s
        if (Math.random() <= 0.5)
            return "Player 1";
        else
            return "Player 2";
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


    private static int myOwnIndexOfFunction(String baseString, String stringToSearchFor)
    {
        int index = 0;
        baseString = baseString.toLowerCase();
        stringToSearchFor = stringToSearchFor.toLowerCase();
        
        if (!baseString.contains(stringToSearchFor))
            return -1;

        boolean isEqual = true;
        char[] subStringArray = stringToSearchFor.toCharArray();
        
        for (int i = 0; i < baseString.length(); i++)
        {
            int startOfIndex = i;
            if (baseString.charAt(i) == subStringArray[0])
            {
                for (int j = 0; j < stringToSearchFor.length(); j++)
                {
                    System.out.println("baseStringChar " + baseString.charAt(startOfIndex) + " - subStringArray: " + subStringArray[j]);

                    if (baseString.charAt(startOfIndex) != subStringArray[j])
                    {
                        //One letter was different not all letters match.
                        isEqual = false;
                        break;
                    }
                    startOfIndex++;                      
                }

                if (isEqual)
                    break;
                    
            }
            index++; 
            isEqual = true;        
        }

        return index;
    }


}
