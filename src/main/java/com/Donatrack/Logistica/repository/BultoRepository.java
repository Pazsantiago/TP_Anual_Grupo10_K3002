package com.Donatrack.Logistica.repository;
import org.springframework.data.mongodb.repository.MongoRepository;
import com.Donatrack.Logistica.domain.Bulto;

public interface BultoRepository extends MongoRepository<Bulto, Long> {
}
