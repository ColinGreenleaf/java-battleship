public class Move
{
    //location of the move, in index form
    private int row;
    private int col;
    

    /**
     * constructor to define the move's coordinates based on index inputs
     * @param r index of the move's row
     * @param c index of the move's column
     */
    public Move(int r, int c)
    {
        row = r;
        col = c;
    }

    /**
     * constructor to define the move's coordinates based an a string
     * @param coordinate string version of the move coordinate to be parsed into indexes
     */
    public Move(String coordinate)
    {
        char rowchar = Character.toUpperCase(coordinate.charAt(0));
        row = (int)rowchar - 65; //gets ascii value of capital letter and subtracts 65, so that it is in index form
        if(coordinate.length() == 2)
            col = Character.getNumericValue(coordinate.charAt(1))-1;
        else
            col = Integer.parseInt((coordinate.charAt(1)) + "" + coordinate.charAt(2))-1;

    }

    /**
     * returns row of the move
     * @return row
     */
    public int row()
    {
        return row;
    }

    /**
     * returns column of the move
     * @return col
     */
    public int col()
    {
        return col;
    }

    /**
     * toString to convert the coordinate indexes into coordinate style
     * @return the move, in alphanumeric form
     */
    public String toString()
    {
        String rows = "ABCDEFGHIJ";
        return String.format("%c%d", rows.charAt(row), col+1);

    }

}