import java.io.*;
import java.net.*;

public class ServidorTCP {
    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(9999); // Puerto del servidor
            System.out.println("Servidor TCP iniciado. Esperando por conexiones...");

            Socket clientSocket = serverSocket.accept(); // Esperando la conexión entrante
            System.out.println("Cliente conectado desde: " + clientSocket.getInetAddress().getHostName());

            // Flujo de entrada del cliente
            BufferedReader inFromClient = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            // Flujo de salida hacia el cliente
            PrintWriter outToClient = new PrintWriter(clientSocket.getOutputStream(), true);

            // Flujo de entrada del servidor desde la consola
            BufferedReader serverConsoleReader = new BufferedReader(new InputStreamReader(System.in));

            String clientMessage;
            String serverMessage;

            while (true) {
                // Leyendo mensaje del cliente
                clientMessage = inFromClient.readLine();
                if (clientMessage == null || clientMessage.equalsIgnoreCase("bye")) {
                    System.out.println("Cliente ha cerrado la conexión.");
                    break;
                }
                System.out.println("Cliente: " + clientMessage);

                // Leyendo mensaje desde la consola del servidor
                System.out.print("Servidor: ");
                serverMessage = serverConsoleReader.readLine();
                outToClient.println(serverMessage); // Enviando mensaje al cliente
            }

            // Cerrando conexiones
            outToClient.close();
            inFromClient.close();
            clientSocket.close();
            serverSocket.close();
        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}
