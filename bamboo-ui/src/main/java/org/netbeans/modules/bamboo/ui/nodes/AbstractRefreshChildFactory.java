package org.netbeans.modules.bamboo.ui.nodes;

import org.openide.nodes.ChildFactory;

/**
 * Parent factory for factories which support the refreshing of children.
 * 
 * @author spindizzy
 */
abstract class AbstractRefreshChildFactory<T> extends ChildFactory<T> {
    
    abstract void refreshNodes();

}
