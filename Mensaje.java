
// OBJETO DE TIPO MENSAJE
// SERIALIZABLE PARA PODER SER PASADO POR REFERENCIA.

package example.udlapnews;

import java.io.Serializable;
import java.util.Date;

public class Mensaje implements Serializable{

  // ------------------------------------------

  // Atributos

  private String contenido;
  private Date date  = new Date();
  private String nombreRemitente;
  // ------------------------------------------

  // Constructor

  public Mensaje(String contenidoMensaje, String remitente){
    contenido = contenidoMensaje;
    nombreRemitente = remitente;
  }

  // ------------------------------------------

  // Metodos

  public void mostrar(){
    System.out.println("-----------------------------");
    System.out.println("Mensaje: ");
    System.out.println(contenido);
    System.out.println("Mandado por: " + nombreRemitente);
    System.out.println("Mandado a las: " + date);
    System.out.println("-----------------------------");
  }

  // ------------------------------------------
}
