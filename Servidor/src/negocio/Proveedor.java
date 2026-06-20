package negocio;

import java.util.*;

import javax.swing.JOptionPane;

import dto.ArticuloDTO;
import dto.ProveedorDTO;
import entities.ArticuloEntity;
import entities.ProveedorEntity;

public class Proveedor {

	private Integer idProv;
	private String nombre;
	private int cuit;
	private String descripcion;
	private String direccion;
	private String telefonoContacto;
	private List<Articulo> articulos;

	public Proveedor(Integer idProv, String nombre, int cuit, String descripcion, String direccion,
			String telefonoContacto, List<Articulo> articulos) {
		super();
		this.idProv = idProv;
		this.nombre = nombre;
		this.cuit = cuit;
		this.descripcion = descripcion;
		this.direccion = direccion;
		this.telefonoContacto = telefonoContacto;
		this.articulos = articulos;
	}

	public Proveedor(Integer idProv, String nombre, int cuit, String descripcion, String direccion,
			String telefonoContacto) {
		super();
		this.idProv = idProv;
		this.nombre = nombre;
		this.cuit = cuit;
		this.descripcion = descripcion;
		this.direccion = direccion;
		this.telefonoContacto = telefonoContacto;
		this.articulos = new ArrayList<Articulo>();
	}

	public ProveedorEntity toEntity() {
		ProveedorEntity aux = new ProveedorEntity();
		aux.setArticulos(this.getArticulosEntity()); 
		aux.setCuit(this.getCuit());
		aux.setDescripcion(this.getDescripcion());
		aux.setDireccion(this.getDireccion());
		aux.setIdProv(this.getIdProv());
		aux.setNombre(this.getNombre());
		aux.setTelefonoContacto(this.getTelefonoContacto());
		return aux;
	}

	public ProveedorDTO toDTO() {
		ProveedorDTO aux = new ProveedorDTO();
		aux.setArticulos(this.getArticulosDTO());
		aux.setCuit(this.getCuit());
		aux.setDescripcion(this.getDescripcion());
		aux.setDireccion(this.getDireccion());
		aux.setIdProv(this.getIdProv());
		aux.setNombre(this.getNombre());
		aux.setTelefonoContacto(this.getTelefonoContacto());
		return aux;
	}


	//Getters y Setters

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

	public List<Articulo> getArticulos() {
		return articulos;
	}

	public List<ArticuloEntity> getArticulosEntity() {
		List<ArticuloEntity> list = new ArrayList<ArticuloEntity>();
		for (Articulo a: this.getArticulos()){
			list.add(a.toEntityUpdate());
		}
		return list;
	}


	public List<ArticuloDTO> getArticulosDTO() {
		List<ArticuloDTO> list = new ArrayList<ArticuloDTO>();
		for (Articulo a: this.getArticulos()){
			list.add(a.toDTO());
		}
		return list;
	}

	public void setArticulos(List<Articulo> articulos) {
		this.articulos = articulos;
	}
}