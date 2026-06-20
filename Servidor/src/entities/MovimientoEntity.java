package entities;

import java.io.Serializable;
import java.util.*;
import javax.persistence.*;

import negocio.Articulo;
import negocio.MovimientoAjuste;
import negocio.MovimientoDaño;
import negocio.MovimientoSimple;
import negocio.MovimientoStock;

@Entity
@Table(name="MovimientoStock")
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="tipoMovimiento",discriminatorType=DiscriminatorType.STRING)

public abstract class MovimientoEntity implements Serializable{
	private static final long serialVersionUID = -4820982784317900909L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	protected Integer idMov;
	protected Date fecha;
	protected String descripicion;
	
	@Column (insertable=false, updatable=false)
	protected String tipoMovimiento; //DAÑO, AJUSTE O SIMPLE
	
	@OneToOne(cascade=CascadeType.PERSIST)
	@JoinColumn(name="idArticulo")
	protected ArticuloEntity articulo;
	
	public MovimientoEntity(){}
	
	public MovimientoEntity(Integer idMov, Date fecha, String tipoMovimiento, ArticuloEntity articulo, String descripcion) {
		super();
		this.idMov = idMov;
		this.fecha = fecha;
		this.tipoMovimiento = tipoMovimiento;
		this.articulo = articulo;
		this.descripicion=descripcion;
	}
	
	public MovimientoEntity(Date fecha, ArticuloEntity articulo, String descripcion) {
		super();
		this.fecha = fecha;
		this.articulo = articulo;
		this.descripicion = descripcion;
	}
	
	public abstract MovimientoStock toNegocio();


	//GETTERS Y SETTERS

	public Integer getIdMov() {
		return idMov;
	}

	public void setIdMov(Integer idMov) {
		this.idMov = idMov;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public String getTipoMovimiento() {
		return tipoMovimiento;
	}

	public void setTipoMovimiento(String tipoMovimiento) {
		this.tipoMovimiento = tipoMovimiento;
	}

	public ArticuloEntity getArticulo() {
		return articulo;
	}

	public void setArticulo(ArticuloEntity articulo) {
		this.articulo = articulo;
	}
}