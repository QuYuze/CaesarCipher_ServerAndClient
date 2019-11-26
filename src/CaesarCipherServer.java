import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;

public class CaesarCipherServer {

    public static void main(String[] args) throws IOException {

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
        outerloop:
        while ((inputLine = input.readLine()) !=null) {
            //display the input on the server
            System.out.println("Message from client: " + inputLine);
            //sent if back to the client
            output.println(inputLine);
            Random random = new Random();
            int randomKey = random.nextInt((25 - 1) + 1) + 1;
            if((inputLine.equals("Please send the key"))){

                output.println(randomKey);
                output.println("The key is " + randomKey);
                //System.out.println(randomKey);
                while((inputLine = input.readLine()) != null){
                    System.out.println("Message from client: "+inputLine);
                    String d = decrypt(inputLine, randomKey);
                    System.out.println("Decrypted Message from client: " + d);
                    if (d.equals("Bye")) {
                        break outerloop;
                    }
                }
            }

            if (inputLine.equals("Bye") || decrypt(inputLine, randomKey).equals("Bye")) {
                break;
            }
        }

        output.close();
        input.close();
        link.close();
        serverSock.close();
    }

    public static String decrypt(String plainText, int key) {
        String result = "";
        int shift = 26 - key;
        for (int i = 0; i < plainText.length(); i++) {
            if(Character.isUpperCase(plainText.charAt(i))){
                char character = (char)(((int)plainText.charAt(i) + shift - 65) % 26 + 65);
                result += character;
            }else{
                char character = (char)(((int)plainText.charAt(i) + shift - 97) % 26 + 97);
                result += character;
            }

        }
        return result;
    }
}
