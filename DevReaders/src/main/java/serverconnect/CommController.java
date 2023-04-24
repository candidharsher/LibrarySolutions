package serverconnect;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.concurrent.CountDownLatch;

import com.google.gson.Gson;
import java.io.OutputStream;
import model.Usuari;


/**
 * Communication specific class
 * @author professor
 */

public class CommController {
public static final String UPDATE_USER = "UPDATE_USER";

    public static int BAD_VALUE = -1;
    private static int sessionCode=BAD_VALUE;
    private static int port=BAD_VALUE;
    private static String serverName="kandula.db.elephantsql.com";

    public static final int OK_RETURN_CODE = 0;

    // Names of the requests

    public static final String LOGIN = "LOGIN";
    public static final String LOGOUT = "LOGOUT";
    public static final String LIST_USERS = "LIST_USERS";
    public static final String ADD_USER = "ADD_USER";
    private static Object user;


    //opens a connection to the server
    private static Socket connect() {
        Socket s;
        if(port==BAD_VALUE || serverName == null){
            return null;
        }else {
            try {
                s= new Socket();
                s.connect(new InetSocketAddress(serverName,port),4000);

                return s;
            } catch (IOException ex) {
                return null;
            }
        }
    }

    /**
     * returns true if client is logged and false otherwhise
     * @return
     */
    public static boolean isLogged(){
        return sessionCode!=BAD_VALUE;
    }

    /**
     * Set the value of port
     *
     * @param port new value of port
     */
    public static void setPort(int port) {
        CommController.port = port;
    }

    /**
     * Set the value of serverName
     *
     * @param serverName new value of serverName
     */
    public static void setServerName(String serverName) {
        CommController.serverName = serverName;
    }

    /**
     * Makes a login request to the server
     * @param user  username
     * @param password password
     * @return result code
     */
    public static int doLogin(String user, String password){

        EndPointValues login = new EndPointValues(LOGIN);
        login.addPrimitiveData(user);
        login.addPrimitiveData(password);
        ReturnValues ret=talkToServer(login);

        if(ret==null) return BAD_VALUE;

        int returnCode=ret.getReturnCode();

        if(returnCode==OK_RETURN_CODE){
            sessionCode=(Integer) ret.getData(0, Integer.class);
        }

        return ret.getReturnCode();
    }

    /**
     * Makes a logout request to the server
     * @return result code
     */
    public static int doLogout(){

        if(sessionCode==BAD_VALUE) return OK_RETURN_CODE;

        EndPointValues logout = new EndPointValues(LOGOUT);

        logout.addPrimitiveData(sessionCode);

        ReturnValues ret=talkToServer(logout);

        if(ret==null) return BAD_VALUE;

        int code= ret.getReturnCode();

        if(code==OK_RETURN_CODE){
            sessionCode=BAD_VALUE;
        }

        return code;

    }

    /**
     * Makes a "list user" request to the server
     * @return result users array; null if error.
     */
    public static Usuari[] doListUsers(){

        if(sessionCode==BAD_VALUE) return null;

        EndPointValues listUsers = new EndPointValues(LIST_USERS);

        listUsers.addPrimitiveData(sessionCode);

        ReturnValues ret=talkToServer(listUsers);

        if(ret==null) return null;

        int returnCode=ret.getReturnCode();

        if(returnCode!=OK_RETURN_CODE) return null;

        String jsonData=ret.getData(0, String.class);

        Usuari[] users = new Gson().fromJson(jsonData, Usuari[].class);

        return users;
    }
    /**
     * Makes an "add user" request to the server
     * @param user  user to be added
     * @return result code
     */
    public static int doAddUser(Usuari user){

        EndPointValues addUser = new EndPointValues(ADD_USER);
        addUser.addPrimitiveData(sessionCode);
        addUser.addDataObject(user);

        ReturnValues ret=talkToServer(addUser);

        if(ret==null) return BAD_VALUE;

        return ret.getReturnCode();

    }

    /**
     * Makes a "query user" request to the server
     * @param username  username to search
     * @return result user with this username; null if error
     */
    public static Usuari doQueryUser(int username){
        if(username==1) { // simulates non-existent key
            return null;
        }else{
            return new Usuari(username,"Mock value", "Mock value");
        }
    }

    // Sends message to the server and returns its response.
    // messages are serialized as Json values before and after communication
    private static ReturnValues talkToServer(EndPointValues message){
        try {
            Socket socket = connect();

            Gson gson= new Gson();

            if(socket==null) return null;

            PrintWriter output = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);


            output.println(gson.toJson(message));

            BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            String data = input.readLine();

            ReturnValues ret=gson.fromJson(data,ReturnValues.class);

            socket.close();

            return ret;

        } catch (IOException ex) {
            return null;}
        }

/**
 * Makes an "update user" request to the server
 * @param user  user to be updated
 * @return result code
 */
public static int doUpdateUser(Usuari user){

    EndPointValues updateUser = new EndPointValues(UPDATE_USER);
    updateUser.addPrimitiveData(sessionCode);
    updateUser.addDataObject(user);

    ReturnValues ret=talkToServerACT(updateUser);

    if(ret==null) return BAD_VALUE;

    return ret.getReturnCode();

}

/**
 * Makes a "query user" request to the server
 * @param username  username to search
 * @return result user with this username; null if error
 */
public static String doQueryUserUpdate(String username){
    String QUERY_USER="QUERY_USER";
    String UPDATE_USER_ERROR="UPDATE_USER_ERROR";
        EndPointValues queryUser = new EndPointValues(QUERY_USER);
        queryUser.addPrimitiveData(sessionCode);
        queryUser.addPrimitiveData(username);

        ReturnValues ret=talkToServerACT(queryUser);

        if(ret==null) return null;

        int returnCode=ret.getReturnCode();

        if(returnCode!=OK_RETURN_CODE) return null;

        String jsonData=ret.getData(0, String.class);

        Usuari user = new Gson().fromJson(jsonData, Usuari.class);
EndPointValues updateUser = new EndPointValues(UPDATE_USER);
        updateUser.addPrimitiveData(sessionCode);
        updateUser.addDataObject(user);

        ReturnValues ret1 = talkToServer(updateUser);
    
        

        if(ret == null) {
            return UPDATE_USER_ERROR;
        }

        int returnCode1 = ret1.getReturnCode();

        if(returnCode == OK_RETURN_CODE) {
            // Send update to other instances
            propagateUpdateToOtherInstances(user);
            return user.toString();
        } else {
            return UPDATE_USER_ERROR;
        }
        
    
}

    

public static ReturnValues talkToServerACT(EndPointValues message){
    try {
        Socket socket = connect();

        Gson gson= new Gson();

        if(socket==null) return null;

        PrintWriter output = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);

        output.println(gson.toJson(message));

        BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        String response = input.readLine();

        ReturnValues ret = gson.fromJson(response, ReturnValues.class);

        socket.close();

        return ret;

    } catch (IOException ex) {
        return null;
    }
}
private static void propagateUpdateToOtherInstances(Usuari user) {
    Gson gson = new Gson();
    String jsonUser = gson.toJson(user);

    // Open a new socket connection for each server instance
    String[] serverInstances = {"kandula.db.elephantsql.com"};
    int[] serverPorts = {5432};

    CountDownLatch latch = new CountDownLatch(serverInstances.length);

    for (int i = 0; i < serverInstances.length; i++) {
        try (Socket socket = new Socket(serverInstances[i], serverPorts[i])) {
            OutputStream outputStream = socket.getOutputStream();
            PrintWriter writer = new PrintWriter(outputStream, true);
            writer.println(jsonUser);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            latch.countDown();
        }
    }

    // Wait until all connections have been closed
    try {
        latch.await();
    } catch (InterruptedException e) {
        e.printStackTrace();
    }
}

    public static int getBAD_VALUE() {
        return BAD_VALUE;
    }

    public static void setBAD_VALUE(int BAD_VALUE) {
        CommController.BAD_VALUE = BAD_VALUE;
    }

    public static int getSessionCode() {
        return sessionCode;
    }

    public static void setSessionCode(int sessionCode) {
        CommController.sessionCode = sessionCode;
    }

    public static Object getUser() {
        return user;
    }

    public static void setUser(Object user) {
        CommController.user = user;
    }

}
    // ...
