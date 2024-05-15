import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.swing.*;

public class CalculadoraCliente extends JFrame {
    private JPanel panel;
    private JTextField txtOperando1;
    private JTextField txtOperando2;
    private JComboBox<String> cmbOperacion;
    private JButton btnCalcular;
    private JLabel lblResultado;

    public CalculadoraCliente() {
        setTitle("Calculadora Cliente");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(panel);
        pack();

        btnCalcular.addActionListener(e -> {
            String operacion = (String) cmbOperacion.getSelectedItem();
            double operando1 = Double.parseDouble(txtOperando1.getText());
            double operando2 = Double.parseDouble(txtOperando2.getText());

            try {
                double resultado = enviarSolicitud(operacion, operando1, operando2);
                lblResultado.setText(String.valueOf(resultado));
            } catch (IOException ex) {
                ex.printStackTrace();
                lblResultado.setText("Error al conectar con el servidor.");
            }
        });
    }

    private double enviarSolicitud(String operacion, double operando1, double operando2) throws IOException {
        String urlServidor = "http://192.168.227.63:3000/operacion";
        URL url = new URL(urlServidor);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "application/json");
        con.setDoOutput(true);

        String jsonInputString = "{\"operacion\": \"" + operacion + "\", \"operando1\": " + operando1 + ", \"operando2\": " + operando2 + "}";

        try (DataOutputStream os = new DataOutputStream(con.getOutputStream())) {
            byte[] input = jsonInputString.getBytes("utf-8");
            os.write(input, 0, input.length);
        }

        try (BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"))) {
            StringBuilder response = new StringBuilder();
            String responseLine = null;
            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }
            return Double.parseDouble(response.toString());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            CalculadoraCliente calculadora = new CalculadoraCliente();
            calculadora.setVisible(true);
        });
    }
}
