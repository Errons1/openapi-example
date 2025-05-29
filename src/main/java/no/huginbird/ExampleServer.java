package no.huginbird;

import jakarta.servlet.DispatcherType;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.jetty.ee10.servlet.DefaultServlet;
import org.eclipse.jetty.ee10.servlet.FilterHolder;
import org.eclipse.jetty.ee10.servlet.ServletHolder;
import org.eclipse.jetty.ee10.webapp.WebAppContext;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.util.resource.PathResourceFactory;
import org.eclipse.jetty.util.resource.Resource;
import org.glassfish.jersey.servlet.ServletContainer;

import javax.sql.DataSource;
import java.io.File;
import java.io.IOException;
import java.util.EnumSet;

@Slf4j
public class ExampleServer {
    private final Server server;

    public ExampleServer(int port, DataSource dataSource) throws IOException {
        this.server = new Server(port);
        server.setHandler(createWebApp(dataSource));
    }

    private static WebAppContext createWebApp(DataSource dataSource) {
        ExampleResourceConfig config = new ExampleResourceConfig(dataSource);
        WebAppContext webAppContext = new WebAppContext();

        webAppContext.setContextPath("/");
        webAppContext.setInitParameter(DefaultServlet.CONTEXT_INIT + "useFileMappedBuffer", "false");
        webAppContext.addFilter(new FilterHolder(new ExampleFilter(config)), "/*", EnumSet.of(DispatcherType.REQUEST));

        loadStaticWebUi(webAppContext);
        webAppContext.addServlet(new ServletHolder(new ServletContainer(config)), "/api/*");

        return webAppContext;
    }

    private static void loadStaticWebUi(WebAppContext webAppContext) {
        String resourceUrl = ExampleServer.class.getResource("/webui").toExternalForm();
        if (resourceUrl != null) {
            webAppContext.setBaseResourceAsString(resourceUrl);
        } else {
            File devPath = new File("src/main/resources/webui");
            if (devPath.exists()) {
                Resource baseResource = new PathResourceFactory().newResource(devPath.getPath());
                webAppContext.setBaseResource(baseResource);
            } else {
                throw new RuntimeException("Could not find webui resources");
            }
        }
    }

    public void start() throws Exception {
        server.start();
        log.warn("Server is starting on {}", server.getURI().toURL());
    }
}
