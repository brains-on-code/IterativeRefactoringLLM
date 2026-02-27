package com.invirgance.convirgance.jdbc;

import com.invirgance.convirgance.ConvirganceException;
import com.invirgance.convirgance.json.JSONArray;
import com.invirgance.convirgance.json.JSONObject;
import com.invirgance.convirgance.source.ClasspathSource;
import com.invirgance.convirgance.storage.Config;
import org.jboss.shrinkwrap.resolver.api.var14.ConfigurableMavenResolverSystem;
import org.jboss.shrinkwrap.resolver.api.var14.Maven;

import javax.sql.DataSource;
import java.io.File;
import java.io.PrintStream;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.net.URLClassLoader;
import java.sql.Driver;
import java.util.Iterator;

class JdbcDriverRegistry implements Iterable<JSONObject> {

    private static final String DRIVERS_PROPERTY = "convirgance.jdbc.drivers";
    private static final String USER_HOME_PROPERTY = "user.home";
    private static final String SLF4J_LOG_LEVEL_PROPERTY = "org.slf4j.simpleLogger.defaultLogLevel";
    private static final String SLF4J_DEFAULT_LOG_LEVEL = "error";
    private static final String SLF4J_PREFIX = "SLF4J: ";
    private static final int MAX_SUPPRESSED_SLF4J_MESSAGES = 3;
    private static final String CONFIG_CLASSPATH = "/database/drivers.json";
    private static final String CONFIG_KEY_NAME = "name";
    private static final String CONFIG_KEY_PREFIXES = "prefixes";
    private static final String CONFIG_KEY_DRIVER = "driver";
    private static final String CONFIG_KEY_DATASOURCE = "datasource";
    private static final String CONFIG_KEY_ARTIFACT = "artifact";

    private final Config config;

    public JdbcDriverRegistry() {
        PrintStream originalErr = System.err;
        File driversDir = resolveDriversDirectory();

        configureSlf4jLogging(originalErr);

        this.config = new Config(new ClasspathSource(CONFIG_CLASSPATH), driversDir, CONFIG_KEY_NAME);
    }

    private File resolveDriversDirectory() {
        String driversPathProperty = System.getProperty(DRIVERS_PROPERTY);
        if (driversPathProperty != null) {
            return new File(driversPathProperty);
        }

        File userHome = new File(System.getProperty(USER_HOME_PROPERTY));
        return new File(new File(new File(userHome, ".convirgance"), "database"), "drivers");
    }

    private void configureSlf4jLogging(PrintStream originalErr) {
        if (System.getProperty(SLF4J_LOG_LEVEL_PROPERTY) != null) {
            return;
        }

        System.getProperty(SLF4J_LOG_LEVEL_PROPERTY, SLF4J_DEFAULT_LOG_LEVEL);

        System.setErr(new PrintStream(originalErr) {
            private int suppressedSlf4jMessages;

            @Override
            public void println(String x) {
                if (x.startsWith(SLF4J_PREFIX) && suppressedSlf4jMessages < MAX_SUPPRESSED_SLF4J_MESSAGES) {
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

    public JSONObject findByName(String name) {
        for (JSONObject driverConfig : this) {
            if (driverConfig.getString(CONFIG_KEY_NAME).equalsIgnoreCase(name)) {
                return driverConfig;
            }
        }
        return null;
    }

    public JSONObject findByPrefix(String url) {
        for (JSONObject driverConfig : this) {
            JSONArray<String> prefixes = driverConfig.getJSONArray(CONFIG_KEY_PREFIXES);
            for (String prefix : prefixes) {
                if (url.startsWith(prefix)) {
                    return driverConfig;
                }
            }
        }
        return null;
    }

    public void insert(JSONObject driverConfig) {
        config.insert(driverConfig);
    }

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

    public Driver createDriver(JSONObject driverConfig) {
        if (driverConfig == null || driverConfig.isNull(CONFIG_KEY_DRIVER)) {
            return null;
        }
        return (Driver) instantiateFromArtifacts(
                driverConfig.getJSONArray(CONFIG_KEY_ARTIFACT),
                driverConfig.getString(CONFIG_KEY_DRIVER)
        );
    }

    public DataSource createDataSource(JSONObject driverConfig) {
        if (driverConfig == null || driverConfig.isNull(CONFIG_KEY_DATASOURCE)) {
            return null;
        }
        return (DataSource) instantiateFromArtifacts(
                driverConfig.getJSONArray(CONFIG_KEY_ARTIFACT),
                driverConfig.getString(CONFIG_KEY_DATASOURCE)
        );
    }

    @Override
    public Iterator<JSONObject> iterator() {
        return config.iterator();
    }
}