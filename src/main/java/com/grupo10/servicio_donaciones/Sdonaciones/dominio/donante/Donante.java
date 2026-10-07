package com.grupo10.servicio_donaciones.Sdonaciones.dominio.donante;

import com.grupo10.servicio_donaciones.Sdonaciones.dominio.donacion.Donacion;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
public class Donante {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToMany(mappedBy = "donante", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<Donacion> donaciones = new ArrayList<>();
    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "id_persona")
    private Persona persona;
    @OneToMany(mappedBy = "donante", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    private List<MedioContacto> mediosDeContacto = new ArrayList<>();
    private LocalDateTime ultimaInteraccion;

    public Donante(List<Donacion> donaciones, Persona persona, List<MedioContacto> mediosDeContacto) {
        this.persona = persona;
        setDonaciones(donaciones);
        setMediosDeContacto(mediosDeContacto);
        this.ultimaInteraccion = LocalDateTime.now();
    }

    public void borrarDonaciones() {
        this.donaciones.clear();
    }

    public void setDonaciones(List<Donacion> donaciones) {
        this.donaciones = donaciones;
        setearDonaciones();
    }

    public void actualizarDonacion(Donacion donacion) {
        getDonaciones().forEach(d -> {
            if (d.getId().equals(donacion.getId())) {
                d = donacion;
            }
        });
    }

    public void setMediosDeContacto(List<MedioContacto> mediosDeContacto) {
        this.mediosDeContacto = mediosDeContacto;
        setearMediosContactos();
    }

    public void agregarMedioContacto(MedioContacto contacto) {
        mediosDeContacto.add(contacto);
        contacto.setDonante(this);
    }

    public MedioContacto obtenerContactoPredeterminado() {
        return mediosDeContacto.stream().filter(p -> p.isEsPredeterminado()).findFirst().orElse(null);
    }

    public void setearRelaciones() {
        this.ultimaInteraccion = LocalDateTime.now();
        setearDonaciones();
        setearMediosContactos();
    }

    public void setearDonaciones() {
        getDonaciones().forEach(d -> {
            d.setDonante(this);
        });
    }

    public void setearMediosContactos() {
        getMediosDeContacto().forEach(medioContacto -> {
            medioContacto.setDonante(this);
        });
    }


    public void eliminarDonacion(Long idDonacion) {
        donaciones.removeIf(d -> d.getId().equals(idDonacion));
    }

    public void agregarDonacion(Donacion donacion) {
        donaciones.add(donacion);
        donacion.setDonante(this);
    }

    public void cambiarContactoPredeterminado(MedioContacto contactoPredeterminado) {
        mediosDeContacto.stream().filter(p -> p.isEsPredeterminado()).findFirst().ifPresent(antiguo -> antiguo.setEsPredeterminado(false));
        mediosDeContacto.stream().filter(p -> p.equals(contactoPredeterminado)).findFirst().ifPresent(nuevo -> nuevo.setEsPredeterminado(true));
        if (!mediosDeContacto.contains(contactoPredeterminado)) {
            mediosDeContacto.add(contactoPredeterminado);
        }
    }

}