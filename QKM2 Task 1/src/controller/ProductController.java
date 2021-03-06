package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import model.Inventory;
import model.Part;
import model.Product;
import view.PopUpDialog;

import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

/**
 * A controller for application add/modify product functionality.
 * @author Brandon McCleary
 */
public class ProductController implements Initializable {
    private int productIndex;
    private Inventory inventory;
    private final String modifyProductLabelText = "Modify Product";
    private final ObservableList<Part> tempAssociatedParts = FXCollections.observableArrayList();
    public static int autoGenId;
    public Label formLabel;
    public TextField id;
    public TextField name;
    public TextField stock;
    public TextField price;
    public TextField max;
    public TextField min;
    public TextField searchByPart;
    public TableView<Part> partTable;
    public TableColumn partIdCol;
    public TableColumn partNameCol;
    public TableColumn partInventoryCol;
    public TableColumn partPriceCol;
    public TableView<Part> productTable;
    public TableColumn productIdCol;
    public TableColumn productNameCol;
    public TableColumn productInventoryCol;
    public TableColumn productPriceCol;
    public Button add;
    public Button removePart;
    public Button cancel;
    public Button save;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        partIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInventoryCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        productIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        productNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        productInventoryCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        productPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

    /**
     * @param inventory the inventory to set
     */
    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
        partTable.setItems(this.inventory.getAllParts());
        productTable.setItems(tempAssociatedParts);
    }

    /**
     * Update the form with existing product attributes.
     * @param product the selected part
     * @param index the index of the part
     */
    public void setProductAttributes(Product product, int index) {
        this.productIndex = index;
        tempAssociatedParts.addAll(product.getAssociatedParts());
        formLabel.setText(modifyProductLabelText);
        id.setText(String.valueOf(product.getId()));
        name.setText(product.getName());
        stock.setText(String.valueOf(product.getStock()));
        price.setText(String.valueOf(product.getPrice()));
        min.setText(String.valueOf(product.getMin()));
        max.setText(String.valueOf(product.getMax()));
    }

    /**
     * Filter search results on the parts table.
     * @param actionEvent the keyboard event
     */
    public void onSearchByPart(ActionEvent actionEvent) {
        inventory.onSearchByPart(partTable, searchByPart.getText());
    }

    /**
     * Add a part to a product's associated parts.
     * @param actionEvent the click event
     */
    public void onAdd(ActionEvent actionEvent) {
        Part part = partTable.getSelectionModel().getSelectedItem();
        tempAssociatedParts.add(part);
    }

    /**
     * Remove a part from a product's associated parts.
     * @param actionEvent the click event
     */
    public void onRemovePart(ActionEvent actionEvent) {
        Part part = productTable.getSelectionModel().getSelectedItem();
        if (part == null) {
            PopUpDialog.notSelected();
        }
        else if (PopUpDialog.confirmRemoval()) {
            tempAssociatedParts.remove(part);
        }
    }

    /**
     * Close the add/modify part window.
     * @param actionEvent the click event
     */
    public void onCancel(ActionEvent actionEvent) {
        Stage stage = (Stage) cancel.getScene().getWindow();
        stage.close();
    }

    /**
     * Save user input to new or existing product.
     * @param actionEvent the click event
     */
    public void onSave(ActionEvent actionEvent) {
        String nameInput = name.getText();
        String stockInput = stock.getText();
        String priceInput = price.getText();
        String minInput = min.getText();
        String maxInput = max.getText();
        List<String> inputs = Arrays.asList(nameInput, stockInput, priceInput, minInput, maxInput);

        // Check for empty text fields
        for (String text : inputs) {
            if (text.length() == 0) {
                PopUpDialog.emptyFields();
                return;
            }
        }

        // Check for integers where required
        int intMin;
        int intMax;
        int intStock;

        try {
            intMin = Integer.parseInt(minInput);
        }
        catch (NumberFormatException ex) {
            PopUpDialog.invalidQuantityTypes();
            return;
        }

        try {
            intMax = Integer.parseInt(maxInput);
        }
        catch (NumberFormatException ex) {
            PopUpDialog.invalidQuantityTypes();
            return;
        }

        try {
            intStock = Integer.parseInt(stockInput);
        }
        catch (NumberFormatException ex) {
            PopUpDialog.invalidQuantityTypes();
            return;
        }

        // Confirm that price is in double format
        double doublePrice;

        try {
            doublePrice = Double.parseDouble(priceInput);
        }
        catch (NumberFormatException ex) {
            PopUpDialog.invalidQuantityTypes();
            return;
        }

        // Confirm that quantities are valid
        if (intMin > intStock | intStock > intMax | intMin >= intMax) {
            PopUpDialog.invalidCounts();
            return;
        }

        int productId;

        if (productIndex >= 0) {
            productId = Integer.parseInt(id.getText());
        } else {
            productId = autoGenId;
        }

        Product product = new Product(productId, nameInput, doublePrice, intStock, intMin, intMax);
        if (tempAssociatedParts.size() > 0) {
            for (Part part: tempAssociatedParts) {
                product.addAssociatedPart(part);
            }
        } else {
            PopUpDialog.noAssociatedParts();
            return;
        }

        if (productIndex >= 0) {
            inventory.updateProduct(productIndex, product);
        } else {
            inventory.addProduct(product);
            autoGenId += 1;
        }

        Stage stage = (Stage) save.getScene().getWindow();
        stage.close();
    }
}
