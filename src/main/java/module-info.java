module org.example.module_10 {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.example.module_10 to javafx.fxml;
    exports org.example.module_10;
}