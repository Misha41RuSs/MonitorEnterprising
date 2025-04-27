package com.kp.monitorenterprising.Controller;

import com.kp.monitorenterprising.dao.UserDAO;
import com.kp.monitorenterprising.model.User;
import com.kp.monitorenterprising.util.SessionManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {

    @FXML
    private TextField loginField;

    @FXML
    private PasswordField passwordField;

    private final UserDAO userDAO = new UserDAO();

    @FXML
    void handleLogin(ActionEvent event) {
        String username = loginField.getText().trim();
        String password = passwordField.getText().trim();

        if (username.isEmpty() || password.isEmpty()) {
            showAlert("Ошибка", "Пожалуйста, введите логин и пароль.");
            return;
        }

        User user = userDAO.login(username, password);

        if (user == null) {
            showAlert("Ошибка", "Неверный логин или пароль.");
        } else {
            SessionManager.setCurrentUser(user); // ✅ сохраняем юзера

            try {
                String fxmlFile = user.getRole().equalsIgnoreCase("Администратор") ? "admin.fxml" : "client.fxml";
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/kp/monitorenterprising/" + fxmlFile));
                Stage stage = (Stage) loginField.getScene().getWindow();
                stage.setScene(new Scene(loader.load()));
                stage.setTitle("Добро пожаловать, " + user.getUsername());
            } catch (IOException e) {
                showAlert("Ошибка", "Не удалось загрузить интерфейс.");
                e.printStackTrace();
            }
        }
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    @FXML
    void goBack(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/kp/monitorenterprising/mainWindow.fxml"));
        Stage stage = (Stage) loginField.getScene().getWindow();
        stage.setScene(new Scene(loader.load()));
        stage.setTitle("Главное меню");
    }
}
