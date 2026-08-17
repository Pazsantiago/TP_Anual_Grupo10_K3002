package ar.edu.utn.ba.dsi.g10.servicio_incentivos.Model.CategoriasDonante;

public enum Categoria {
    COLABORADOR,
    SOSTENEDOR,
    TRANSFORMADOR;

    public Categoria siguiente() {
        switch (this) {
            case COLABORADOR:
                return SOSTENEDOR;
            case SOSTENEDOR:
                return TRANSFORMADOR;
            default:
                return this;
        }
    }
}
