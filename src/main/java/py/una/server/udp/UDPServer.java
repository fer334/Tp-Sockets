package py.una.server.udp;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;

import javax.xml.crypto.Data;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import py.una.bd.PersonaDAO;
import py.una.entidad.Cotizacion;
import py.una.entidad.CotizacionJSON;
import py.una.entidad.Estacion;
import py.una.entidad.Persona;
import py.una.entidad.PersonaJSON;

public class UDPServer {
	
	
    public static void main(String[] a){
        
        // Variables
        int puertoServidor = 9876;
        PersonaDAO pdao = new PersonaDAO();
        ArrayList<Estacion> estaciones= new ArrayList<Estacion>();
        
        try {
            //1) Creamos el socket Servidor de Datagramas (UDP)
            DatagramSocket serverSocket = new DatagramSocket(puertoServidor);
			System.out.println("Servidor Sistemas Distribuidos - UDP ");
			
            //2) buffer de datos a enviar y recibir
            byte[] receiveData = new byte[1024];
            byte[] sendData = new byte[1024];

			
            //3) Servidor siempre esperando
            while (true) {

                receiveData = new byte[1024];

                DatagramPacket receivePacket =
                        new DatagramPacket(receiveData, receiveData.length);


                System.out.println("Esperando a algun cliente... ");

                // 4) Receive LLAMADA BLOQUEANTE
                serverSocket.receive(receivePacket);
				
				System.out.println("________________________________________________");
                System.out.println("Aceptamos un paquete");

                // Datos recibidos e Identificamos quien nos envio
                String datoRecibido = new String(receivePacket.getData());
                datoRecibido = datoRecibido.trim();
                System.out.println("DatoRecibido: " + datoRecibido );

                JSONParser parser = new JSONParser();
                Object obj = parser.parse(datoRecibido.trim());
                JSONObject jsonObject = (JSONObject) obj;
                String tipo = (String)jsonObject.get("tipo");
                
                switch (tipo) {
                    case "datosEstacion":
                        Estacion e = Estacion.strToObj(datoRecibido);
                        estaciones.add(e);
                        break;
                    case "temperaturaCiudad":
                        Estacion este=null;
                        for (Estacion est : estaciones) {
                            if (est.ciudad==(String)jsonObject.get("ciudad")){
                                este = est;
                                break;
                            }
                        }
                        if(este != null){
                            sendData= Estacion.objToString(este).getBytes();
                        }

                    case "temperaturaDia":
                        Estacion elegido = null;
                        for (Estacion est : estaciones) {
                            if (est.ciudad == (String) jsonObject.get("fecha")) {
                                elegido = est;
                                break;
                            }
                        }
                        if (elegido != null) {
                            sendData = Estacion.objToString(elegido).getBytes();
                        }
                    default:
                        break;
                }

                Cotizacion c = CotizacionJSON.strToObj(datoRecibido);

                InetAddress IPAddress = receivePacket.getAddress();

                int port = receivePacket.getPort();
                
                DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);

                serverSocket.send(sendPacket);
            }

        } catch (Exception ex) {
        	ex.printStackTrace();
            System.exit(1);
        }

    }
}  

