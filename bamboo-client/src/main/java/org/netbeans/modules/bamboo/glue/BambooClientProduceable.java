package org.netbeans.modules.bamboo.glue;

import java.util.Optional;
import org.netbeans.modules.bamboo.model.InstanceValues;

/**
 * Create a new client for a bamboo instance.
 * @author spindizzy
 */
public interface BambooClientProduceable {
    
    /**
     * This method supplies a new client when the server for the given arguments can bea accessed.
     * If the server is not avialable the result is empty.
     * @param values the necessary values to access the server.
     * @return an {@link Optional} of {@link BambooClient}.
     */
    Optional<BambooClient> newClient(InstanceValues values);
}