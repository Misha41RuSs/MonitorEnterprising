package com.kp.monitorenterprising.Controller;

import com.kp.monitorenterprising.dao.UserDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;

public class RegisterController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private PasswordField repeatPasswordField;

    private final UserDAO userDAO = new UserDAO();

    @FXML
    void handleRegister(ActionEvent event) throws IOException {
        String username = usernameField.getText().trim();
        String password = passwordField.getText().trim();
        String repeatPassword = repeatPasswordField.getText().trim();

        if (username.isEmpty() || password.isEmpty() || repeatPassword.isEmpty()) {
            showAlert("Ошибка", "Заполните все поля.");
            return;
        }

        if (!password.equals(repeatPassword)) {
            showAlert("Ошибка", "Пароли не совпадают.");
            return;
        }

        boolean success = userDAO.register(username, password); // по умолчанию роль "Заказчик"

        if (success) {
            showAlert("Успешно", "Регистрация прошла успешно!");
            goBack(null);
        } else {
            showAlert("Ошибка", "Пользователь с таким именем уже существует.");
        }
    }

    @FXML
    void goBack(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/kp/monitorenterprising/mainWindow.fxml"));
        Stage stage = (Stage) usernameField.getScene().getWindow();
        stage.setScene(new Scene(loader.load()));
        stage.setTitle("Главное меню");
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
