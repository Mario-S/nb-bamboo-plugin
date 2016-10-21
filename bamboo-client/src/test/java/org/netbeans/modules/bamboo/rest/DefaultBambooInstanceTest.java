package org.netbeans.modules.bamboo.rest;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Collection;

import java.util.prefs.Preferences;
import org.junit.After;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.netbeans.modules.bamboo.model.ProjectVo;

import static java.util.Collections.emptyList;

import java.util.Optional;
import org.mockito.InOrder;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.given;

import org.openide.util.RequestProcessor.Task;
import org.netbeans.modules.bamboo.glue.InstanceConstants;

import static org.mockito.Mockito.inOrder;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.internal.util.reflection.Whitebox.setInternalState;

import org.netbeans.modules.bamboo.model.PlanVo;
import org.netbeans.modules.bamboo.model.QueueEvent;

import static java.util.Collections.singletonList;

/**
 *
 * @author spindizzy
 */
@RunWith(MockitoJUnitRunner.class)
public class DefaultBambooInstanceTest {

    private static final String FOO = "foo";

    @Mock
    private BambooInstanceProperties properties;
    @Mock
    private Preferences preferences;
    @Mock
    private AbstractBambooClient client;

    private PlanVo plan;
    private ProjectVo project;

    private final PropertyChangeListener listener;

    private DefaultBambooInstance classUnderTest;

    private Collection<ProjectVo> projects;

    public DefaultBambooInstanceTest() {
        listener = mock(PropertyChangeListener.class);
    }

    @Before
    public void setUp() {
        classUnderTest = new DefaultBambooInstance(properties);
        
        

        plan = new PlanVo(FOO);
        project = new ProjectVo(FOO);

        classUnderTest.setSyncInterval(5);
        classUnderTest.addPropertyChangeListener(listener);

        given(properties.getPreferences()).willReturn(preferences);
        given(client.existsService()).willReturn(true);

        projects = emptyList();

        setInternalState(classUnderTest, "client", client);
    }

    @After
    public void shutDown() {
        classUnderTest.removePropertyChangeListener(listener);
    }

    private void waitForListener() throws InterruptedException {
        synchronized (listener) {
            listener.wait(1000);
        }
    }

    /**
     * Test of getPreferences method, of class DefaultBambooInstance.
     */
    @Test
    public void testGetPreferences() {
        DefaultBambooInstance instance = new DefaultBambooInstance(properties);
        Preferences result = instance.getPreferences();
        assertNotNull(result);
    }

    /**
     * Test of applyProperties method, of class DefaultBambooInstance.
     */
    @Test
    public void testSetProperties_WithSync() {
        given(properties.get(InstanceConstants.PROP_SYNC_INTERVAL)).willReturn("5");

        DefaultBambooInstance instance = new DefaultBambooInstance(properties);
        assertEquals(5, instance.getSyncInterval());
    }

    /**
     * Test of setChildren method, of class DefaultBambooInstance.
     */
    @Test
    public void testSetProjects_ShouldCreateTask() {
        given(properties.get(InstanceConstants.PROP_SYNC_INTERVAL)).willReturn("5");

        DefaultBambooInstance instance = new DefaultBambooInstance(properties);
        instance.setChildren(projects);
        assertThat(instance.getSynchronizationTask().isPresent(), is(true));
    }

    /**
     * Test of setChildren method, of class DefaultBambooInstance.
     */
    @Test
    public void testSetProjects_ExpectProjectsHaveParent() {
        given(properties.get(InstanceConstants.PROP_SYNC_INTERVAL)).willReturn("5");

        DefaultBambooInstance instance = new DefaultBambooInstance(properties);
        instance.setChildren(projects);
        projects.forEach(pr -> {
            assertThat(pr.getParent().get(), is(instance));
        });
    }

    @Test
    public void testSynchronize_ListenerShouldBeCalled() throws InterruptedException {
        classUnderTest.synchronize();
        waitForListener();
        InOrder order = inOrder(client, listener);
        order.verify(client).getVersionInfo();
        order.verify(listener).propertyChange(any(PropertyChangeEvent.class));
    }

    @Test
    public void testUpdateSyncInterval() {
        DefaultBambooInstance instance = new DefaultBambooInstance(properties);
        instance.updateSyncInterval(1);
        Optional<Task> task = instance.getSynchronizationTask();
        assertThat(task.get().isFinished(), is(false));
    }

    @Test
    public void testQueue_ResponseCode200_ExpectSameBuildNumber() throws InterruptedException {
        project.setChildren(singletonList(plan));
        classUnderTest.setChildren(singletonList(project));
        given(client.queue(plan)).willReturn(200);
        classUnderTest.queue(plan);
        waitForListener();
        assertThat(plan.getResult().getNumber(), is(0));
    }
    

    @Test
    public void testQueue_ResponseCode500_ExpectSameBuildNumber() throws InterruptedException {
        project.setChildren(singletonList(plan));
        classUnderTest.setChildren(singletonList(project));
        given(client.queue(plan)).willReturn(500);
        classUnderTest.queue(plan);
        waitForListener();
        assertThat(plan.getResult().getNumber(), is(0));
    }
    
    @Test
    public void testQueue_ResponseCode200_ExpectEventInLookup() throws InterruptedException {
        project.setChildren(singletonList(plan));
        classUnderTest.setChildren(singletonList(project));
        given(client.queue(plan)).willReturn(200);
        classUnderTest.queue(plan);
        waitForListener();
        
        Collection<? extends QueueEvent> events = classUnderTest.getLookup().lookupAll(QueueEvent.class);
        assertThat(events.isEmpty(), is(false));
    }
}
