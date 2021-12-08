public class Game
{
    //initialize both boards
    protected ComputerBoard computer;
    protected UserBoard player;

    /**
     * constructor to create each board with ship position data from a file
     */
    public Game()
    {
        computer = new ComputerBoard("CompFleet.txt");
        player = new UserBoard("UserFleet.txt");
    }

    /**
     * calls player board's method that makes a move againt it, and returns some statuses
     * @return cell targeted by computer, and null/message based on if that move sunk a ship
     */
    public String[] makeComputerMove()
    {
        return player.makeComputerMove();
    }

    /**
     * call's computer board's method that makes a move against it, and returns a status
     * @param s player's inputted move
     * @return null/message based one of that move sunk a ship
     */
    public String makePlayerMove(String s)
    {
        return computer.makePlayerMove(new Move(s));
    }

    /**
     * checks to see if the computer was defeated
     * @return true/false based on if the computer's whole fleet is sunk or not
     */
    public boolean computerDefeated()
    {
        return computer.gameOver();
    }

    /**
     * checks to see if the player was defeated
     * @return true/false based on if the player's whole fleet is sunk or not
     */
    public boolean userDefeated()
    {
        return player.gameOver();
    }

    /**
     * toString to visualize both boards, properly labeled
     * @return string representation of the game
     */
    public String toString()
    {
        String boards = "";
        boards += "COMPUTER:\n";
        boards += computer.toString() + "\n\n";
        boards += "USER:\n";
        boards += player.toString() + "\n\n";

        return boards;
    }
    
}
