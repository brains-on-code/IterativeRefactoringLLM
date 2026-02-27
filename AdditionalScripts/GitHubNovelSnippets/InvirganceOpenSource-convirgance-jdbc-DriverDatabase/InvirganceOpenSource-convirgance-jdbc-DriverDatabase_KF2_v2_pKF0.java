package com.invirgance.convirgance.jdbc;

import com.invirgance.convirgance.ConvirganceException;
import com.invirgance.convirgance.json.JSONArray;
import com.invirgance.convirgance.json.JSONObject;
import com.invirgance.convirgance.source.ClasspathSource;
import com.invirgance.convirgance.storage.Config;
import org.jboss.shrinkwrap.resolver.api.maven.ConfigurableMavenResolverSystem;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;

import javax.sql.DataSource;
import java.io.File;
import java.io.PrintStream;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.net.URLClassLoader;
import java.sql.Driver;
import java.util.Iterator;

class DriverDatabase implements Iterable<JSONObject> {

    private static final String DRIVERS_PROPERTY = "convirgance.jdbc.drivers";
    private static final String USER_HOME = "user.home";
    private static final String LOGGER_LEVEL_PROPERTY = "org.slf4j.simpleLogger.defaultLogLevel";
    private static final String LOGGER_DEFAULT_LEVEL = "error";
    private static final String DRIVERS_CONFIG_PATH = "/database/drivers.json";
    private static final String CONFIG_KEY_NAME = "name";

    private static final String DESCRIPTOR_NAME = "name";
    private static final String DESCRIPTOR_PREFIXES = "prefixes";
    private static final String DESCRIPTOR_ARTIFACT = "artifact";
    private static final String DESCRIPTOR_DRIVER = "driver";
    private static final String DESCRIPTOR_DATASOURCE = "datasource";

    private static final int MAX_SUPPRESSED_LOG_LINES = 3;
    private static final String SLF4J_PREFIX = "SLF4J: ";

    private final Config config;

    public DriverDatabase() {
        PrintStream originalErr = System.err;
        File driversLocation = resolveDriversLocation();

        configureSlf4jLogging(originalErr);

        this.config = new Config(
                new ClasspathSource(DRIVERS_CONFIG_PATH),
                driversLocation,
                CONFIG_KEY_NAME
        );
    }

    private File resolveDriversLocation() {
        String customLocation = System.getProperty(DRIVERS_PROPERTY);
        if (customLocation != null) {
            return new File(customLocation);
        }

        File home = new File(System.getProperty(USER_HOME));
        return new File(new File(new File(home, ".convirgance"), "database"), "drivers");
    }

    private void configureSlf4jLogging(PrintStream originalErr) {
        if (System.getProperty(LOGGER_LEVEL_PROPERTY) != null) {
            return;
        }

        System.setProperty(LOGGER_LEVEL_PROPERTY, LOGGER_DEFAULT_LEVEL);

        System.setErr(new PrintStream(originalErr) {
            private int suppressedCount;

            @Override
            public void println(String str) {
                if (str != null && str.startsWith(SLF4J_PREFIX) && suppressedCount < MAX_SUPPRESSED_LOG_LINES) {
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
        if (name == null) {
            return null;
        }

        for (JSONObject descriptor : this) {
            if (name.equalsIgnoreCase(descriptor.getString(DESCRIPTOR_NAME))) {
                return descriptor;
            }
        }

        return null;
    }

    public JSONObject findDescriptorByURL(String url) {
        if (url == null) {
            return null;
        }

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
        } catch (ClassNotFoundException |
                 NoSuchMethodException |
                 InstantiationException |
                 IllegalAccessException |
                 InvocationTargetException e) {
            throw new ConvirganceException(e);
        }
    }

    public Driver getDriver(JSONObject descriptor) {
        if (descriptor == null || descriptor.isNull(DESCRIPTOR_DRIVER)) {
            return null;
        }

        JSONArray<String> artifacts = descriptor.getJSONArray(DESCRIPTOR_ARTIFACT);
        String driverClassName = descriptor.getString(DESCRIPTOR_DRIVER);

        return (Driver) loadClass(artifacts, driverClassName);
    }

    public DataSource getDataSource(JSONObject descriptor) {
        if (descriptor == null || descriptor.isNull(DESCRIPTOR_DATASOURCE)) {
            return null;
        }

        JSONArray<String> artifacts = descriptor.getJSONArray(DESCRIPTOR_ARTIFACT);
        String dataSourceClassName = descriptor.getString(DESCRIPTOR_DATASOURCE);

        return (DataSource) loadClass(artifacts, dataSourceClassName);
    }

    @Override
    public Iterator<JSONObject> iterator() {
        return config.iterator();
    }
}