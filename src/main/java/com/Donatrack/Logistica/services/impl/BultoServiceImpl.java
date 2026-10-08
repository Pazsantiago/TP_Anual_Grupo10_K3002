package com.Donatrack.Logistica.services.impl;

import com.Donatrack.Logistica.domain.Bulto;
import com.Donatrack.Logistica.repository.BultoRepository;
import com.Donatrack.Logistica.services.BultoService;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Primary
public class BultoServiceImpl implements BultoService {

    private final BultoRepository bultoRepository;

    public BultoServiceImpl(BultoRepository bultoRepository) {
        this.bultoRepository = bultoRepository;
    }

    @Override
    public List<Bulto> obtenerBultosPendientes() {
        // Ejemplo: traer todos los bultos que aún no fueron asignados
        return bultoRepository.findAll();
    }

    @Override
    public Bulto guardar(Bulto bulto) {
        return bultoRepository.save(bulto);
    }

    //@Override
    public Bulto obtenerPorId(Long id) {
        return bultoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Bulto no encontrado con id: " + id));
    }

    //@Override
    public void eliminar(Long id) {
        bultoRepository.deleteById(id);
    }
}
