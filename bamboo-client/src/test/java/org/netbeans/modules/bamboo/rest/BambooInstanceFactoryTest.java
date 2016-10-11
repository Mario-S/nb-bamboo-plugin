package org.netbeans.modules.bamboo.rest;

import java.util.Optional;
import org.netbeans.modules.bamboo.glue.BambooServiceAccessable;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.netbeans.modules.bamboo.model.BambooInstance;
import org.netbeans.modules.bamboo.glue.InstanceValues;
import org.netbeans.modules.bamboo.model.VersionInfo;
import org.netbeans.modules.bamboo.mock.MockRestClient;
import org.netbeans.modules.bamboo.model.ProjectVo;

import static java.util.Collections.singletonList;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static org.mockito.BDDMockito.given;
import static org.openide.util.Lookup.getDefault;

/**
 *
 * @author spindizzy
 */
@RunWith(MockitoJUnitRunner.class)
public class BambooInstanceFactoryTest {
    @Mock
    private BambooServiceAccessable delegate;
    @Mock
    private InstanceValues values;
    
    private BambooInstanceFactory classUnderTest;
    
    @Before
    public void setUp() {
        MockRestClient client = (MockRestClient) getDefault().lookup(BambooServiceAccessable.class);
        client.setDelegate(delegate);
        classUnderTest = new BambooInstanceFactory();
    }
    
     /**
     * Test of create method, of class BambooInstanceFactory.
     */
    @Test
    public void testCreate_ValidValues_ExpectInstanceWithVersionInfo() {
        VersionInfo versionInfo = new VersionInfo();
        versionInfo.setBuildNumber(1);
        given(delegate.getVersionInfo(values)).willReturn(versionInfo);
        given(delegate.existsService(values)).willReturn(true);
        Optional<BambooInstance> result = classUnderTest.create(values);
        assertThat(result.get().getVersionInfo().getBuildNumber(), is(1));
    }

    /**
     * Test of create method, of class BambooInstanceFactory.
     */
    @Test
    public void testCreate_ValidValues_ExpectInstanceWithProject() {
        given(delegate.getProjects(values)).willReturn(singletonList(new ProjectVo("")));
        given(delegate.existsService(values)).willReturn(true);
        Optional<BambooInstance> result = classUnderTest.create(values);
        assertThat(result.get().getChildren().isEmpty(), is(false));
    }
    
     /**
     * Test of create method, of class BambooInstanceFactory.
     */
    @Test
    public void testCreate_InvalidUrl_ExpectEmpty() {
        given(delegate.existsService(values)).willReturn(false);
        Optional<BambooInstance> result = classUnderTest.create(values);
        assertThat(result.isPresent(), is(false));
    }
    
}
