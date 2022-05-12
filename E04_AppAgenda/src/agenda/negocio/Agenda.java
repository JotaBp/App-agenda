package agenda.negocio;

import java.io.FileNotFoundException;
import java.util.Set;

import agenda.modelo.Contacto;


public interface Agenda {
	
	public Set<Contacto> buscarContactoPorNombre(String nombre);
	
	public void insertarContacto(Contacto c);

	public Set<Contacto> buscarTodos();
	
	public int importarCSV(String fichero) throws FileNotFoundException;
	
	public boolean eliminar(Contacto c);
	
	public void modificar(Contacto c);
	
	public Contacto buscar(int idContacto);
}
