package controller;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;

import model.InHouse;
import model.Inventory;
import model.Outsourced;
import model.Part;
import view.PopUpDialog;

import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

/**
 * A controller for application add/modify part functionality.
 * @author Brandon McCleary
 */
public class PartController implements Initializable {
    private int partIndex;
    private Inventory inventory;
    private boolean isOutsourced;
    private final String inHouseTypeIdLabelText = "Machine ID";
    private final String outsourcedTypeIdLabelText = "Company Name";
    private final String modifyPartLabelText = "Modify Part";
    public static int autoGenId;
    public Label typeIdLabel;
    public Label formLabel;
    public RadioButton inHouse;
    public RadioButton outsourced;
    public TextField partId;
    public TextField name;
    public TextField stock;
    public TextField price;
    public TextField min;
    public TextField max;
    public TextField typeId;
    public Button save;
    public Button cancel;
    public ToggleGroup partType;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        isOutsourced = false;
    }

    /**
     * @param inventory the inventory to set
     */
    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    /**
     * Set attributes for InHouse parts.
     * @param actionEvent the toggle event
     */
    public void onInHouse(ActionEvent actionEvent) {
        isOutsourced = false;
        typeIdLabel.setText(inHouseTypeIdLabelText);
        typeId.setText("");
    }

    /**
     * Set attributes for Outsourced parts.
     * @param actionEvent the toggle event
     */
    public void onOutsourced(ActionEvent actionEvent) {
        isOutsourced = true;
        typeIdLabel.setText(outsourcedTypeIdLabelText);
        typeId.setText("");
    }

    /**
     * Update the form with existing part attributes.
     * @param part the selected part
     * @param index the index of the part
     */
    public void setPartAttributes(Part part, int index) {
        this.partIndex = index;
        formLabel.setText(modifyPartLabelText);
        partId.setText(String.valueOf(part.getId()));
        name.setText(part.getName());
        stock.setText(String.valueOf(part.getStock()));
        price.setText(String.valueOf(part.getPrice()));
        min.setText(String.valueOf(part.getMin()));
        max.setText(String.valueOf(part.getMax()));

        if (part instanceof InHouse) {
            inHouse.setSelected(true);
            typeIdLabel.setText(inHouseTypeIdLabelText);
            typeId.setText(String.valueOf(((InHouse) part).getMachinedId()));
        }
        else {
            isOutsourced = true;
            outsourced.setSelected(true);
            typeIdLabel.setText(outsourcedTypeIdLabelText);
            typeId.setText(String.valueOf(((Outsourced) part).getCompanyName()));
        }
    }

    /**
     * Save user input to new or existing part.
     * @param actionEvent the click event
     */
    public void onSave(ActionEvent actionEvent) {
        String nameInput = name.getText();
        String stockInput = stock.getText();
        String priceInput = price.getText();
        String minInput = min.getText();
        String maxInput = max.getText();
        String typeIdInput = typeId.getText();
        List<String> inputs = Arrays.asList(nameInput, stockInput, priceInput, minInput, maxInput, typeIdInput);

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

        // Confirm that machine ID is valid
        if (!isOutsourced) {
            try {
                Integer.parseInt(typeIdInput);
            }
            catch (NumberFormatException ex) {
                PopUpDialog.invalidMachineId();
                return;
            }
        }

        // Save part
        if (partIndex >= 0) {
            if (isOutsourced) {
                Outsourced part = new Outsourced(Integer.parseInt(partId.getText()), nameInput, doublePrice, intStock,
                        intMin, intMax, typeIdInput);
                inventory.updatePart(partIndex, part);
            } else {
                InHouse part = new InHouse(Integer.parseInt(partId.getText()), nameInput, doublePrice, intStock, intMin,
                        intMax, Integer.parseInt(typeIdInput));
                inventory.updatePart(partIndex, part);
            }

        } else {
            if (isOutsourced) {
                Outsourced part = new Outsourced(autoGenId, nameInput, doublePrice, intStock, intMin, intMax,
                        typeIdInput);
                inventory.addPart(part);
            } else {
                InHouse part = new InHouse(autoGenId, nameInput, doublePrice, intStock, intMin, intMax,
                        Integer.parseInt(typeIdInput));
                inventory.addPart(part);
            }
            autoGenId += 1;
        }
        Stage stage = (Stage) save.getScene().getWindow();
        stage.close();
    }

    /**
     * Close the add/modify part window.
     * @param actionEvent the click event
     */
    public void onCancel(ActionEvent actionEvent) {
        Stage stage = (Stage) cancel.getScene().getWindow();
        stage.close();
    }
}