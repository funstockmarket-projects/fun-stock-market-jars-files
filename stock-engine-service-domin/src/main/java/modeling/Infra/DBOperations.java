package modeling.Infra;

import java.util.Collection;
import java.util.Optional;

/**
 * Generic interface for basic database operations.
 *
 * @param <T>  The type of entity this repository will handle
 * @param <ID> The type of the entity's identifier (e.g., Long, String, UUID)
 * @param <O>  The type of the input object for saving
 */
public interface DBOperations<T, ID, O> {

    /**
     * Save an entity to the database.
     *
     * @param o The input object for saving
     * @return The saved entity (usually with updated fields like generated ID)
     */
    T commitIteam(O o);

    /**
     * Find all entities of type T.
     *
     * @return A list of all entities in the database
     */
    Collection<T> findAll();

    /**
     * Find a single entity by its identifier.
     *
     * @param id The identifier to search for
     * @return An Optional containing the entity if found, or empty if not
     */
    Optional<T> findBy(ID id);

}
