package agenda.negocio;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import agenda.modelo.Contacto;

class TestAgenda {

	private static Contacto[] contactos;
	private Agenda agenda; 
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void carga() {
		contactos = new Contacto[10];
		for (int i = 0; i < contactos.length; i++) {
			Contacto c = new Contacto();
			c.setNombre((char)(10 - i + 64) + "Con" + (i+1));
			c.setApellidos((char)(10 - i + 64) + "Con" + (i+1) + " Ape");
			c.setApodo((char)(10 - i + 64) + "Con" + (i+1) + " Apodo");
			contactos[i] = c;
		}
		agenda = new AgendaImpl();
		for (Contacto contacto : contactos) {
			agenda.insertarContacto(contacto);
		}
	}

	@Test
	void testAgendaImpl() {
		agenda = new AgendaImpl();
		assertNotNull(agenda.buscarTodos());
		assertEquals(0, agenda.buscarTodos().size());
	}

	@Test
	void testBuscarContactoPorNombre() {
		assertEquals(0, agenda.buscarContactoPorNombre("cabe").size());
		assertEquals(2, agenda.buscarContactoPorNombre("1").size());
		assertEquals(1, agenda.buscarContactoPorNombre("i").size());
		assertEquals(10, agenda.buscarContactoPorNombre("CON").size());
		Object[] ret = agenda.buscarContactoPorNombre("acon").toArray();
		assertEquals(10, ((Contacto)ret[0]).getIdContacto());
	}

	@Test
	void testInsertarContacto() {
		agenda = new AgendaImpl();
		for (Contacto contacto : contactos) {
			agenda.insertarContacto(contacto);
		}
		assertEquals(contactos.length, agenda.buscarTodos().size());
	}

	@Test
	void testBuscarTodos() {
		assertEquals(10, agenda.buscarTodos().size());
		Object[] ret = agenda.buscarTodos().toArray();
		assertEquals(10, ((Contacto)ret[0]).getIdContacto());
		assertEquals(8, ((Contacto)ret[2]).getIdContacto());
		assertEquals(1, ((Contacto)ret[9]).getIdContacto());

	}

//	@Test
	void testImportarCSV() {
		fail("Not yet implemented");
	}

	@Test
	void testModificar() {
		assertEquals(0, agenda.buscarContactoPorNombre("Cabe").size());
		assertEquals(10, agenda.buscarContactoPorNombre("Con").size());
		Contacto b = new Contacto();
		b.setIdContacto(5);
		b.setApodo("Cabezon");
		b.setNombre("nuevo nombre");
		b.setApellidos("nuevo apellidos");
		agenda.modificar(b);
		assertEquals(1, agenda.buscarContactoPorNombre("Cabe").size());
		assertEquals(9, agenda.buscarContactoPorNombre("Con").size());
	}

}
