package org.netbeans.modules.bamboo.ui.wizard;

import org.junit.Before;
import org.junit.Test;

import org.junit.runner.RunWith;

import static org.mockito.BDDMockito.given;

import org.mockito.InOrder;

import static org.mockito.Matchers.anyString;

import org.mockito.Mock;

import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.verify;

import static org.mockito.internal.util.reflection.Whitebox.setInternalState;

import org.mockito.runners.MockitoJUnitRunner;

import org.netbeans.modules.bamboo.glue.BambooInstance;
import org.netbeans.modules.bamboo.glue.InstanceManageable;

import org.openide.util.Task;

import java.beans.PropertyChangeEvent;


/**
 * @author spindizzy
 */
@RunWith(MockitoJUnitRunner.class)
public class AddInstanceWorkerTest {
    @Mock
    private AbstractDialogAction action;
    @Mock
    private InstanceManageable instanceManager;
    @Mock
    private InstancePropertiesForm form;
    @Mock
    private BambooInstance instance;

    private AddInstanceWorker classUnderTest;

    @Before
    public void setUp() {
        given(action.getInstanceManager()).willReturn(instanceManager);
        classUnderTest = new AddInstanceWorker(action);
        given(form.getInstanceName()).willReturn("test");
    }

    @Test
    public void testExecute_Cancel() {
        classUnderTest.execute(form);
        classUnderTest.cancel();
        verify(form).getPassword();
    }

    @Test
    public void testPropertyChangeEvent_InstanceCreated() {
        PropertyChangeEvent ev =
            new PropertyChangeEvent(this, WorkerEvents.INSTANCE_CREATED.name(), null, instance);
        classUnderTest.propertyChange(ev);

        InOrder order = inOrder(instanceManager, action);
        order.verify(instanceManager).addInstance(instance);
        order.verify(action).onDone();
    }

    @Test
    public void testTaskFinished_Cancel() {
        classUnderTest.execute(form);
        setInternalState(classUnderTest, "cancel", true);

        Task task = new Task(null);
        classUnderTest.taskFinished(task);
        verify(instanceManager).removeInstance(anyString());
    }
}
