package no.example;

import no.example.pets.PetsApiImpl;
import org.glassfish.jersey.server.ResourceConfig;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class ExampleResourceConfig extends ResourceConfig {
    private final ThreadLocal<Connection> requestConnection = new ThreadLocal<>();
    private final DataSource dataSource;

    public ExampleResourceConfig(DataSource dataSource) {
        super(PetsApiImpl.class);
        this.dataSource = dataSource;
    }

    public Connection createConnectionForRequest() throws SQLException {
        requestConnection.set(dataSource.getConnection());
        return requestConnection.get();
    }

    public void cleanRequestConnection() {
        requestConnection.remove();
    }
}
