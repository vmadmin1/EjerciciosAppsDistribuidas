package clientecalculadora;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class ClienteCalculadora extends JFrame implements ActionListener {

    private JTextField pantalla;
    private String operacionActual = "";
    private final String URL_SERVIDOR = "http://192.168.215.40:3000/operacion";

    public ClienteCalculadora() {
        super("Calculadora");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(360, 442);
        setBackground(Color.DARK_GRAY);

        pantalla = new JTextField(20);
        pantalla.setEditable(false);
        pantalla.setFont(new Font("Arial", Font.PLAIN, 30));
        pantalla.setHorizontalAlignment(JTextField.RIGHT);
        getContentPane().add(pantalla, BorderLayout.NORTH);

        JPanel panelBotones = new JPanel(new GridLayout(5, 4));
        String[] botones = {
            "C", "/", "*", "B",
            "7", "8", "9", "-",
            "4", "5", "6", "+",
            "1", "2", "3", "=",
            "0", ".", ""
        };
        for (String boton : botones) {
            JButton b = new JButton(boton);
            b.addActionListener(this);
            panelBotones.add(b);
        }
        getContentPane().add(panelBotones, BorderLayout.CENTER);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String cmd = e.getActionCommand();
        if (cmd.equals("=")) {
            enviarOperacion();
        } else if (cmd.equals("C")) {
            operacionActual = "";
        } else if (cmd.equals("B")) {
            if (operacionActual.length() > 0) {
                operacionActual = operacionActual.substring(0, operacionActual.length() - 1);
            }
        } else {
            operacionActual += cmd;
        }
        pantalla.setText(operacionActual);
    }

    private void enviarOperacion() {
        String result = "";
        if (!operacionActual.isEmpty()) {
            try {
                URL url = new URL(URL_SERVIDOR);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Content-Type", "application/json");
                connection.setDoOutput(true);

                String jsonInputString = "{\"operacion\": \"custom\", \"operando1\": \"" + operacionActual + "\"}";

                try (OutputStream os = connection.getOutputStream()) {
                    byte[] input = jsonInputString.getBytes("utf-8");
                    os.write(input, 0, input.length);
                }

                int responseCode = connection.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    try (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"))) {
                        StringBuilder response = new StringBuilder();
                        String responseLine = null;
                        while ((responseLine = br.readLine()) != null) {
                            response.append(responseLine.trim());
                        }
                        result = response.toString();
                        pantalla.setText(result); // Actualizar la pantalla con el resultado
                        System.out.print(result);
                    }
                } else {
                    pantalla.setText("Error en el servidor");
                }

                connection.disconnect();
            } catch (IOException e) {
                e.printStackTrace();
                pantalla.setText("Error de conexión");
            }
        } else {
            pantalla.setText("Error");
        }
        operacionActual = "";
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ClienteCalculadora::new);
    }
}
