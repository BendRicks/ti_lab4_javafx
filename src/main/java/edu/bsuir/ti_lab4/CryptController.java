package edu.bsuir.ti_lab4;

import edu.bsuir.ti_lab4.crypting.CryptResult;
import edu.bsuir.ti_lab4.crypting.EDSCryptor;
import edu.bsuir.ti_lab4.crypting.EDSKey;
import edu.bsuir.ti_lab4.logic.ArithmeticOperations;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

import java.io.File;
import java.io.IOException;
import java.util.function.UnaryOperator;

public class CryptController {
    @FXML
    private RadioButton encryptButton;
    @FXML
    private TextField pValueTextField;
    @FXML
    private TextField qValueTextField;
    @FXML
    private TextField dValueTextField;
    @FXML
    private Button continueButton;
    @FXML
    private TextArea outputTextArea;

    private String kValue = "";

    @FXML
    private void initialize() {
        UnaryOperator<TextFormatter.Change> filter = change -> {
            String input = change.getText();
            if (input.matches("[0-9]*")) {
                return change;
            }
            return null;
        };
        pValueTextField.setTextFormatter(new TextFormatter<String>(filter));
        pValueTextField.textProperty().addListener(actionEvent -> checkValues());
        qValueTextField.setTextFormatter(new TextFormatter<String>(filter));
        qValueTextField.textProperty().addListener(actionEvent -> checkValues());
        dValueTextField.setTextFormatter(new TextFormatter<String>(filter));
        dValueTextField.textProperty().addListener(actionEvent -> checkValues());
        continueButton.setDisable(true);
        outputTextArea.setEditable(false);
        pValueTextField.setStyle("-fx-background-color: white; -fx-background-radius: 10;");
        qValueTextField.setStyle("-fx-background-color: white; -fx-background-radius: 10;");
        dValueTextField.setStyle("-fx-background-color: white; -fx-background-radius: 10;");
    }

    @FXML
    private void buttonClicked() {
        FileChooser loadFileChooser = new FileChooser();
        loadFileChooser.setTitle("Open file");
        File loadFile = loadFileChooser.showOpenDialog(continueButton.getScene().getWindow());
        if (loadFile != null) {
            if (encryptButton.isSelected()) {
                FileChooser saveFileChooser = new FileChooser();
                saveFileChooser.setTitle("Save file");
                File saveFile = saveFileChooser.showSaveDialog(continueButton.getScene().getWindow());
                if (saveFile != null) {
                    try {
                        CryptResult result = EDSCryptor.cryptData(loadFile, saveFile, new EDSKey(Long.parseLong(pValueTextField.getText()),
                                Long.parseLong(qValueTextField.getText()), Long.parseLong(dValueTextField.getText())));
                        outputTextArea.clear();
                        outputTextArea.appendText("Посчитанный хэш: " + result.getHashCalculated() + System.lineSeparator());
                        outputTextArea.appendText("Подпись: " + result.getKey().getEDS() + System.lineSeparator());
                        outputTextArea.appendText("Открытый ключ: (" + result.getKey().getE()+", " + result.getKey().getR() + ")" + System.lineSeparator());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } else {
                try {
                    CryptResult result = EDSCryptor.DecryptData(loadFile, new EDSKey(Long.parseLong(pValueTextField.getText()),
                            Long.parseLong(qValueTextField.getText()), Long.parseLong(dValueTextField.getText())));
                    outputTextArea.appendText("Посчитанный хэш: " + result.getHashCalculated() + System.lineSeparator());
                    outputTextArea.appendText("Хэш согласно подписи: " + result.getHashRetrieved() + System.lineSeparator());
                    outputTextArea.appendText("Валидность подписи: " + result.isValid() + System.lineSeparator());
                    outputTextArea.appendText("Подпись: " + result.getKey().getEDS() + System.lineSeparator());
                    outputTextArea.appendText("Открытый ключ: (" + result.getKey().getE()+", " + result.getKey().getR() + ")" + System.lineSeparator());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void makeNotification(boolean isSuccess, String title, String text, double delayTime) {
        Notifications newNotification = Notifications.create()
                .title(title)
                .text(text)
                .graphic(null)
                .hideAfter(Duration.millis(delayTime))
                .position(Pos.BOTTOM_RIGHT);
        if (isSuccess) {
            newNotification.showConfirm();
        } else {
            newNotification.showError();
        }
    }

    private synchronized void checkValues() {
        boolean incorrectData = false;
        long p = 0;
        long q = 0;
        long d = 0;
        if (pValueTextField.getText().isEmpty() || Long.parseLong(pValueTextField.getText()) <= 1
                || !ArithmeticOperations.isPrime(Long.parseLong(pValueTextField.getText()))) {
            pValueTextField.setStyle("-fx-background-color: white; -fx-background-radius: 10;");
            incorrectData = true;
        } else {
            pValueTextField.setStyle("-fx-background-color: lightgreen; -fx-background-radius: 10;");
            p = Long.parseLong(pValueTextField.getText());
        }
        if (qValueTextField.getText().isEmpty() || Long.parseLong(qValueTextField.getText()) <= 1
                || !ArithmeticOperations.isPrime(Long.parseLong(qValueTextField.getText()))) {
            qValueTextField.setStyle("-fx-background-color: white; -fx-background-radius: 10;");
            incorrectData = true;
        } else {
            qValueTextField.setStyle("-fx-background-color: lightgreen; -fx-background-radius: 10;");
            q = Long.parseLong(qValueTextField.getText());
        }
        if (dValueTextField.getText().isEmpty() || incorrectData) {
            dValueTextField.setStyle("-fx-background-color: white; -fx-background-radius: 10;");
            incorrectData = true;
        } else {
            d = Long.parseLong(dValueTextField.getText());
            long phiFunc = ArithmeticOperations.calcEulerFunc(p, q);
            long e = ArithmeticOperations.extendedEuclidFunc(d, phiFunc);
            if ((e * d) % phiFunc != 1 || e <= 1){
                dValueTextField.setStyle("-fx-background-color: white; -fx-background-radius: 10;");
                incorrectData = true;
            } else {
                dValueTextField.setStyle("-fx-background-color: lightgreen; -fx-background-radius: 10;");
            }
        }
        continueButton.setDisable(incorrectData);
    }


}