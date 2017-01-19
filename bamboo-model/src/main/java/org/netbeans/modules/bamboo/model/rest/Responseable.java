package org.netbeans.modules.bamboo.model.rest;

import java.util.Collection;

/**
 *
 * @author Mario Schroeder
 */
public interface Responseable<T>{
    Collection<T> asCollection();
    
    int getMaxResult();
    
    int getSize();
}
