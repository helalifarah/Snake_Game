module com.helali.javafxdemo {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires com.almasb.fxgl.all;

    // Add the following line to include the necessary AWT module for java.desktop
    requires java.desktop;

    opens com.helali.snake to javafx.fxml;
    exports com.helali.snake;
}
