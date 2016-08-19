package org.netbeans.modules.bamboo.rest;

import org.netbeans.modules.bamboo.glue.BuildProject;
import org.netbeans.modules.bamboo.glue.InstanceValues;
import org.netbeans.modules.bamboo.glue.ProjectsProvideable;

import org.openide.util.Lookup;
import org.openide.util.lookup.ServiceProvider;

import java.util.Collection;


/**
 */
@ServiceProvider(service = BambooInstanceProduceable.class)
public class BambooInstanceFactory implements BambooInstanceProduceable {
    private BambooServiceAccessable instanceAccessor;

    public BambooInstanceFactory() {
        initClient();
    }

    private void initClient() {
        this.instanceAccessor = Lookup.getDefault().lookup(BambooServiceAccessable.class);
    }

    @Override
    public ProjectsProvideable create(final InstanceValues values) {
        Collection<BuildProject> projects = instanceAccessor.getProjects(values);
        DefaultBambooInstance instance = new DefaultBambooInstance(values);

        //TODO get version from server
        instance.setVersion("0815");

        instance.setProjects(projects);

        return instance;
    }
}
