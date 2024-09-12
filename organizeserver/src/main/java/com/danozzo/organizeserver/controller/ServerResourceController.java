package com.danozzo.organizeserver.controller;

import com.danozzo.organizeserver.model.Response;
import com.danozzo.organizeserver.model.Server;
import com.danozzo.organizeserver.service.ServerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Time;
import java.util.concurrent.TimeUnit;

import static com.danozzo.organizeserver.enumaration.Status.SERVER_UP;
import static java.time.LocalDateTime.now;
import static java.util.Map.of;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.IMAGE_PNG_VALUE;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/server")
@RequiredArgsConstructor
public class ServerResourceController {

    private final ServerService serverService;

    @GetMapping("/list")
    public ResponseEntity<Response> getServers() {
        return ResponseEntity.ok(
                Response.builder()
                        .timestamp(now())
                        .data(of("servers", serverService.list(30)))
                        .message("Servers retrieved successfully")
                        .status(OK)
                        .statusCode(OK.value())
                        .build()
        );
    }

    @SneakyThrows
    @GetMapping("/ping/{ipAddress}")
    public ResponseEntity<Response> pingServer(@PathVariable("ipAddress") String ipAddress) {
        Server server = serverService.ping(ipAddress);
        return ResponseEntity.ok(
                Response.builder()
                        .timestamp(now())
                        .data(of("server", server))
                        .message(server.getStatus() == SERVER_UP ? "Ping success" : "Ping failed")
                        .status(OK)
                        .statusCode(OK.value())
                        .build()
        );
    }

    @PostMapping("/save")
    public ResponseEntity<Response> saveServer(@RequestBody @Valid Server server) {

        return ResponseEntity.ok(
                Response.builder()
                        .timestamp(now())
                        .data(of("server", serverService.create(server)))
                        .message("Server created successfully")
                        .status(CREATED)
                        .statusCode(CREATED.value())
                        .build()
        );
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Response> getServer(@PathVariable("id") Long id) {
        return ResponseEntity.ok(
                Response.builder()
                        .timestamp(now())
                        .data(of("server", serverService.get(id)))
                        .message("Server retrieved successfully")
                        .status(OK)
                        .statusCode(OK.value())
                        .build()
        );
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Response> deleteServer(@PathVariable("id") Long id) {
        return ResponseEntity.ok(
                Response.builder()
                        .timestamp(now())
                        .data(of("server", serverService.delete(id)))
                        .message("Server deleted")
                        .status(OK)
                        .statusCode(OK.value())
                        .build()
        );
    }

    @SneakyThrows
    @GetMapping(path = "/image/{fileName}", produces = IMAGE_PNG_VALUE)
    public byte[] getServerImage(@PathVariable("fileName") String fileName) {
        return Files.readAllBytes(Paths.get(System.getProperty("user.home") + "/Downloads/images/" + fileName));
    }
}
