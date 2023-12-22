// CREAR CLIENTES PARA DEMOSTRAR LA SINCRONIZACION DEL PROGRAMA

package example.udlapnews;


import java.util.Date;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;


public class DemostrarSemaforo2{

  // funcion main
  public static void main(String[] args){

    // Evitar excepciones
    try{

      System.out.println("Demostrar la funcionalidad del semaforo justo");
      System.out.println("Segundo programa");
      System.out.println("-------------------------------------");

      // crear el cliente
      System.out.println("Crear el cliente 2");
      Cliente objCliente = new Cliente("Cliente2", 2);
      System.out.println("-------------------------------------");
      objCliente.showInfo();
      System.out.println("-------------------------------------");

      // obtener el stub del cliente
      UdlapNewsUpdate clientStub  =
      (UdlapNewsUpdate) UnicastRemoteObject.exportObject(objCliente, 0);

      // Obtener el servidor de nombres
      Registry registry = LocateRegistry.getRegistry();

      // Ver cuales son los servicios registrados en el registro
      // Tomar una lista con los nombres de los registros
      String[] serviceNames = registry.list();

      // usar el primer estacionamiento
      String nameEstacionamiento = serviceNames[0];

      System.out.println("Se va a usar el estacionamiento: " + nameEstacionamiento);
      System.out.println("Para la demostracion, se espera que tenga capacidad de 1");
      System.out.println("-------------------------------------");

      // tomar el stub del estacionamiento
      UdlapNewsEstacionamiento serverStub =
      (UdlapNewsEstacionamiento) registry.lookup(nameEstacionamiento);

      // comienzo de la simulacion
      System.out.println("Comienzo de la demostracion");
      System.out.println("-------------------------------------");

      // pedir que comience
      Scanner scanner = new Scanner(System.in);
      System.out.println("Presionar 1 para continuar");
      System.out.println("Hacerlo en orden segun el numero del programa");
      int respuesta = scanner.nextInt();

      System.out.println("El cliente 2 va a intentar entrar al estacionamiento");
      System.out.println("El estacionamiento esta lleno (debido al cliente 1)");
      System.out.println("Por lo tanto el cliente 2 va a esperar a que se desocupe");
      System.out.println("Esperando...");
      serverStub.entraCliente(clientStub);


      System.out.println("El cliente 1 ya salio del estacionamiento");
      System.out.println("Por lo que el cliente 2 ya pudo pasar");
      System.out.println("El estacionamiento vuelve a estar lleno");
      System.out.println("El cliente 3 esta esperando lugar");

      // pedir que salga
      System.out.println("Presionar 1 para sacar al cliente 2");
      respuesta = scanner.nextInt();
      serverStub.saleCliente(clientStub);


      System.out.println("Fin");


    } // end try
    catch (Exception e){
      // Imprimir la excepcion
      System.err.println("Client exception: " + e.toString());
      e.printStackTrace();
    } // end catch

  } // end main
}
