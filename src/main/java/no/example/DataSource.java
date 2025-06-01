package no.example;

import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.flywaydb.core.Flyway;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

@Slf4j
public class DataSource {

    public static HikariDataSource getDataSource() throws IOException {
        try {
            HikariDataSource dataSource = new HikariDataSource();
            dataSource.setAutoCommit(true);
            setDbConnectionInfo(dataSource, "application.properties");

            Flyway flyway = Flyway.configure().dataSource(dataSource).load();
            flyway.migrate();
            return dataSource;

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void setDbConnectionInfo(HikariDataSource dataSource, String file) throws IOException {
        dataSource.setJdbcUrl(System.getenv("DB_URL"));
        dataSource.setUsername(System.getenv("DB_USER"));
        dataSource.setPassword(System.getenv("DB_PASSWORD"));

        Properties properties = new Properties();
        try (FileReader reader = new FileReader(file)) {
            properties.load(reader);
            dataSource.setJdbcUrl(properties.getProperty("jdbc.url"));
            dataSource.setUsername(properties.getProperty("jdbc.username"));
            dataSource.setPassword(properties.getProperty("jdbc.password"));

        } catch (FileNotFoundException ignored) {
//            throw new RuntimeException();
        }
    }
}
