package org.netbeans.modules.bamboo.ui.nodes;

import java.io.Serializable;
import org.netbeans.modules.bamboo.glue.BambooInstance;

import org.openide.nodes.ChildFactory;
import org.openide.nodes.Node;

import org.openide.util.Lookup;
import org.openide.util.LookupEvent;
import org.openide.util.LookupListener;

import static java.util.Collections.sort;
import java.util.Comparator;
import java.util.List;


/**
 * @author spindizzy
 */
class BambooInstanceNodeFactory extends ChildFactory<BambooInstance>
    implements LookupListener {
    private static final BambooInstanceComparator COMPARATOR = new BambooInstanceComparator();
    private final Lookup lookup;

    private Lookup.Result<BambooInstance> result;

    public BambooInstanceNodeFactory(final Lookup lookup) {
        this.lookup = lookup;
        lookupResult();
    }

    private void lookupResult() {
        result = lookup.lookupResult(BambooInstance.class);
        result.addLookupListener(this);
    }

    @Override
    protected Node createNodeForKey(final BambooInstance key) {
        return new BambooInstanceNode(key);
    }

    @Override
    protected boolean createKeys(final List<BambooInstance> toPopulate) {
        toPopulate.addAll(result.allInstances());
        sort(toPopulate, COMPARATOR);

        return true;
    }

    @Override
    public void resultChanged(final LookupEvent ev) {
        refresh(true);
    }

    private static class BambooInstanceComparator implements Comparator<BambooInstance>, Serializable {

        private static final long serialVersionUID = 1L;
        
        @Override
        public int compare(final BambooInstance o1, final BambooInstance o2) {
            int val = 0;
            
            final String left = o1.getName();
            final String right = o2.getName();
            
            if(left != null && right != null){
                val = left.compareToIgnoreCase(right);
            }
            return val;
        }
    }
}