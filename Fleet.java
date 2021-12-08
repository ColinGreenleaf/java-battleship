public class Fleet
{
    //create a ship of each type
    protected Ship battleShip;
    protected Ship aircraftCarrier;
    protected Ship cruiser;
    protected Ship sub;
    protected Ship destroyer;

    /**
     * constructor to intialize each ship
     */
    public Fleet()
    {
        battleShip = new Battleship();
        aircraftCarrier = new AircraftCarrier();
        cruiser = new Cruiser();
        sub = new Sub();
        destroyer = new Destroyer();
    }

    /**
     * hit a ship
     * @param s ship type to be hit
     * @return true/false based on if that hit sunk the ship
     */
    public boolean updateFleet(ShipType s)
    {
        boolean sunkStatus = false;
        
        switch (s) {
            case ST_SUB: sunkStatus = sub.hit();
                break;
            case ST_AIRCRAFT_CARRIER: sunkStatus = aircraftCarrier.hit();
                break;
            case ST_BATTLESHIP: sunkStatus = battleShip.hit();
                break;
            case ST_CRUISER: sunkStatus = cruiser.hit();
                break;
            case ST_DESTROYER: sunkStatus = destroyer.hit();
                break;
            default: sunkStatus = false;
        }
        
        return sunkStatus;
    }

    /**
     * checks if all of the ships are sunk, and if so, signals that the game is over
     * @return true/false based on game-endedness
     */
    public boolean gameOver()
    {
        if(battleShip.getSunk() && aircraftCarrier.getSunk() && cruiser.getSunk() && sub.getSunk() && destroyer.getSunk())
            return true;
        else
            return false;
    }

}