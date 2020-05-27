package DatosDeOperaciones;

public class Item {
    private Producto producto;
    private int cantidad;

    public double valorTotal(){
        return cantidad * producto.getPrecio();
    }
}
