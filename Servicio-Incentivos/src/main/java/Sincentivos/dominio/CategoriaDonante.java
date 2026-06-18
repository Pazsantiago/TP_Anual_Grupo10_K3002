package Sincentivos.dominio;


import java.util.List;

public class CategoriaDonante {
    public CategoriaDonante categoriaSiguiente;
    public List<Mision> Misiones;
    public CategoriaDonante siguienteCategoria() {
        return this.categoriaSiguiente;
    }
}

class Colaborador extends CategoriaDonante {
    public Colaborador() {
        categoriaSiguiente = new Sostenedor();
}

class Sostenedor extends CategoriaDonante {
    public Sostenedor() {
        categoriaSiguiente = new Transformador();
    }
}

class Transformador extends CategoriaDonante {
    public Transformador() {
        categoriaSiguiente = null;
    }
}}