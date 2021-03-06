package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import model.*;
import view.PopUpDialog;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * A controller for the application main window functionality.
 * @author Brandon McCleary
 */
public class MainController implements Initializable {
    public Inventory inventory;
    public TextField searchByPart;
    public TableView<Part> partTable;
    public TableColumn partIdCol;
    public TableColumn partNameCol;
    public TableColumn partInventoryLevelCol;
    public TableColumn partPriceCol;
    public TextField searchByProduct;
    public TableView<Product> productTable;
    public TableColumn productIdCol;
    public TableColumn productNameCol;
    public TableColumn productInventoryLevelCol;
    public TableColumn productPriceCol;
    public Button addPart;
    public Button modifyPart;
    public Button deletePart;
    public Button addProduct;
    public Button modifyProduct;
    public Button deleteProduct;
    public Button exit;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Link part table columns to Part attributes
        partIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInventoryLevelCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        // Link product table columns to Product attributes
        productIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        productNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        productInventoryLevelCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        productPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

    /**
     * Add dummy data to the inventory for presentation purposes only.
     */
    public void addDummyData() {
        // Create bicycle product and associated parts
        Product bike = new Product(1, "Bicycle", 129.99, 13, 10, 30);
        Part frame = new InHouse(1, "Frame", 65.49, 13, 10, 30, 23);
        Part tire = new InHouse(2, "Tire", 23.99, 17, 10, 50, 19);
        Part chain = new Outsourced(3, "Chain", 14.99, 26, 10, 80, "GE");
        bike.addAssociatedPart(frame);
        bike.addAssociatedPart(tire);
        bike.addAssociatedPart(chain);

        // Create fan product and associated parts
        Product fan = new Product(2, "Fan", 99.95, 16, 10, 30);
        Part blade = new Outsourced(4, "Blades", 10.99, 24, 10, 40, "AMC");
        Part bulb = new Outsourced(5, "Bulb", 2.49, 33, 10, 40, "GE");
        Part fixture = new InHouse(6, "Fixture", 20.05, 14, 10, 50, 37);
        fan.addAssociatedPart(blade);
        fan.addAssociatedPart(bulb);
        fan.addAssociatedPart(fixture);

        // Increment item IDs per dummy data
        PartController.autoGenId = 7;
        ProductController.autoGenId = 3;

        // Add all items to the inventory
        inventory.addPart(frame);
        inventory.addPart(tire);
        inventory.addPart(chain);
        inventory.addProduct(bike);
        inventory.addPart(blade);
        inventory.addPart(bulb);
        inventory.addPart(fixture);
        inventory.addProduct(fan);

        // Set table data
        partTable.setItems(inventory.getAllParts());
        productTable.setItems(inventory.getAllProducts());
    }

    /**
     * Search the part table for matching values.
     * @param inputMethodEvent the keyboard event
     */
    public void onSearchByPart(ActionEvent inputMethodEvent) {
        inventory.onSearchByPart(partTable, searchByPart.getText());
    }

    /**
     * Open the Add Part window.
     * @param actionEvent the click event
     * @throws IOException if the resource cannot be found
     */
    public void onAddPart(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/addPartForm.fxml"));
        Parent root = loader.load();
        PartController addPartController = loader.getController();
        addPartController.setInventory(inventory);
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setResizable(false);
        stage.show();
    }

    /**
     * Open the Modify Part window.
     * @param actionEvent the click event
     * @throws IOException if the resource cannot be found
     */
    public void onModifyPart(ActionEvent actionEvent) throws IOException {
        Part part = partTable.getSelectionModel().getSelectedItem();
        int index = partTable.getSelectionModel().getSelectedIndex();
        // No selection
        if (part == null) {
            PopUpDialog.notSelected();
            return;
        }
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/addPartForm.fxml"));
        Parent root = loader.load();
        PartController modifyPartController = loader.getController();
        modifyPartController.setInventory(inventory);
        modifyPartController.setPartAttributes(part, index);
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setResizable(false);
        stage.show();
    }

    /**
     * Handle a request to delete parts from the part table.
     * @param actionEvent the click event
     */
    public void onDeletePart(ActionEvent actionEvent) {
        Part part = partTable.getSelectionModel().getSelectedItem();
        // No selection
        if (part == null) {
            PopUpDialog.notSelected();
            return;
        }
        if (PopUpDialog.confirmRemoval()) {
            inventory.deletePart(part);
        }
    }

    /**
     * Search the product table for matching values.
     * @param inputMethodEvent the keyboard event
     */
    public void onSearchByProduct(ActionEvent inputMethodEvent) {
        inventory.onSearchByProduct(productTable, searchByProduct.getText());
    }

    /**
     * Open the Add Product window.
     * @param actionEvent the click event
     * @throws IOException if the resource cannot be found
     */
    public void onAddProduct(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/addProductForm.fxml"));
        Parent root = loader.load();
        ProductController addProductController = loader.getController();
        addProductController.setInventory(inventory);
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setResizable(false);
        stage.show();
    }

    /**
     * Open the Modify Product window.
     * @param actionEvent the button click
     * @throws IOException if the resource cannot be found
     */
    public void onModifyProduct(ActionEvent actionEvent) throws IOException {
        Product product = productTable.getSelectionModel().getSelectedItem();
        int index = productTable.getSelectionModel().getSelectedIndex();
        // No selection
        if (product == null) {
            PopUpDialog.notSelected();
            return;
        }
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/addProductForm.fxml"));
        Parent root = loader.load();
        ProductController modifyProductController = loader.getController();
        modifyProductController.setInventory(inventory);
        modifyProductController.setProductAttributes(product, index);
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setResizable(false);
        stage.show();
    }

    /**
     * Handle a request to delete products from the product table.
     * @param actionEvent the click event
     */
    public void onDeleteProduct(ActionEvent actionEvent) {
        Product product = productTable.getSelectionModel().getSelectedItem();
        // No selection
        if (product == null) {
            PopUpDialog.notSelected();
            return;
        }
        // Check for associated parts
        if (product.getAssociatedParts().size() > 0) {
            PopUpDialog.notDeleted();
        }
        else if (PopUpDialog.confirmRemoval()) {
            inventory.deleteProduct(product);
        }
    }

    /**
     * Close the main window.
     * @param actionEvent the click event
     */
    public void onExit(ActionEvent actionEvent) {
        Stage stage = (Stage) exit.getScene().getWindow();
        stage.close();
    }
}
