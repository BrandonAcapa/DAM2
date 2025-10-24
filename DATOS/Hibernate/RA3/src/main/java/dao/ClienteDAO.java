package dao;

import entities.Cliente;
import java.util.List;

public interface ClienteDAO {
    /**
     * Recuperar todos los clientes de la tabla
     * @return lista de clientes
     */
    List<Cliente> findAll();

    /**
     * Busca un cliente por su id
     * @return cliente
     */
    Cliente findById(Long id);

    /**
     * Inserta un nuevo cliente en la tabla
     * @param cliente
     * @return cliente
     */
    Cliente create(Cliente cliente);
    /**
     * Edita un cliente de la tabla
     * @param cliente
     * @return cliente
     */
    Cliente update(Cliente cliente);
    /**
     * Borra un cliente de la tabla a partir del id
     * @param id
     * @return true si lo ha borrado, false en caso contrario
     */
    boolean deleteById(Long id);
}
