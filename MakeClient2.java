
// CREA UN CLIENTE.


// USADO PARA EL EJEMPLO DEL REPORTE

package example.udlapnews;


import java.util.Date;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;

public class MakeClient2{

  public static void main(String[] args){

    // Evitar excepciones
    try{

      // Tomar los valores para el cliente
      String nombreCliente = args[0];
      String idString = args[1];
      int idCliente = Integer.parseInt(idString);

      // Crear un objeto cliente
      // con los datos pasados
      Cliente objCliente = new Cliente(nombreCliente, idCliente);

      // Mostrar al cliente creado
      System.err.println("-------------------------------------");
      System.out.println("Se creo un cliente");
      objCliente.showInfo();

      // Se hace el stub del cliente usando la interfaz
      // Es decir, exportar el cliente (el objeto asociado a el)
      // para hacer el stub de este mismo.
      UdlapNewsUpdate clientStub  =
      (UdlapNewsUpdate) UnicastRemoteObject.exportObject(objCliente, 0);

      // Obtener el servidor de nombres
      Registry registry = LocateRegistry.getRegistry();

      // Ver cuales son los servicios registrados en el registro
      // Tomar una lista con los nombres de los registros
      String[] serviceNames = registry.list();
      // Imprimir el nombre de los servicios
      System.out.println("Servicios registrados en el registro RMI:");
      for (String serviceName : serviceNames) {
          System.out.println(serviceName);
      }
      System.err.println("-------------------------------------");

      // Preguntarle a cada uno si se quiere registrar
      Scanner scanner = new Scanner(System.in);
      for(String serviceName : serviceNames){
        // ver si el cliente se registra
        System.out.println("Se quiere registrar a " + serviceName + "?");
        System.out.println("1-si | 0-no");
        // ver que responde el cliente
        int respuesta = scanner.nextInt();
        // si se quiere registrar, hacerlo
        if (respuesta == 1) {
                System.out.println("Registrar a: " + serviceName);

                // registrar

                // tomar el stub del estacionamiento
                UdlapNewsEstacionamiento serverStub =
                (UdlapNewsEstacionamiento) registry.lookup(serviceName);

                // registrar
                serverStub.register(clientStub);

        } // end if
        else{
          System.out.println("No se registraa a: " + serviceName);
        }
        System.err.println("-------------------------------------");
      } // end-for

      // tomar el stub del estacionamiento de ciencias
      String estacionamientoCiencias = "EstacionamientoCiencias";

      // tomar el stub del estacionamiento
      UdlapNewsEstacionamiento serverStub =
      (UdlapNewsEstacionamiento) registry.lookup(estacionamientoCiencias);

      // entrar
      System.out.println("El cliente entra al estacionamiento de ciencias");
      serverStub.entraCliente(clientStub);


      System.err.println("-------------------------------------");

    } // end try
    catch (Exception e){
      // Imprimir la excepcion
      System.err.println("Client exception: " + e.toString());
      e.printStackTrace();
    } // end catch
  } // end main
}
