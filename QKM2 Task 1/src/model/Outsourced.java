package model;

/**
 * Represents a part that is manufactured by a vendor.
 * @author Brandon McCleary
 */
public class Outsourced extends Part {
    private String companyName;

    /**
     * Creates a Part that is manufactured by a vendor.
     * @param id the part id
     * @param name the part name
     * @param price the part price
     * @param stock the part stock
     * @param min the minimum number of parts
     * @param max the maximum number of parts
     * @param companyName the company from which the part is sourced
     */
    public Outsourced(int id, String name, double price, int stock, int min, int max, String companyName) {
        super(id, name, price, stock, min, max);
        this.companyName = companyName;
    }

    /**
     * @return the company from which the part is sourced
     */
    public String getCompanyName() { return companyName; }

    /**
     * @param companyName the company name to set
     */
    public void setCompanyName(String companyName) { this.companyName = companyName; }
}




