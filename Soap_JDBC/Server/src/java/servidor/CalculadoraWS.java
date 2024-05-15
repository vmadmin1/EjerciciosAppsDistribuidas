/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servidor;

import javax.jws.WebService;
import javax.jws.WebMethod;

@WebService
public class CalculadoraWS {

    private int contadorOperaciones;

    public CalculadoraWS() {
        this.contadorOperaciones = 0;
    }

    @WebMethod
    public int sumar(int a, int b) {
        contadorOperaciones++;
        return (a + b);
    }

    @WebMethod
    public int restar(int a, int b) {
        contadorOperaciones++;
        return (a - b);
    }

    @WebMethod
    public int multiplicar(int a, int b) {
        contadorOperaciones++;
        return (a * b);
    }

    @WebMethod
    public int dividir(int a, int b) {
        contadorOperaciones++;
        return (a / b);
    }

    @WebMethod
    public int getContadorPeticiones() {
        return (this.contadorOperaciones);
    }
}
