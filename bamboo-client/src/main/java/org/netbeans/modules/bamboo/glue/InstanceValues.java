package org.netbeans.modules.bamboo.glue;

import org.netbeans.modules.bamboo.glue.OpenableInBrowser;

/**
 * Interface which will return the necessary values for a connection to the CI
 * server.
 */
public interface InstanceValues extends OpenableInBrowser {
    /**
     * Name of the Bamboo instance.
     *
     * @return instance name
     */
    String getName();

    /**
     * @return Synchronization interval in minutes.
     */
    int getSyncInterval();
    
    /**
     * Get the user's name used for authentication.
     *
     * @return user name
     */
    String getUsername();

    /**
     * Get the password of the user.
     *
     * @return password as String
     */
    char[] getPassword();
}