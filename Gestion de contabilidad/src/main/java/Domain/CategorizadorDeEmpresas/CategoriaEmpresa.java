package Domain.CategorizadorDeEmpresas;

import javax.persistence.*;

import Domain.Organizacion.Empresa;
@Entity
@Table
public class CategoriaEmpresa {
   
	@Id
	@GeneratedValue
	@Column(name="categoria_id")
	private int categoriaId;
	@Column
	private String nombre;
    /*
	@Column(name="monto_min")
    private double montoMin;
    @Column(name="monto_max")
    private double montoMax;
    @Column(name="personal_min")
    private int personalMin;
    @Column(name="personal_max")
    private int personalMax;*/

    public CategoriaEmpresa(String categoria, Double montoMin, Double montoMax, int personalMin, int personalMax){
        this.nombre      = categoria;
        this.montoMin    = montoMin;
        this.montoMax    = montoMax;
        this.personalMin = personalMin;
        this.personalMax = personalMax;
    }

//    public boolean dentroDelMinMaxPersonal(Empresa unaEmpresa){
//        return this.personalMin < unaEmpresa.getCantidadDePersonal() && unaEmpresa.getCantidadDePersonal() <= this.personalMax;
//    }
//
//    public boolean dentroDelMinMaxMonto(Empresa unaEmpresa){
//        return this.montoMin < unaEmpresa.getVentasAnuales() && unaEmpresa.getVentasAnuales() <= this.montoMax;
//    }

    public Boolean dentroDelMinMax(Empresa unaEmpresa){
        if((montoMin < unaEmpresa.getVentasAnuales() && unaEmpresa.getVentasAnuales() <= montoMax)
                || (personalMin < unaEmpresa.getCantidadDePersonal() && unaEmpresa.getCantidadDePersonal() <= personalMax)) return true;
        else return false;
    }

    public String getNombre(){
        return nombre;
    }
}
