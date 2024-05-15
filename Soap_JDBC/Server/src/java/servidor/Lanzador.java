/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servidor;
import javax.xml.ws.Endpoint;

public class Lanzador {

    public final static String urlCalculadoraWS = "http://192.168.1.65:8888/calculadora";

    public static void main(String[] args) {
        Endpoint.publish(urlCalculadoraWS, new CalculadoraWS());
        System.out.println("Servicio Web en espera en "+urlCalculadoraWS);        
    }
}
