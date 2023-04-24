/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proves;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 *
 * @author User
 */

public class Client {
     private int port;
     private String host;
     private Socket client;
     private GestorUsuari gestor;

    public int getPort() {
        return port;
    }

    public String getHost() {
        return host;
    }

    public Socket getClient() {
        return client;
    }

    public GestorUsuari getGestor() {
        return gestor;
    }
       
        
        //establir connexio
        
        //entorn execucio
       
       public Client(int port, String host) throws IOException{
        this.port=port;
        this.host=host;
        client = new Socket(host, port);
        
       }
      public void connexioClient(Usuari usuari) throws IOException{
          
         ObjectOutputStream sortida = new ObjectOutputStream(client.getOutputStream());
         sortida.writeObject(usuari);
         sortida.close();
        }   
}
