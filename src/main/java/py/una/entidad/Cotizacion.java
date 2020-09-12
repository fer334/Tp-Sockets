package py.una.entidad;

public class Cotizacion {
    public String tipo;
    public Long compra;
    public Long venta;

    public Cotizacion(String tipo, Long compra, Long venta) {
        this.tipo=tipo;
        this.compra=compra;
        this.venta=venta;
    }
}