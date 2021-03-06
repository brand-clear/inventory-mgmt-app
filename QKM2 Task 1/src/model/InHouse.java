package model;

/**
 * Represents a part that is manufacture in-house.
 * @author Brandon McCleary
 */
public class InHouse extends Part{
    private int machinedId;

    /**
     * Creates a Part that is manufactured in-house.
     * @param id the part id
     * @param name the part name
     * @param price the part price
     * @param stock the part stock
     * @param min the minimum number of parts
     * @param max the maximum number of parts
     * @param machinedId the machine id
     */
    public InHouse(int id, String name, double price, int stock, int min, int max, int machinedId) {
        super(id, name, price, stock, min, max);
        this.machinedId = machinedId;
    }

    /**
     * @return the machine id
     */
    public int getMachinedId() { return machinedId; }

    /**
     * @param machinedId the machine id to set
     */
    public void setMachinedId(int machinedId) { this.machinedId = machinedId; }
}




