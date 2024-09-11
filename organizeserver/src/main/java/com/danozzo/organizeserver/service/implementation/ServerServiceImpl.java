package com.danozzo.organizeserver.service.implementation;

import com.danozzo.organizeserver.model.Server;
import com.danozzo.organizeserver.repository.ServerRepository;
import com.danozzo.organizeserver.service.ServerService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Collection;
import java.util.Random;

import static com.danozzo.organizeserver.enumaration.Status.SERVER_DOWN;
import static com.danozzo.organizeserver.enumaration.Status.SERVER_UP;
import static java.lang.Boolean.TRUE;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ServerServiceImpl implements ServerService {

    private final ServerRepository serverRepository;

    @Override
    public Server create(Server server) {
        log.info("Creating new server: {}", server.getName());
        server.setImageUrl(setServerImageUrl());
        return serverRepository.save(server);
    }


    @Override
    public Server ping(String ipAddress) throws IOException {
        log.info("Pinging server with IP address: {}", ipAddress);
        Server server = serverRepository.findByIpAddress(ipAddress);
        InetAddress address = InetAddress.getByName(ipAddress);
        log.info("Server status before  ping: {}", server.getStatus());
        server.setStatus(address.isReachable(10000) ? SERVER_UP : SERVER_DOWN);
        log.info("Server status after ping: {}", server.getStatus());
        serverRepository.save(server);
        return server;
    }

    @Override
    public Collection<Server> list(int limit) {
        log.info("Faching all servers");
        return serverRepository.findAll(PageRequest.of(0, limit)).toList();
    }

    @Override
    public Server get(Long id) {
        log.info("Fetching server with ID: {}", id);
        return serverRepository.findById(id).orElse(null);
    }

    @Override
    public Server update(Server server) {
        log.info("Updating server with name: {}", server.getName());
        return serverRepository.save(server);
    }

    @Override
    public Boolean delete(Long id) {
        log.info("Deleting server with ID: {}", id);
        serverRepository.deleteById(id);
        return TRUE;
    }

    private String setServerImageUrl() {
        String[] imagesNames = {"server1", "server2", "server3", "server4"};
        return ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/static/images/" + imagesNames[new Random()
                        .nextInt(4)]).toUriString();

    }

    private boolean isReachable(String ipAddress, int port, int timeOut) {
        try {
            try (Socket socket = new Socket()) {
                socket.connect(new InetSocketAddress(ipAddress, port), timeOut);
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
