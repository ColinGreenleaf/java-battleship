import java.util.ArrayList;
import java.io.*;
import java.util.Scanner;

public class Board
{
    //initialize variables for use
    protected ArrayList<ArrayList<CellStatus>> layout;
    protected Fleet fleet;
    public final int SIZE = 0;
    
    //initialize constants for length of ships
    private final int SUB_LENGTH = 3;
    private final int DESTROYER_LENGTH = 2;
    private final int AIRCRAFT_CARRIER_LENGTH = 5;
    private final int BATTLESHIP_LENGTH = 4;
    private final int CRUISER_LENGTH = 3;

    /**
     * constructor to... do a lot. it creates a fleet, makes a 2d array for the layout, sets all cells to nothing. 
     * then opens a file of ship location data and sets up the board accordingly.
     * @param filename name of the file to open and pull ship location data from
     */
    public Board(String filename)
    {
        fleet = new Fleet();
        //create the 2d array for the board layout and set all cells to NOTHING
        layout = new ArrayList<ArrayList<CellStatus>>(10);
        for(int i = 0; i<10; i++)
        {
            layout.add(new ArrayList<CellStatus>(10));
            for(int j = 0; j<10; j++)
                 layout.get(i).add(CellStatus.NOTHING);
        }
        try
        {
            File file = new File(filename);
            Scanner infile = new Scanner(file);
            while(infile.hasNext())
            {
                //read in the data from the file
                String data = infile.nextLine();
                char shipchar = data.charAt(0);

                String pos1 = "";
                String pos2 = "";
                int layoutRow = 0;
                int layoutColStart = 0;
                int layoutColEnd = 0;
                
                //three possible scenarios for 1st and 2nd cell combinations
                if(data.length() == 9)
                {
                    pos1 = "" + data.charAt(2) + data.charAt(3) + data.charAt(4);
                    pos2 = "" + data.charAt(6) + data.charAt(7) + data.charAt(8);
                    layoutColStart = Integer.parseInt(pos1.charAt(1) + "" + pos1.charAt(2));
                    layoutColEnd = Integer.parseInt(pos2.charAt(1) + "" + pos2.charAt(2));

                }
                else if(data.length() == 8)
                {
                    pos1 = "" + data.charAt(2) + data.charAt(3);
                    pos2 = "" + data.charAt(5) + data.charAt(6) + data.charAt(7);
                    layoutColStart = Character.getNumericValue(pos1.charAt(1));
                    layoutColEnd = Integer.parseInt(pos2.charAt(1) + "" + pos2.charAt(2));
                }
                else if(data.length() == 7)
                {
                    pos1 = "" + data.charAt(2) + data.charAt(3);
                    pos2 = "" + data.charAt(5) + data.charAt(6);
                    layoutColStart = Character.getNumericValue(pos1.charAt(1));
                    layoutColEnd = Character.getNumericValue(pos2.charAt(1));

                }
                int shipLength = layoutColEnd - layoutColStart;

                //convert character to row index
                switch (pos1.charAt(0))
                {
                    case 'A': layoutRow = 0;
                        break;
                    case 'B': layoutRow = 1;
                        break;
                    case 'C': layoutRow = 2;
                        break;
                    case 'D': layoutRow = 3;
                        break;
                    case 'E': layoutRow = 4;
                        break;
                    case 'F': layoutRow = 5;
                        break;
                    case 'G': layoutRow = 6;
                        break;
                    case 'H': layoutRow = 7;
                        break;
                    case 'I': layoutRow = 8;
                        break;
                    case 'J': layoutRow = 9;
                        break;
                    default: layoutRow = 0;
                        break;
                }

                //if statement to make either a vertical or horizontal ship
                if(layoutColEnd == layoutColStart)
                {
                    switch(shipchar) 
                    {
                        case 'C': for(int i = 0; i < CRUISER_LENGTH; i++) 
                            layout.get(layoutRow+i).set(layoutColStart-1, CellStatus.CRUISER);
                            break;
                        case 'A': for(int i = 0; i < AIRCRAFT_CARRIER_LENGTH; i++) 
                            layout.get(layoutRow+i).set(layoutColStart-1, CellStatus.AIRCRAFT_CARRIER);
                            break;
                        case 'B': for(int i = 0; i < BATTLESHIP_LENGTH; i++) 
                            layout.get(layoutRow+i).set(layoutColStart-1, CellStatus.BATTLESHIP);
                            break;
                        case 'S': for(int i = 0; i < SUB_LENGTH; i++) 
                            layout.get(layoutRow+i).set(layoutColStart-1, CellStatus.SUB);
                            break;
                        case 'D': for(int i = 0; i < DESTROYER_LENGTH; i++) 
                            layout.get(layoutRow+i).set(layoutColStart-1, CellStatus.DESTROYER);
                            break;
                        
                    }
                }
                else
                {
                    switch(shipchar) 
                    {
                        case 'C': for(int i = -1; i < shipLength; i++) 
                            layout.get(layoutRow).set(layoutColStart+i, CellStatus.CRUISER);
                            break;
                        case 'A': for(int i = -1; i < shipLength; i++) 
                            layout.get(layoutRow).set(layoutColStart+i, CellStatus.AIRCRAFT_CARRIER);
                            break;
                        case 'B': for(int i = -1; i < shipLength; i++) 
                            layout.get(layoutRow).set(layoutColStart+i, CellStatus.BATTLESHIP);
                            break;
                        case 'S': for(int i = -1; i < shipLength; i++) 
                            layout.get(layoutRow).set(layoutColStart+i, CellStatus.SUB);
                            break;
                        case 'D': for(int i = -1; i < shipLength; i++) 
                            layout.get(layoutRow).set(layoutColStart+i, CellStatus.DESTROYER);
                            break;
                        
                    }
                }
            }
            infile.close();
            
        //whoops! looks like there was a file problem. do this instead!
        } catch (FileNotFoundException e)
        {
            System.out.print("no file found for board placement");
            System.exit(0);
        }

    //whew! what a constructor, huh?
    }


    /**
     * takes a move and alters the corresponding cell accordingly, and sinks the ship if that hit would sink it
     * @param m move to apply to the board
     */
    public void applyMoveToLayout(Move m)
    {
        CellStatus cell = layout.get(m.row()).get(m.col());
        switch(cell) 
        {
            case AIRCRAFT_CARRIER: 
                fleet.updateFleet(ShipType.ST_AIRCRAFT_CARRIER);
                layout.get(m.row()).set(m.col(), CellStatus.AIRCRAFT_CARRIER_HIT);
                if(fleet.aircraftCarrier.getSunk())
                    sinkAircraftCarrier();
                break;
                
            case BATTLESHIP: 
                fleet.updateFleet(ShipType.ST_BATTLESHIP);
                layout.get(m.row()).set(m.col(), CellStatus.BATTLESHIP_HIT);
                if(fleet.battleShip.getSunk())
                    sinkBattleship();
                break;

            case CRUISER: 
                fleet.updateFleet(ShipType.ST_CRUISER);
                layout.get(m.row()).set(m.col(), CellStatus.CRUISER_HIT);
                if(fleet.cruiser.getSunk())
                    sinkCruiser();
                break;

            case DESTROYER: 
                fleet.updateFleet(ShipType.ST_DESTROYER);
                layout.get(m.row()).set(m.col(), CellStatus.DESTROYER_HIT);
                if(fleet.destroyer.getSunk())
                    sinkDestroyer();
                break;

            case SUB: fleet.updateFleet(ShipType.ST_SUB);
                layout.get(m.row()).set(m.col(), CellStatus.SUB_HIT);
                if(fleet.sub.getSunk())
                    sinkSub();
                break;

            case NOTHING: 
                layout.get(m.row()).set(m.col(), CellStatus.NOTHING_HIT);
                break;

            default:
                break;
            
        }
    }

    /**
     * checks if a move was already taken
     * @param m move to check validity of
     * @return true/false based on if the move was already taken.
     */
    public boolean moveTaken(Move m)
    {
        CellStatus cell = layout.get(m.row()).get(m.col());
        if(cell == CellStatus.AIRCRAFT_CARRIER || cell == CellStatus.CRUISER || cell == CellStatus.DESTROYER || cell == CellStatus.SUB || cell == CellStatus.BATTLESHIP || cell == CellStatus.NOTHING)
        {    
            applyMoveToLayout(m);
            return true;
        }
        return false;
    }

    /**
     * returns the layout of the board
     * @return 2d array layout
     */
    public ArrayList<ArrayList<CellStatus>> getLayout()
    {
        return layout;
    }

    /**
     * returns fleet
     * @return fleet
     */
    public Fleet getFleet()
    {
        return fleet;
    }

    /**
     * determines if the game is over by checking if this board's fleet is sunk
     * @return true/false based on fleet sunkness
     */
    public boolean gameOver()
    {
        return fleet.gameOver();
    }

    /**
     * contains the code to convert all hit aircraft carrier cells to sunk ones
     */
    private void sinkAircraftCarrier()
    {
        for(int i = 0; i<10; i++)
        {
            for(int j = 0; j<10; j++)
            {
                if(layout.get(i).get(j) == CellStatus.AIRCRAFT_CARRIER_HIT)
                    layout.get(i).set(j, CellStatus.AIRCRAFT_CARRIER_SUNK);
            }
        }
    }

    /**
     * contains the code to convert all hit battleship cells to sunk ones
     */
    private void sinkBattleship()
    {
        for(int i = 0; i<10; i++)
        {
            for(int j = 0; j<10; j++)
            {
                if(layout.get(i).get(j) == CellStatus.BATTLESHIP_HIT)
                    layout.get(i).set(j, CellStatus.BATTLESHIP_SUNK);
            }
        }
    }

    /**
     * contains the code to convert all hit cruiser cells to sunk ones
     */
    private void sinkCruiser()
    {
        for(int i = 0; i<10; i++)
        {
            for(int j = 0; j<10; j++)
            {
                if(layout.get(i).get(j) == CellStatus.CRUISER_HIT)
                    layout.get(i).set(j, CellStatus.CRUISER_SUNK);
            }
        }
    }

    /**
     * contains the code to convert all hit destroyer cells to sunk ones
     */
    private void sinkDestroyer()
    {
        for(int i = 0; i<10; i++)
        {
            for(int j = 0; j<10; j++)
            {
                if(layout.get(i).get(j) == CellStatus.DESTROYER_HIT)
                    layout.get(i).set(j, CellStatus.DESTROYER_SUNK);
            }
        }
    }

    /**
     * contains the code to convert all hit sub cells to sunk ones
     */
    private void sinkSub()
    {
        for(int i = 0; i<10; i++)
        {
            for(int j = 0; j<10; j++)
            {
                if(layout.get(i).get(j) == CellStatus.SUB_HIT)
                    layout.get(i).set(j, CellStatus.SUB_SUNK);
            }
        }
    }
        
}