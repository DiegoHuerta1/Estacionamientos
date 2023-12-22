
// CLIENTE. IMPLEMENTA LA INTERFAZ UdlapNewsUpdate


package example.udlapnews;

import java.util.Date;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class Cliente implements UdlapNewsUpdate{

  // ---------------------------------------------------

  // ATRIBUTOS

  private String nombre;
  private int id;

  // ---------------------------------------------------

  // Constructor basicos

  // Con nombre y id
  public Cliente(String nombreCliente, int idCliente){
    nombre = nombreCliente;
    id = idCliente;
  }

  // ---------------------------------------------------

  // METODOS

  // Implementa las acciones de la interfaz

  // Recibir un mensaje del servidor
  public void recibirMensaje (Mensaje mensaje){

    // Mostrar el mensaje
    mensaje.mostrar();
  }

  // Dar su informacion
  public String getNombre() {
    return nombre;
  }
  public int getID() {
    return id;
  }

  // Mostar la informacion del cliente
  public void showInfo(){
    System.out.println("Nombre del cliente: " + nombre);
    System.out.println("ID del cliente: " + id);
    System.out.println("-----------------------------");
  }

  // ---------------------------------------------------

} // end class
