import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

class ServerTCP {

    public static String reserva(String s){
        String res = "";
        String [] ERRORES = {"Entradas agotadas", "Error del sistema", "Inténtelo más tarde", "Error en la petición"};
        double random = Math.random();
        if (random < 0.8)
            res = "Reserva exitosa";
        else res = "Reserva fallida: " + ERRORES[new Random().nextInt(ERRORES.length)];

        return res;
    }

    public static void main(String[] args) throws IOException {
        Integer port = null;
        // SOCKETS
        ServerSocket server = null; // Pasivo (recepción de peticiones)
        Socket client = null;       // Activo (atención al cliente)

        // FLUJOS PARA EL ENVÍO Y RECEPCIÓN
        BufferedReader in = null;
        PrintWriter out = null;

        // Crea e inicaliza el socket del servidor (socket pasivo)
        try {
            server = new ServerSocket(port);
        } catch (IOException e) {
            System.out.println("ERROR: Could not listen on port "+port);
            System.exit(-1);
        }

        while (true) // Bucle de recepción de conexiones entrantes
        {
            System.out.println("STATUS: Waiting for clients");
            // Espera conexiones entrantes
            try {
                client = server.accept();
            } catch (IOException e) {
                System.out.println("Accept failed: "+ port);
                System.exit(-1);
            }
		    // Una vez aceptada una conexion, inicializar flujos de entrada/salida del socket conectado
            try {
                out = new PrintWriter( client.getOutputStream(), true);
                in = new BufferedReader( new InputStreamReader(client.getInputStream()) );
            } catch (IOException e) {
                System.err.println("Couldn't get I/O for " + client.getRemoteSocketAddress());
                System.exit(1);
            }
            System.out.println("STATUS: Client connected from: " + client.getRemoteSocketAddress());

            try{
                Integer peticiones = 0; // Número de entradas pedidas por el cliente
                String line = null;

                line = in.readLine();
                System.out.println("STATUS: Received from client " + line);
                peticiones += Character.getNumericValue(line.charAt(0));

                line = reserva(line);
                out.println(line);
                System.out.println("STATUS: Sending to client " + line);
            }catch (IOException e){
                System.out.println("ERROR: Recepción/envío: "+ e.getMessage());
            }

            System.out.println("STATUS: Closing connection with the client");
            //* COMPLETAR: Cerrar flujos y socket
            in.close();
            out.close();
            client.close();
        } // fin del bucle
    } // fin del metodo
}
