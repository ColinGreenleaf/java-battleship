/*
 * Colin Greenleaf
 * CS100
 * The FInal Project! this file (along with the others) runs the classic game Battleship!
*/

//import necessary utilities
import java.util.Random;
import java.util.Scanner;

public class BattleShipDriver
{
    public static void main(String[] args) 
    {
        //create important objects
        Game game = new Game();
        Random rand = new Random();
        Scanner keyboard = new Scanner(System.in);

        //initialize variables for collecting method outputs for display
        String[] moveResultComp;
        String moveResultUser;

        //and so it begins!
        System.out.println("Welcome to Battleship!\n");

        //"flip a coin" to determine who goes first
        String turn;
        int turnint = rand.nextInt(2);
        if(turnint == 0)
        {
            turn = "player";
            System.out.println("You won the coin toss and you get to go first.");
        }
            
        else
        {
            turn = "computer";
            System.out.println("The computer won the coin toss and gets to go first.");
        }

        //while neither player has lost:
        while(!game.computerDefeated() && !game.userDefeated())
        {

            //if it's the player's turn
            if(turn.equals("player"))
            {
                //collect the user's input move
                System.out.print("Your turn: ");
                String movestring;

                //input validation
                do
                {    
                    movestring = keyboard.nextLine();
                    //if the length of the player's input is 3 (since we need to account for double digit column)
                    if(movestring.length() == 3)
                    {
                        //if the second character is a letter (which is not allowed):
                        if(Character.isLetter(movestring.charAt(1)))
                        {
                            //ask for another move and make movestring something that breaks the conditions automatically so it loops again
                            System.out.print("Please input a valid move: ");
                            movestring += "  ";
                        }
                        //otherwise, if the column input is something greater than 10 (e.g. A19):
                        else if(Character.getNumericValue(movestring.charAt(2)) != 0)
                        {
                            //cut off the second digit of the column, and treat the letter and the first number as the input (A19 becomes A1)
                            String reduced = movestring.charAt(0) + "" + movestring.charAt(1);
                            movestring = reduced;
                            System.out.print("input reduced to " + movestring);
                            //if that new move has already been taken:
                            if(game.player.moveTaken(new Move(movestring)) == false)
                            {
                                //ask for a new move and make movestring something that breaks the conditions automatically so it loops again
                                System.out.print("That move was already taken, please input another: ");
                                movestring += "  ";
                            }
                        }
                    }
                    //if the input isn't three letters, then check that: it isn't blank, the first character is a letter, the second digit is a number, and it isn't 4+ characters:
                    else if(movestring.equals("") || Character.isLetter(movestring.charAt(0)) == false || Character.isDigit(movestring.charAt(1)) == false || movestring.length() > 3)
                        //if it is any of those things, ask for a new input
                        System.out.print("Please input a valid move: ");
                    //if it meets all the conditions, but the move has already been taken: 
                    else if(game.player.moveTaken(new Move(movestring)) == false)
                    {
                        ////ask for a new move and make movestring something that breaks the conditions automatically so it loops again
                        System.out.print("That move was already taken, please input another: ");
                        movestring += "  ";
                    }
                //all the conditions that a proper input must not have: if any are true, try again
                } while (movestring.equals("") || Character.isLetter(movestring.charAt(0)) == false || Character.isDigit(movestring.charAt(1)) == false || movestring.length() > 3);

                //after all validation, make the move the player chose against the computer's board, and assign its return to a variable
                moveResultUser = game.makePlayerMove(movestring);

                //if makePlayerMove returned something (a message syaing that a ship was sunk):
                if(moveResultUser != null)
                    //print out what message is sent
                    System.out.println("Computer says: " + moveResultUser);

                //print out the game board, updated after the most recent move
                System.out.print("\n\n" + game);

                //pass the turn to the computer
                turn = "computer";
            } 
            //if it is the computer's turn:
            else if(turn.equals("computer"))
            {
                //have the player advance the game to the computer's move
                System.out.print("Computer's turn. press Enter to continue.");
                keyboard.nextLine();

                //make the move that the computer randomly chose against the player's board, and assign its return to a variable
                moveResultComp = game.makeComputerMove();
                //display the move that the computer chose
                System.out.print("Computer chose: " + moveResultComp[0]);

                //if makeComputerMove returned something (a message syaing that a ship was sunk):
                if(moveResultComp[1] != null)
                    //print out what message is sent
                    System.out.println("You say: " + moveResultComp[1]);

                //print out the game board, updated after the most recent move
                System.out.print("\n\n" + game);

                //pass the turn to the player
                turn = "player";
            }
            
        }
        //once a player has been defeated, print that the game is over
        System.out.print("Game complete! ");
        //if it was the player who was defeated display that the computer won
        if(game.userDefeated())
            System.out.println("Winner: the computer :(");
        //otherwise, display that you won!
        else   
            System.out.println("Winner: You! :)");

        //close the keyboard scanner
        keyboard.close();

    }
}