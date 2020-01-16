/* 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.netbeans.modules.bamboo.ui.nodes;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.util.List;
import javax.swing.Action;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.netbeans.modules.bamboo.model.rcp.BambooInstance;
import org.netbeans.modules.bamboo.model.rcp.ModelChangedValues;
import org.netbeans.modules.bamboo.model.event.ServerConnectionEvent;
import org.openide.util.Lookup;
import org.openide.util.lookup.AbstractLookup;
import org.openide.util.lookup.InstanceContent;

import static java.util.Collections.emptyList;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.netbeans.modules.bamboo.client.glue.InstanceConstants.PROP_SYNC_INTERVAL;

import static org.netbeans.modules.bamboo.ui.nodes.Bundle.Unavailable;
import static org.netbeans.modules.bamboo.ui.nodes.Bundle.Disconnected;

/**
 *
 * @author Mario Schroeder
 */
@RunWith(MockitoJUnitRunner.class)
public class BambooInstanceNodeTest {
    private static final String FOO = "foo";
    
    private InstanceContent content;

    @Mock
    private BambooInstance instance;
    @Mock
    private PropertyChangeEvent event;

    private BambooInstanceNode classUnderTest;

    @Before
    public void setUp() {
        content = new InstanceContent();
        
        Lookup lookup = new AbstractLookup(content);
        given(instance.getName()).willReturn(FOO);
        given(instance.getSyncInterval()).willReturn(1);
        
        classUnderTest = new BambooInstanceNode(instance) {
            @Override
            protected List<? extends Action> findActions(String path) {
                return emptyList();
            }
        };
    }

    /**
     * Test of propertyChange method, of class BambooInstanceNode.
     */
    @Test
    public void testPropertyChange_NoValidProperty_ExpectEmptyHtml() {
        classUnderTest.propertyChange(event);
        String htmlDisplayName = classUnderTest.getHtmlDisplayName();
        assertThat(htmlDisplayName, not(notNullValue()));
    }

    /**
     * Test of propertyChange method, of class BambooInstanceNode.
     */
    @Test
    public void testPropertyChange_AvailabilityPropertyFalse_ExpectUnavailable() {
        content.add(new ServerConnectionEvent(FOO, false));
        classUnderTest.resultChanged(null);
        String htmlDisplayName = classUnderTest.getHtmlDisplayName();
        assertThat(htmlDisplayName, containsString(Unavailable()));
    }
    
    /**
     * Test of propertyChange method, of class BambooInstanceNode.
     */
    @Test
    public void testPropertyChange_SyncIntervalZero_ExpectDisconnected() {
        given(event.getPropertyName()).willReturn(PROP_SYNC_INTERVAL);
        given(instance.getSyncInterval()).willReturn(0);
        classUnderTest.propertyChange(event);
        String htmlDisplayName = classUnderTest.getHtmlDisplayName();
        assertThat(htmlDisplayName, containsString(Disconnected()));
    }

    /**
     * Test of propertyChange method, of class BambooInstanceNode.
     */
    @Test
    public void testPropertyChange_AvailabilityPropertyTrue_ExpectEmptyHtml() {
        given(event.getPropertyName()).willReturn(ModelChangedValues.Available.toString());
        classUnderTest.propertyChange(event);
        String htmlDisplayName = classUnderTest.getHtmlDisplayName();
        assertThat(htmlDisplayName, not(notNullValue()));
    }
    
    @Test
    public void testDestroy_ShouldStopSynchronization() throws IOException{
        classUnderTest.destroy();
        verify(instance).removePropertyChangeListener(any(PropertyChangeListener.class));
    }
}
