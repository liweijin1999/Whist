module com.weijin.whist {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;
    requires com.almasb.fxgl.all;
    requires java.desktop;
    requires java.scripting;

    opens com.weijin.whist to javafx.fxml;
    exports com.weijin.whist;

    opens com.weijin.whist.javafxComponents to javafx.fxml;
    exports com.weijin.whist.javafxComponents;
    exports com.weijin.whist.controllers;
    opens com.weijin.whist.controllers to javafx.fxml;
    exports com.weijin.whist.model;
    opens com.weijin.whist.model to javafx.fxml;


}