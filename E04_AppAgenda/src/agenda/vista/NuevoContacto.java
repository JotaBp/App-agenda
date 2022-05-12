package agenda.vista;


import java.util.Scanner;

import agenda.modelo.Contacto;
import agenda.negocio.Agenda;

public class NuevoContacto {
	
	public Agenda agenda;
	
	public NuevoContacto(Agenda agenda) {
		this.agenda = agenda;
		inicio();
	}
	
	public void inicio() {
		System.out.println("Nuevo Contacto");
		
		@SuppressWarnings("resource")
		Scanner keyboard = new Scanner(System.in);
		
		System.out.println("Nombre contacto:");
		String nombre = keyboard.next();
		System.out.println("Apellidos: ");
		String apellidos = keyboard.next();
		System.out.println("Apodo: ");
		String apodo = keyboard.next();
		System.out.println("Telefono1: ");
		String tel1 = keyboard.next();
		System.out.println("Telefono2: ");
		String tel2 = keyboard.next();
		
		Contacto nuevo = new Contacto();
		nuevo.setNombre(nombre);
		nuevo.setApellidos(apellidos);
		nuevo.setApodo(apodo);
		nuevo.addTelefonos(tel1,tel2);
		
		agenda.insertarContacto(nuevo);
		
	}

}
