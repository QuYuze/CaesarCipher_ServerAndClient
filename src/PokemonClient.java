import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class PokemonClient {
    public static void main(String[] args) throws IOException {
        //initialize a socket object link, printer object output, bufferReader object input
        Socket link = null;
        PrintWriter output = null;
        BufferedReader input = null;

        //using the local machine ip address and the port number 50000 to create a link object,
        try{
            link= new Socket("127.0.0.1", 50000);
            //output object is an output stream from the socket
            output = new PrintWriter(link.getOutputStream(), true);
            //input object is an input stream from the socket
            input = new BufferedReader(new InputStreamReader(link.getInputStream()));
        } catch(UnknownHostException e) {
            System.out.println("Unknown Host");
            System.exit(1);
        } catch (IOException ie) {
            System.out.println("Cannot connect to host");
            System.exit(1);
        }

        //buffer reader at the client side recieves message sent by PrintWirter object at server side
        BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
        String usrInput;

        //read from keyboard
        while ((usrInput = stdIn.readLine())!=null) {
            //send to server
            output.println(usrInput);
            //recieve from the server and display it
            System.out.println("Echo from Server: " + input.readLine());
        }

        output.close();
        input.close();
        stdIn.close();
        link.close();
    }
}
