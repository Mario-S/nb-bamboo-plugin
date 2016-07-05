package org.netbeans.modules.bamboo.glue;

import org.netbeans.modules.bamboo.model.BambooInstanceConstants;
import org.netbeans.modules.bamboo.model.BambooInstanceProperties;
import org.netbeans.modules.bamboo.model.DefaultBambooInstance;

import org.openide.util.Exceptions;
import org.openide.util.Lookup;
import org.openide.util.NbPreferences;
import org.openide.util.lookup.AbstractLookup;
import org.openide.util.lookup.InstanceContent;
import org.openide.util.lookup.ServiceProvider;

import java.awt.EventQueue;

import java.util.logging.Logger;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;


/**
 * @author spindizzy
 */
@ServiceProvider(service = InstanceManageable.class)
public class DefaultInstanceManager implements InstanceManageable {
    private static final Logger LOG = Logger.getLogger(DefaultInstanceManager.class.getName());

    private final InstanceContent content;
    private final Lookup lookup;

    public DefaultInstanceManager() {
        this.content = new InstanceContent();
        this.lookup = new AbstractLookup(content);
    }

    @Override
    public Lookup getLookup() {
        return lookup;
    }

    @Override
    public InstanceContent getContent() {
        return content;
    }

    @Override
    public void addInstance(final DefaultInstanceValues values) {
        EventQueue.invokeLater(() -> { add(values); });
    }

    void add(final InstanceValues values) {
        DefaultBambooInstance instance = new DefaultBambooInstance(values);
        BambooInstanceProperties props = new BambooInstanceProperties(instancesPrefs());
        props.copyProperties(instance);
        instance.setProperties(props);

        content.add(instance);
    }

    @Override
    public void removeInstance(final BambooInstance instance) {
        try {
            instance.getPreferences().removeNode();
            content.remove(instance);
        } catch (BackingStoreException ex) {
            Exceptions.printStackTrace(ex);
        }
    }

    @Override
    public boolean existsInstance(final String name) {
        try {
            String[] names = instancesPrefs().childrenNames();

            for (String prefName : names) {
                if (prefName.equals(name)) {
                    return true;
                }
            }
        } catch (BackingStoreException ex) {
            Exceptions.printStackTrace(ex);
        }

        return false;
    }

    @Override
    public void loadInstances() {
        Preferences prefs = instancesPrefs();

        try {
            String[] names = prefs.childrenNames();

            for (String name : names) {
                loadInstance(prefs, name);
            }

            LOG.finer(String.format("loaded nodes: %s", names.length));
        } catch (BackingStoreException ex) {
            Exceptions.printStackTrace(ex);
        }
    }

    private void loadInstance(final Preferences prefs, final String name) {
        BambooInstanceProperties props = new BambooInstanceProperties(prefs);
        props.put(BambooInstanceConstants.INSTANCE_NAME, name);

        DefaultBambooInstance instance = new DefaultBambooInstance();
        instance.setProperties(props);
        content.add(instance);
    }

    Preferences instancesPrefs() {
        return NbPreferences.forModule(InstanceManageable.class).node("instances");
    }
}
