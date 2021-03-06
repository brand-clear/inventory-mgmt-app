package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Represents a product.
 * @author Brandon McCleary
 */
public class Product {
    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;
    private final ObservableList<Part> associatedParts = FXCollections.observableArrayList();

    /**
     * Creates a new product.
     * @param id the product id
     * @param name the product name
     * @param price the product price
     * @param stock the product stock
     * @param min the minimum number of products
     * @param max the maximum number of products
     */
    public Product(int id, String name, double price, int stock, int min, int max) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }

    /**
     * @return the product id
     */
    public int getId() { return id; }

    /**
     * @param id the product id to set
     */
    public void setId(int id) { this.id = id; }

    /**
     * @return the product name
     */
    public String getName() { return name; }

    /**
     * @param name the product name to set
     */
    public void setName(String name) { this.name = name; }

    /**
     * @return the product price
     */
    public double getPrice() { return price; }

    /**
     * @param price the product price to set
     */
    public void setPrice(double price) { this.price = price; }

    /**
     * @return the product stock
     */
    public int getStock() { return stock; }

    /**
     * @param stock the product stock to set
     */
    public void setStock(int stock) { this.stock = stock; }

    /**
     * @return the minimum number of products
     */
    public int getMin() { return min; }

    /**
     * @param min the minimum to set
     */
    public void setMin(int min) { this.min = min; }

    /**
     * @return the maximum number of products
     */
    public int getMax() { return max; }

    /**
     * @param max the maximum to set
     */
    public void setMax(int max) { this.max = max; }

    /**
     * Adds an associated part to the product.
     * @param part the associated part to add
     */
    public void addAssociatedPart(Part part) {
        associatedParts.add(part);
    }

    /**
     * Removes an associated part from the product.
     * @param selectedAssociatedPart the associated part to remove
     * @return true if the removal was successful
     */
    public boolean deleteAssociatedPart(Part selectedAssociatedPart) {
        return associatedParts.remove(selectedAssociatedPart);
    }

    /**
     * @return the parts associated with the product
     */
    public ObservableList<Part> getAssociatedParts() { return associatedParts; }
}



