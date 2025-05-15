package com.kp.monitorenterprising.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

public class QualityController {

    @FXML
    void handleLogout(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/com/kp/monitorenterprising/mainWindow.fxml")
        );
        Stage stage = (Stage)((javafx.scene.Node)event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(loader.load()));
        stage.setTitle("Главное меню");
    }

    @FXML
    void showReadyMonitors(ActionEvent event) {
        navigateTo("readyMonitors.fxml", "Готовые мониторы", event);
    }

    @FXML
    void viewGost12345678(ActionEvent event) {
        navigateTo("gost12345678.fxml", "ГОСТ 12345678", event);
    }

    @FXML
    void viewGost87654321(ActionEvent event) {
        navigateTo("gost87654321.fxml", "ГОСТ 87654321", event);
    }

    private void navigateTo(String fxml, String title, ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/com/kp/monitorenterprising/" + fxml)
            );
            Stage stage = (Stage)((javafx.scene.Node)event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(loader.load()));
            stage.setTitle(title);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /** Открывает окно «Готовые мониторы» */
    @FXML
    void openReadyMonitors(ActionEvent event) {
        try {
            URL res = getClass().getResource("/com/kp/monitorenterprising/readyMonitors.fxml");
            Stage st = new Stage();
            st.setTitle("Готовые мониторы");
            st.setScene(new Scene(FXMLLoader.load(res)));
            st.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void openGost52745(ActionEvent event) {
        openWebpage("https://spb.23met.ru/gost_files/gost-r-52745-2021.pdf");
    }

    @FXML
    void openGost16504(ActionEvent event) {
        openWebpage("https://ohranatruda.ru/upload/iblock/46b/4294851950.pdf");
    }

    private void openWebpage(String uri) {
        try {
            Desktop.getDesktop().browse(new URI(uri));
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
    }
}
