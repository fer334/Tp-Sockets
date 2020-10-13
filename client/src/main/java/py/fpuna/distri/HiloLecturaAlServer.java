package py.fpuna.distri;

import java.io.*;
import java.net.Socket;

class HiloLecturaAlServer extends Thread {

    BufferedReader in = null;
    Socket socket = null;

    public HiloLecturaAlServer(Socket socket) {
        super("HiloLecturaAlServer");
        this.socket = socket;
    }

    public void run() {
        String fromServer;
        try {
            in = new BufferedReader(
                new InputStreamReader(socket.getInputStream())
            );

            while (true) {
                fromServer = in.readLine();
                
                System.out.println("Servidor: " + fromServer);
                if (fromServer.equals("Bye")) {
                    break;
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}