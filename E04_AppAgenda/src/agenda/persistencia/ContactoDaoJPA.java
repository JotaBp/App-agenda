package agenda.persistencia;

import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import agenda.modelo.Contacto;
import agenda.persistencia.util.EMF;

@SuppressWarnings("serial")
public class ContactoDaoJPA implements ContactoDao, Serializable {

	private EntityManager em;
	
	@Override
	public void insertar(Contacto contacto) {
		
		em = EMF.getEmf().createEntityManager();		
		em.getTransaction().begin();
		em.persist(contacto);
		em.getTransaction().commit();
		em.close();
		
	}

	@Override
	public boolean eliminar(int idContacto) {
		
		em = EMF.getEmf().createEntityManager();
		Contacto c = em.find(Contacto.class, idContacto);
		
		if(c != null) {
			
			em.getTransaction().begin();
			em.remove(c);
			em.getTransaction().commit();
			return true;
		}
		
		em.close();
		
		return false;
	}

	@Override
	public void actualizar(Contacto contacto) {
		// TODO Auto-generated method stub
				
		em = EMF.getEmf().createEntityManager();
		em.getTransaction().begin();
		em.merge(contacto);
		em.getTransaction().commit();
		em.close();
	}

	@Override
	public Contacto buscar(int idContacto) {

		em = EMF.getEmf().createEntityManager();
		String jpql = """
				select c 
				from Contacto c 
				left join fetch c.telefonos 
				left join fetch c.emails 
				where idcontactos = :id
				""";
		TypedQuery<Contacto> q = em.createQuery(jpql, Contacto.class);
		q.setParameter("id", idContacto);
		Contacto searchedContact = q.getSingleResult();
		em.close();
		return searchedContact;
	}

	@Override
	public Set<Contacto> buscar(String nombre) {
		
		String jpqlNombre = """
				select c 
				from Contacto c 
				left join fetch c.telefonos 
				left join fetch c.emails 
				where c.nombre 
				like :nomb
				""";
		
		em = EMF.getEmf().createEntityManager();
		
		TypedQuery<Contacto> qNomb = em.createQuery(jpqlNombre, Contacto.class);
		qNomb.setParameter("nomb", "%" + nombre + "%");
		
		List<Contacto> contactosNomb = qNomb.getResultList();
		
		Set<Contacto> resContactosNomb = new LinkedHashSet<Contacto>();
		
		for (Contacto contacto : contactosNomb) {
			resContactosNomb.add(contacto);
		}
		
		em.close();
		
		return resContactosNomb;
	}

	@Override
	public Set<Contacto> buscarTodos() {
		
		String jpqlTodos = """
							select c 
							from Contacto c
							join fetch c.telefonos
							join fetch c.emails
						""";
		
		em = EMF.getEmf().createEntityManager();
		
		TypedQuery<Contacto> qTodos = em.createQuery(jpqlTodos, Contacto.class);
		
		List<Contacto> contactos = qTodos.getResultList();
		
		Set<Contacto> resContactos = new  LinkedHashSet<Contacto>();
		
		for (Contacto contacto : contactos) {
			resContactos.add(contacto);
		}
		
		em.close();
		
		return resContactos;
	}

}
