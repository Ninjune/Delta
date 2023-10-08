package dev.ninjune.delta.util;

import dev.ninjune.delta.Delta;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ApiUtils
{
    /**
     * Modified from Cowlection.
     */
    public static BufferedReader request(String url) throws IOException
    {
        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();

        connection.setConnectTimeout(5000);
        connection.setReadTimeout(8000);
        connection.addRequestProperty("User-Agent", "Forge Mod " + Delta.MODNAME + "/" + Delta.VERSION);
        connection.getResponseCode();

        BufferedReader reader;
        InputStream errorStream = connection.getErrorStream();
        if (errorStream != null) {
            reader = new BufferedReader(new InputStreamReader(errorStream));
        } else {
            reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        }

        return reader;
    }
}
