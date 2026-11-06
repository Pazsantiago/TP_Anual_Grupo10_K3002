package com.Donatrack.Logistica.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.Donatrack.Logistica.domain.EventoPendiente;

public interface EventoPendienteRepository extends MongoRepository<EventoPendiente, String> {}
