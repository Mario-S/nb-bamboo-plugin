package org.netbeans.modules.bamboo.ui.actions;

import java.util.Optional;
import javax.swing.Action;

import static org.hamcrest.CoreMatchers.equalTo;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.netbeans.modules.bamboo.LookupContext;
import org.netbeans.modules.bamboo.model.rcp.BambooInstance;
import org.netbeans.modules.bamboo.model.rcp.PlanVo;
import org.netbeans.modules.bamboo.model.rcp.ProjectVo;
import org.netbeans.modules.bamboo.model.rcp.ResultVo;

import static org.mockito.Mockito.verify;

import org.openide.util.Lookup;

import static java.util.Optional.of;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.internal.util.reflection.Whitebox.getInternalState;
import static org.mockito.internal.util.reflection.Whitebox.setInternalState;

/**
 *
 * @author spindizzy
 */
@RunWith(MockitoJUnitRunner.class)
public class ShowChangesActionTest {
    
    private static final String FOO = "foo";
    
    @Mock
    private BambooInstance instance;
   
    private PlanVo plan;

    private ShowChangesAction classUnderTest;
    
    private boolean available;

    @Before
    public void setUp() {
        plan = new PlanVo(FOO){
            @Override
            public boolean isAvailable() {
                return available;
            }
            
        };
        
        Lookup lookup = LookupContext.Instance.getLookup();
        classUnderTest = new ShowChangesAction(lookup);
    }
    
    @After
    public void shutDown() {
        LookupContext.Instance.remove(plan);
    }
    
    @Test
    public void testGetName_ExpectBundle() {
        String name = (String) classUnderTest.getValue(Action.NAME);
        assertThat(name, equalTo(Bundle.CTL_ShowChangesAction()));
    }
    
    @Test
    public void testIsEnabled_NoPlan_ExpectFalse() {
        boolean result = classUnderTest.isEnabled();
        assertThat(result, is(false));
    }

    
     /**
     * Test of actionPerformed method, of class QueuePlanAction.
     */
    @Test
    public void testActionPerformed_ExpectPlanPresent() {
        
        LookupContext.Instance.add(plan);
        
        classUnderTest.actionPerformed(null);
        Optional<PlanVo> opt = (Optional<PlanVo>) getInternalState(classUnderTest, "plan");
        assertThat(opt.isPresent(), is(true));
    }
    
     /**
     * Test of actionPerformed method, of class QueuePlanAction.
     */
    @Test
    public void testResultChanged_PlanEnabled_ExpectEnabled() {
        
        available = true;
        LookupContext.Instance.add(plan);
        
        boolean result = classUnderTest.isEnabled();
        assertThat(result, is(true));
    }

    @Test
    public void testRun_ExpectInvoke() {
        ProjectVo project = new ProjectVo(FOO);
        project.setParent(instance);
        plan.setParent(project);
        ResultVo result = new ResultVo();
        plan.setResult(result);
        
        setInternalState(classUnderTest, "plan", of(plan));
        classUnderTest.run();
        
        verify(instance).attachChanges(result);
    }
}