/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proves;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author User
 */
public class Servidor {
        
        
        Client client;
        
        public Servidor(Client client){
            this.client=client;
        }
        //entorno de ejecucion
      public boolean connexioServidor() throws IOException, ClassNotFoundException{
          ServerSocket serverSocket = new ServerSocket(this.client.getPort());
        Socket clientSocket = serverSocket.accept();
        ObjectInputStream ois = new ObjectInputStream(clientSocket.getInputStream());
        ArrayList<Usuari> usuarisConnectats = new ArrayList<Usuari>();
        while(ois.available()>0){
            usuarisConnectats.add((Usuari) ois.readObject());
        }
        serverSocket.close();
        if(usuarisConnectats!=null){
            return true;
        }
        return false;
      }
        
    }
    

