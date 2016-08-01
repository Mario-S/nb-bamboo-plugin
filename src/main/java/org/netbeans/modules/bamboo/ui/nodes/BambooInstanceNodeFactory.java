package org.netbeans.modules.bamboo.ui.nodes;

import org.netbeans.modules.bamboo.glue.BambooInstance;
import org.netbeans.modules.bamboo.glue.PlansProvideable;

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
class BambooInstanceNodeFactory extends ChildFactory<PlansProvideable> implements LookupListener {
    private static final BambooInstanceComparator COMPARATOR = new BambooInstanceComparator();
    private final Lookup lookup;

    private Lookup.Result<PlansProvideable> result;

    public BambooInstanceNodeFactory(final Lookup lookup) {
        this.lookup = lookup;
        lookupResult();
    }

    private void lookupResult() {
        result = lookup.lookupResult(PlansProvideable.class);
        result.addLookupListener(this);
        resultChanged(null);
    }

    @Override
    protected Node createNodeForKey(final PlansProvideable key) {
        return new BambooInstanceNode(key);
    }

    @Override
    protected boolean createKeys(final List<PlansProvideable> toPopulate) {
        toPopulate.addAll(result.allInstances());
        sort(toPopulate, COMPARATOR);

        return true;
    }

    @Override
    public void resultChanged(final LookupEvent ev) {
        refresh(true);
    }

    private static class BambooInstanceComparator implements Comparator<BambooInstance> {
        @Override
        public int compare(final BambooInstance o1, final BambooInstance o2) {
            return o1.getName().compareTo(o2.getName());
        }
    }
}
