package AppSocketsUDPHora;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ServidorUDP {
    public static void main(String[] args) throws SocketException, IOException{
        int PUERTO = 10000;
        int RETARDO = 3000;

        try (DatagramSocket socketUDP = new DatagramSocket(PUERTO)){
            byte[] buffer = new byte[1024];
            System.out.println("Servidor UDP escuchando en el puertos :" + PUERTO);
            while (true) {
                DatagramPacket peticion = new DatagramPacket(buffer, buffer.length);
                socketUDP.receive(peticion);
                System.out.println("Datagrama recivido del host :" + peticion.getAddress());
                System.out.println("Desde el puerto : " + peticion.getPort());
                System.out.println("Datos recibidos :" + new String(peticion.getData()));

                LocalDateTime now = LocalDateTime.now();
                DateTimeFormatter formatter =  DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                String fecha = now.format(formatter);
                System.out.println("Hora del servidor :" + fecha);
                try {                    
                    System.out.println("Simulando un retardo en milisegundos : " + RETARDO);
                    Thread.sleep(RETARDO);
                } catch (Exception e) { }
                DatagramPacket respuesta = new DatagramPacket(fecha.getBytes(), fecha.getBytes().length, peticion.getAddress(), peticion.getPort());
                socketUDP.send(respuesta);
                System.out.println("Datos enviado al cliente");
            }
        } catch (Exception e) { }

    }

}

