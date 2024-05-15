import java.io.*;
import java.net.*;

public class ClienteUDP {

    public static void main(String[] args) {
        try {
            Socket clientSocket = new Socket("192.168.224.205", 9999); // Conectando al servidor
            System.out.println("Conexión establecida con el servidor.");

            // Flujo de entrada desde el servidor
            BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            // Flujo de salida hacia el servidor
            PrintWriter outToServer = new PrintWriter(clientSocket.getOutputStream(), true);

            // Flujo de entrada del cliente desde la consola
            BufferedReader clientConsoleReader = new BufferedReader(new InputStreamReader(System.in));

            String serverMessage;
            String clientMessage;

            while (true) {
                // Leyendo mensaje desde la consola del cliente
                System.out.print("Cliente: ");
                clientMessage = clientConsoleReader.readLine();
                outToServer.println(clientMessage); // Enviando mensaje al servidor

                // Leyendo mensaje desde el servidor
                serverMessage = inFromServer.readLine();
                if (serverMessage == null || serverMessage.equalsIgnoreCase("bye")) {
                    System.out.println("El servidor ha cerrado la conexión.");
                    break;
                }
                System.out.println("Servidor: " + serverMessage);
            }

            // Cerrando conexiones
            outToServer.close();
            inFromServer.close();
            clientSocket.close();
        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}