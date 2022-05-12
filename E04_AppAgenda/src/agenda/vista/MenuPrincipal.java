package agenda.vista;

import java.util.Scanner;

import agenda.negocio.Agenda;
import agenda.negocio.AgendaImpl;

public class MenuPrincipal {

	//Atributos
	private Agenda agenda;
	
	//Constructor
	public MenuPrincipal() {
		agenda = new AgendaImpl();
	}
	
	//Atributos
	public void menu() {
		System.out.println("AGENDA");
		System.out.println("----------------------------");
		
		boolean salir = false;
		int opcion;
		do {
			System.out.println("Menu principal");
			System.out.println("1 - Nuevo contacto");
			System.out.println("2 - Buscar contactos");
			System.out.println("3 - Listar todos");
			System.out.println("4 - Eliminar contacto");
			System.out.println("5 - Importar contactos");
			System.out.println("9 - Salir");
			
			System.out.println("Ingrese numuero de opcion: ");
			
			@SuppressWarnings("resource")
			Scanner keyboard = new Scanner(System.in);
			opcion = keyboard.nextInt();
			
			switch (opcion) {
			case 1:
				new NuevoContacto(agenda);
				break;
			case 3: 
				new ConsultarTodos(agenda);
				break;
			case 9:
				salir = true;
				break;
			}
		} while(!salir);
	}
}
