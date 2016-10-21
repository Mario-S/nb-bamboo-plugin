package org.netbeans.modules.bamboo.ui.notification;

import static java.util.Collections.singletonList;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;

import org.mockito.Mock;

import static org.mockito.Mockito.verify;

import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;
import org.netbeans.modules.bamboo.model.BambooInstance;
import org.netbeans.modules.bamboo.model.PlanVo;
import org.netbeans.modules.bamboo.model.ProjectVo;
import org.netbeans.modules.bamboo.model.QueueEvent;
import org.netbeans.modules.bamboo.model.ResultVo;
import org.netbeans.modules.bamboo.model.State;
import org.openide.util.Lookup;

/**
 *
 * @author spindizzy
 */
@RunWith(MockitoJUnitRunner.class)
public class BuildResultNotifyTest {
    @Mock
    private BambooInstance instance;
    @Mock
    private Lookup lookup;
    @Mock
    private Lookup.Result<QueueEvent> result;
    
    @Spy
    private NotifyDelegator delegator;
    
    private PlanResultNotify classUnderTest;
    
    private PlanVo plan;
    
    @Before
    public void setUp() {
        plan = new PlanVo("a");
        plan.setName("test");
        ResultVo resultVo = new ResultVo();
        resultVo.setNumber(1);
        plan.setResult(resultVo);
        
        ProjectVo project = new ProjectVo("");
        project.setChildren(singletonList(plan));
        
        given(instance.getChildren()).willReturn(singletonList(project));
        given(instance.getLookup()).willReturn(lookup);
        given(lookup.lookupResult(QueueEvent.class)).willReturn(result);
        
        classUnderTest = new PlanResultNotify(instance);
        classUnderTest.setDelegator(delegator);
    }

    /**
     * Test of propertyChange method, of class PlanResultNotify.
     */
    @Test
    public void testPropertyChange() {
        ResultVo resultVo = new ResultVo();
        resultVo.setNumber(2);
        resultVo.setState(State.Failed);
        plan.setResult(resultVo);
        verify(delegator).notify(any(BuildResult.class));
    }
    
}
