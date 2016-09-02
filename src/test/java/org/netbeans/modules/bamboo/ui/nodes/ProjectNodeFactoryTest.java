package org.netbeans.modules.bamboo.ui.nodes;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import org.junit.runner.RunWith;

import static org.mockito.BDDMockito.given;

import org.mockito.Mock;

import org.mockito.runners.MockitoJUnitRunner;

import org.netbeans.modules.bamboo.glue.BuildProject;
import org.netbeans.modules.bamboo.glue.ProjectsProvideable;

import org.openide.nodes.Node;

import java.util.ArrayList;
import java.util.List;
import static org.hamcrest.CoreMatchers.equalTo;


/**
 * @author spindizzy
 */
@RunWith(MockitoJUnitRunner.class)
public class ProjectNodeFactoryTest {
    @Mock
    private ProjectsProvideable projectsProvideable;

    private ProjectNodeFactory classUnderTest;
    
    private List<BuildProject> projects;

    private BuildProject project;

    @Before
    public void setUp() {
        project = new BuildProject();
        project.setShortName("b");
        
        BuildProject other = new BuildProject();
        other.setShortName("a");

        projects = new ArrayList<>();
        projects.add(project);
        projects.add(other);
        
        given(projectsProvideable.getProjects()).willReturn(projects);

        classUnderTest = new ProjectNodeFactory(projectsProvideable);
    }

    /**
     * Test of createNodeForKey method, of class ProjectNodeFactory.
     */
    @Test
    public void testCreateNodeForKey() {
        Node result = classUnderTest.createNodeForKey(project);
        assertNotNull(result);
    }

    /**
     * Test of createKeys method, of class ProjectNodeFactory.
     */
    @Test
    public void testCreateKeys() {
        List<BuildProject> toPopulate = new ArrayList<>();
        classUnderTest.createKeys(toPopulate);
        assertThat(toPopulate.get(0).getShortName(), equalTo("a"));
    }
}