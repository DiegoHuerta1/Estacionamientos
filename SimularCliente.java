
// SIMULAR UN CLIENTE ALEATORIO

package example.udlapnews;


import java.util.Date;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;
import java.util.Random;
import java.text.DecimalFormat;



public class SimularCliente{

  // simular un tiempo de espera, en milisegundos
  public static float getTiempoEspera(Random random) {

    // tiempo medio en segundos
    float media = 5;

    // parametro lambda
    float lambda = 1/media;

    // generar un numero aleatorio uniforme e [0,1)
    float u = random.nextFloat();

    // obtener la muestra exponencial
    float muestraSegundos = (float) -Math.log(1 - u) / lambda;

    // esta en segundos
    // pasar a milisegundos
    float muestraMili = muestraSegundos * 1000;

    return muestraMili;
  }

  // esperar un tiempo dado en milisegundos
  public static void esperar(float milisegundos) {

    // Crear un formato con dos decimales para Imprimir
    DecimalFormat formato = new DecimalFormat("#.##");

    // decir que se va a esperar
    System.out.println("<<<Descansar " + formato.format(milisegundos/1000)+ " segundos>>>");
    try {
      Thread.sleep((long) milisegundos);
    } // end try
    catch (InterruptedException e) {
      System.out.println("No se pudo esperar");
      Thread.currentThread().interrupt();
    }
  }


  // seleccionar un string aleatorio
  public static String seleccionarEstacionamiento(String[] estacionamientos, Random random){
    // obtener el indice del estacionamiento
    int indiceAleatorio = random.nextInt(estacionamientos.length);

    // tomar el estacionamietno asociado y devolverlo
    return estacionamientos[indiceAleatorio];

  }

  public static void main(String[] args){

    // Evitar excepciones
    try{

      // Tomar los valores para el cliente
      String nombreCliente = args[0];
      String idString = args[1];
      int idCliente = Integer.parseInt(idString);
      String tiempoVidaString = args[2];
      int tiempoVida = Integer.parseInt(tiempoVidaString);

      // Crear un objeto cliente
      // con los datos pasados
      Cliente objCliente = new Cliente(nombreCliente, idCliente);

      // mostrar que se simula
      System.err.println("-------------------------------------");
      System.out.println("Cliente simulado");
      objCliente.showInfo();
      System.out.println("La simulacion dura " + tiempoVida + " segundos");
      System.err.println("-------------------------------------");

      // Se hace el stub del cliente usando la interfaz
      // Es decir, exportar el cliente (el objeto asociado a el)
      // para hacer el stub de este mismo.
      UdlapNewsUpdate clientStub  =
      (UdlapNewsUpdate) UnicastRemoteObject.exportObject(objCliente, 0);

      // Obtener el servidor de nombres
      Registry registry = LocateRegistry.getRegistry();

      // Ver cuales son los servicios registrados en el registro
      // Tomar una lista con los nombres de los registros
      String[] estacionamientos = registry.list();

      // Delimitar cuanto va a vivir esta simulacion
      // Ver cuando inicia (ahora)
      long tiempoInicial = System.currentTimeMillis();
      // ver cuando va a terminar (recordar que son milisegundos)
      long tiempoLimite = tiempoInicial + tiempoVida * 1000;

      // para generar numeros aleatorios
      Random random = new Random();

      // nombres de los estacionamientos a conectar
      String servicioConectar;

      // para ver cuanto tiempo se tarda en entrar
      long startTimeEntrar;
      long endTimeEntrar;
      long tiempoTotalEntrar;
      double tiempoEnSegundosEntrar;

      // Crear un formato con dos decimales para Imprimir
      DecimalFormat formato = new DecimalFormat("#.##");

      // Entrar y salir de estacionamientos
      // mientras viva la simulacion
      while(System.currentTimeMillis() < tiempoLimite){

        float tiempoEsperar;

        // Esperar antes de entrar a un estacionamiento
        tiempoEsperar = getTiempoEspera(random);
        esperar(tiempoEsperar);

        // seleccionar un estacionamiento al azar
        servicioConectar = seleccionarEstacionamiento(estacionamientos, random);

        // obtener el stub de este estacionamiento
        UdlapNewsEstacionamiento serverStub =
        (UdlapNewsEstacionamiento) registry.lookup(servicioConectar);


        // Entrar a un estacionamiento
        System.out.println("Entrar al estacionamiento: " + servicioConectar);
        // ver cuanto tiempo se tarda en entrar
        startTimeEntrar = System.currentTimeMillis();
        serverStub.entraCliente(clientStub);
        endTimeEntrar = System.currentTimeMillis();

        // saber cuanto tiempo se tardo en entrar
        tiempoTotalEntrar = endTimeEntrar - startTimeEntrar;
        tiempoEnSegundosEntrar = tiempoTotalEntrar / 1000.0;
        System.out.println("En entrar se tarda " + formato.format(tiempoEnSegundosEntrar)
        + " segundos");

        // Esperar antes de salir del estacionamiento
        tiempoEsperar = getTiempoEspera(random);
        esperar(tiempoEsperar);

        // Salir del estacionamiento
        System.out.println("Salir del estacionamiento: " + servicioConectar);
        serverStub.saleCliente(clientStub);


      } // end while


      System.out.println("Simulacion terminada");


    } // end try
    catch (Exception e){
      // Imprimir la excepcion
      System.err.println("Client exception: " + e.toString());
      e.printStackTrace();
    } // end catch
  } // end main
}
