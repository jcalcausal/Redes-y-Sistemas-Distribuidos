import java.io.IOException;
import java.net.*;
import java.nio.charset.Charset;

/**
 *
 * @author <su nombre aquí>
 */
public class ServerUDP {
    public static String extraerTexto(String texto) {
        String resultado = "";
        int salto = Character.getNumericValue(texto.charAt(0));
    
        for (int i = 1; i < texto.length(); i += salto + 1) {
            resultado += texto.charAt(i);
        }
    
        return resultado;
    }

    public static void main(String[] args)
    {
        // DATOS DEL SERVIDOR
        //* FIJO: Si se lee de línea de comando debe comentarse
        // int port = 54322; // puerto del servidor
        //* VARIABLE: Si se lee de línea de comando debe descomentarse
        int port = Integer.parseInt(args[0]); // puerto del servidor

        // SOCKET
        DatagramSocket server = null;

        //* COMPLETAR Crear e inicalizar el socket del servidor

        // Funcion PRINCIPAL del servidor
        while (true)
        {
            //* COMPLETAR: Crear e inicializar un datagrama VACIO para recibir la respuesta de máximo 400 bytes

            //* COMPLETAR: Recibir datagrama

            //* COMPLETAR: Obtener texto recibido
            String line = null;

            //* COMPLETAR: Mostrar por pantalla la direccion socket (IP y puerto) del cliente y su texto

            // Capitalizamos la linea
            line = extraerTexto(line);

            //* COMPLETAR: crear datagrama de respuesta

            //* COMPLETAR: Enviar datagrama de respuesta

        } // Fin del bucle del servicio
    }

}
