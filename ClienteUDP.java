package AppSocketsUDPHora;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ClienteUDP {
    
    public static void main(String[] args) throws IOException{
        String mensaje = new String("Dame la hora local");
        String servidor = new String("localhost");
        int PUERTO = 10000;
        int ESPERA = 6000;

        DatagramSocket socketUDP = new DatagramSocket();
        InetAddress hostServer = InetAddress.getByName(servidor);

        DatagramPacket peticion = new DatagramPacket(mensaje.getBytes(), mensaje.getBytes().length, hostServer, PUERTO);
        socketUDP.setSoTimeout(ESPERA);
        System.out.println("Esperamos datos en un maximo de " + ESPERA + " milisegundos...");
        socketUDP.send(peticion);

        try {
            byte[] buffer = new byte[1024];
            DatagramPacket respuesta = new DatagramPacket(buffer, buffer.length);
            socketUDP.receive(respuesta);
            String text = new String(respuesta.getData(), 0, respuesta.getLength());


            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            LocalDateTime horaServidor = LocalDateTime.parse(text, formatter);
            LocalDateTime now = LocalDateTime.now();
            Duration diferencia = Duration.between(horaServidor, now);
            System.out.println("Hora del servidor es :" +  horaServidor.format(formatter));            
            System.out.println("Hora del cleinte es :" +  now.format(formatter));            
            System.out.println("La diferencia entre la dos horas es de : " + diferencia.getSeconds() + " segundos");
            socketUDP.close();
        } catch (Exception e) {
            System.out.println("Se espero el tiempo maximo");
        }

    }

}
