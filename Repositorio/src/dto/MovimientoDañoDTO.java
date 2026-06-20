package dto;

import java.io.Serializable;
import java.util.*;

public class MovimientoDañoDTO extends MovimientoStockDTO implements Serializable{

	private static final long serialVersionUID = -3178642457978171490L;
	
	private String destino;
	private String tipo;
	private String encargado;
	private String autorizante;

	public MovimientoDañoDTO(int idMov, Date fecha, ArticuloDTO articulo, String descripcion, String encargado, String autorizante, String destino){
		super(idMov, fecha, articulo, "DAÑO", descripcion);
		this.destino = destino;
		this.encargado = encargado;
		this.autorizante = autorizante;
	}

	//Getters y Setters

	public String getDestino() {
		return destino;
	}

	public void setDestino(String destino) {
		this.destino = destino;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

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

	public String getAutorizante() {
		return autorizante;
	}

	public void setAutorizante(String autorizante) {
		this.autorizante = autorizante;
	}
}
