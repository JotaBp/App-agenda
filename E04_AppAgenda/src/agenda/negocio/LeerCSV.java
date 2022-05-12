package agenda.negocio;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.LinkedList;
import java.util.List;

import agenda.modelo.Contacto;
import agenda.modelo.Domicilio;
import agenda.persistencia.ContactoDao;
import agenda.persistencia.ContactoDaoMem;

public class LeerCSV {
	
	public static void main(String[] args) {
		try (FileReader fr = new FileReader("contactos.csv")) {
			importarCSV(fr);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static List<String> leerCSV(Reader r) {
		List<String> contactListStrings = new LinkedList<String>();
		
		try (BufferedReader br = new BufferedReader(r);) {
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
	
	public static void leerCSV(InputStream is) {
		leerCSV(new InputStreamReader(is));
	}
	
	public static void importarCSV(Reader r) {
		List<String> contactos = leerCSV(r);
		Contacto contacto = new Contacto();
		ContactoDao contactoDao = new ContactoDaoMem();
		
		for (String contactoString : contactos) {
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
			
//			for (int i = 0; i < contactoArr.length; i++) {
//				System.out.println(i + ": " + contactoArr[i]);
//			}
		}
		
		contactoDao.insertar(contacto);
		System.out.println("Contacto insertado: " + contactoDao.toString());
	}

}
