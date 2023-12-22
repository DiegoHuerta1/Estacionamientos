
// INTERFAZ IMPLEMENTADA POR EL ESTACIONAMIENTO

package example.udlapnews;

import java.util.Date;
import java.rmi.Remote;
import java.rmi.RemoteException;

// El contrato debe de ser publico y extender Remote
public interface UdlapNewsEstacionamiento extends Remote{
  // Servicios asincronos:

  // registrar a un cliente para los avisos
  void register(UdlapNewsUpdate client_stub) throws RemoteException;

  // quitar del registro a un cliente para los avisos
  void unregister(UdlapNewsUpdate client_stub) throws RemoteException;

  // meter un cliente al estacionamiento
  void entraCliente(UdlapNewsUpdate client_stub) throws RemoteException;

  // sacar a un cliente del estacionamiento
  void saleCliente(UdlapNewsUpdate client_stub) throws RemoteException;

  // mandar un mensaje a todos los clientes registrados
  void notificar(Mensaje mensaje) throws RemoteException;

}
