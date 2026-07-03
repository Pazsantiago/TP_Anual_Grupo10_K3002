package Servicio_incentivos.dominio.CategoriasDonante;

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