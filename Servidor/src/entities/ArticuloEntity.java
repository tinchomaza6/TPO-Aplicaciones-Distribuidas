package entities;

import java.io.Serializable;
import java.util.*;
import javax.persistence.*;

import negocio.Articulo;

@Entity
@Table(name="Articulo")
public class ArticuloEntity implements Serializable{

	private static final long serialVersionUID = 6062902714342437232L;
	
	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	private Integer idArticulo;
	private String nombre;
	private int CapacidadArticulo;
	private String codBarras;
	private String descripcion;
	private String presentacion;
	private String unidadMedida;
	private int cantCompraFija;
	private float precioVentaUnitario;

	@ManyToMany(mappedBy="articulos", fetch = FetchType.LAZY)
	private List<ProveedorEntity> proveedores;
	
	public ArticuloEntity(){}

	public ArticuloEntity(Integer idArticulo, String nombre, int CapacidadArticulo, String codBarras, String descripcion,
			String presentacion, String unidadMedida, int cantCompraFija, float precioVentaUnitario, List<ProveedorEntity> proveedores) {
		super();
		this.idArticulo = idArticulo;
		this.nombre = nombre;
		this.CapacidadArticulo = CapacidadArticulo;
		this.codBarras = codBarras;
		this.descripcion = descripcion;
		this.presentacion = presentacion;
		this.unidadMedida = unidadMedida;
		this.cantCompraFija = cantCompraFija;
		this.precioVentaUnitario = precioVentaUnitario;
		this.proveedores = proveedores;
	}
	
	public Articulo toNegocio() { 
		return new Articulo(this.idArticulo,this.nombre, this.CapacidadArticulo, this.codBarras, this.descripcion,
				this.presentacion, this.unidadMedida,this.cantCompraFija, this.precioVentaUnitario);
	}

	


	//GETTERS Y SETTERS
	
	public int getIdArticulo() {
		return idArticulo;
	}
	public void setIdArticulo(int idArticulo) {
		this.idArticulo = idArticulo;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public int getCapacidadArticulo() {
		return CapacidadArticulo;
	}
	public void setCapacidadArticulo(int CapacidadArticulo) {
		this.CapacidadArticulo = CapacidadArticulo;
	}
	public String getCodBarras() {
		return codBarras;
	}
	public void setCodBarras(String codBarras) {
		this.codBarras = codBarras;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public String getPresentacion() {
		return presentacion;
	}
	public void setPresentacion(String presentacion) {
		this.presentacion = presentacion;
	}
	public String getUnidadMedida() {
		return unidadMedida;
	}
	public void setUnidadMedida(String unidadMedida) {
		this.unidadMedida = unidadMedida;
	}
	public int getCantCompraFija() {
		return cantCompraFija;
	}
	public void setCantCompraFija(int cantCompraFija) {
		this.cantCompraFija = cantCompraFija;
	}
	public float getPrecioVentaUnitario() {
		return precioVentaUnitario;
	}
	public void setPrecioVentaUnitario(float precioVentaUnitario) {
		this.precioVentaUnitario = precioVentaUnitario;
	}
	
	public List<ProveedorEntity> getProveedores() {
		return proveedores;
	}

	public void setProveedores(List<ProveedorEntity> proveedores) {
		this.proveedores = proveedores;
	}



}
