package dto;

import java.io.Serializable;
import java.util.Date;

public class MovimientoAjusteDTO extends MovimientoStockDTO implements Serializable{
	private static final long serialVersionUID = 8558888602244826288L;
	
	private String encargado;
	
	public MovimientoAjusteDTO (int idMov, Date fecha, ArticuloDTO articulo, String descripcion, String encargado){
		super(idMov, fecha, articulo, "AJUSTE", descripcion);
		this.encargado = encargado;
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
}
