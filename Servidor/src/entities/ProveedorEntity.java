package entities;

import java.util.*;
import java.io.*;
import javax.persistence.*;

import negocio.Articulo;
import negocio.Proveedor;

@Entity
@Table (name = "Proveedor")

public class ProveedorEntity implements Serializable{

	private static final long serialVersionUID = -7255115411009073474L;

	@Id
	private Integer idProv;
	private String nombre;
	private int cuit;
	private String descripcion;
	private String direccion;
	private String telefonoContacto;

	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinTable(name = "ProveedoresArticulos", 
	joinColumns = { @JoinColumn(name = "idProv") }, 
	inverseJoinColumns = { @JoinColumn(name = "idArticulo") })
	private List<ArticuloEntity> articulos;

	public ProveedorEntity() {}

	public ProveedorEntity(Integer idProv, String nombre, int cuit, String descripcion, String direccion,
			String telefonoContacto, List<ArticuloEntity> articulos) {
		super();
		this.idProv = idProv;
		this.nombre = nombre;
		this.cuit = cuit;
		this.descripcion = descripcion;
		this.direccion = direccion;
		this.telefonoContacto = telefonoContacto;
		this.articulos = articulos;
	}

	//GETTERS Y SETTERS

	public Integer getIdProv() {
		return idProv;
	}

	public void setIdProv(Integer idProv) {
		this.idProv = idProv;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getCuit() {
		return cuit;
	}

	public void setCuit(int cuit) {
		this.cuit = cuit;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getTelefonoContacto() {
		return telefonoContacto;
	}

	public void setTelefonoContacto(String telefonoContacto) {
		this.telefonoContacto = telefonoContacto;
	}

	public List<ArticuloEntity> getArticulos() {
		return articulos;
	}

	public void setArticulos(List<ArticuloEntity> articulos) {
		this.articulos = articulos;
	}

	public Proveedor toNegocio() {
		Proveedor p =new Proveedor(this.idProv, this.nombre, this.cuit, this.descripcion, this.direccion, this.telefonoContacto);
		p.setArticulos(this.getArticulosNegocio());
		return p;
	}

	private List<Articulo> getArticulosNegocio() {
		List<Articulo> arts = new ArrayList<Articulo>();
		for (ArticuloEntity a: this.articulos) {
			arts.add(a.toNegocio());
		}
		return arts;
	}

}
