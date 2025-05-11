module org.example.hospital_information_system {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires com.almasb.fxgl.all;
    requires org.apache.logging.log4j;
    requires java.sql;
    opens org.example.hospital_information_system.model to javafx.base;
    opens org.example.hospital_information_system to javafx.fxml;
    exports org.example.hospital_information_system;
}