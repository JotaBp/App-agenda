package agenda.modelo;

import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "contactos")
public class Contacto implements Comparable<Contacto>, Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idcontactos")
	private int idContacto;
	private String nombre;
	private String apellidos;
	private String apodo;
	
	@Embedded
	@AttributeOverrides({
		@AttributeOverride(name = "tipoVia", column = @Column(name = "tipo_via")),
		@AttributeOverride(name = "codigoPostal", column = @Column(name = "codigo_postal"))		
	})
	private Domicilio dom;
	
	@ElementCollection
	@CollectionTable(name = "telefonos", joinColumns = @JoinColumn(name = "id_contacto"))
	@Column(name = "telefono")
	private Set<String> telefonos;
	
	@ElementCollection
	@CollectionTable(name = "correos", joinColumns = @JoinColumn(name = "id_contacto"))
	@Column(name = "correo")
	private Set<String> emails;
	
	public Contacto() {
		this.dom = new Domicilio();
		this.telefonos = new LinkedHashSet<String>();
		this.emails = new LinkedHashSet<String>();
	}
	
	
	public int getIdContacto() {
		return idContacto;
	}


	public void setIdContacto(int idContact) {
		this.idContacto = idContact;
	}


	public String getNombre() {
		return nombre;
	}


	public void setNombre(String nombre) {
		this.nombre = nombre;
	}


	public String getApellidos() {
		return apellidos;
	}


	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}


	public String getApodo() {
		return apodo;
	}


	public void setApodo(String apodo) {
		this.apodo = apodo;
	}


	public Domicilio getDom() {
		return dom;
	}


	public void setDom(Domicilio dom) {
		this.dom = dom;
	}


	public Set<String> getTelefonos() {
		return telefonos;
	}


	public void setTelefonos(Set<String> telefonos) {
		this.telefonos = telefonos;
	}


	public Set<String> getEmails() {
		return emails;
	}


	public void setEmails(Set<String> emails) {
		this.emails = emails;
	}
	
	public void addTelefonos(String... telefonos) {
		for (int i = 0; i < telefonos.length; i++) {
			this.telefonos.add(telefonos[i]);
		}
	}
	
	public void addEmails(String...emails) {
		for (String string : emails) {
			this.emails.add(string);
		}
	}
	
	@Override
	public boolean equals(Object objCompared) {
		if(this == objCompared) return true;
		if(objCompared == null) return false;
		if(this.getClass() != objCompared.getClass()) return false;
		Contacto contactCompared = (Contacto) objCompared;
		return this.idContacto == contactCompared.idContacto;
	}

	@Override
	public int hashCode() {
		return idContacto;
	}

	@Override
	public int compareTo(Contacto o) {
		return this.idContacto - o.idContacto;
	}


	@Override
	public String toString() {
		return "Contacto [idContacto=" + idContacto + ", nombre=" + nombre + ", apellidos=" + apellidos + ", apodo="
				+ apodo + ", dom=" + dom + ", telefonos=" + telefonos + ", emails=" + emails + "]";
	}
	
//	static class MyComparator implements Comparator<Contacto>{
//		
//		public int compare(Contacto o1, String o2) {
//			return o1.getNombre().compareTo(o2.getNombre());
//		}
//	}
	
	


}
