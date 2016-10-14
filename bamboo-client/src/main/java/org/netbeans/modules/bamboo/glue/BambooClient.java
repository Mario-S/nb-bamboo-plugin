package org.netbeans.modules.bamboo.glue;

import org.netbeans.modules.bamboo.model.VersionInfo;
import java.util.Collection;
import org.netbeans.modules.bamboo.model.InstanceValues;
import org.netbeans.modules.bamboo.model.ProjectVo;

/**
 * This interface defines methods to access the Bamboo server.
 *
 * @author spindizzy
 */
public interface BambooClient {
    
    /**
     * This method returns <code>true</code> when the url can be reached, if not it returns <code>false</code>.
     *
     * @return <code>true</code> when server is present, otherwhise <code>false</code>
     */
    boolean existsService();
  
    /**
     * Return a collection of available {@link BuildProject}.
     *
     * @return the projects.
     */
    Collection<ProjectVo> getProjects();

     /**
     * This method updated the given projects.It will change the content of the given projects parameter.
     *
     * @param projects projects to be updated
     */
    void updateProjects(Collection<ProjectVo> projects);

    /**
     * This method returns information about the Bamboo server.
     *
     * @return information about the version.
     */
    VersionInfo getVersionInfo();
}