package com.kp.monitorenterprising.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

public class EngineerController {

    @FXML
    void handleLogout(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/com/kp/monitorenterprising/mainWindow.fxml")
        );
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(loader.load()));
        stage.setTitle("Главное меню");
    }

    @FXML
    void goBack(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/com/kp/monitorenterprising/manager.fxml")
        );
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(loader.load()));
        stage.setTitle("Менеджер");
    }

    @FXML
    void openGost61747(ActionEvent event) {
        openWebpage("https://rosgosts.ru/file/gost/31/120/gost_r_mek_61747-1-1-2015.pdf");
    }

    @FXML
    void openGost81234(ActionEvent event) {
        openWebpage("https://protect.gost.ru/default.aspx/document1.aspx?control=31&baseC=6&page=0&month=3&year=-1&search=&id=266028");
    }

    private void openWebpage(String uri) {
        try {
            Desktop.getDesktop().browse(new URI(uri));
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
            // можно показать Alert об ошибке
        }
    }

    /** Открывает отдельное окно со списком мониторов «В процессе работы» */
    @FXML
    void openProductionWindow(ActionEvent event) {
        try {
            URL resource = getClass().getResource("/com/kp/monitorenterprising/productionMonitors.fxml");
            FXMLLoader loader = new FXMLLoader(resource);
            Stage stage = new Stage();
            stage.setTitle("Мониторы в процессе работы");
            stage.setScene(new Scene(loader.load()));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
