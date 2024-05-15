package sumarmi;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class SumadorImpl extends UnicastRemoteObject implements Sumador {
    private OperacionesBD operacionesBD;

    public SumadorImpl() throws RemoteException {
        super();
        operacionesBD = new OperacionesBD();
    }

    public int sumar(int a, int b) throws RemoteException {
        int resultado = a + b;
        operacionesBD.guardarOperacion(a, b, resultado);
        return resultado;
    }

    @Override
    public void enviarResultado(int resultado) throws RemoteException {
        throw new RemoteException("Operación no soportada en el cliente.");
    }
}

    