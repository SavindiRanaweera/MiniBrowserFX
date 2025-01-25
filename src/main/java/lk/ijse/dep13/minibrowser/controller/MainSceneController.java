package lk.ijse.dep13.minibrowser.controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.web.WebView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;

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

            if(host.isBlank () || port == -1) throw new RuntimeException ("Invalid host or port");

        }catch (Exception e){
            e.printStackTrace();
        }

        if (!(protocol.equalsIgnoreCase("http") || protocol.equalsIgnoreCase("https"))) {
            throw new RuntimeException ("Unsupported protocol: " + protocol);
        }

        try {
            Socket socket = new Socket ( host , port );
            System.out.println (socket.getRemoteSocketAddress() );

            new Thread( () -> {
                try {
                    InputStream is = socket.getInputStream ();
                    InputStreamReader isr = new InputStreamReader (is);
                    BufferedReader br = new BufferedReader (isr);


                } catch (IOException e) {
                    throw new RuntimeException ( "Failed to load webpage" );
                }
            });

        } catch (IOException e) {
            throw new RuntimeException ( "Connection error" );
        }

    }
}
