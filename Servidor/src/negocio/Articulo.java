package negocio;

import dao.ArticuloDao;
import dto.ArticuloDTO;
import entities.ArticuloEntity;
import excepciones.ArticuloException;

public class Articulo {

	private int idArticulo;
	private String nombre;
	private int CapacidadArticulo;
	private String codBarras;
	private String descripcion;
	private String presentacion;
	private String unidadMedida;
	private int cantCompraFija;
	private float precioVentaUnitario;



	public Articulo(int idArticulo, String nombre, int CapacidadArticulo, String codBarras, String descripcion,
			String presentacion, String unidadMedida, int cantCompraFija, float precioVentaUnitario) {
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

	}
	
	public Articulo(String nombre, int CapacidadArticulo, String codBarras, String descripcion, String presentacion,
			String unidadMedida, int cantCompraFija, float precioVentaUnitario) {
		super();
		this.nombre = nombre;
		this.CapacidadArticulo = CapacidadArticulo;
		this.codBarras = codBarras;
		this.descripcion = descripcion;
		this.presentacion = presentacion;
		this.unidadMedida = unidadMedida;
		this.cantCompraFija = cantCompraFija;
		this.precioVentaUnitario = precioVentaUnitario;
	}

	public ArticuloEntity toEntityUpdate(){ 
		ArticuloEntity aux = new ArticuloEntity();
		aux.setCantCompraFija(this.cantCompraFija);
		aux.setCapacidadArticulo(this.CapacidadArticulo); 
		aux.setCodBarras(this.codBarras);
		aux.setDescripcion(this.descripcion);
		aux.setIdArticulo(this.idArticulo);
		aux.setNombre(this.nombre);
		aux.setPrecioVentaUnitario(this.precioVentaUnitario);
		aux.setPresentacion(this.presentacion);
		aux.setUnidadMedida(this.unidadMedida);
		return aux;
	}


	public ArticuloEntity toEntitySave(){
		ArticuloEntity aux = new ArticuloEntity();
		aux.setCantCompraFija(this.cantCompraFija);
		aux.setCapacidadArticulo(this.CapacidadArticulo);
		aux.setCodBarras(this.codBarras);
		aux.setDescripcion(this.descripcion);
		aux.setNombre(this.nombre);
		aux.setPrecioVentaUnitario(this.precioVentaUnitario);
		aux.setPresentacion(this.presentacion);
		aux.setUnidadMedida(this.unidadMedida);
		return aux;
	}

	
	public ArticuloDTO toDTO() {
		ArticuloDTO aux = new ArticuloDTO();
		aux.setCantCompraFija(this.cantCompraFija);
		aux.setCapacidadArticulo(this.getCapacidadArticulo());
		aux.setCodBarras(this.getCodBarras());
		aux.setDescripcion(this.getDescripcion());
		aux.setIdArticulo(this.getIdArticulo());
		aux.setNombre(this.getNombre());
		aux.setPrecioVentaUnitario(this.getPrecioVentaUnitario());
		aux.setPresentacion(this.getPresentacion());
		aux.setUnidadMedida(this.getUnidadMedida());
		return aux;
	}

	public void save() throws ArticuloException {
		ArticuloDao.getInstancia().save(this);	
	}

	public void update() throws ArticuloException{
		ArticuloDao.getInstancia().update(this);
	}




	//	Getters y Setters


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

	public int cantidadReservada(int nroPedido) {
		return ArticuloDao.getInstancia().cantidadReservada(nroPedido, this.idArticulo);
	}


}
