package agenda.vista;

import java.util.Set;

import agenda.modelo.Contacto;
import agenda.negocio.Agenda;

public class ConsultarTodos {
	
	Agenda agenda;
	
	public ConsultarTodos(Agenda agenda) {
		this.agenda = agenda;
		inicio();
	}
	
	private void inicio() {
		Set<Contacto> todos = agenda.buscarTodos();
		System.out.println("Todos los contactos");
		for (Contacto contacto : todos) {
			
			System.out.println(contacto.getNombre() + "\t" + contacto.getApellidos() +
					"\t" + contacto.getApodo() + "\t" + contacto.getTelefonos());
		}
	}

}
