package agenda.persistencia;

import java.util.Set;

import javax.persistence.EntityManager;

import agenda.modelo.Contacto;
import agenda.persistencia.util.EMF;

public class TestMapeoJPA {

	public static void main(String[] args) {
		
//		EntityManager em = EMF.getEmf().createEntityManager();
//		
//		Contacto c = em.find(Contacto.class, 6);
//		
//		System.out.println(c);
//		
//		em.close();
//		Contacto insertContact = new Contacto();
//		insertContact.setNombre("Pepe");
//		insertContact.setApellidos("Lane");
//		insertContact.setApodo("Supernovia");
		
		ContactoDaoJPA contactoDaoJPA = new ContactoDaoJPA();
//		contactoDaoJPA.insertar(insertContact);
		Set<Contacto> contactoBuscado = contactoDaoJPA.buscar("Pepe");
		
		System.out.println(contactoBuscado.size());
		System.out.println();
		for (Contacto contacto : contactoBuscado) {
			System.out.println(contacto.toString());
		}
		
//		Set<Contacto> contactosTodos = contactoDaoJPA.buscarTodos();
//		
//		System.out.println(contactosTodos.size());
//		System.out.println();
//		for (Contacto contacto : contactosTodos) {
//			System.out.println(contacto.toString());
//		}
		
		
	}

}
