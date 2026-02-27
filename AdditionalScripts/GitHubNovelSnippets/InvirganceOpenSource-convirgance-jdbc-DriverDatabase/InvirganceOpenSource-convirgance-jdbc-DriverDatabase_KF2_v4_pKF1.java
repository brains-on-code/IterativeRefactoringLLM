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

    private final Config driverConfiguration;

    public DriverDatabase() {
        PrintStream originalErrorStream = System.err;
        String driversDirectoryProperty = System.getProperty("convirgance.jdbc.drivers");

        File userHomeDirectory = new File(System.getProperty("user.home"));
        File defaultDriversDirectory = new File(
                new File(new File(userHomeDirectory, ".convirgance"), "database"),
                "drivers"
        );

        File driversDirectory = driversDirectoryProperty != null
                ? new File(driversDirectoryProperty)
                : defaultDriversDirectory;

        if (System.getProperty("org.slf4j.simpleLogger.defaultLogLevel") == null) {
            System.getProperty("org.slf4j.simpleLogger.defaultLogLevel", "error");

            System.setErr(new PrintStream(originalErrorStream) {
                private int suppressedSlf4jMessageCount;

                @Override
                public void println(String message) {
                    if (message.startsWith("SLF4J: ") && suppressedSlf4jMessageCount < 3) {
                        suppressedSlf4jMessageCount++;
                        return;
                    }

                    if (System.err == this) {
                        System.setErr(originalErrorStream);
                    }

                    super.println(message);
                }
            });
        }

        this.driverConfiguration = new Config(
                new ClasspathSource("/database/drivers.json"),
                driversDirectory,
                "name"
        );
    }

    public JSONObject findDescriptorByName(String driverName) {
        for (JSONObject driverDescriptor : this) {
            if (driverDescriptor.getString("name").equalsIgnoreCase(driverName)) {
                return driverDescriptor;
            }
        }

        return null;
    }

    public JSONObject findDescriptorByUrl(String jdbcUrl) {
        for (JSONObject driverDescriptor : this) {
            JSONArray<String> urlPrefixes = driverDescriptor.getJSONArray("prefixes");
            for (String urlPrefix : urlPrefixes) {
                if (jdbcUrl.startsWith(urlPrefix)) {
                    return driverDescriptor;
                }
            }
        }

        return null;
    }

    public void saveDescriptor(JSONObject driverDescriptor) {
        driverConfiguration.insert(driverDescriptor);
    }

    public void deleteDescriptor(JSONObject driverDescriptor) {
        driverConfiguration.delete(driverDescriptor);
    }

    private URLClassLoader createClassLoader(JSONArray<String> mavenCoordinates) {
        ConfigurableMavenResolverSystem mavenResolver = Maven.configureResolver();
        URL[] artifactUrls = mavenResolver
                .withMavenCentralRepo(true)
                .resolve(mavenCoordinates)
                .withTransitivity()
                .as(URL.class);

        return new URLClassLoader(artifactUrls);
    }

    private Object instantiateClass(JSONArray<String> mavenCoordinates, String fullyQualifiedClassName) {
        URLClassLoader classLoader = createClassLoader(mavenCoordinates);
        Class<?> loadedClass;

        try {
            loadedClass = classLoader.loadClass(fullyQualifiedClassName);
            return loadedClass.getDeclaredConstructor().newInstance();
        } catch (ClassNotFoundException
                 | NoSuchMethodException
                 | InstantiationException
                 | IllegalAccessException
                 | InvocationTargetException exception) {
            throw new ConvirganceException(exception);
        }
    }

    public Driver getDriver(JSONObject driverDescriptor) {
        if (driverDescriptor == null || driverDescriptor.isNull("driver")) {
            return null;
        }

        JSONArray<String> artifactCoordinates = driverDescriptor.getJSONArray("artifact");
        String driverClassName = driverDescriptor.getString("driver");

        return (Driver) instantiateClass(artifactCoordinates, driverClassName);
    }

    public DataSource getDataSource(JSONObject driverDescriptor) {
        if (driverDescriptor == null || driverDescriptor.isNull("datasource")) {
            return null;
        }

        JSONArray<String> artifactCoordinates = driverDescriptor.getJSONArray("artifact");
        String dataSourceClassName = driverDescriptor.getString("datasource");

        return (DataSource) instantiateClass(artifactCoordinates, dataSourceClassName);
    }

    @Override
    public Iterator<JSONObject> iterator() {
        return driverConfiguration.iterator();
    }
}