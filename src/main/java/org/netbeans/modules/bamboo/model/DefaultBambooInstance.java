package org.netbeans.modules.bamboo.model;

import java.util.prefs.Preferences;

/**
 *
 * @author spindizzy
 */
public final class DefaultBambooInstance implements BambooInstance {

    private String name;

    private String url;

    private int sync;

    private BambooInstanceProperties properties;

    public DefaultBambooInstance() {
    }

    public DefaultBambooInstance(String name, String url) {
        this.name = name;
        this.url = url;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int getSyncInterval() {
        return sync;
    }

    public void setSyncInterval(int sync) {
        this.sync = sync;
    }

    @Override
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public Preferences getPreferences() {
        return properties.getPreferences();
    }

    public void setProperties(BambooInstanceProperties properties) {
        this.properties = properties;
        updateFields(properties);
    }

    private void updateFields(BambooInstanceProperties props) throws NumberFormatException {
        this.name = props.get(BambooInstanceConstants.INSTANCE_NAME);
        this.url = props.get(BambooInstanceConstants.INSTANCE_URL);
        String syncProp = props.get(BambooInstanceConstants.INSTANCE_SYNC);
        if (syncProp != null) {
            this.sync = Integer.parseInt(syncProp);
        }
    }

}
