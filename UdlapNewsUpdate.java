
// INTERFAZ IMPLEMENTADA POR EL CLIENTE

package example.udlapnews;

import java.rmi.Remote;
import java.rmi.RemoteException;


// El contrato debe de ser publico y extender Remote
  public interface UdlapNewsUpdate extends Remote {

  // Acciones de un cliente:

  // Dar su informacion
  String getNombre() throws RemoteException;
  int getID() throws RemoteException;

  // Recibir un mensaje del servidor
  void recibirMensaje(Mensaje mensaje) throws RemoteException;
}
