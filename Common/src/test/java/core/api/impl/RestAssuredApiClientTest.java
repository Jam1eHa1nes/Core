package core.api.impl;

import core.api.ApiClient;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import io.restassured.response.Response;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class RestAssuredApiClientTest {
    private static HttpServer server;
    private static String baseUrl;

    @BeforeClass
    public static void startServer() throws IOException {
        server = HttpServer.create(new InetSocketAddress(0), 0);
        server.createContext("/echo", new EchoHandler());
        server.start();
        baseUrl = "http://localhost:" + server.getAddress().getPort();
    }

    @AfterClass
    public static void stopServer() {
        if (server != null) server.stop(0);
    }

    @Test
    public void testSetBaseUriAndSimpleGet() {
        ApiClient client = new RestAssuredApiClient();
        client.setBaseUri(baseUrl);

        Response res = client.get("/echo");
        assertEquals(200, res.statusCode());
        assertTrue(res.asString().contains("method=GET"));
        assertTrue(res.asString().contains("path=/echo"));
    }

    @Test
    public void testGetWithQueryAndHeaders() {
        ApiClient client = new RestAssuredApiClient();
        client.setBaseUri(baseUrl);

        Map<String, Object> qp = new HashMap<>();
        qp.put("a", 1);
        qp.put("b", "two");
        Map<String, String> headers = new HashMap<>();
        headers.put("X-Test", "yes");

        Response res = client.get("/echo", qp, headers);
        String body = res.asString();
        assertEquals(200, res.statusCode());
        assertTrue(body.contains("query[a]=1"));
        assertTrue(body.contains("query[b]=two"));
        // header names can vary in case depending on client/server; compare case-insensitively
        assertTrue(body.toLowerCase().contains("header[x-test]=yes"));
    }

    @Test
    public void testPostVariants() {
        ApiClient client = new RestAssuredApiClient();
        client.setBaseUri(baseUrl);

        Response r1 = client.post("/echo");
        assertEquals(200, r1.statusCode());
        assertTrue(r1.asString().contains("method=POST"));

        Map<String, Object> qp = Map.of("q", "x");
        Map<String, String> headers = Map.of("H", "v");
        Map<String, Object> bodyObj = Map.of("name", "Alice");

        Response r2 = client.post("/echo", bodyObj);
        assertEquals(200, r2.statusCode());
        assertTrue(r2.asString().contains("body={\"name\":\"Alice\"}"));

        Response r3 = client.post("/echo", qp, bodyObj);
        assertEquals(200, r3.statusCode());
        String b3 = r3.asString();
        assertTrue(b3.contains("query[q]=x"));
        assertTrue(b3.contains("body={\"name\":\"Alice\"}"));

        Response r4 = client.post("/echo", qp, headers, bodyObj);
        String b4 = r4.asString();
        assertEquals(200, r4.statusCode());
        assertTrue(b4.contains("header[H]=v"));
        assertTrue(b4.contains("query[q]=x"));
        assertTrue(b4.contains("body={\"name\":\"Alice\"}"));
    }

    @Test
    public void testPutVariants() {
        ApiClient client = new RestAssuredApiClient();
        client.setBaseUri(baseUrl);

        Response r1 = client.put("/echo");
        assertEquals(200, r1.statusCode());
        assertTrue(r1.asString().contains("method=PUT"));

        Map<String, Object> body = Map.of("id", 7);
        Map<String, Object> qp = Map.of("f", 1);
        Map<String, String> headers = Map.of("A", "b");

        assertTrue(client.put("/echo", body).asString().contains("body={\"id\":7}"));
        assertTrue(client.put("/echo", qp, body).asString().contains("query[f]=1"));
        assertTrue(client.put("/echo", qp, headers, body).asString().contains("header[A]=b"));
    }

    @Test
    public void testPatchVariants() {
        ApiClient client = new RestAssuredApiClient();
        client.setBaseUri(baseUrl);

        Response r1 = client.patch("/echo");
        assertEquals(200, r1.statusCode());
        assertTrue(r1.asString().contains("method=PATCH"));

        Map<String, Object> qp = Map.of("p", 2);
        Map<String, String> headers = Map.of("T", "z");
        Map<String, Object> body = Map.of("flag", true);

        assertTrue(client.patch("/echo", body).asString().contains("body={\"flag\":true}"));
        assertTrue(client.patch("/echo", qp, body).asString().contains("query[p]=2"));
        assertTrue(client.patch("/echo", qp, headers, body).asString().contains("header[T]=z"));
    }

    @Test
    public void testDeleteVariants() {
        ApiClient client = new RestAssuredApiClient();
        client.setBaseUri(baseUrl);

        Response r1 = client.delete("/echo");
        assertEquals(200, r1.statusCode());
        assertTrue(r1.asString().contains("method=DELETE"));

        Map<String, Object> qp = Map.of("rm", "1");
        assertTrue(client.delete("/echo", qp).asString().contains("query[rm]=1"));

        Map<String, String> headers = Map.of("Auth", "tkn");
        assertTrue(client.delete("/echo", qp, headers).asString().contains("header[Auth]=tkn"));
    }

    private static class EchoHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            String method = exchange.getRequestMethod();
            URI uri = exchange.getRequestURI();

            // Capture request body
            String body = readAll(exchange.getRequestBody());

            StringBuilder sb = new StringBuilder();
            sb.append("method=").append(method).append('\n');
            sb.append("path=").append(uri.getPath()).append('\n');

            // Echo query
            String query = uri.getQuery();
            if (query != null && !query.isEmpty()) {
                for (String pair : query.split("&")) {
                    String[] kv = pair.split("=", 2);
                    String key = decode(kv[0]);
                    String val = kv.length > 1 ? decode(kv[1]) : "";
                    sb.append("query[").append(key).append("]=").append(val).append('\n');
                }
            }

            // Echo headers
            exchange.getRequestHeaders().forEach((k, v) -> {
                if (!v.isEmpty()) {
                    sb.append("header[").append(k).append("]=").append(v.get(0)).append('\n');
                }
            });

            if (body != null && !body.isEmpty()) {
                sb.append("body=").append(body).append('\n');
            }

            byte[] bytes = sb.toString().getBytes(StandardCharsets.UTF_8);
            exchange.sendResponseHeaders(200, bytes.length);
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(bytes);
            }
        }

        private static String readAll(InputStream is) throws IOException {
            if (is == null) return "";
            byte[] buf = is.readAllBytes();
            return new String(buf, StandardCharsets.UTF_8);
        }

        private static String decode(String s) {
            return java.net.URLDecoder.decode(s, StandardCharsets.UTF_8);
        }
    }
}
