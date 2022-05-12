package agenda.persistencia.util;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**@author Jota
 * Para implemementar JPA, el entitityManagerFactory debe declararse con patron 
 * singleton, para ello debemos seguir los siguientes pasos:
 * 
 * 	1 - El objeto a entregar como Singleton, se debe declarar como att privado y 
 * estatico.
 *  2 - El metodo factoria que entrega el Singleton, se declara publico y estatico. 
 */

public class EMF {
	
	private static EntityManagerFactory emf;
	
	private EMF() {}
	
	public static EntityManagerFactory getEmf() {
		
		if (emf == null) {
			emf = Persistence.createEntityManagerFactory("Persistence_Unit_Name");
		}
		
		return emf;
	}

}
