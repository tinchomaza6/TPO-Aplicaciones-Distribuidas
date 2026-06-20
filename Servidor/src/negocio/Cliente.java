package negocio;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.swing.JOptionPane;

import controladores.ControladorFacturacion;
import dao.ClienteDao;
import dao.CuentaCorrienteDao;
import dto.ClienteDTO;
import entities.ClienteEntity;
import excepciones.ClienteException;
import excepciones.CuentaCorrienteException;
import excepciones.FacturaException;

public class Cliente {

	private int dni;
	private String nombre;
	private String razonSocial;
	private int cuit;
	private float limiteCredito;
	private CuentaCorriente cuentaCorriente;
	private String condEspPago;
	private String notasAdv;
	private String calleDom;
	private int nroDom;
	private String localidadDom;
	private int cpDom;

	public Cliente(int dni, String nombre, String razonSocial, int cuit, float limiteCredito,
			CuentaCorriente cuentaCorriente, String condEspPago, String notasAdv, String calleDom, int nroDom,
			String localidadDom, int cpDom) {
		super();
		this.dni = dni;
		this.nombre = nombre;
		this.razonSocial = razonSocial;
		this.cuit = cuit;
		this.limiteCredito = limiteCredito;
		this.cuentaCorriente = cuentaCorriente;
		this.condEspPago = condEspPago;
		this.notasAdv = notasAdv;
		this.calleDom = calleDom;
		this.nroDom = nroDom;
		this.localidadDom = localidadDom;
		this.cpDom = cpDom;
	}
	public Cliente() {
		super();
	}

	public Cliente(int dni, String nombre, String razonSocial, int cuit, float limiteCredito, String condEspPago, String notasAdv, String calleDom, int nroDom,
			String localidadDom, int cpDom) {
		super();
		this.dni = dni;
		this.nombre = nombre;
		this.razonSocial = razonSocial;
		this.cuit = cuit;
		this.limiteCredito = limiteCredito;
		this.condEspPago = condEspPago;
		this.notasAdv = notasAdv;
		this.calleDom = calleDom;
		this.nroDom = nroDom;
		this.localidadDom = localidadDom;
		this.cpDom = cpDom;
	}
	public ClienteEntity toEntity(){
		ClienteEntity aux = new ClienteEntity();
		aux.setDni(this.dni);
		aux.setNombre(this.nombre);
		aux.setRazonSocial(this.razonSocial);
		aux.setCuit(this.cuit);
		aux.setLimiteCredito(this.limiteCredito);
		aux.setCuentaCorrienteEntity(this.cuentaCorriente.toEntityUpdate());
		aux.setCondEspPago(this.condEspPago);
		aux.setNotasAdv(this.notasAdv);
		aux.setCalleDom(this.calleDom);
		aux.setNroDom(this.nroDom);
		aux.setLocalidadDom(this.localidadDom);
		aux.setCpDom(this.cpDom);
		return aux;
	}

	public ClienteDTO toDTO (){
		ClienteDTO cliD = new ClienteDTO();
		cliD.setDni(this.dni);
		cliD.setNombre(this.nombre);
		cliD.setRazonSocial(this.razonSocial);
		cliD.setCuit(this.cuit);
		cliD.setLimiteCredito(this.limiteCredito);
		cliD.setCuentaCorrienteDTO(this.cuentaCorriente.toDTO());
		cliD.setCondEspPago(this.condEspPago);
		cliD.setNotasAdv(this.notasAdv);
		cliD.setCalleDom(this.calleDom);
		cliD.setNroDom(this.nroDom);
		cliD.setLocalidadDom(this.localidadDom);
		cliD.setCpDom(this.cpDom);
		return cliD;
	}

	public void save() throws SQLException {
		ClienteDao.getInstancia().save(this);

	}
	//Getters y Setters

	public int getDni() {
		return dni;
	}
	public void setDni(int dni) {
		this.dni = dni;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getRazonSocial() {
		return razonSocial;
	}
	public void setRazonSocial(String razonSocial) {
		this.razonSocial = razonSocial;
	}
	public int getCuit() {
		return cuit;
	}
	public void setCuit(int cuit) {
		this.cuit = cuit;
	}

	public float getLimiteCredito() {
		return limiteCredito;
	}
	public void setLimiteCredito(float limiteCredito) {
		this.limiteCredito = limiteCredito;
	}
	public CuentaCorriente getCuentaCorriente() {
		return cuentaCorriente;
	}
	public void setCuentaCorriente(CuentaCorriente cuentaCorriente) {
		this.cuentaCorriente = cuentaCorriente;
	}
	public String getCondEspPago() {
		return condEspPago;
	}
	public void setCondEspPago(String condEspPago) {
		this.condEspPago = condEspPago;
	}
	public String getNotasAdv() {
		return notasAdv;
	}
	public void setNotasAdv(String notasAdv) {
		this.notasAdv = notasAdv;
	}
	public String getCalleDom() {
		return calleDom;
	}
	public void setCalleDom(String calleDom) {
		this.calleDom = calleDom;
	}
	public int getNroDom() {
		return nroDom;
	}
	public void setNroDom(int nroDom) {
		this.nroDom = nroDom;
	}
	public String getLocalidadDom() {
		return localidadDom;
	}
	public void setLocalidadDom(String localidadDom) {
		this.localidadDom = localidadDom;
	}
	public int getCpDom() {
		return cpDom;
	}
	public void setCpDom(int cpDom) {
		this.cpDom = cpDom;
	}

	public void pagarFactura(Factura factura) throws FacturaException, CuentaCorrienteException {
		//genera dos movimientos: uno carga saldo y otro resta. ambos por el valor de la factura
		Date fecha = Calendar.getInstance().getTime();
		this.cuentaCorriente.nuevoMovimientoCarga(fecha, factura.getTotalFact(), "Carga de saldo para pago de factura numero " + factura.getNroFactura());
		this.cuentaCorriente.nuevoMovimientoResta(fecha, factura.getTotalFact(), "Pago de factura numero " + factura.getNroFactura());
		factura.setEstado("PAGADA");
		factura.update(); 
	}
	
	public void cargarSaldo(float monto) throws CuentaCorrienteException {
		Date fecha = Calendar.getInstance().getTime();
		this.cuentaCorriente.nuevoMovimientoCarga(fecha, monto, "Carga de saldo");
	}
	
	public List<String> pagoDeFacturas() throws FacturaException, CuentaCorrienteException {
		List<String> facturasPagadas = new ArrayList<String>();
		List<Factura> facturas = ControladorFacturacion.getInstancia().buscarFacturasByCliente(this.dni);
		Date fecha = Calendar.getInstance().getTime();
		for(Factura f : facturas) {
			if(this.cuentaCorriente.consultarSaldo() >= f.getTotalFact()){
				this.cuentaCorriente.nuevoMovimientoResta(fecha, f.getTotalFact(), "Pago de factura numero " + f.getNroFactura());
				facturasPagadas.add(Integer.toString(f.getNroFactura()));
				f.setEstado("PAGADA");
				f.update();
			}
		}
		return facturasPagadas;
	}
	public void delete() throws ClienteException {
		ClienteDao.getInstancia().delete(this);
	}
	public void update() throws ClienteException {
		ClienteDao.getInstancia().update(this);
	}
}