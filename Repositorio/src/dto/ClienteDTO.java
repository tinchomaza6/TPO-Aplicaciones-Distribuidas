package dto;

import java.io.Serializable;

public class ClienteDTO implements Serializable {

	private static final long serialVersionUID = -8655239895983395351L;
	
	private int dni;
	private String nombre;
	private String razonSocial;
	private int cuit;
	private float limiteCredito;
	private CuentaCorrienteDTO cuentaCorriente;
	private String condEspPago;
	private String notasAdv;
	private String calleDom;
	private int nroDom;
	private String localidadDom;
	private int cpDom;
	
	public ClienteDTO() {
		super();
	}

	public ClienteDTO(int dni, String nombre, String razonSocial, int cuit, float limiteCredito,
			CuentaCorrienteDTO cuentaCorriente, String condEspPago, String notasAdv, String calleDom, int nroDom,
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

	//Getters y setters

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

	public CuentaCorrienteDTO getCuentaCorrienteDTO() {
		return cuentaCorriente;
	}

	public void setCuentaCorrienteDTO(CuentaCorrienteDTO cuentaCorriente) {
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

	public CuentaCorrienteDTO getCuentaCorriente() {
		return cuentaCorriente;
	}

	public void setCuentaCorriente(CuentaCorrienteDTO cuentaCorriente) {
		this.cuentaCorriente = cuentaCorriente;
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
}