package no.example;

public class Main {
    public static void main(String[] args) throws Exception {
        ExampleServer server = new ExampleServer(getPortToUse(), DataSource.getDataSource());
        server.start();
    }

    public static int getPortToUse() {
        String port = System.getenv("HTTP_PLATFORM_PORT");
        if (port == null || port.isBlank()) {
            return 8080;
        } else {
            return Integer.parseInt(port);
        }
    }

}
