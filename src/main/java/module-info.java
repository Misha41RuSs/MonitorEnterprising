module com.kp.monitorenterprising {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires telegrambots;
    requires telegrambots.meta;

    exports com.kp.monitorenterprising;
    exports com.kp.monitorenterprising.Controller;

    opens com.kp.monitorenterprising.Controller to javafx.fxml;
    opens com.kp.monitorenterprising.model to javafx.base; // 👈 вот это добавь
}
