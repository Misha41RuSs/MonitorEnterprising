package com.kp.monitorenterprising.Controller;

import com.kp.monitorenterprising.Main;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class Controller {

    @FXML
    private Button aboutButton;

    @FXML
    private Button loginButton;

    @FXML
    private Button registerButton;

    @FXML
    void about(MouseEvent event) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Об авторе");
        alert.setHeaderText("Курсовой проект");
        alert.setContentText("Разработано студентом группы бИСТ-234\n" +
                "Швец Михаил Вячеславович\n" +
                "Преподаватель: С. И. Короткевич");
        alert.showAndWait();
    }

    @FXML
    void login(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("login.fxml"));
        Scene scene = new Scene(loader.load());
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Авторизация");
    }

    @FXML
    void register(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("register.fxml"));
        Scene scene = new Scene(loader.load());
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Регистрация");
    }
}
