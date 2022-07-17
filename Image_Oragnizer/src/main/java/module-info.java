module com.example.image_oragnizer {
    requires javafx.controls;
    requires javafx.fxml;
    requires metadata.extractor;
    requires org.apache.commons.io;
    requires java.desktop;


    opens com.example.image_oragnizer to javafx.fxml;
    exports com.example.image_oragnizer;
}