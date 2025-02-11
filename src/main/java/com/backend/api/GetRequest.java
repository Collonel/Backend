package com.backend.api;

import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.time.Instant;
import org.json.JSONObject;

public class GetRequest {

    public static void main(String[] args) throws IOException {
        int port = 8080;
        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);

        server.createContext("/", new InfoHandler());
        server.setExecutor(null);
        server.start();

        System.out.println("Server started at http://localhost:" + port);
    }

    //Handler for the API endpoint
    static class InfoHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            //prep the Json Response
            if ("GET".equals(exchange.getRequestMethod())) {
                JSONObject jsonResponse = new JSONObject();
                jsonResponse.put("email", "cmusani@gmail.com");
                jsonResponse.put("current_datetime", Instant.now().toString());
                jsonResponse.put("github_url", "https://github.com/Collonel/Backend");

                //set response headers
                byte[] response = jsonResponse.toString().getBytes();
                exchange.getResponseHeaders().add("Content-Type", "application/json");
                exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*"); // Enable CORS Handling
                exchange.sendResponseHeaders(200, response.length);

                //write the response
                OutputStream os = exchange.getResponseBody();
                os.write(response);
                os.close();
            } else {
                //Handle unsupported http methods
                exchange.sendResponseHeaders(405, -1); // Method Not Allowed
            }
        }
    }
}
