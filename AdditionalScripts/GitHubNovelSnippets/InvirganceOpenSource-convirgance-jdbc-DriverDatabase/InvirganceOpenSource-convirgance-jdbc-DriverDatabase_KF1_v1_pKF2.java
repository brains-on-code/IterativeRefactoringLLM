package com.invirgance.convirgance.jdbc;

import com.invirgance.convirgance.ConvirganceException;
import com.invirgance.convirgance.json.JSONArray;
import com.invirgance.convirgance.json.JSONObject;
import com.invirgance.convirgance.source.ClasspathSource;
import com.invirgance.convirgance.storage.Config;
import java.io.File;
import java.io.PrintStream;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.net.URLClassLoader;
import java.sql.Driver;
import java.util.Iterator;
import javax.sql.DataSource;
import org.jboss.shrinkwrap.resolver.api.var14.ConfigurableMavenResolverSystem;
import org.jboss.shrinkwrap.resolver.api.var14.Maven;

/**
 * Manages JDBC driver configurations and dynamic loading of drivers and data sources.
 */
class JdbcDriverRegistry implements Iterable<JSONObject> {

    private final Config config;

    public JdbcDriverRegistry() {
        PrintStream originalErr = System.err;
        String driversPath = System.getProperty("convirgance.jdbc.drivers");

        File userHome = new File(System.getProperty("user.home"));
        File defaultDriversDir =
                new File(new File(new File(userHome, ".convirgance"), "database"), "drivers");

        File driversDir = (driversPath != null) ? new File(driversPath) : defaultDriversDir;

        if (System.getProperty("org.slf4j.simpleLogger.defaultLogLevel") == null) {
            System.setProperty("org.slf4j.simpleLogger.defaultLogLevel", "error");

            System.setErr(new PrintStream(originalErr) {
                private int suppressedSlf4jMessages;

                @Override
                public void println(String x) {
                    if (x.startsWith("SLF4J: ") && suppressedSlf4jMessages < 3) {
                        suppressedSlf4jMessages++;
                        return;
                    }

                    if (System.err == this) {
                        System.setErr(originalErr);
                    }

                    super.println(x);
                }
            });
        }

        this.config = new Config(new ClasspathSource("/database/drivers.json"), driversDir, "name");
    }

    /**
     * Finds a driver configuration by its name (case-insensitive).
     */
    public JSONObject findByName(String name) {
        for (JSONObject driverConfig : this) {
            if (driverConfig.getString("name").equalsIgnoreCase(name)) {
                return driverConfig;
            }
        }
        return null;
    }

    /**
     * Finds a driver configuration whose prefix list matches the given URL.
     */
    public JSONObject findByUrlPrefix(String url) {
        for (JSONObject driverConfig : this) {
            for (String prefix : (JSONArray<String>) driverConfig.getJSONArray("prefixes")) {
                if (url.startsWith(prefix)) {
                    return driverConfig;
                }
            }
        }
        return null;
    }

    /**
     * Adds or updates a driver configuration.
     */
    public void insert(JSONObject driverConfig) {
        config.insert(driverConfig);
    }

    /**
     * Deletes a driver configuration.
     */
    public void delete(JSONObject driverConfig) {
        config.delete(driverConfig);
    }

    private URLClassLoader createClassLoader(JSONArray<String> artifacts) {
        ConfigurableMavenResolverSystem resolver = Maven.configureResolver();
        URL[] urls = resolver
                .withMavenCentralRepo(true)
                .resolve(artifacts)
                .withTransitivity()
                .as(URL.class);

        return new URLClassLoader(urls);
    }

    private Object instantiateFromArtifacts(JSONArray<String> artifacts, String className) {
        URLClassLoader classLoader = createClassLoader(artifacts);
        try {
            Class<?> clazz = classLoader.loadClass(className);
            return clazz.getDeclaredConstructor().newInstance();
        } catch (ClassNotFoundException
                 | NoSuchMethodException
                 | InstantiationException
                 | IllegalAccessException
                 | InvocationTargetException e) {
            throw new ConvirganceException(e);
        }
    }

    /**
     * Creates a JDBC {@link Driver} instance from the given configuration.
     */
    public Driver createDriver(JSONObject driverConfig) {
        if (driverConfig == null || driverConfig.isNull("driver")) {
            return null;
        }
        return (Driver) instantiateFromArtifacts(
                driverConfig.getJSONArray("artifact"),
                driverConfig.getString("driver")
        );
    }

    /**
     * Creates a {@link DataSource} instance from the given configuration.
     */
    public DataSource createDataSource(JSONObject driverConfig) {
        if (driverConfig == null || driverConfig.isNull("datasource")) {
            return null;
        }
        return (DataSource) instantiateFromArtifacts(
                driverConfig.getJSONArray("artifact"),
                driverConfig.getString("datasource")
        );
    }

    @Override
    public Iterator<JSONObject> iterator() {
        return config.iterator();
    }
}