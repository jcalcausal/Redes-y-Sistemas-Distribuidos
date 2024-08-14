import java.io.IOException;
import java.net.*;
import java.nio.charset.StandardCharsets;

public class ServerUDP {

    public static String reserva(String s){
        String res = "";
        String [] ERRORES = {"Entradas agotadas", "Error del sistema", "Inténtelo más tarde", "Error en la petición"};
        double random = Math.random();
        if (random < 0.8)
            res = "Reserva exitosa";
        else res = "Reserva fallida: " + ERRORES[new Random().nextInt(ERRORES.length)];

        return res;
    }

    public static void main(String[] args)
    {
        Integer port = null;
        // SOCKET
        DatagramSocket server = null;

        // Entradas por cliente
        Map<String, Integer> entradas = new HashMap<String, Integer>();

        // Crea e inicaliza el socket del servidor
        try {
            server = new DatagramSocket();
        } catch (SocketException e) {
            System.out.println("Could not listen on port "+port);
            System.out.println(e.getMessage());
            System.exit(-1);
        }

        // Funcion PRINCIPAL del servidor
        while (true)
        {
            System.out.println("Waiting for a new UDP client");

            // Crea e inicializa un datagrama VACIO para recibir la respuesta de máximo 100 bytes
            byte [] buffer = new byte[200];
            DatagramPacket receivedDatagram = new DatagramPacket(
                    buffer,             // Zona de memoria donde se almacenará lo que se lea
                    buffer.length       // Tamaño de dicha zona
            );

		    // Recibe datagrama
            try {
                server.receive(receivedDatagram);
            } catch (IOException e) {
                e.printStackTrace();
            }

            // Obtiene el texto recibido
            String line = new String(receivedDatagram.getData(),
                    receivedDatagram.getOffset(),
                    receivedDatagram.getLength(),
                    StandardCharsets.UTF_8
            );

            // Muestra por pantalla la direccion socket (IP y puerto) del cliente y su texto
            System.out.println("Message from client " + receivedDatagram.getAddress() + ":" + receivedDatagram.getPort() + " > " + line);

            String client = receivedDatagram.getAddress() + ":" + receivedDatagram.getPort();
            Integer peticiones = entradas.get(client);
            Integer nuevas_peticiones = Character.getNumericValue(line.charAt(0));
            if(peticiones == null){
                peticiones = nuevas_peticiones;
            } else {
                peticiones += nuevas_peticiones;
            }
            entradas.put(client, peticiones);

            // Multiplicamos la línea
            // Si el formato es incorrecto la función devuelve "ERROR"
            line = multiply(line);

            System.out.println("STATUS: Reply sent -> " + line);
        } // Fin del bucle del servicio
    } 
    
}
