/*
 * The MIT License
 * 
 * Copyright 2025 jbanes.
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and
 * associated documentation files (the "Software"), to deal in the Software without restriction,
 * including without limitation the rights to use, copy, modify, merge, publish, distribute,
 * sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in all copies or
 * substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT
 * NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT
 * OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
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
import org.jboss.shrinkwrap.resolver.api.maven.ConfigurableMavenResolverSystem;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;

/**
 * Manages JDBC driver descriptors.
 *
 * <p>Descriptors are stored in a user-specific configuration directory and can be used to:
 * <ul>
 *   <li>Look up drivers and data sources by database name or JDBC URL prefix</li>
 *   <li>Resolve and load driver classes via Maven artifacts</li>
 *   <li>Add, update, and remove driver descriptors</li>
 * </ul>
 */
class DriverDatabase implements Iterable<JSONObject> {

    private static final String DEFAULT_CONFIG_PATH = ".convirgance/database/drivers";
    private static final String SYSTEM_PROPERTY_DRIVERS = "convirgance.jdbc.drivers";
    private static final String MAVEN_LOG_LEVEL_PROPERTY = "org.slf4j.simpleLogger.defaultLogLevel";
    private static final String MAVEN_LOG_LEVEL_ERROR = "error";

    private final Config config;

    /**
     * Creates a new {@code DriverDatabase}.
     *
     * <p>Configuration is loaded from:
     * <ol>
     *   <li>Classpath resource: {@code /database/drivers.json}</li>
     *   <li>User configuration directory:
     *       {@code ${user.home}/.convirgance/database/drivers}, unless overridden by the
     *       {@code convirgance.jdbc.drivers} system property.</li>
     * </ol>
     *
     * <p>Also configures Maven logging to reduce noise on stderr.
     */
    public DriverDatabase() {
        PrintStream originalErr = System.err;
        String overridePath = System.getProperty(SYSTEM_PROPERTY_DRIVERS);

        File home = new File(System.getProperty("user.home"));
        File defaultLocation = new File(new File(new File(home, ".convirgance"), "database"), "drivers");
        File location = overridePath != null ? new File(overridePath) : defaultLocation;

        configureMavenLogging(originalErr);

        this.config = new Config(new ClasspathSource("/database/drivers.json"), location, "name");
    }

    /**
     * Returns the descriptor for the database with the given name (case-insensitive).
     *
     * @param name database name (e.g. {@code "PostgreSQL"})
     * @return the matching descriptor, or {@code null} if none is found
     */
    public JSONObject findDescriptorByName(String name) {
        for (JSONObject descriptor : this) {
            if (descriptor.getString("name").equalsIgnoreCase(name)) {
                return descriptor;
            }
        }
        return null;
    }

    /**
     * Returns the descriptor whose configured URL prefix matches the given JDBC URL.
     *
     * <p>Example: for URL {@code "jdbc:derby:classpath:SomeDatabaseName"}, this method searches
     * descriptors whose {@code prefixes} array contains a value that is a prefix of the URL.
     *
     * @param url a full JDBC URL or just the prefix
     * @return the matching descriptor, or {@code null} if none is found
     */
    public JSONObject findDescriptorByURL(String url) {
        for (JSONObject descriptor : this) {
            JSONArray<String> prefixes = descriptor.getJSONArray("prefixes");
            for (String prefix : prefixes) {
                if (url.startsWith(prefix)) {
                    return descriptor;
                }
            }
        }
        return null;
    }

    /**
     * Inserts or updates a database descriptor in the user configuration.
     *
     * @param descriptor descriptor to save
     */
    public void saveDescriptor(JSONObject descriptor) {
        config.insert(descriptor);
    }

    /**
     * Removes a database descriptor from the user configuration.
     *
     * @param descriptor descriptor to delete
     */
    public void deleteDescriptor(JSONObject descriptor) {
        config.delete(descriptor);
    }

    /**
     * Resolves the given Maven artifacts and returns a class loader for them.
     *
     * @param artifacts list of Maven coordinates
     * @return a {@link URLClassLoader} containing the resolved artifacts
     */
    private URLClassLoader getClassLoader(JSONArray<String> artifacts) {
        ConfigurableMavenResolverSystem maven = Maven.configureResolver();
        URL[] files = maven.withMavenCentralRepo(true)
                           .resolve(artifacts)
                           .withTransitivity()
                           .as(URL.class);
        return new URLClassLoader(files);
    }

    /**
     * Loads and instantiates a class from the given Maven artifacts.
     *
     * @param artifacts list of Maven coordinates
     * @param className fully qualified class name
     * @return a new instance of the loaded class
     * @throws ConvirganceException if the class cannot be loaded or instantiated
     */
    private Object loadClass(JSONArray<String> artifacts, String className) {
        URLClassLoader loader = getClassLoader(artifacts);
        try {
            Class<?> clazz = loader.loadClass(className);
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
     * Loads and returns the {@link Driver} defined in the given descriptor.
     *
     * <p>The driver class is resolved via Maven using the descriptor's {@code artifact} field.
     *
     * @param descriptor driver descriptor
     * @return the loaded {@link Driver}, or {@code null} if the descriptor is {@code null} or
     *         does not define a {@code driver} field
     */
    public Driver getDriver(JSONObject descriptor) {
        if (descriptor == null || descriptor.isNull("driver")) {
            return null;
        }
        return (Driver) loadClass(descriptor.getJSONArray("artifact"),
                                  descriptor.getString("driver"));
    }

    /**
     * Loads and returns the {@link DataSource} defined in the given descriptor.
     *
     * <p>The data source class is resolved via Maven using the descriptor's {@code artifact} field.
     *
     * @param descriptor driver descriptor
     * @return the loaded {@link DataSource}, or {@code null} if the descriptor is {@code null} or
     *         does not define a {@code datasource} field
     */
    public DataSource getDataSource(JSONObject descriptor) {
        if (descriptor == null || descriptor.isNull("datasource")) {
            return null;
        }
        return (DataSource) loadClass(descriptor.getJSONArray("artifact"),
                                      descriptor.getString("datasource"));
    }

    /**
     * Returns an iterator over all configured driver descriptors.
     */
    @Override
    public Iterator<JSONObject> iterator() {
        return config.iterator();
    }

    /**
     * Configures Maven logging to reduce noise on stderr.
     *
     * <p>If {@code org.slf4j.simpleLogger.defaultLogLevel} is not already set, it is set to
     * {@code "error"}. Additionally, the first few SLF4J initialization messages are filtered
     * from {@code System.err}.
     *
     * @param originalErr the original {@link System#err} stream
     */
    private void configureMavenLogging(PrintStream originalErr) {
        if (System.getProperty(MAVEN_LOG_LEVEL_PROPERTY) != null) {
            return;
        }

        System.setProperty(MAVEN_LOG_LEVEL_PROPERTY, MAVEN_LOG_LEVEL_ERROR);

        System.setErr(new PrintStream(originalErr) {
            private int slf4jMessageCount;

            @Override
            public void println(String str) {
                if (str != null && str.startsWith("SLF4J: ") && slf4jMessageCount < 3) {
                    slf4jMessageCount++;
                    return;
                }

                if (System.err == this) {
                    System.setErr(originalErr);
                }

                super.println(str);
            }
        });
    }
}