package dao;

import entities.Cliente;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;

import java.util.List;

public class ClienteDAOImplTest {
    private static ClienteDAOImpl dao;

    @BeforeClass
    public static void setUpClass() {
        dao = new ClienteDAOImpl();
    }

    /**
     *  Test of findAll method, of class ClienteDAOImpl
     */

    @Test
    public void testFindAll() {
        List<Cliente> clientes = dao.findAll();
        // se podrían hacer asserts, o un sout
        System.out.println(clientes);

    }

    @Test
    public void testFindById(){
        System.out.println("--- Test Find By ID ---");

        // 3. DEBES PASAR UN ID (tipo Long)
        Long idBuscado = 4L;
        Cliente cliente = dao.findById(idBuscado);

        // 4. COMPRUEBA (Assert) que el resultado es el esperado
        System.out.println("Cliente 1L encontrado: " + cliente);

        // Comprueba que no es nulo (que lo encontró)
        Assert.assertNotNull("El cliente con ID 1L no debería ser null", cliente);

        // Comprueba que el ID del cliente devuelto es el que pediste
        Assert.assertEquals("El ID del cliente no coincide", idBuscado, cliente.getId());

        // (Recomendado) Prueba también un ID que NO exista
        Long idNoExistente = 999L;
        Cliente clienteNull = dao.findById(idNoExistente);

        System.out.println("Resultado de buscar ID 999L: " + clienteNull);
        // Comprueba que devuelve null
        Assert.assertNull("El cliente con ID 999L debería ser null", clienteNull);
    }

    @Test
    public void testCreate(){
        System.out.println("--- Create Cliente ---");

        // 1. Prepara el cliente (el ID es null porque es nuevo)
        var c1 = new Cliente(null, "Cris", "Ronaldo", "cr7@mail.com", 40);

        // 2. Ejecuta el método y RECOGE el resultado
        Cliente clienteGuardado = dao.create(c1);

        // 3. VERIFICA (Assert) que el método funcionó
        Assert.assertNotNull("El cliente guardado no debería ser nulo", clienteGuardado);

        // 4. VERIFICA que Hibernate asignó un ID al objeto original
        Assert.assertNotNull("El ID del cliente no debería ser nulo después de guardarlo", c1.getId());

        System.out.println("Cliente creado con éxito. ID asignado: " + c1.getId());

        // (Opcional pero recomendable)
        // Puedes verificar que el objeto devuelto es el mismo que enviaste
        Assert.assertEquals(c1.getId(), clienteGuardado.getId());
    }

    @Test
    public void testUpdate(){
        System.out.println("--- Update Cliente ---");

        var c2 = new Cliente(4L, "Kylian", "Mbappé", "km@gmail.com", 25);

        Cliente clienteActualizado = dao.update(c2);
        Assert.assertNotNull("El cliente actualizado no debería ser nulo", clienteActualizado);
        Assert.assertNotNull("El ID del cliente no debería ser nulo después de actualizarlo", c2.getId());
        System.out.println("Cliente guardado con éxito");
        Assert.assertEquals(c2.getId(), clienteActualizado.getId());
   }

   @Test
   public void testDelete(){
       System.out.println("--- Delete Cliente ---");

       dao.deleteById(4L);
       System.out.println("Cliente Eliminado con Éxito");
   }
}
