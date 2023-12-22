# Estacionamientos
Programa desarrollado en JAVA, usando paradigma RMI. Es un programa para administrar lugares en los estacionamientos, se tiene un sistema de notificaciones para avisar a las personas la dispinibilidad de lugares.


Se tienen los estacionamientos del campus, con sus respectivas capacidades máximas y disponibilidades actuales. En este programa entran clientes, definidos por su nombre e ID, estos clientes pueden entrar a algún estacionamiento y posteriormente salir de este. Además, los clientes pueden registrarse a los estacionamientos, cuando un cliente esta registrado en un estacionamiento, el estacionamiento le notifica al cliente cada vez que alguien entra o sale de este. Los estacionamientos notifican utilizando mensajes, los cuales tienen contenido, fecha de envió y nombre del remitente.


## Instucciones para ejecutar el programa


Colocar una copia del contenido de este directorio en:

C:\RMI\

Para compilar utilizar la siguiente linea:

	javac -d . udlapnews\*.java

Para levantar el servidor de nombres (el CLASSPATH debe incluir el path de las clases del servidor):

	rmiregistry

Para ejecutar el servidor:

	java -Djava.rmi.server.codebase=file:/RMI/ example.udlapnews.MakeEstacionamiento <Nombre> <Capacidad>

Para ejecutar el cliente:

	java example.udlapnews.MakeClient <Nombre> <ID>

Para simular un cliente:

	java example.udlapnews.SimularCliente <Nombre> <ID> <Duracion>

Hacer la demostracion del semaforo:

	java example.udlapnews.DemostrarSemaforo1
	java example.udlapnews.DemostrarSemaforo2
	java example.udlapnews.DemostrarSemaforo3

