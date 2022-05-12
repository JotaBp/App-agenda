package agenda.persistencia;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;

public class SingletonConexionAgenda {
	
	private static DataSource ds;
	
	private SingletonConexionAgenda() {}
	
	public static DataSource getDataSource() {
		if (ds == null) {
			
			BasicDataSource bds = new BasicDataSource();
			
			Properties prop = new Properties();
			try (FileReader fr = new FileReader("bbdd.properties")) {
				
				prop.load(fr);
				bds.setDriverClassName(prop.getProperty("driver"));
				bds.setUrl(prop.getProperty("url"));
				bds.setUsername(prop.getProperty("username"));
				bds.setPassword(prop.getProperty("password"));
				
			} catch (IOException e1) {
				System.err.println("No se pudo cargar el fichero de properties");
				e1.printStackTrace();
			}
			ds = bds;
		}
		return ds;
	}

}
