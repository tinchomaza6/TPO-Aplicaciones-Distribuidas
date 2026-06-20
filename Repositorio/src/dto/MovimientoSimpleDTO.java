package dto;

import java.io.Serializable;
import java.util.*;

public class MovimientoSimpleDTO extends MovimientoStockDTO implements Serializable{
	private static final long serialVersionUID = -4356772351782694501L;

	public MovimientoSimpleDTO(int idMov, Date fecha, ArticuloDTO articulo, String descripcion) {
		super(idMov, fecha, articulo, "SIMPLE", descripcion);
	}
	
	public String getDescripcion() {
		return this.descripcion;
	}
	
	public void setDescripcion(String descripcion) {
		this.descripcion=descripcion;
	}
}
