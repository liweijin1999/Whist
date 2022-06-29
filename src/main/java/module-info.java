module com.weijin.whistdemo {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;
    requires com.almasb.fxgl.all;
    requires java.desktop;

    opens com.weijin.whistdemo to javafx.fxml;
    exports com.weijin.whistdemo;
}