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

class DriverDatabase implements Iterable<JSONObject> {

    private static final String DRIVERS_PROPERTY = "convirgance.jdbc.drivers";
    private static final String USER_HOME_PROPERTY = "user.home";

    private static final String LOGGER_LEVEL_PROPERTY = "org.slf4j.simpleLogger.defaultLogLevel";
    private static final String LOGGER_DEFAULT_LEVEL = "error";
    private static final String SLF4J_PREFIX = "SLF4J: ";

    private static final String DRIVERS_CONFIG_CLASSPATH = "/database/drivers.json";

    private static final String CONFIG_KEY_NAME = "name";

    private static final String DESCRIPTOR_NAME = "name";
    private static final String DESCRIPTOR_PREFIXES = "prefixes";
    private static final String DESCRIPTOR_ARTIFACT = "artifact";
    private static final String DESCRIPTOR_DRIVER = "driver";
    private static final String DESCRIPTOR_DATASOURCE = "datasource";

    private final Config config;

    public DriverDatabase() {
        PrintStream originalErr = System.err;
        File driversLocation = resolveDriversLocation();

        configureSlf4jLogging(originalErr);

        this.config = new Config(
                new ClasspathSource(DRIVERS_CONFIG_CLASSPATH),
                driversLocation,
                CONFIG_KEY_NAME
        );
    }

    private File resolveDriversLocation() {
        String driversProperty = System.getProperty(DRIVERS_PROPERTY);
        if (driversProperty != null) {
            return new File(driversProperty);
        }

        File userHome = new File(System.getProperty(USER_HOME_PROPERTY));
        return new File(new File(new File(userHome, ".convirgance"), "database"), "drivers");
    }

    private void configureSlf4jLogging(PrintStream originalErr) {
        if (System.getProperty(LOGGER_LEVEL_PROPERTY) != null) {
            return;
        }

        System.getProperty(LOGGER_LEVEL_PROPERTY, LOGGER_DEFAULT_LEVEL);

        System.setErr(new PrintStream(originalErr) {
            private int suppressedCount;

            @Override
            public void println(String str) {
                if (str.startsWith(SLF4J_PREFIX) && suppressedCount < 3) {
                    suppressedCount++;
                    return;
                }

                if (System.err == this) {
                    System.setErr(originalErr);
                }

                super.println(str);
            }
        });
    }

    public JSONObject findDescriptorByName(String name) {
        for (JSONObject descriptor : this) {
            if (descriptor.getString(DESCRIPTOR_NAME).equalsIgnoreCase(name)) {
                return descriptor;
            }
        }
        return null;
    }

    public JSONObject findDescriptorByURL(String url) {
        for (JSONObject descriptor : this) {
            JSONArray<String> prefixes = descriptor.getJSONArray(DESCRIPTOR_PREFIXES);
            for (String prefix : prefixes) {
                if (url.startsWith(prefix)) {
                    return descriptor;
                }
            }
        }
        return null;
    }

    public void saveDescriptor(JSONObject descriptor) {
        config.insert(descriptor);
    }

    public void deleteDescriptor(JSONObject descriptor) {
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

    public Driver getDriver(JSONObject descriptor) {
        if (descriptor == null || descriptor.isNull(DESCRIPTOR_DRIVER)) {
            return null;
        }

        return (Driver) loadClass(
                descriptor.getJSONArray(DESCRIPTOR_ARTIFACT),
                descriptor.getString(DESCRIPTOR_DRIVER)
        );
    }

    public DataSource getDataSource(JSONObject descriptor) {
        if (descriptor == null || descriptor.isNull(DESCRIPTOR_DATASOURCE)) {
            return null;
        }

        return (DataSource) loadClass(
                descriptor.getJSONArray(DESCRIPTOR_ARTIFACT),
                descriptor.getString(DESCRIPTOR_DATASOURCE)
        );
    }

    @Override
    public Iterator<JSONObject> iterator() {
        return config.iterator();
    }
}