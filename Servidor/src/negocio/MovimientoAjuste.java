package negocio;

import java.util.*;

import dao.MovimientoDao;
import dto.MovimientoAjusteDTO;
import excepciones.MovimientoException;

public class MovimientoAjuste extends MovimientoStock {

	private String encargado;

	public MovimientoAjuste(int idMov, Date fecha, Articulo articulo, String tipoMovimiento, String encargado, String descripcion) {
		super(idMov, fecha, articulo, tipoMovimiento, descripcion);
		this.encargado = encargado;
	}
	
	public MovimientoAjuste(Date fecha, Articulo articulo, String encargado, String descripcion) {
		super(fecha, articulo, descripcion);
		this.tipoMovimiento = "AJUSTE";
		this.encargado = encargado;
	}

	public void save() throws MovimientoException{
		MovimientoDao.getInstancia().save(this);
	}

	//Getters y Setters

	public String getEncargado() {
		return encargado;
	}

	public void setEncargado(String encargado) {
		this.encargado = encargado;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	public MovimientoAjusteDTO toDTO() {
		return new MovimientoAjusteDTO(this.idMov, this.fecha, this.articulo.toDTO(), this.descripcion, this.encargado);
	}
}