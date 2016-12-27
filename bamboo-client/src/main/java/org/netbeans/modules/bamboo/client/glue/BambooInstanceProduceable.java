package org.netbeans.modules.bamboo.client.glue;

import org.netbeans.modules.bamboo.model.rcp.InstanceValues;
import org.netbeans.modules.bamboo.model.rcp.BambooInstance;
import java.util.Optional;


/**
 * This interface can be used for implementation which produce a new {@link BambooInstance}.
 */
public interface BambooInstanceProduceable {
    
    /**
     * Creates a new instance if the given values are for an existing server or empty if the url can not be reached.
     * @param values required values
     * @return a new instance or empty.
     */
    Optional<BambooInstance> create(InstanceValues values);
    
}