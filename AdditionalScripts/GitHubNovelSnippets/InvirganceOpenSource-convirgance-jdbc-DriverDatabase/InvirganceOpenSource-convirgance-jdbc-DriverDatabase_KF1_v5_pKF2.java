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

class JdbcDriverRegistry implements Iterable<JSONObject> {

    private static final String JDBC_DRIVERS_PROPERTY = "convirgance.jdbc.drivers";

    private static final String SLF4J_LOG_LEVEL_PROPERTY = "org.slf4j.simpleLogger.defaultLogLevel";
    private static final String SLF4J_DEFAULT_LOG_LEVEL = "error";
    private static final String SLF4J_PREFIX = "SLF4J: ";
    private static final int MAX_SUPPRESSED_SLF4J_MESSAGES = 3;

    private final Config config;

    public JdbcDriverRegistry() {
        PrintStream originalErr = System.err;
        File driversDir = resolveDriversDirectory();

        configureSlf4jLogging(originalErr);

        this.config = new Config(
                new ClasspathSource("/database/drivers.json"),
                driversDir,
                "name"
        );
    }

    private File resolveDriversDirectory() {
        String driversPath = System.getProperty(JDBC_DRIVERS_PROPERTY);
        if (driversPath != null) {
            return new File(driversPath);
        }

        File userHome = new File(System.getProperty("user.home"));
        return new File(new File(new File(userHome, ".convirgance"), "database"), "drivers");
    }

    private void configureSlf4jLogging(PrintStream originalErr) {
        if (System.getProperty(SLF4J_LOG_LEVEL_PROPERTY) != null) {
            return;
        }

        System.setProperty(SLF4J_LOG_LEVEL_PROPERTY, SLF4J_DEFAULT_LOG_LEVEL);
        System.setErr(new Slf4jSuppressingPrintStream(originalErr));
    }

    private static class Slf4jSuppressingPrintStream extends PrintStream {

        private final PrintStream originalErr;
        private int suppressedSlf4jMessages;

        Slf4jSuppressingPrintStream(PrintStream originalErr) {
            super(originalErr);
            this.originalErr = originalErr;
        }

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
    }

    public JSONObject findByName(String name) {
        for (JSONObject driverConfig : this) {
            if (driverConfig.getString("name").equalsIgnoreCase(name)) {
                return driverConfig;
            }
        }
        return null;
    }

    public JSONObject findByUrlPrefix(String url) {
        for (JSONObject driverConfig : this) {
            JSONArray<String> prefixes = driverConfig.getJSONArray("prefixes");
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
        if (driverConfig == null || driverConfig.isNull("driver")) {
            return null;
        }
        return (Driver) instantiateFromArtifacts(
                driverConfig.getJSONArray("artifact"),
                driverConfig.getString("driver")
        );
    }

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