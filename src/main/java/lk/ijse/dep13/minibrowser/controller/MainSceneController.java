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
        //wbDisplay.getEngine().load(url);
        System.out.println (url );

        int i;
        String protocol = null;
        String host = null;
        int port = -1;
        String path = null;

        try{
            //Identify the protocol
            if((i = url.indexOf ( "://" )) != -1){
                protocol = url.substring (0, i );
                url = url.substring (i + 3);
            }

            //Identify the host and port
            int slashIndex = url.indexOf ( "/" );
            String hostNPort = (slashIndex != -1) ? url.substring (0, slashIndex) : url;
            path = (slashIndex != -1) ? url.substring (slashIndex ) : url ;

            //Separate the host and port
            int colonIndex = hostNPort.indexOf ( ":" );
            if(colonIndex != -1){
                host = hostNPort.substring (0, colonIndex);
                try{
                    port = Integer.parseInt(hostNPort.substring(colonIndex+1));
                }catch (NumberFormatException e){
                    throw new RuntimeException ("Invalid port number");
                }
            }else {
                host = hostNPort;
                port = (protocol != null && protocol.equalsIgnoreCase ( "https" )) ?443 :
                        (protocol != null && protocol.equalsIgnoreCase ( "http" )) ? 80 :
                                (protocol == null) ? 80 : -1;
            }
            if(protocol == null) protocol = "http";

            System.out.println ("Protocol: " + protocol );
            System.out.println ("Host: " + host );
            System.out.println ("Port: " + port );
            System.out.println ("Path: " + path );

        }catch (Exception e){
            e.printStackTrace();
        }


    }
}
