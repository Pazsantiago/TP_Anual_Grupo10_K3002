package com.Donatrack.Logistica.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.Donatrack.Logistica.domain.LoteRuta;

public interface LoteRutaRepository extends MongoRepository<LoteRuta, String> {}