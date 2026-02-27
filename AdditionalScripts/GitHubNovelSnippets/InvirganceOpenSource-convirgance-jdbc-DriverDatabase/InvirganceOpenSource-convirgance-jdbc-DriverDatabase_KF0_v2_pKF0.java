/*
 * The MIT License
 * 
 * Copyright 2025 jbanes.
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
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
 * Manages the Driver descriptors used throughout the program.
 * Provides ways to load the classes with Maven, and make descriptor configuration changes.
 *
 * Used by AutomaticDriver to determine the correct driver.
 *
 * Descriptors are stored in the user's home directory under:
 *   ~/.convirgance/database/drivers
 * or in the location specified by the "convirgance.jdbc.drivers" system property.
 *
 * Descriptors are keyed by the "name" field.
 *
 * Each descriptor may contain:
 *   - name        : String
 *   - prefixes    : JSONArray<String>
 *   - driver      : String (FQCN of java.sql.Driver)
 *   - datasource  : String (FQCN of javax.sql.DataSource)
 *   - artifact    : JSONArray<String> (Maven coordinates)
 *
 * @author jbanes
 */
class DriverDatabase implements Iterable<JSONObject> {

    private static final String DRIVERS_PROPERTY = "convirgance.jdbc.drivers";
    private static final String USER_HOME_PROPERTY = "user.home";
    private static final String LOGGER_LEVEL_PROPERTY = "org.slf4j.simpleLogger.defaultLogLevel";
    private static final String LOGGER_LEVEL_ERROR = "error";
    private static final String DEFAULT_CONFIG_CLASSPATH = "/database/drivers.json";

    private static final String DESCRIPTOR_KEY_NAME = "name";
    private static final String DESCRIPTOR_KEY_PREFIXES = "prefixes";
    private static final String DESCRIPTOR_KEY_DRIVER = "driver";
    private static final String DESCRIPTOR_KEY_DATASOURCE = "datasource";
    private static final String DESCRIPTOR_KEY_ARTIFACT = "artifact";

    private final Config config;

    /**
     * Initializes the DriverDatabase.
     * When instantiated, writes to the user's home directory under
     * '~/.convirgance/database/drivers' unless overridden by the
     * "convirgance.jdbc.drivers" system property.
     */
    public DriverDatabase() {
        PrintStream originalErr = System.err;
        File location = resolveConfigLocation();

        configureMavenLogging(originalErr);

        this.config = new Config(
                new ClasspathSource(DEFAULT_CONFIG_CLASSPATH),
                location,
                DESCRIPTOR_KEY_NAME
        );
    }

    private File resolveConfigLocation() {
        String customLocation = System.getProperty(DRIVERS_PROPERTY);
        if (customLocation != null) {
            return new File(customLocation);
        }

        File home = new File(System.getProperty(USER_HOME_PROPERTY));
        return new File(new File(new File(home, ".convirgance"), "database"), "drivers");
    }

    private void configureMavenLogging(PrintStream originalErr) {
        if (System.getProperty(LOGGER_LEVEL_PROPERTY) != null) {
            return;
        }

        System.setProperty(LOGGER_LEVEL_PROPERTY, LOGGER_LEVEL_ERROR);

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

    /**
     * Returns information about the database with the provided name.
     * For example, "PostgreSQL" would return the driver it uses, its data source,
     * and other information such as connection examples and prefixes.
     *
     * @param name Database name
     * @return The descriptor, or null if not found
     */
    public JSONObject findDescriptorByName(String name) {
        if (name == null) {
            return null;
        }

        for (JSONObject descriptor : this) {
            String descriptorName = descriptor.optString(DESCRIPTOR_KEY_NAME, null);
            if (descriptorName != null && descriptorName.equalsIgnoreCase(name)) {
                return descriptor;
            }
        }

        return null;
    }

    /**
     * Returns the database descriptor based on the URL prefix.
     * Example: "jdbc:derby:classpath:SomeDatabaseName"
     *
     * @param url The URL or just the prefix itself.
     * @return The descriptor, or null if not found
     */
    public JSONObject findDescriptorByURL(String url) {
        if (url == null) {
            return null;
        }

        for (JSONObject descriptor : this) {
            JSONArray<String> prefixes = descriptor.getJSONArray(DESCRIPTOR_KEY_PREFIXES);
            for (String prefix : prefixes) {
                if (url.startsWith(prefix)) {
                    return descriptor;
                }
            }
        }

        return null;
    }

    /**
     * Adds or updates the database's descriptor in the user's configuration file.
     *
     * @param descriptor A JSONObject.
     */
    public void saveDescriptor(JSONObject descriptor) {
        if (descriptor == null) {
            return;
        }
        config.insert(descriptor);
    }

    /**
     * Removes a database descriptor.
     *
     * @param descriptor A JSONObject.
     */
    public void deleteDescriptor(JSONObject descriptor) {
        if (descriptor == null) {
            return;
        }
        config.delete(descriptor);
    }

    private URLClassLoader getClassLoader(JSONArray<String> artifacts) {
        ConfigurableMavenResolverSystem maven = Maven.configureResolver();
        URL[] files = maven
                .withMavenCentralRepo(true)
                .resolve(artifacts)
                .withTransitivity()
                .as(URL.class);

        return new URLClassLoader(files);
    }

    private Object loadClass(JSONArray<String> artifacts, String className) {
        if (artifacts == null || className == null) {
            return null;
        }

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
     * Returns and loads the driver in the descriptor.
     * The driver itself is loaded from Maven based on the artifact id.
     *
     * @param descriptor Driver descriptor.
     * @return The driver, or null if not defined
     */
    public Driver getDriver(JSONObject descriptor) {
        if (descriptor == null || descriptor.isNull(DESCRIPTOR_KEY_DRIVER)) {
            return null;
        }

        JSONArray<String> artifacts = descriptor.getJSONArray(DESCRIPTOR_KEY_ARTIFACT);
        String driverClassName = descriptor.getString(DESCRIPTOR_KEY_DRIVER);

        return (Driver) loadClass(artifacts, driverClassName);
    }

    /**
     * Returns and loads the data source in the descriptor.
     * The class object is returned.
     *
     * @param descriptor The descriptor.
     * @return The DataSource, or null if not defined
     */
    public DataSource getDataSource(JSONObject descriptor) {
        if (descriptor == null || descriptor.isNull(DESCRIPTOR_KEY_DATASOURCE)) {
            return null;
        }

        JSONArray<String> artifacts = descriptor.getJSONArray(DESCRIPTOR_KEY_ARTIFACT);
        String dataSourceClassName = descriptor.getString(DESCRIPTOR_KEY_DATASOURCE);

        return (DataSource) loadClass(artifacts, dataSourceClassName);
    }

    // TODO Null pointer if user config missing at this point
    @Override
    public Iterator<JSONObject> iterator() {
        return config.iterator();
    }
}