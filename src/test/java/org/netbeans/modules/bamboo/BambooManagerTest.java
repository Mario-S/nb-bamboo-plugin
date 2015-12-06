package org.netbeans.modules.bamboo;

import java.util.Collection;
import static org.hamcrest.CoreMatchers.any;
import static org.hamcrest.CoreMatchers.is;
import org.hamcrest.Matcher;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.junit.Assume.assumeThat;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Mockito;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import org.mockito.runners.MockitoJUnitRunner;
import org.netbeans.modules.bamboo.model.BambooInstance;
import org.openide.util.Lookup;
import org.openide.util.LookupEvent;
import org.openide.util.LookupListener;

/**
 *
 * @author spindizzy
 */
@RunWith(MockitoJUnitRunner.class)
public class BambooManagerTest {

    private Lookup.Result<BambooInstance> result;

    @Mock
    private LookupListener listener;

    @Captor
    private ArgumentCaptor<LookupEvent> lookupCaptor;

    @Before
    public void setUp() {
        result = BambooManager.Instance.getLookup().lookupResult(
                BambooInstance.class);
        result.addLookupListener(listener);
    }

    @After
    public void shutDown() {
        result.allInstances().forEach(i -> BambooManager.removeInstance(i));
    }

    private void addInstance() {
        BambooManager.addInstance("a", "", 0);
    }

    /**
     * Test of addInstance method, of class BambooManager.
     */
    @Test
    public void testAddInstance() {
        addInstance();
        assertThat(result.allInstances().isEmpty(), is(false));
        verify(listener).resultChanged(lookupCaptor.capture());
    }

    /**
     * Test of addInstance method, of class BambooManager.
     */
    @Test
    public void testRemoveInstance() {
        addInstance();
        Collection<? extends BambooInstance> instances = result.allInstances();
        assumeThat(instances.isEmpty(), is(false));
        BambooManager.removeInstance(instances.iterator().next());
        verify(listener, times(2)).resultChanged(lookupCaptor.capture());
    }

    @Test
    public void testLoadInstances() {
        addInstance();
        BambooManager.loadInstances();
        verify(listener, atLeast(1)).resultChanged(lookupCaptor.capture());
    }
}