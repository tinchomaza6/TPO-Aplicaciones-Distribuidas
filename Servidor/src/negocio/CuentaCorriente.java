package negocio;

import java.util.*;

import javax.swing.JOptionPane;

import dao.CuentaCorrienteDao;
import dto.CuentaCorrienteDTO;
import dto.MovimientoCtaCteDTO;
import entities.CuentaCorrienteEntity;
import entities.FacturaEntity;
import entities.ItemFacturaEntity;
import entities.MovimientoCtaCteEntity;
import excepciones.CuentaCorrienteException;

public class CuentaCorriente {

	private int idCuentaCorriente;
	private Date fecha;
	private String especie;
	private float saldo;
	private List<MovimientoCtaCte> movimientos;

	public CuentaCorriente(){};

	public CuentaCorriente(int idCuentaCorriente, Date fecha, String especie, float saldo,
			List<MovimientoCtaCte> movimientos) {
		super();
		this.idCuentaCorriente = idCuentaCorriente;
		this.fecha = fecha;
		this.especie = especie;
		this.saldo = saldo;
		this.movimientos = movimientos;
	}
	
	public CuentaCorriente(int idCuentaCorriente, Date fecha, String especie, float saldo) {
		super();
		this.idCuentaCorriente = idCuentaCorriente;
		this.fecha = fecha;
		this.especie = especie;
		this.saldo = saldo;
		this.movimientos = new ArrayList<MovimientoCtaCte>();
	}
	
	public CuentaCorriente(String especie, float saldo) {
		super();
		this.fecha = Calendar.getInstance().getTime();
		this.especie = especie;
		this.saldo = saldo;
		this.movimientos = new ArrayList<MovimientoCtaCte>();
	}

	public float consultarSaldo(){
		return this.saldo;
	}
	
	public void nuevoMovimientoCarga(Date fecha, float monto, String descripcion) throws CuentaCorrienteException {
		MovimientoCtaCte mov = new MovimientoCtaCte(this, fecha, monto, descripcion);
		int id = mov.save();
		mov.setNroMov(id);
		this.movimientos.add(mov);
		this.saldo += monto;
		this.update();
	}
	
	public void nuevoMovimientoResta(Date fecha, float monto, String descripcion) throws CuentaCorrienteException {
		MovimientoCtaCte mov = new MovimientoCtaCte(this, fecha, monto, descripcion);
		int id = mov.save();
		mov.setNroMov(id);
		this.movimientos.add(mov);
		this.saldo -= monto;
		this.update();
	}
	
	/*public void nuevoMovimientoRestaGeneral(Date fecha, float monto, String descripcion) throws CuentaCorrienteException {
		MovimientoCtaCte mov = new MovimientoCtaCte(this, fecha, monto, descripcion);
		int id = mov.save();
		mov.setNroMov(id);
		this.movimientos.add(mov);
		this.saldo -= monto;
		this.updateSaldo();
	}*/
	/*private void updateSaldo() {
		CuentaCorrienteDao.getInstancia().updateSaldo(this);
	}*/

	public void update() throws CuentaCorrienteException {
		CuentaCorrienteDao.getInstancia().update(this);
	}

	public CuentaCorrienteEntity toEntitySave() {		
		CuentaCorrienteEntity aux = new CuentaCorrienteEntity();
		aux.setEspecie(this.especie);
		aux.setFecha(this.fecha);
		aux.setSaldo(this.saldo);
		aux.setMovimientos(this.getMovimientosEntity(aux));
		return aux;
	}
	

	public CuentaCorrienteEntity toEntityUpdate() {
		CuentaCorrienteEntity aux = new CuentaCorrienteEntity();
		aux.setEspecie(this.especie);
		aux.setFecha(this.fecha);
		aux.setSaldo(this.saldo);
		aux.setIdCuentaCorriente(this.idCuentaCorriente);
		aux.setMovimientos(this.getMovimientosEntity(aux));
		return aux;
	}

	private List<MovimientoCtaCteEntity> getMovimientosEntity(CuentaCorrienteEntity aux2) {
		List<MovimientoCtaCteEntity> movimientos = new ArrayList<MovimientoCtaCteEntity>();
		MovimientoCtaCteEntity aux = new MovimientoCtaCteEntity();
		for(MovimientoCtaCte mov : this.movimientos) {
			aux.setFecha(mov.getFecha());
			aux.setDescripcion(mov.getDescripcion());
			aux.setMonto(mov.getMonto());
			aux.setNroMov(mov.getNroMov());
			aux.setCuentaCorriente(aux2);
			movimientos.add(aux);
		}
		return movimientos;
	}

	public CuentaCorrienteDTO toDTO(){
		CuentaCorrienteDTO cuentadto = new CuentaCorrienteDTO(this.idCuentaCorriente, this.fecha, this.especie, this.saldo);
		if(!this.movimientos.isEmpty() || this.movimientos != null) {
			for(MovimientoCtaCte m : this.movimientos) {
				MovimientoCtaCteDTO movdto = m.toDTO();
				movdto.setCuentaCorriente(cuentadto);
				cuentadto.getMovimientos().add(movdto);
			}	
		}
		return cuentadto;
	}
	
	public int save() {
		return CuentaCorrienteDao.getInstancia().save(this);
		
	}

	//Getters y Setters

	public Date getFecha() {
		return fecha;
	}
	public int getidCuentaCorriente() {
		return idCuentaCorriente;
	}
	public void setidCuentaCorriente(int idCtaCte) {
		this.idCuentaCorriente = idCtaCte;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	public String getEspecie() {
		return especie;
	}
	public void setEspecie(String especie) {
		this.especie = especie;
	}
	public float getSaldo() {
		return saldo;
	}
	public void setSaldo(float saldo) {
		this.saldo = saldo;
	}

	public List<MovimientoCtaCte> getMovimientos() {
		return movimientos;
	}

	public void setMovimientos(List<MovimientoCtaCte> movimientos) {
		this.movimientos = movimientos;
	}


}