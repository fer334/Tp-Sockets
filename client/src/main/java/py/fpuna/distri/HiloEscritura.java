package py.fpuna.distri;

import java.io.*;
import java.net.Socket;

class HiloEscritura extends Thread {

    PrintWriter out = null;
    Socket socket;

    public HiloEscritura(Socket socket) {
        super("HiloEscritura");
        this.socket = socket;

    }

    public void run() {

        try {
            // enviamos nosotros
            out = new PrintWriter(socket.getOutputStream(), true);

        } catch (IOException e) {
            System.err.println("Error de I/O en la conexion al host");
            System.exit(1);
        }

        BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
        String fromUser;
        
        try {
            while (true) {
                fromUser = stdIn.readLine();
                if (fromUser != null) {
                    System.out.println("Cliente: " + fromUser);
                    
                    //escribimos al servidor
                    out.println(fromUser);
                }
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        out.close();

    }
}