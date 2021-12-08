import java.util.Random;
import java.util.ArrayList;


public class UserBoard extends Board
{

    //initialize some variables
    private ArrayList<String> moves;
    private Random rand;

    /**
     * constructor which creates an array of all possible moves that the computer could take
     * @param fname name of file to read ship position data from
     */
    public UserBoard(String fname)
    {
        super(fname);
        moves = new ArrayList<String>(100);
        rand = new Random();

        for(int i = 0; i<10; i++)
        {
            for(int j = 0; j<10; j++)
                 moves.add(String.format("%d%d", i, j));
        }        
    }

    /**
     * picks a random move from the array of possible moves, and makes it against the player's board
     * @return array of Strings with cell targeted by computer, and null/message based on if the move sunk a ship
     */
    public String[] makeComputerMove()
    {
        int randIndex = rand.nextInt(moves.size());
        String randmove = moves.get(randIndex);
        moves.remove(randIndex);
        
        int row = Integer.parseInt("" + randmove.charAt(0));
        int col = Integer.parseInt("" + randmove.charAt(1));

        Move m = new Move(row, col);

        String[] returns = new String[2];
        String alpha = "ABCDEFGHIJ";
        this.applyMoveToLayout(m);
        returns[0] = alpha.charAt(m.row()) + "" + (m.col()+1);
        CellStatus c = layout.get(m.row()).get(m.col());
        String sunkcase = ("You sunk my ");
        switch(c) 
        {
            case AIRCRAFT_CARRIER_SUNK: returns[1] = sunkcase + "Aircraft Carrier!";
            case BATTLESHIP_SUNK: returns[1] = sunkcase + "Battleship!";
            case CRUISER_SUNK: returns[1] = sunkcase + "Cruiser!";
            case DESTROYER_SUNK: returns[1] = sunkcase + "Destroyer!";
            case SUB_SUNK: returns[1] = sunkcase + "Sub!";
            default: returns[1] = null;
        }
        return returns;
    }

    /**
     * toString to visualize player's board
     * @return representation of player's board
     */
    public String toString()
    {
        String alpha = "ABCDEFGHIJ";
        String board = "";
        board += "   1  2  3  4  5  6  7  8  9  10\n";
        for(int k = 0; k<10; k++)
        {
            board += alpha.charAt(k) + "  ";
            for(int l = 0; l<10; l++)
                board += layout.get(k).get(l).toString().charAt(1) + "  ";
            board +="\n";
        }

        return board;
    }
}