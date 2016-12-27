package org.netbeans.modules.bamboo.client.rest;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import lombok.extern.java.Log;

import static java.lang.String.format;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

/**
 * This utility provides HTTP related functionality.
 *
 * @author spindizzy
 */
@Log
public class HttpUtility {

    private static final String WRONG_URL = "url is wrong: %s";

    /**
     * Checks if the server behind the url answers with a valid http response.
     *
     * @param url the url to the server.
     * @return true if exists
     */
    public boolean exists(String url) {
        boolean exists = false;
        if (isNotBlank(url)) {
            try {
                URL endpoint = new URL(url);
                URLConnection connection = endpoint.openConnection();
                if (connection instanceof HttpURLConnection) {
                    HttpURLConnection httpConn = (HttpURLConnection) connection;
                    int status = httpConn.getResponseCode();
                    exists = isValid(status);
                }
            } catch (IOException ex) {
                log.info(format(WRONG_URL, ex.getMessage()));
            }
        }
        return exists;
    }

    private boolean isValid(int status) {
        HttpResponseCode code = HttpResponseCode.getCode(status);
        return code.equals(HttpResponseCode.Successful) || code.equals(HttpResponseCode.Redirect) || code.equals(
                HttpResponseCode.Unauthorized);

    }
}