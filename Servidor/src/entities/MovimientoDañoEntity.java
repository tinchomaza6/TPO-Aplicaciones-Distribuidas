package entities;

import java.util.*;
import javax.persistence.*;

import negocio.MovimientoDaño;

@Entity
@DiscriminatorValue("DAÑO")

public class MovimientoDañoEntity extends MovimientoEntity{
	private static final long serialVersionUID = 7616669139282081664L;
	
	private String destino;
	private String encargado;
	private String autorizante;

	public MovimientoDañoEntity() {
		super();
	}

	public MovimientoDañoEntity(Integer idMov, Date fecha, String tipoMovimiento, ArticuloEntity articulo, String destino, String encargado, String autorizante, String descripcion) {
		super(idMov, fecha, tipoMovimiento, articulo, descripcion);
		this.destino = destino;
		this.encargado = encargado;
		this.autorizante = autorizante;
	}
	
	public MovimientoDañoEntity(Date fecha, String tipoMovimiento, ArticuloEntity articulo, String destino, String encargado, String autorizante, String descripcion) {
		super(fecha, articulo, descripcion);
		this.tipoMovimiento="DAÑO";
		this.destino = destino;
		this.encargado = encargado;
		this.autorizante = autorizante;
	}
	
	public MovimientoDaño toNegocio() {
		return new MovimientoDaño(this.idMov, this.fecha, this.articulo.toNegocio(), "DAÑO", this.destino, this.encargado, this.descripicion, this.autorizante);
	}
	
	//GETTERS Y SETTERS

	public String getDestino() {
		return destino;
	}
	public void setDestino(String destino) {
		this.destino = destino;
	}
	public String getEncargado() {
		return encargado;
	}
	public void setEncargado(String encargado) {
		this.encargado = encargado;
	}
	public String getAutorizante() {
		return autorizante;
	}
	public void setAutorizante(String autorizante) {
		this.autorizante = autorizante;
	}
	public String getDescripcion() {
		return descripicion;
	}
	public void setDescripcion(String descripcion) {
		this.descripicion = descripcion;
	}
}