package Sdonaciones.dominio.donante;
public class Documento{
    public String getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(String tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    private String tipoDocumento;
    private String documento;

    public Documento(String tipo, String doc){
        this.tipoDocumento = tipo;
        this.documento = doc;
    }

}