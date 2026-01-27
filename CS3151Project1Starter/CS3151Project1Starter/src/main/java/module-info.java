module edu.westga.dsdm.knightstravails {
    requires javafx.controls;
    requires javafx.fxml;

    opens edu.westga.dsdm.knightstravails to javafx.fxml;
    exports edu.westga.dsdm.knightstravails;
    exports edu.westga.dsdm.knightstravails.view;
    opens edu.westga.dsdm.knightstravails.view to javafx.fxml;
}