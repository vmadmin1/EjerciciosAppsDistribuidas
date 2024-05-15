import java.io.*;
import java.net.*;

public class ServidorTCP {
    public static void main(String[] args) {
        int puerto = 12345;

        try (ServerSocket serverSocket = new ServerSocket(puerto);
             Socket clientSocket = serverSocket.accept();
             PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))) {

            System.out.println("Cliente conectado desde: " + clientSocket.getInetAddress());

            // Recibir mensaje del cliente
            String mensaje = in.readLine();
            System.out.println("Recibido: " + mensaje);

            // Enviar respuesta al cliente
            out.println("Bien, ¿y tú?");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

