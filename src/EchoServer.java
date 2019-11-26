import java.net.*;
import java.io.*;
public class EchoServer{
    public static void main(String[] args) throws IOException{

        //create a server socket object
        ServerSocket serverSock = null;

        //initiate the ServerSocket object on a port
        try{
            serverSock = new ServerSocket(50000);

            //in case that the connection is unsuccessful
        } catch (IOException ie) {
            System.out.println("Can't listen on 50000");
            System.exit(1);
        }

        //set a client socket, waiting the client to connect to our server
        Socket link = null;
        System.out.println("Listening for connection ...");

        //if the client sends a request, we accept the connection
        try {
            link = serverSock.accept();
        } catch (IOException ie) {
            System.out.println("Accept failed");
            System.exit(1);
        }
        //if the connection is successful, disaply the message that the sever is listsning for input
        System.out.println("Connection successful");
        System.out.println("Listening for input...");

        //wrap the outputStream object with printWriter object and the InputStream object with a buffer reader object
        PrintWriter output = new PrintWriter(link.getOutputStream(),true);
        BufferedReader input = new BufferedReader(new InputStreamReader(link.getInputStream()));
        String inputLine;

        //read a line from the client
        while ((inputLine = input.readLine()) !=null) {
            //display the input on the server
            System.out.println("Message from client: " + inputLine);
            //sent if back to the client
            output.println(inputLine);
            if (inputLine.equals("Bye")) {
                break;
            }
        }

        output.close();
        input.close();
        link.close();
        serverSock.close();
    }
}
