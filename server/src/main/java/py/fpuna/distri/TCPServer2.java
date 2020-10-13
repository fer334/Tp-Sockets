package py.fpuna.distri;

import java.net.*;
import java.io.*;

public class TCPServer2 {

    public static void main(String[] args) throws IOException {

        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(4444);
        } catch (IOException e) {
            System.err.println("No se puede abrir el puerto: 4444.");
            System.exit(1);
        }
        System.out.println("Puerto abierto: 4444.");
        Socket clientSocket1= null;
        Socket clientSocket2 = null;
        try {
            clientSocket1 = serverSocket.accept();
            System.out.println("Acceptado1");
            clientSocket2 = serverSocket.accept();
            System.out.println("Acceptado2");
        } catch (IOException e) {
            System.err.println("Fallo el accept().");
            System.exit(1);
        }

        PrintWriter out1 = new PrintWriter(clientSocket1.getOutputStream(), true);
        PrintWriter out2 = new PrintWriter(clientSocket2.getOutputStream(), true);
     //   BufferedReader in1 = new BufferedReader(
       //     new InputStreamReader(clientSocket1.getInputStream())
        //);
        //BufferedReader in2 = new BufferedReader(
         //   new InputStreamReader(clientSocket2.getInputStream())
        //);
        DataInputStream in2 = new DataInputStream(clientSocket2.getInputStream());
        DataInputStream in1 = new DataInputStream(clientSocket1.getInputStream());

        out1.println("Bienvenido!");
        out2.println("Bienvenido!");
        String inputLine1, inputLine2, outputLine1, outputLine2;
        BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));

        while (true) {
            inputLine2 = in2.readUTF();
            //inputLine2 = stdIn.readLine();
            System.out.println("Mensaje recibido de 2: " + inputLine2);
            
            outputLine1 = "El dos te envio: " + inputLine2;
            out1.println(outputLine1);

            inputLine1 = in1.readUTF();
//            inputLine1 = stdIn.readLine();
            System.out.println("Mensaje recibido de 1: " + inputLine1);
            outputLine2 = "El uno te envio: " + inputLine1;
            out2.println(outputLine2);
        }
    }
}
