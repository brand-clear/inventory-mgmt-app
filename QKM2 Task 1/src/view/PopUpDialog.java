package view;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Optional;

/**
 * Contains methods for all application popup dialogs.
 * @author Brandon McCleary
 */
public class PopUpDialog {

    public static void emptyFields() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setHeaderText("Missing required inputs.");
        alert.setContentText("Add inputs and try again.");
        alert.showAndWait();
    }

    public static void invalidCounts() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setHeaderText("There is an inconsistency in the stock counts.");
        alert.setContentText("Adjust the stock counts and try again.");
        alert.showAndWait();
    }

    public static void invalidQuantityTypes() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setHeaderText("Quantities must be expressed in the appropriate data types.");
        alert.setContentText("Adjust the quantity data type(s) and try again.");
        alert.showAndWait();
    }

    public static void invalidMachineId() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setHeaderText("The machine ID must be represented as an integer.");
        alert.setContentText("Correct the machine ID and try again.");
        alert.showAndWait();
    }

    public static void notDeleted() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setHeaderText("Products with associated parts cannot be deleted.");
        alert.setContentText("Delete associated parts and try again.");
        alert.showAndWait();
    }

    public static void notSelected() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setHeaderText("No item selected.");
        alert.setContentText("Select an item and try again.");
        alert.showAndWait();
    }

    public static void notFound() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setHeaderText("No matching items were found.");
        alert.setContentText("Verify search criteria and try again.");
        alert.showAndWait();
    }

    public static boolean confirmRemoval() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("Are you sure you want to remove this item?");
        alert.setContentText("This action cannot be undone.");
        Optional<ButtonType> result = alert.showAndWait();
        return result.get() == ButtonType.OK;
    }

    public static void noAssociatedParts() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setHeaderText("Products must have at least 1 associated part.");
        alert.setContentText("Add associated part(s) and try again.");
        alert.showAndWait();
    }
}
