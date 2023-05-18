package controllers_pop_settings;

import database.DatabaseConnector;
import database.QExecutor;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import other.PasswordHash;
import validate.ValidateEmployee;

import java.io.IOException;

public class EditPasswordInSettingsController {

    @FXML
    private Button cancel2Button;

    @FXML
    private Button cancelButton;
    @FXML
    private Button continueButton;
    @FXML
    private GridPane passwordGrid;
    @FXML
    private TextField passwordActualField;
    @FXML
    private TextField passwordNewField;
    @FXML
    private TextField passwordRepeatField;
    @FXML
    private Button saveButton;
    @FXML
    private TextField tokenField;
    @FXML
    private GridPane tokenGrid;
//    @FXML
//    private Label wrongLabel;
    @FXML
    private Label wrongTokenLabel;

    @FXML
    public void initialize() {
        tokenGrid.toFront();
    }

    //To jest do obsługi buttonów
    public void buttonsHandler(ActionEvent event) throws IOException {
        Object source = event.getSource();

        if (source == continueButton) {
            //Sprawdzenie tokenu
            try {
                ValidateEmployee.goodToken(tokenField.getText());
            } catch (Exception e) {
                wrongTokenLabel.setText(e.getMessage());
            }
            passwordGrid.toFront();

        } else if (source == saveButton) {
            resetPassword();
        } else if (source == cancel2Button) {
            //Zamykanie okienka
            Stage stage = (Stage) cancel2Button.getScene().getWindow();
            stage.close();
        } else if (source == cancelButton) {
            //Zamykanie okienka
            Stage stage = (Stage) cancelButton.getScene().getWindow();
            stage.close();

        }
    }

    private void resetPassword() {
        //Zmiana hasła
        String newPassword = passwordNewField.getText();
        String repeatNewPassword = passwordRepeatField.getText();

        try {
            ValidateEmployee.samePassword(newPassword, repeatNewPassword);
            String hashedPassword = PasswordHash.hashedPassword(newPassword);

            DatabaseConnector.connect();
            QExecutor.executeQuery("UPDATE login SET password='" + hashedPassword +
                    "' WHERE token='" + tokenField.getText() + "'");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}