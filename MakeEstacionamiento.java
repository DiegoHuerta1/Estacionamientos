
// CREA UN ESTACIONAMIENTO


package example.udlapnews;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class MakeEstacionamiento{

  public static void main(String args[]){

    // Evitar excepciones
    try{

      // Tomar los valores para el Estacionamiento
      // Nombre y capacidad
      String nombreEstacionamiento = args[0];
      String capacidadString = args[1];
      int capacidadEstacionamiento = Integer.parseInt(capacidadString);

      // Crear un estacionamiento con los objetos pasados
      Estacionamiento objEstacionamiento =
      new Estacionamiento(nombreEstacionamiento, capacidadEstacionamiento);

      // Se hace el stub del estacionamiento usando la interfaz
      // Es decir, exportar el estacionamiento (el objeto asociado a el)
      // para hacer el stub de este mismo.
      UdlapNewsEstacionamiento serverStub =
      (UdlapNewsEstacionamiento) UnicastRemoteObject.exportObject(objEstacionamiento, 0);


      // Obtener el servidor de nombres
      Registry registry = LocateRegistry.getRegistry();

      // Usando el servidor de nombres, registrar el servicio
      // Usando el stub de este servicio
      String nombreServicio = nombreEstacionamiento;
      registry.bind(nombreServicio, serverStub);

      // Indicar que el servidor esta listo
      System.err.println("-------------------------------------");
      System.err.println("Estacionamiento listo bajo el nombre: " + nombreServicio);
      System.out.println("Con una capacidad maxima de " + capacidadEstacionamiento);
      System.err.println("-------------------------------------");


    } // end try
    catch (Exception e){
      // Imprimir la excepcion
      System.err.println("Server exception: " + e.toString());
    	e.printStackTrace();
    } // end catch

  } // end  main
}
