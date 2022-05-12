package agenda.persistencia;

import java.util.Set;

import agenda.modelo.Contacto;

public interface ContactoDao {

	void insertar(Contacto contacto);
	boolean eliminar(int idContacto);
	void actualizar(Contacto contacto);
	Contacto buscar(int idContacto);
	Set<Contacto> buscar(String nombre);
	Set<Contacto> buscarTodos();
}
