module com.weijin.whistdemo {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;
    requires com.almasb.fxgl.all;
    requires java.desktop;

    opens com.weijin.whistdemo to javafx.fxml;
    exports com.weijin.whistdemo;

    opens com.weijin.whistdemo.javafxComponents to javafx.fxml;
    exports com.weijin.whistdemo.javafxComponents;
    exports com.weijin.whistdemo.controllers;
    opens com.weijin.whistdemo.controllers to javafx.fxml;
    exports com.weijin.whistdemo.model;
    opens com.weijin.whistdemo.model to javafx.fxml;


}