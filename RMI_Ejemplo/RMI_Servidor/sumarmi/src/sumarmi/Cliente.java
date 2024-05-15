// Cliente.java
package sumarmi;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import javax.swing.JOptionPane;

public class Cliente {
    public static void main(String[] args) {
        try {
            // Obtener una referencia al registro RMI
            Registry registry = LocateRegistry.getRegistry("localhost", 1099);

            // Buscar el objeto remoto en el registro RMI usando su nombre lógico
            Sumador sumador = (Sumador) registry.lookup("Sumador");

            // Interfaz gráfica para ingresar los números
            String input1 = JOptionPane.showInputDialog("Ingrese el primer número:");
            String input2 = JOptionPane.showInputDialog("Ingrese el segundo número:");

            // Convertir los inputs a números enteros
            int numero1 = Integer.parseInt(input1);
            int numero2 = Integer.parseInt(input2);

            // Invocar el método remoto
            int resultado = sumador.sumar(numero1, numero2);
            JOptionPane.showMessageDialog(null, "Resultado de la suma: " + resultado);
        } catch (Exception e) {
            System.err.println("Excepción en el cliente: " + e.toString());
            e.printStackTrace();
        }
    }
}