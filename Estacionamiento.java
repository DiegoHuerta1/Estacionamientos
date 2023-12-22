
// ESTACIONAMIENTO. IMPLEMENTA LA INTERFAZ UdlapNewsEstacionamiento

package example.udlapnews;

import java.util.Date;
import java.util.LinkedList;
import java.util.Vector;
import java.util.List;
import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.concurrent.Semaphore;


public class Estacionamiento implements UdlapNewsEstacionamiento {

  // ---------------------------------------------------

  // Atributos:

  String nombre;
  int capacidadMaxima;
  int disponibilidad;

  // El estacionamiento es como un semaforo
  private final Semaphore semaphore;

  // hacer una lista de los clientes regitrados
  private Vector<UdlapNewsUpdate> clientesRegistrados =
   new Vector<UdlapNewsUpdate>();

  // hacer una lista de los clientes que estan dentro
  private Vector<UdlapNewsUpdate> clientesDentro =
   new Vector<UdlapNewsUpdate>();

  // ---------------------------------------------------

  // Constructor basico
  public Estacionamiento(String nombreEstacionamiento, int c) {
    nombre = nombreEstacionamiento;
    capacidadMaxima = c;
    disponibilidad = c;

    // inicializar el semaforo
    // semaforo justo
    // con la capacidad maxima del estacionamietno
    semaphore = new Semaphore(c, true);
  }

  // ---------------------------------------------------

  // Metodos

  // Implementar las acciones de la interfaz:

  // registrar a un cliente
  public void register(UdlapNewsUpdate client_stub){
    System.out.println("------------------------------------");

    try{ // majenar excepciones
      System.out.println("Registrar un cliente para recibir notificaciones");
      System.out.println("Nombre del cliente: " + client_stub.getNombre());
      System.out.println("ID del cliente: " + client_stub.getID());

      // ver si ese cliente ya esta registrado
      if(clientesRegistrados.contains(client_stub)){
        System.out.println("El cliente ya habia sido registrado");
      }
      // si no esta registrado entonces si se registra
      else{
        clientesRegistrados.addElement(client_stub);
      }

    } // end try
    catch(Exception e){
      System.out.println("No se pudo hacer el registro");
    } // end catch
    System.out.println("------------------------------------");

  }

  // quitar del registro a un cliente
  public void unregister(UdlapNewsUpdate client_stub){
    try{ // majenar excepciones
      System.out.println("Quitar del registro a un cliente");
      System.out.println("Nombre del cliente: " + client_stub.getNombre());
      System.out.println("ID del cliente: " + client_stub.getID());

      // ver si el cliente es parte de los registrados o no
      if (!clientesRegistrados.contains(client_stub)){
        System.out.println("El cliente no estaba registrado");
      }
      // si esta registrado, se quita del registro
      else{
        clientesRegistrados.removeElement(client_stub);
      }

    } // end try
    catch(Exception e){
      System.out.println("No se pudo quitar el registro");
    } // end catch
    System.out.println("------------------------------------");
  }

  // meter un cliente al estacionamiento
  public void entraCliente(UdlapNewsUpdate client_stub){

    System.out.println("------------------------------------");

    // manejar excepciones
    try{

      // si el cliente ya esta dentro
      // entonces solo se dice esto y ya
      if(clientesDentro.contains(client_stub)){
        System.out.println("El cliente " + client_stub.getNombre() +
        " queria entrar al estacionamiento");
        System.out.println("El cliente ya estaba dentro");
      } // end if
      // el cliente no esta dentro
      else{

         semaphore.acquire(); // hacer un down en el semaforo
         disponibilidad = disponibilidad - 1;

         // meter al cliente
         clientesDentro.addElement(client_stub);

         // notificar su entrada
         String stringMensajeMandar = "Ha entrado " + client_stub.getNombre()
         + ".\n" +
         "Quedan " + disponibilidad + " lugares de " + capacidadMaxima;
         Mensaje mensajeMandar = new Mensaje(stringMensajeMandar, this.nombre);
         this.notificar(mensajeMandar);

      } // end else


    } // end try
    catch(Exception e){
      // si hay una excepecion, decirlo
      System.out.println("El cliente no pudo entrar");
    } // end catch
    System.out.println("------------------------------------");

  }


  // sacar a un cliente del estacionamiento
  public void saleCliente(UdlapNewsUpdate client_stub){

    System.out.println("------------------------------------");

    // manejar excepciones
    try{

      // si el cliente no esta dentro
      // solo se notifica el intento
      if(!clientesDentro.contains(client_stub)){
        System.out.println("El cliente " + client_stub.getNombre() +
        " queria salir del estacionamiento");
        System.out.println("El cliente no estaba dentro");
      } // end if

      // el cliente si estaba adentro
      else{

        // sacar al cliente
        clientesDentro.removeElement(client_stub);

        // subir en uno la disponibilidad
        disponibilidad = disponibilidad + 1;

        // notificar su salida
        String stringMensajeMandar = "Ha salido " + client_stub.getNombre()
        + ".\n" +
        "Quedan " + disponibilidad + " lugares de " + capacidadMaxima;
        Mensaje mensajeMandar = new Mensaje(stringMensajeMandar, this.nombre);
        this.notificar(mensajeMandar);

        // hacer up en el candado
        semaphore.release();

      }// end else

    } // end try

    catch(Exception e){
      // si hay una excepecion, decirlo
      System.out.println("El cliente no pudo salir");
    } // end catch

  }




  // mandar un mensaje a todos los clientes registrados
  public void notificar(Mensaje mensaje){
    System.out.println("------------------------------------");
    System.out.println("Notificar un mensaje a los clientes");

    // manejar excepciones
    try{

      // iterar en los clientes registrados
      for (UdlapNewsUpdate client: clientesRegistrados){
        // notificar a cada cliente
        client.recibirMensaje(mensaje);
      } // end for

    } // end try
    catch(Exception e){
        // si hay una excepecion, decirlo
        System.out.println("No se pudo notificar a los clientes");
    } // end catch

    System.out.println("--------------------------------");
  }

}
