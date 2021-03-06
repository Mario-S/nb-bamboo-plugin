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
package org.netbeans.modules.bamboo.client.rest;

import java.beans.PropertyChangeListener;
import java.util.List;
import java.util.prefs.Preferences;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.mockito.junit.jupiter.MockitoExtension;
import org.netbeans.modules.bamboo.client.glue.InstanceConstants;
import org.netbeans.modules.bamboo.model.rcp.BambooInstance;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 *
 * @author Mario Schroeder
 */
@ExtendWith(MockitoExtension.class)
class BambooInstancePropertiesTest {
    
    @Mock
    private BambooInstance instance;
    @Mock
    private Preferences preferences;
    @Mock
    private PropertyChangeListener listener;
    @InjectMocks
    private BambooInstanceProperties classUnderTest;

    /**
     * Test of copyProperties method, of class BambooInstanceProperties.
     */
    @Test
    void testCopyProperties() {
        given(instance.getPassword()).willReturn(new char[]{'a'});
        classUnderTest.copyProperties(instance);
        verify(instance).getName();
    }

    /**
     * Test of remove method, of class BambooInstanceProperties.
     */
    @Test
    void testRemove() {
        String key = "a";
        String expResult = "b";
        classUnderTest.put(key, expResult);
        
        String result = classUnderTest.remove(key);
        assertEquals(expResult, result);
    }

    /**
     * Test of isPersisted method, of class BambooInstanceProperties.
     */
    @Test
    void testIsPersisted_True() {
        boolean expResult = true;
        boolean result = classUnderTest.isPersisted();
        assertEquals(expResult, result);
    }


    /**
     * Test of getCurrentListeners method, of class BambooInstanceProperties.
     */
    @Test
    void testGetCurrentListeners() {
        classUnderTest.addPropertyChangeListener(listener);
        List<PropertyChangeListener> result = classUnderTest.getCurrentListeners();
        assertEquals(1, result.size());
    }

    /**
     * Test of getPreferences method, of class BambooInstanceProperties.
     */
    @Test
    void testGetPreferences() {
        String name = "foo";
        given(preferences.node(name)).willReturn(preferences);
        classUnderTest.put(InstanceConstants.PROP_NAME, name);
        Preferences result = classUnderTest.getPreferences();
        assertNotNull(result);
    }
    
}
