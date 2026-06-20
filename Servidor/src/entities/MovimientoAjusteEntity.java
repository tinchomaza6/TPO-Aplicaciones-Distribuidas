package entities;

import java.util.*;
import javax.persistence.*;

import negocio.MovimientoAjuste;

@Entity
@DiscriminatorValue("AJUSTE")

public class MovimientoAjusteEntity extends MovimientoEntity {
	private static final long serialVersionUID = 3609250510543411955L;
	
	private String encargado;
	
	public MovimientoAjusteEntity() {}

	public MovimientoAjusteEntity(Integer idMov, Date fecha, String tipoMovimiento, ArticuloEntity articulo, String encargado, String descripcion) {
		super(idMov, fecha, tipoMovimiento, articulo, descripcion);
		this.encargado = encargado;
	}
	
	public MovimientoAjusteEntity(Date fecha, ArticuloEntity articulo, String encargado, String descripcion) {
		super(fecha, articulo, descripcion);
		this.tipoMovimiento="AJUSTE";
		this.encargado = encargado;
	}
	
	public MovimientoAjuste toNegocio() {
		return new MovimientoAjuste(this.idMov, this.fecha, this.articulo.toNegocio(), "AJUSTE", this.encargado, this.descripicion);
	}

	//GETTERS Y SETTERS
	
	public String getEncargado() {
		return encargado;
	}
	public void setEncargado(String encargado) {
		this.encargado = encargado;
	}
	public String getDescripcion() {
		return descripicion;
	}
	public void setDescripcion(String descripcion) {
		this.descripicion = descripcion;
	}
}