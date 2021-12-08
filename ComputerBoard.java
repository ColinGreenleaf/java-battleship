public class ComputerBoard extends Board
{

    /**
     * calls the Board constructor with the name of a file with computer's ship positions
     * @param fname name of file with position data
     */
    public ComputerBoard(String fname)
    {
        super(fname);
    }

    /**
     * makes the player's move on this board
     * @param m move to make
     * @return null/message based on if the move sunk a ship
     */
    public String makePlayerMove(Move m)
    {
        this.applyMoveToLayout(m);
        CellStatus c = layout.get(m.row()).get(m.col());
        String sunkcase = ("You sunk my ");
        switch(c) 
        {
            case AIRCRAFT_CARRIER_SUNK: return sunkcase + "Aircraft Carrier!";
            case BATTLESHIP_SUNK: return sunkcase + "Battleship!";
            case CRUISER_SUNK: return sunkcase + "Cruiser!";
            case DESTROYER_SUNK: return sunkcase + "Destroyer!";
            case SUB_SUNK: return sunkcase + "Sub!";
            default: return null;
            
        }

    }

    /**
     * toString to visualize computer's board
     * @return representation of computer's board
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
                board += layout.get(k).get(l).toString().charAt(0) + "  ";
            board +="\n";
        }

        return board;
    }
}