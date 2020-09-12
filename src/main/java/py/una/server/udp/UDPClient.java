package py.una.server.udp;

import java.io.*;
import java.net.*;
import java.nio.CharBuffer;

import org.json.simple.JSONObject;

import py.una.entidad.Cotizacion;
import py.una.entidad.CotizacionJSON;
import py.una.entidad.Estacion;
import py.una.entidad.Persona;
import py.una.entidad.PersonaJSON;

class UDPClient {

    public static void main(String a[]) throws Exception {

       
        // Datos necesario
        String direccionServidor = "127.0.0.1";

        if (a.length > 0) {
            direccionServidor = a[0];
        }

        int puertoServidor = 9876;
        
        try {

            BufferedReader inFromUser =
                    new BufferedReader(new InputStreamReader(System.in));

            DatagramSocket clientSocket = new DatagramSocket();

            InetAddress IPAddress = InetAddress.getByName(direccionServidor);
            System.out.println("Intentando conectar a = " + IPAddress + ":" + puertoServidor +  " via UDP...");

            byte[] sendData = new byte[1024];
            byte[] receiveData = new byte[1024];
            String datoPaquete="";

            System.out.println("Que tipo de operacion quiere realizar:");
            System.out.println("a- Enviar datos de sensores meteorológicos");
            System.out.println("b- Consultar la temperatura en una ciudad");
            System.out.println("c- Consultar la temperatura promedio en un dia.");
            System.out.println("Seleccione una opcion: ");

			switch (inFromUser.readLine()) {
                case "a":
                //  (id_estacion, ciudad, porcentaje_humedad, temperatura, velocidad_viento, fecha, hora)
                    System.out.print("Ingrese el id de la estacion: ");
                    Long id_estacion = Long.parseLong(inFromUser.readLine());
                    System.out.print("Ingrese el nombre ciudad: ");
                    String ciudad = inFromUser.readLine();
                    System.out.print("Ingrese el porcentaje de humedad(numerico): ");
                    Long porcentaje_humedad = Long.parseLong(inFromUser.readLine());
                    System.out.print("Ingrese la temperatura(numerico): ");
                    Long temperatura = Long.parseLong(inFromUser.readLine());
                    System.out.print("Ingrese la velocidad viento(numerico): ");
                    Long velocidad_viento = Long.parseLong(inFromUser.readLine());
                    System.out.print("Ingrese la fecha: ");
                    String fecha = inFromUser.readLine();
                    System.out.print("Ingrese la hora: ");
                    String hora = inFromUser.readLine();

                    Estacion e = new Estacion(id_estacion, ciudad, porcentaje_humedad, temperatura, velocidad_viento,fecha, hora);

                    datoPaquete = Estacion.objToString(e);

                    break;
                case "b":
                    System.out.print("Ingrese el nombre ciudad: ");
                    String ciudad_consulta = inFromUser.readLine();
                    final JSONObject obj = new JSONObject();
                    obj.put("ciudad", ciudad_consulta);
                    obj.put("tipo", "temperaturaCiudad");
                    datoPaquete = obj.toJSONString();
                    
                    break;
                case "c":
                    System.out.print("Ingrese una fecha para obtener el promedio de ese dia: ");
                    String fecha_consulta = inFromUser.readLine();
                    final JSONObject obj1 = new JSONObject();
                    obj1.put("fecha", fecha_consulta);
                    obj1.put("tipo", "temperaturaDia");
                    datoPaquete = obj1.toJSONString();
                    break;
            
                default:
                    break;
            }

            sendData = datoPaquete.getBytes();

            System.out.println("Enviar " + datoPaquete + " al servidor. ("+ sendData.length + " bytes)");
            DatagramPacket sendPacket =
                    new DatagramPacket(sendData, sendData.length, IPAddress, puertoServidor);

            clientSocket.send(sendPacket);

            DatagramPacket receivePacket =
                    new DatagramPacket(receiveData, receiveData.length);

            System.out.println("Esperamos si viene la respuesta.");

            //Vamos a hacer una llamada BLOQUEANTE entonces establecemos un timeout maximo de espera
            clientSocket.setSoTimeout(10000);

            try {
                // ESPERAMOS LA RESPUESTA, BLOQUENTE
                clientSocket.receive(receivePacket);
                System.out.println("servidor: recibido ok!!!");

                // String respuesta = new String(receivePacket.getData());
                // Cotizacion c = CotizacionJSON.strToObj(respuesta.trim());
                
                // InetAddress returnIPAddress = receivePacket.getAddress();
                // int port = receivePacket.getPort();

                // System.out.println("Respuesta desde =  " + returnIPAddress + ":" + port);
                // System.out.println("Asignaturas: ");
                
                // for(String tmp: presp.getAsignaturas()) {
                // 	System.out.println(" -> " +tmp);
                // }
                

            } catch (SocketTimeoutException ste) {

                System.out.println("TimeOut: El paquete udp se asume perdido.");
            }
            clientSocket.close();
        } catch (UnknownHostException ex) {
            System.err.println(ex);
        } catch (IOException ex) {
            System.err.println(ex);
        }
        
    }
} 

