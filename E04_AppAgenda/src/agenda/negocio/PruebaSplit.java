package agenda.negocio;

public class PruebaSplit {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String linea = "uno;dos;tres;cuatro;uno con espacios;ultimo";
		
		String[] datos = linea.split(";");
		
		for (int i = 0; i < datos.length; i++) {
			System.out.println(datos[i]);
		}

	}

}
