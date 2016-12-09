package org.netbeans.modules.bamboo.ui;

import org.junit.Before;
import org.junit.Test;
import org.netbeans.modules.bamboo.model.PlanVo;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

/**
 *
 * @author spindizzy
 */
public class IgnoreButtonTest {
    
    private IgnoreButton classUnderTest;
    
    private PlanVo plan;
    
    @Before
    public void setUp() {
        plan = new PlanVo("");
        classUnderTest = new IgnoreButton(plan);
    }

    /**
     * Test of actionPerformed method, of class IgnoreButton.
     */
    @Test
    public void testActionPerformed_PlanPresent_ExpectNoNotify() {
        classUnderTest.actionPerformed(null);

        assertThat(plan.isNotify(), is(false));
    }
    
}