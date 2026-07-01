package Sdonaciones.dominio.donante;
public class TipoPersona{
    private String direccion;

    public Documento getDocumento() {
        return documento;
    }

    public void setDocumento(Documento documento) {
        this.documento = documento;
    }

    private Documento documento;

    public TipoPersona(String tipoDocumento, String documento){
        this.documento = new Documento(tipoDocumento, documento);
    }

}