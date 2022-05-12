package agenda.persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

import javax.sql.DataSource;

import agenda.exceptions.SQLProblemException;
import agenda.modelo.Contacto;
import agenda.modelo.Domicilio;

public class ContactoDaoJDBC implements ContactoDao{
	
	private DataSource ds;
	
	public ContactoDaoJDBC() {
		this.ds = SingletonConexionAgenda.getDataSource();
	}

	@Override
	public void insertar(Contacto contacto) {
		// TODO Auto-generated method stub
		String sql = 
			"""
				insert into contactos values(null, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
			""";
		try (Connection con = this.ds.getConnection()) {
//			con.setAutoCommit(false);
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, contacto.getNombre());
			ps.setString(2, contacto.getApellidos());
			ps.setString(3, contacto.getApodo());
			ps.setString(4, contacto.getDom().getTipoVia());
			ps.setString(5, contacto.getDom().getVia());
			ps.setInt(6, contacto.getDom().getNumero());
			ps.setInt(7, contacto.getDom().getPiso());
			ps.setString(8, contacto.getDom().getPuerta());
			ps.setString(9, contacto.getDom().getCodigoPostal());
			ps.setString(10, contacto.getDom().getCiudad());
			ps.setString(11, contacto.getDom().getProvincia());
			
			int filas = ps.executeUpdate();
			
			if (filas == 1) {
				Statement st = con.createStatement();
				ResultSet rs = st.executeQuery("select LAST_INSERT_ID()");
				rs.next();
				int ultimoIdGenerado = rs.getInt(1);
				System.out.println(ultimoIdGenerado);
				
				sql = "insert into correos values(null, ?, ?)";
				PreparedStatement psCorreos = con.prepareStatement(sql);
				for (String correo : contacto.getEmails()) {
					psCorreos.setInt(1, ultimoIdGenerado);
					psCorreos.setString(2, correo);
					psCorreos.executeUpdate();
				}
				
				sql = "insert into telefonos values(null, ?, ?)";
				PreparedStatement psTelefonos = con.prepareStatement(sql);
				for (String telefono : contacto.getTelefonos()) {
					psTelefonos.setInt(1, ultimoIdGenerado);
					psTelefonos.setString(2, telefono);
					psTelefonos.executeUpdate();
				}
				
				con.commit();
				con.setAutoCommit(true);
			} else {
				con.rollback();
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new SQLProblemException(e);
		}
		
		
	}

	@Override
	public boolean eliminar(int idContacto) {
		// TODO Auto-generated method stub
		String sql = """
				DELETE FROM  agenda.contactos
				WHERE idcontactos = ?
				""";
		boolean eliminado = false;
		try (Connection con = ds.getConnection()) {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, idContacto);
			eliminado = ps.execute()? true: false;
		} catch (SQLException e) {
			System.err.println("No se ha podido eliminar el contacto");
			e.printStackTrace();
		}
		return eliminado;
		
	}

	@Override
	public void actualizar(Contacto contacto) {
		// TODO Auto-generated method stub
		String sql = """
				UPDATE agenda.contactos
				SET agenda.contactos.nombre = ?, 
				agenda.contactos.apellidos = ?, 
				agenda.contactos.apodo = ?, 
				agenda.contactos.tipo_via = ?, 
				agenda.contactos.via = ?, 
				agenda.contactos.numero = ?, 
				agenda.contactos.piso = ?, 
				agenda.contactos.puerta = ?, 
				agenda.contactos.codigo_postal = ?, 
				agenda.contactos.ciudad = ?, 
				agenda.contactos.provincia = ?
				WHERE agenda.contactos.idcontactos = ?
				""";
		
		try (Connection con = ds.getConnection()){
			con.setAutoCommit(false);
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, contacto.getNombre());
			ps.setString(2, contacto.getApellidos());
			ps.setString(3, contacto.getApodo());
			ps.setString(4, contacto.getDom().getTipoVia());
			ps.setString(5, contacto.getDom().getVia());
			ps.setInt(6, contacto.getDom().getNumero());
			ps.setInt(7, contacto.getDom().getPiso());
			ps.setString(8, contacto.getDom().getPuerta());
			ps.setString(9, contacto.getDom().getCodigoPostal());
			ps.setString(10, contacto.getDom().getCiudad());
			ps.setString(11, contacto.getDom().getProvincia());
			ps.setInt(12, contacto.getIdContacto());
						
			int filas = ps.executeUpdate();
			
			if (filas == 1) {
				
				String sqlDelTelfs = """
						DELETE FROM  agenda.telefonos
						WHERE id_contacto = ?
						""";
				
				PreparedStatement psDelTelf = con.prepareStatement(sqlDelTelfs);
				psDelTelf.setInt(1, contacto.getIdContacto());
				psDelTelf.executeUpdate();
				
				String sqlUpdTelfs = """
									INSERT INTO agenda.telefonos
									VALUES(null, ?, ?)
									""";
																	
				PreparedStatement psUpdTelfs = con.prepareStatement(sqlUpdTelfs);
				for (String telefono : contacto.getTelefonos()) {
					psUpdTelfs.setInt(1, contacto.getIdContacto());
					psUpdTelfs.setString(2, telefono);
					psUpdTelfs.executeUpdate();
				}
				
				String sqlDelEmails = """
						DELETE FROM  agenda.correos
						WHERE id_contacto = ?
						""";
				
				PreparedStatement psDelEmails = con.prepareStatement(sqlDelEmails);
				psDelEmails.setInt(1, contacto.getIdContacto());
				psDelEmails.executeUpdate();
				
				String sqlUpdEmails = """
									INSERT INTO agenda.correos
									VALUES(null, ?, ?)
									""";
				
				PreparedStatement psUpdCorreos = con.prepareStatement(sqlUpdEmails);
				for (String correo : contacto.getEmails()) {
					psUpdCorreos.setInt(1, contacto.getIdContacto());
					psUpdCorreos.setString(2, correo);
					psUpdCorreos.executeUpdate();
				}
				
				con.commit();
				con.setAutoCommit(true);
			} else {
				con.rollback();
			}
			
			
		} catch (SQLException e) {
			System.err.println("No ha sido posible actualizar el contacto");
			e.printStackTrace();
		}
		
	}

	@Override
	public Contacto buscar(int idContacto) {
		String sql = """
					SELECT idcontactos, nombre, apellidos, apodo, tipo_via, 
					via, numero, piso, puerta, codigo_postal, ciudad, provincia 
					FROM agenda.contactos
					where idcontactos = ?
					""";
		
		Contacto contactoEncontrado = new Contacto();
		
		try (Connection con = ds.getConnection()) {
			
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, idContacto);
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				contactoEncontrado.setIdContacto(rs.getInt("idcontactos"));
				contactoEncontrado.setNombre(rs.getString("nombre"));
				contactoEncontrado.setApellidos(rs.getString("apellidos"));
				contactoEncontrado.setApodo(rs.getString("apodo"));
				
				Domicilio dom = new Domicilio();
				dom.setTipoVia(rs.getString("tipo_via"));
				dom.setVia(rs.getString("via"));
				dom.setNumero(rs.getInt("numero"));
				dom.setPiso(rs.getInt("piso"));
				dom.setPuerta(rs.getString("puerta"));
				dom.setCodigoPostal(rs.getString("codigo_postal"));
				dom.setCiudad(rs.getString("ciudad"));
				dom.setProvincia(rs.getString("provincia"));
				contactoEncontrado.setDom(dom);
				
				String sqlTelf = """
								SELECT id_telefono, id_contacto, telefono 
								FROM agenda.telefonos
								where id_contacto = ?
								""";
				
				PreparedStatement psTelf = con.prepareStatement(sqlTelf);
				psTelf.setInt(1, rs.getInt("idcontactos"));
				ResultSet rsTelf = psTelf.executeQuery();
				
				while (rsTelf.next()) {
					contactoEncontrado.addTelefonos(rsTelf.getString("telefono"));
				}
				
				String sqlEmail = """
								SELECT id_correo, id_contacto,correo 
								FROM agenda.correos 
								where id_contacto = ?
								""";
				
				PreparedStatement psEmail = con.prepareStatement(sqlEmail);
				psEmail.setInt(1, rs.getInt("idcontactos"));
				ResultSet rsEmail = psEmail.executeQuery();
				
				while (rsEmail.next()) {
					contactoEncontrado.addEmails(rsEmail.getString("correo"));
				}
			}
			
			
		} catch (SQLException e) {
			System.err.println("No ha sido posible consultar el contacto buscado "
					+ "de la base de datos");
			e.printStackTrace();
		}
		
				
		return contactoEncontrado;
	}

	@Override
	public Set<Contacto> buscar(String nombre) {
		// TODO Auto-generated method stub
		String param = "%?%";
		String sql = """
				SELECT idcontactos, nombre, apellidos, apodo, tipo_via, 
				via, numero, piso, puerta, codigo_postal, ciudad, provincia 
				FROM agenda.contactos
				WHERE nombre like 
				""" 
				+ param;	
				
		Set<Contacto> agendaContactosNombre= new TreeSet<Contacto>();
		
		try (Connection con = ds.getConnection()) {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, nombre);
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				Contacto contacto = new Contacto();	
				contacto.setIdContacto(rs.getInt("idcontactos"));
				contacto.setNombre(rs.getString("nombre"));
				contacto.setApellidos(rs.getString("apellidos"));
				contacto.setApodo(rs.getString("apodo"));
				
				Domicilio dom = new Domicilio();
				dom.setTipoVia(rs.getString("tipo_via"));
				dom.setVia(rs.getString("via"));
				dom.setNumero(rs.getInt("numero"));
				dom.setPiso(rs.getInt("piso"));
				dom.setPuerta(rs.getString("puerta"));
				dom.setCodigoPostal(rs.getString("codigo_postal"));
				dom.setCiudad(rs.getString("ciudad"));
				dom.setProvincia(rs.getString("provincia"));
				contacto.setDom(dom);
				
				
				sql = """
						SELECT id_telefono, id_contacto, telefono 
						FROM agenda.telefonos
						where id_contacto = ?
					""";
				
				PreparedStatement psTelf = con.prepareStatement(sql);
				psTelf.setInt(1, rs.getInt("idcontactos"));
				ResultSet rsTelf = psTelf.executeQuery();
				
				while (rsTelf.next()) {
					contacto.addTelefonos(rsTelf.getString("telefono"));
				}
				
				sql= """
						SELECT id_correo, id_contacto,correo 
						FROM agenda.correos 
						where id_contacto = ?
					""";
				
				PreparedStatement psEmail = con.prepareStatement(sql);
				psEmail.setInt(1, rs.getInt("idcontactos"));
				ResultSet rsEmail = psEmail.executeQuery();
				
				while (rsEmail.next()) {
					contacto.addEmails(rsEmail.getString("correo"));
				}
				
				agendaContactosNombre.add(contacto);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return agendaContactosNombre;
	}

	@Override
	public Set<Contacto> buscarTodos() {
		String sql = "SELECT idcontactos, nombre, apellidos, apodo, tipo_via, "
				+ "via, numero, piso, puerta, codigo_postal, ciudad, provincia "
				+ "FROM agenda.contactos";
		
		Set<Contacto> agendaContactos = new TreeSet<Contacto>();
		
		try (Connection con = ds.getConnection()) {
			PreparedStatement ps = con.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				Contacto contacto = new Contacto();	
				contacto.setIdContacto(rs.getInt("idcontactos"));
				contacto.setNombre(rs.getString("nombre"));
				contacto.setApellidos(rs.getString("apellidos"));
				contacto.setApodo(rs.getString("apodo"));
				
				Domicilio dom = new Domicilio();
				dom.setTipoVia(rs.getString("tipo_via"));
				dom.setVia(rs.getString("via"));
				dom.setNumero(rs.getInt("numero"));
				dom.setPiso(rs.getInt("piso"));
				dom.setPuerta(rs.getString("puerta"));
				dom.setCodigoPostal(rs.getString("codigo_postal"));
				dom.setCiudad(rs.getString("ciudad"));
				dom.setProvincia(rs.getString("provincia"));
				contacto.setDom(dom);
				
				
				sql = """
						SELECT id_telefono, id_contacto, telefono 
						FROM agenda.telefonos
						where id_contacto = ?
					""";
				
				PreparedStatement psTelf = con.prepareStatement(sql);
				psTelf.setInt(1, rs.getInt("idcontactos"));
				ResultSet rsTelf = psTelf.executeQuery();
				
				while (rsTelf.next()) {
//					Set<String> telefonos = new TreeSet<String>();
//					telefonos.add(rsTelfs.getString("telefono"));
//					contacto.setTelefonos(telefonos);
					contacto.addTelefonos(rsTelf.getString("telefono"));
				}
				
				sql= """
						SELECT id_correo, id_contacto,correo 
						FROM agenda.correos 
						where id_contacto = ?
					""";
				
				PreparedStatement psEmail = con.prepareStatement(sql);
				psEmail.setInt(1, rs.getInt("idcontactos"));
				ResultSet rsEmail = psEmail.executeQuery();
				
				while (rsEmail.next()) {
					contacto.addEmails(rsEmail.getString("correo"));
				}
				
				agendaContactos.add(contacto);
			}
			
			
			
		} catch (SQLException e) {
			System.err.println("No ha sido posible consultar los contactos "
					+ "de la base de datos");
			
			e.printStackTrace();
		}
		
		
		return agendaContactos;
	}
	

}
