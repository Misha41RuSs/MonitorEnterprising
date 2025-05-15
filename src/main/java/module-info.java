module com.kp.monitorenterprising {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires telegrambots;
    requires telegrambots.meta;
    requires java.desktop;

    exports com.kp.monitorenterprising;
    exports com.kp.monitorenterprising.Controller;

    opens com.kp.monitorenterprising.Controller to javafx.fxml;
    opens com.kp.monitorenterprising.model to javafx.base;
    exports com.kp.monitorenterprising.dao;
    opens com.kp.monitorenterprising.dao to javafx.fxml; // 👈 вот это добавь
}
