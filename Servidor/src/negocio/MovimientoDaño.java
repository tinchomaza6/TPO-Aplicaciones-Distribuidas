package negocio;

import java.util.Date;

import dao.MovimientoDao;
import dto.MovimientoDañoDTO;
import excepciones.MovimientoException;

public class MovimientoDaño extends MovimientoStock{

	private String destino;
	private String encargado;
	private String autorizante;

	public MovimientoDaño(int idMov, Date fecha, Articulo articulo, String tipoMovimiento, String destino, String encargado, String descripcion, String autorizante) {
		super(idMov, fecha, articulo, tipoMovimiento, descripcion);
		this.destino = destino;
		this.encargado = encargado;
		this.autorizante = autorizante;
	}
	
	public MovimientoDaño(Date fecha, Articulo articulo, String destino, String encargado, String descripcion, String autorizante) {
		super(fecha, articulo, descripcion);
		this.destino = destino;
		this.tipoMovimiento="DAÑO";
		this.encargado = encargado;
		this.autorizante = autorizante;
	}

	public void save() throws MovimientoException{
		MovimientoDao.getInstancia().save(this);
	}

	//Getters y Setters

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
	
	public MovimientoDañoDTO toDTO() {
		return new MovimientoDañoDTO(this.idMov, this.fecha, this.articulo.toDTO(), this.descripcion, this.encargado, this.autorizante, this.destino);
	}
}