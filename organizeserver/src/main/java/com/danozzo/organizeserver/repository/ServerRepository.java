package com.danozzo.organizeserver.repository;

import com.danozzo.organizeserver.model.Server;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServerRepository extends JpaRepository<Server, Long> {
Server findByIpAddress(String ipAddress);
Server findByName(String name);
}
