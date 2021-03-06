package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;

import view.PopUpDialog;

/**
 * Represents an inventory that contains parts and products.
 * @author Brandon McCleary
 */
public class Inventory {
    private ObservableList<Part> allParts = FXCollections.observableArrayList();
    private ObservableList<Product> allProducts = FXCollections.observableArrayList();

    /**
     * Creates an inventory of all parts and products.
     */
    public Inventory(){
        System.out.println("Inventory created...");
    }

    /**
     * Search the part inventory for matching names.
     * @param partialName search criteria
     * @return a collection of parts whose name's match the search criteria
     */
    private ObservableList<Part> getPartialPartNameMatches(String partialName) {
        ObservableList<Part> partialMatches = FXCollections.observableArrayList();
        for (Part part : getAllParts()) {
            if (part.getName().toLowerCase().contains(partialName.toLowerCase())) {
                partialMatches.add(part);
            }
        }
        return partialMatches;
    }

    /**
     * Search the product inventory for matching names.
     * @param partialName search criteria
     * @return a collection of products whose name's match the search criteria
     */
    private ObservableList<Product> getPartialProductNameMatches(String partialName) {
        ObservableList<Product> partialMatches = FXCollections.observableArrayList();
        for(Product product : getAllProducts()) {
            if (product.getName().toLowerCase().contains(partialName.toLowerCase())) {
                partialMatches.add(product);
            }
        }
        return partialMatches;
    }

    /**
     * Search the part inventory for matching IDs.
     * @param id search criteria
     * @return a collection of parts whose IDs match the search criteria
     */
    private ObservableList<Part> getPartIdMatches(int id) {
        ObservableList<Part> idMatches = FXCollections.observableArrayList();
        for (Part part : getAllParts()) {
            if (part.getId() == id) {
                idMatches.add(part);
            }
        }
        return idMatches;
    }

    /**
     * Search the product inventory for matching IDs.
     * @param id search criteria
     * @return a collection of products whose IDs match the search criteria
     */
    private ObservableList<Product> getProductIdMatches(int id) {
        ObservableList<Product> idMatches = FXCollections.observableArrayList();
        for (Product product : getAllProducts()) {
            if (product.getId() == id) {
                idMatches.add(product);
            }
        }
        return idMatches;
    }

    /**
     * Search the part inventory for matching names or IDs.
     * @param table the parts table
     * @param searchText search criteria
     */
    public void onSearchByPart(TableView<Part> table, String searchText) {
        int id;
        ObservableList<Part> parts;

        try {
            id = Integer.parseInt(searchText);
        }
        catch (NumberFormatException ex) {
            // Handle part name search
            parts = getPartialPartNameMatches(searchText);
            if (parts.size() != 0) {
                table.setItems(parts);
            }
            else {
                PopUpDialog.notFound();
            }
            table.getSelectionModel().clearSelection();
            return;
        }

        // Handle part id search
        parts = getPartIdMatches(id);
        if (parts.size() == 1) {
            table.getSelectionModel().select(parts.get(0));
        }
        else {
            PopUpDialog.notFound();
            table.getSelectionModel().clearSelection();
        }
    }

    /**
     * Search the product inventory for matching names or IDs.
     * @param table the products table
     * @param searchText search criteria
     */
    public void onSearchByProduct(TableView<Product> table, String searchText) {
        int id;
        ObservableList<Product> products;

        try {
            id = Integer.parseInt(searchText);
        }
        catch (NumberFormatException ex) {
            // Handle product name search
            products = getPartialProductNameMatches(searchText);
            if (products.size() != 0) {
                table.setItems(products);
            }
            else {
                PopUpDialog.notFound();
            }
            table.getSelectionModel().clearSelection();
            return;
        }

        // Handle product id search
        products = getProductIdMatches(id);
        if (products.size() == 1) {
            table.getSelectionModel().select(products.get(0));
        }
        else {
            PopUpDialog.notFound();
            table.getSelectionModel().clearSelection();
        }
    }

    /**
     * Add a new part to the inventory.
     * @param newPart the new part
     */
    public void addPart(Part newPart) {
        allParts.add(newPart);
    }

    /**
     * Add a new product to the inventory.
     * @param newProduct the new product
     */
    public void addProduct(Product newProduct) {
        allProducts.add(newProduct);
    }

    /**
     * Retrieve a part from the inventory.
     * @param partId the part id
     * @return the part
     */
    public Part lookupPart(int partId) {
        for (Part part : allParts) {
            if (part.getId() == partId) {
                return part;
            }
        }
        return null;
    }

    /**
     * Retrieve a product from the inventory.
     * @param productId the product id
     * @return the product
     */
    public Product lookupProduct(int productId) {
        for (Product product : allProducts) {
            if (product.getId() == productId) {
                return product;
            }
        }
        return null;
    }

    /**
     * Retrieve a part from the inventory.
     * @param partName the part name
     * @return the part
     */
    public Part lookupPart(String partName) {
        for (Part part : allParts) {
            if (part.getName() == partName) {
                return part;
            }
        }
        return null;
    }

    /**
     * Retrieve a product from the inventory.
     * @param productName the product name
     * @return the product
     */
    public Product lookupProduct(String productName) {
        for (Product product : allProducts) {
            if (product.getName() == productName) {
                return product;
            }
        }
        return null;
    }

    /**
     * Update a part in the inventory.
     * @param index the index to set
     * @param selectedPart the part to update
     */
    public void updatePart(int index, Part selectedPart) {
        allParts.set(index, selectedPart);
    }

    /**
     * Update a product in the inventory.
     * @param index the index to set
     * @param newProduct the product to update
     */
    public void updateProduct(int index, Product newProduct) {
        allProducts.set(index, newProduct);
    }

    /**
     * Remove a part from the inventory.
     * @param selectedPart the part to remove
     * @return true if the part is removed
     */
    public boolean deletePart(Part selectedPart) {
        return allParts.remove(selectedPart);
    }

    /**
     * Remove a product from the inventory
     * @param selectedProduct the product to remove
     * @return true if the product is removed
     */
    public boolean deleteProduct(Product selectedProduct) {
        return allProducts.remove(selectedProduct);
    }

    /**
     * @return the list of all parts in the inventory
     */
    public ObservableList<Part> getAllParts() {
        return allParts;
    }

    /**
     * @return the list of all products in the inventory
     */
    public ObservableList<Product> getAllProducts() {
        return allProducts;
    }
}
