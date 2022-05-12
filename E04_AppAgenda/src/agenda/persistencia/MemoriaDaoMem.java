package agenda.persistencia;

import java.io.Serializable;
import java.util.Map;
import java.util.TreeMap;

import agenda.modelo.Contacto;

public class MemoriaDaoMem implements Serializable{
	private static final long serialVersionUID = 1L;
	
	Map<Integer, Contacto> store;
	int idGen;
	
	MemoriaDaoMem(){
		store = new TreeMap<Integer, Contacto>();
	}
}
