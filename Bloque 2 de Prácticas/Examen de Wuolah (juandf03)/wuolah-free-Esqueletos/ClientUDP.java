import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.nio.charset.StandardCharsets;

public class ClientUDP {
    public static void main(String[] args) throws IOException {
        String serverName = null;
        int serverPort = -1;

        DatagramSocket serviceSocket = null;

        // Crea socket
        try
        {
            serviceSocket = new DatagramSocket();
        }
        catch (SocketException e)
        {
            System.err.println("ERROR: No se pudo crear el socket");
            System.exit(1);
        }

        // INICIALIZA ENTRADA POR TECLADO
        BufferedReader stdIn = new BufferedReader( new InputStreamReader(System.in) );
        String userInput;
        System.out.println("Indique el número de entradas (0 para acabar): ");
        userInput = stdIn.readLine(); /*CADENA ALMACENADA EN userInput*/

        System.out.println("Introduzca el nombre de la persona: ");
        userInput += stdIn.readLine(); /*CADENA ALMACENADA EN userInput*/
        // Crear datagrama con la cadena escrito en el cuerpo
        DatagramPacket datagram = null;
        try {
            datagram = new DatagramPacket(
                            userInput.getBytes(StandardCharsets.UTF_8),               // Datos a enviar
                            userInput.getBytes(StandardCharsets.UTF_8).length,        // Tamaño de los datos
                            InetAddress.getByName(serverName),  // Dirección servidor
                            serverPort                          // Puerto servidor
            );
        } catch (UnknownHostException e) {
            System.err.println("ERROR: No se pudo resolver la dirección del servidor");
            System.exit(1);
        }

        // Envia datagrama a traves del socket
        try {
            serviceSocket.send(datagram);
        } catch (IOException iOException) {
            System.err.println("ERROR: No se pudo enviar el datagrama");
            System.exit(1);
        }

        System.out.println("STATUS: Waiting for the reply");

        // Crea e inicializa un datagrama VACIO para recibir la respuesta de máximo 900 bytes  (9x100)
        byte [] buffer = new byte[200];
        DatagramPacket receivedDatagram = new DatagramPacket(
                buffer,             // Zona de memoria donde se almacenará lo que se lea
                buffer.length       // Tamaño de dicha zona
        );

        // Recibe el datagrama de respuesta
        try {
            do{
                serviceSocket.receive(receivedDatagram);
            }while(false); // Cambiar la condición para resolver el apartado 1F
        } catch (IOException e) {
            System.err.println("ERROR: No se pudo recibir ningún datagrama");
            System.exit(1);
        }

        // Extraee el contenido del cuerpo del datagrama en variable line
        String line = new String(receivedDatagram.getData(),
                            receivedDatagram.getOffset(),
                            receivedDatagram.getLength(),
                            StandardCharsets.UTF_8
        );

        System.out.println("echo: " + line);

        System.out.println("STATUS: Closing client");

        // Cierra socket cliente
        serviceSocket.close();

        System.out.println("STATUS: closed");
    }
}
