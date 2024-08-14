import java.io.*;
import java.net.Socket;


public class ClientTCP {

    public static void main(String[] args) throws IOException {
        // DATOS DEL SERVIDOR:
        String serverName = null;
        Integer serverPort = null;

        // SOCKET
        Socket serviceSocket = null;

        // FLUJOS PARA EL ENVÍO Y RECEPCIÓN
	    PrintWriter out = null;
        BufferedReader in = null;

        // Crea socket y conecta con servidor
        try {
            serviceSocket = new Socket(serverName, serverPort);
        } catch (IOException e) {
            System.err.println("ERROR: cannot connect to " + serverName + ":" + serverPort);
            System.exit(1);
        }

        // Inicializar los flujos de entrada/salida del socket conectado en las variables PrintWriter y BufferedReader
        try {
            out = new PrintWriter( serviceSocket.getOutputStream(), true);
            in = new BufferedReader( new InputStreamReader(serviceSocket.getInputStream()) );
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for " + serverName);
            System.exit(1);
        }

        System.out.println("STATUS: Conectado al servidor ");

        // Obtener texto por teclado
        BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
        String userInput;

        try {
            System.out.println("Numero de entradas: ");
            userInput = stdIn.readLine(); /*CADENA ALMACENADA EN userInput*/
            System.out.println("Nombre de la persona al que van las entradas: ");
            userInput += stdIn.readLine(); /*CADENA ALMACENADA EN userInput*/

            // Envia texto en userInput al servidor a través del flujo de salida del socket conectado
            out.println(userInput);

            System.out.println("STATUS: Enviando " + userInput); // muestra por pantalla el texto enviado
            System.out.println("STATUS: Esperando la respuesta"); // muestra por pantalla estado

            //* COMPLETAR: Recibir texto enviado por el servidor a través del flujo de entrada del socket conectado
            String line = null;
            line = in.readLine();

            System.out.println("Respuesta recibida: " + line); // muestra por pantalla el eco recibido
        }catch (IOException e){
            System.out.println("ERROR: Recepción/envío: "+ e.getMessage());
        }

        // Salimos
        System.out.println("STATUS: Cerrando conexión");
        System.out.print("STATUS: Closing ...");
        //* COMPLETAR Cerrar flujos y socket
        out.close();
        in.close();
        stdIn.close();
        serviceSocket.close();

        System.out.println(" closed");
    }
}
