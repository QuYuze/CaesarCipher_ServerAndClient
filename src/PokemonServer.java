import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Random;

public class PokemonServer {
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

            if(inputLine.equals("Play Pokemon game")){

                String[] pokemonList ={"RAYQUAZA", "PIKACHU", "MEWTWO", "GENGAR", "CHARMANDER", "SNORLAX", "DITTO",
                "GARCHOMP", "BLASTOISE", "MUDKIP", "SCIZOR", "LUXRAY", "TORTERRA", "EEVEE", "CHARIZARD",
                "BULBASAUR", "BLAZIKEN", "UMBREON", "LUCARIO", "GARDEVOIR"};

                Random random = new Random();
                int randomKey = random.nextInt((19 - 0) + 1) + 0;

                String answer = pokemonList[randomKey];
                String placeHolders = "";

                for(int i=0; i<answer.length(); i++){
                    placeHolders += "-";
                }

                output.println("The game begins!" + placeHolders);

                int completed = 0;
                while ((inputLine = input.readLine()) !=null){
                    char firstLetter = inputLine.charAt(0);
                    int index = placeHolders.indexOf("-");
                    if(answer.substring(index, answer.length()).contains(inputLine)){
                        placeHolders = placeHolders.substring(0, answer.substring(index, answer.length()).indexOf(inputLine) + index) + firstLetter
                                + placeHolders.substring(answer.substring(index, answer.length()).indexOf(inputLine) + index + 1);
                        completed ++;
                    }

                    if(completed == answer.length()){
                        output.println("You got it! The pokemon is :" + placeHolders);
                        break;
                    }else{
                        output.println(placeHolders);
                    }
                }
            }

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
