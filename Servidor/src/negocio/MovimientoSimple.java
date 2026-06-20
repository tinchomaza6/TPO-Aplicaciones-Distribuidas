package negocio;

import java.util.*;

import dao.MovimientoDao;
import dto.MovimientoSimpleDTO;
import excepciones.MovimientoException;

public class MovimientoSimple extends MovimientoStock {

	public MovimientoSimple(int idMov, Date fecha, Articulo articulo, String tipoMovimiento, String descripcion) {
		super(idMov, fecha, articulo, tipoMovimiento, descripcion);
	}
	
	public MovimientoSimple(Date fecha, Articulo articulo, String descrpicion) {
		super(fecha, articulo, descrpicion);
		this.tipoMovimiento="SIMPLE";
	}

	public void save() throws MovimientoException{
		MovimientoDao.getInstancia().save(this);
	}
	
	public String getDescripcion() {
		return this.descripcion;
	}
	
	public MovimientoSimpleDTO toDTO() {
		return new MovimientoSimpleDTO(this.idMov, this.fecha, this.articulo.toDTO(), this.descripcion);
	}
}