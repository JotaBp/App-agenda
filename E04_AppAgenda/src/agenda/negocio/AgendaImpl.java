package agenda.negocio;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.Collator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.TreeSet;

import agenda.modelo.Contacto;
import agenda.modelo.Domicilio;
import agenda.persistencia.ContactoDao;
import agenda.persistencia.ContactoDaoJDBC;
import agenda.persistencia.ContactoDaoJPA;
import agenda.persistencia.ContactoDaoMem;

public class AgendaImpl implements Agenda{

	private ContactoDao dao;
	
	public AgendaImpl() {
		/**
		 * Para conectar la agenda a la memoria o a la base de datos cambiaremos aqui en el 
		 * constructor de la agenda contactoDaoMem, si queremos usar la memoria y que se guarde en
		 * un archivo 
		 */
//		this.dao = new ContactoDaoMem();
//		this.dao = new ContactoDaoJDBC();
		this.dao = new ContactoDaoJPA();
	}
	
	@Override
	public Set<Contacto> buscarContactoPorNombre(String nombre) {
		Set<Contacto> orderNom = new TreeSet<Contacto>(
				new Comparator<Contacto>() {
					@Override
					public int compare(Contacto o1, Contacto o2) {
						if (o1.equals(o2)) {
							return 0;
							}
						String nom1 = o1.getNombre() + 
								o1.getApellidos() + o1.getIdContacto();
						String nom2 = o2.getNombre() + 
								o2.getApellidos() + o2.getIdContacto();
						return Collator.getInstance(new Locale("es"))
								.compare(nom1, 
										nom2);
						}
				}
		);
		orderNom.addAll(this.dao.buscar(nombre));
		
		return orderNom;
	}

	@Override
	public void insertarContacto(Contacto c) {
		this.dao.insertar(c);
		
	}

	@Override
	public Set<Contacto> buscarTodos() {
		Set<Contacto> orderNom = new TreeSet<Contacto>(
				new Comparator<Contacto>() {
					@Override
					public int compare(Contacto o1, Contacto o2) {
						if (o1.equals(o2)) {
							return 0;
							}
						String nom1 = o1.getNombre() + 
								o1.getApellidos() + o1.getIdContacto();
						String nom2 = o2.getNombre() + 
								o2.getApellidos() + o2.getIdContacto();
						return Collator.getInstance(new Locale("es"))
								.compare(nom1, 
										nom2);
						}
				}
		);
		
		orderNom.addAll(this.dao.buscarTodos());
		
		return orderNom;
	}

	@Override
	public int importarCSV(String fichero) throws FileNotFoundException{
		
		List<String> contactos = leerCSV(fichero);
		List<String> datosErroneos = new ArrayList<String>();
		int cant = 0;
		
		for (String contactoString : contactos) {
			
			try {
				Contacto contacto = new Contacto();
			Domicilio dom = new Domicilio();

			String splitChar = ";";
			String splitCharTelfsEmails = "/";

			String [] contactoArr = contactoString.split(splitChar);
			String [] telfsArr = contactoArr[11].split(splitCharTelfsEmails);
			String [] emailsArr = contactoArr[12].split(splitCharTelfsEmails);
			
			contacto.setNombre(contactoArr[0]);
			contacto.setApellidos(contactoArr[1]);
			contacto.setApodo(contactoArr[2]);
			dom.setTipoVia(contactoArr[3]);
			dom.setVia(contactoArr[4]);
			dom.setNumero(Integer.parseInt(contactoArr[5]));
			dom.setPiso(Integer.parseInt(contactoArr[6]));
			dom.setPuerta(contactoArr[7]);
			dom.setCodigoPostal(contactoArr[8]);
			dom.setCiudad(contactoArr[9]);
			dom.setProvincia(contactoArr[10]);
			contacto.setDom(dom);
			contacto.addTelefonos(telfsArr);
			contacto.addEmails(emailsArr);
			
			this.dao.insertar(contacto);
			cant++;
			} catch (Exception e) {
				 datosErroneos.add(contactoString);
			}
			
		}
		
//		Esto siguiente no se hace asi
		if (datosErroneos.size() > 0) {
			System.err.println("De  " + contactos.size() + " se importaron " + cant);
			System.err.println("Filas erroneas");
			for (String lineaError : datosErroneos) {
				System.err.println(Arrays.asList(lineaError));
			}			
		}

		return cant;
	}
	
	
	private List<String> leerCSV(String fichero) throws FileNotFoundException {
		
		List<String> contactListStrings = new LinkedList<String>();
		
		try (BufferedReader br = new BufferedReader(new FileReader(fichero))) {
			String contactoString = br.readLine();
			
			while (contactoString != null) {
				contactListStrings.add(contactoString);
				contactoString = br.readLine();
			}
		} catch (IOException e) {
			System.out.println("FALLO ContactoString no añadido");
			e.printStackTrace();
		}
		
		return contactListStrings;
		
	}

	@Override
	public boolean eliminar(Contacto c) {
		return this.dao.eliminar(c.getIdContacto());
	}

	@Override
	public void modificar(Contacto c) {
		this.dao.actualizar(c);
		
	}

	@Override
	public Contacto buscar(int idContacto) {
		return dao.buscar(idContacto);
	}

}
