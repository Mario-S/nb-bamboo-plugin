package org.netbeans.modules.bamboo.glue;

/**
 * A wrapper for all parameters to be used to create a new BambooInstance
 * @author spindizzy
 */
public class DefaultInstanceValues implements InstanceValues{
    
    private String name;
    
    private String url;
    
    private int syncTime;
    
    private String username;
    
    private char [] password;

    public DefaultInstanceValues() {
    }
    
    public DefaultInstanceValues(DefaultInstanceValues other) {
        this.name = other.getName();
        this.url = other.getUrl();
        this.syncTime = other.getSyncInterval();
        this.username = other.getUsername();
        this.password = other.getPassword();
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
        return syncTime;
    }
    
    public void setSyncInterval(int syncTime) {
        this.syncTime = syncTime;
    }

    @Override
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
    
    @Override
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public char[] getPassword() {
        return password;
    }

    public void setPassword(char[] password) {
        this.password = password;
    }
}
