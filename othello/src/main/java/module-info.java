module othello {
    requires javafx.controls;
    requires javafx.fxml;

    opens src.gui to javafx.fxml;
    exports src.gui;
}