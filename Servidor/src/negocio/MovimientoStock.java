package negocio;

import java.util.*;

import dto.MovimientoStockDTO;

public abstract class MovimientoStock {

	protected int idMov;
	protected Date fecha;
	protected Articulo articulo;
	protected String tipoMovimiento;
	protected String descripcion;

	public MovimientoStock(int idMov, Date fecha, Articulo articulo, String tipoMovimiento, String descripcion) {
		super();
		this.idMov = idMov;
		this.fecha = fecha;
		this.articulo = articulo;
		this.tipoMovimiento = tipoMovimiento;
		this.descripcion=descripcion;
	}
	
	public MovimientoStock(Date fecha, Articulo articulo, String descripcion) {
		super();
		this.fecha = fecha;
		this.articulo = articulo;
		this.descripcion=descripcion;
	}
	
	public abstract MovimientoStockDTO toDTO();

	//Getters y Setters

	public int getIdMov() {
		return idMov;
	}

	public void setIdMov(int idMov) {
		this.idMov = idMov;
	}
	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public Articulo getArticulo() {
		return articulo;
	}

	public void setArticulo(Articulo articulo) {
		this.articulo = articulo;
	}
}