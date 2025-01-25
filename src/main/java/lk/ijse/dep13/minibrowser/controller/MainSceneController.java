package lk.ijse.dep13.minibrowser.controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.web.WebView;

public class MainSceneController {

    public AnchorPane root;
    public TextField txtAddress;
    public WebView wbDisplay;

    public void initialize() {
        txtAddress.setText ( "https://www.google.com" );
        loadWebPage ( txtAddress.getText() );
        txtAddress.focusedProperty ().addListener((observable, oldValue, newValue) -> {
            System.out.println (newValue );
            if(newValue) Platform.runLater ( txtAddress::selectAll );
        });
    }

    public void txtAddressOnAction(ActionEvent event) {
        String url = txtAddress.getText();
        if(url.isBlank ()) return;
        loadWebPage ( txtAddress.getText() );
    }

    private void loadWebPage(String url) {
        wbDisplay.getEngine().load(url);
    }
}
