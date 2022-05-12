package agenda.persistencia;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
//import java.text.Collator;
//import java.util.Locale;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import agenda.modelo.Contacto;

public class ContactoDaoMem implements ContactoDao {

	private MemoriaDaoMem memoria;
	
	public ContactoDaoMem() {
		
		try (FileInputStream fis = new FileInputStream("agenda.dat")) {
			ObjectInputStream ois = new ObjectInputStream(fis);
			this.memoria = (MemoriaDaoMem) ois.readObject();
		} catch (FileNotFoundException e) {
			this.memoria = new MemoriaDaoMem();
		} catch (Exception e1) {
			e1.printStackTrace();
			System.err.println("Problemas al leer el fichero de datos");
			throw new RuntimeException();
		}
		
	}
	
	private void grabarMemoria() {
		
		try (FileOutputStream fos = new FileOutputStream("agenda.dat")) {
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(memoria);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.err.println("Problemas al grabar el fichero de datos");
			throw new RuntimeException();
		}
	}
	@Override
	public void insertar(Contacto contacto) {
		contacto.setIdContacto(++this.memoria.idGen);
		this.memoria.store.put(contacto.getIdContacto(), contacto);
		grabarMemoria();
	}

	@Override
	public boolean eliminar(int idContacto) {
//		return this.memoria.store.remove(idContacto, memoria.store.get(idContacto));
		boolean eliminado = this.memoria.store.remove(idContacto, memoria.store.get(idContacto));
		if (eliminado) {
			grabarMemoria();
			return true;
		}
		return false;
	}

	@Override
	public void actualizar(Contacto contacto) {
		this.memoria.store.replace(contacto.getIdContacto(), 
								this.memoria.store.get(contacto.getIdContacto()),
								contacto);
		grabarMemoria();
	}

	@Override
	public Contacto buscar(int idContacto) {
		return this.memoria.store.get(idContacto);
	}

	@Override
	public Set<Contacto> buscar(String nombre) {
		String formatNombre = nombre.toLowerCase();
		Set<Contacto> coincidentes = new HashSet<Contacto>();
		for (Map.Entry<Integer, Contacto> entry : this.memoria.store.entrySet()) {
//			Integer key = entry.getKey();
			Contacto val = entry.getValue();
//			Collator collator = Collator.getInstance(new Locale("es"));
//			if (collator.compare(val.getNombre().toLowerCase(), formatNombre) == 0 ||
//				collator.compare(val.getApellidos().toLowerCase(), formatNombre) == 0 ||
//				collator.compare(val.getApodo().toLowerCase(), formatNombre) == 0) {
//				coincidentes.add(val);
//			}
			if (val.getNombre().toLowerCase().contains(formatNombre)||
				val.getApellidos().toLowerCase().contains(formatNombre) ||
				val.getApodo().toLowerCase().contains(formatNombre)) {
				coincidentes.add(val);
			}
		}
		return coincidentes;
	}

	@Override
	public Set<Contacto> buscarTodos() {
		return new HashSet<Contacto>(this.memoria.store.values());
	}

}
