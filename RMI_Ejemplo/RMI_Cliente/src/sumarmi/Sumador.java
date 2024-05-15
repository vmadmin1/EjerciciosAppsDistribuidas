// Sumador.java
package sumarmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Sumador extends Remote {
    int sumar(int a, int b) throws RemoteException;
}   
