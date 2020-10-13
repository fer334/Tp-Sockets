package py.fpuna.distri;

import java.net.*;

public class TCPClient {

    public static void main(String[] args) throws Exception {
        
        Socket socket = new Socket("127.0.0.1", 4444);

        // new HiloLecturaAlServer(in).start();
        new HiloEscritura(socket).start();
        new HiloLecturaAlServer(socket).start();
	
		
    }
}
