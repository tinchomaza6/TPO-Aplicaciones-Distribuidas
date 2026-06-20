package dao;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import dto.MovimientoAjusteDTO;
import dto.MovimientoDañoDTO;
import dto.MovimientoSimpleDTO;
import dto.MovimientoStockDTO;
import entities.MovimientoAjusteEntity;
import entities.MovimientoDañoEntity;
import entities.MovimientoEntity;
import entities.MovimientoSimpleEntity;
import excepciones.MovimientoException;
import hibernate.HibernateUtil;
import negocio.MovimientoAjuste;
import negocio.MovimientoDaño;
import negocio.MovimientoSimple;
import negocio.MovimientoStock;

public class MovimientoDao {

	private static MovimientoDao instancia;
	private static SessionFactory sf;

	private MovimientoDao(){}

	public static MovimientoDao getInstancia(){
		if(instancia == null){
			instancia = new MovimientoDao();
			sf = HibernateUtil.getSessionFactory();
		}
		return instancia;
	}

	public void save(MovimientoSimple movimiento) throws MovimientoException{
		if (movimiento != null){
			MovimientoSimpleEntity aux = new MovimientoSimpleEntity();
			aux.setArticulo(movimiento.getArticulo().toEntityUpdate());
			aux.setFecha(movimiento.getFecha());
			aux.setTipoMovimiento("SIMPLE");
			aux.setDescripcion(movimiento.getDescripcion());
			Session s = sf.openSession();
			s.beginTransaction();
			s.save(aux);
			s.getTransaction().commit();
			s.close();
		}else{
			throw new MovimientoException("Error en el guardado de un movimiento simple en la BD");
		}
	}

	public void save(MovimientoDaño movimiento) throws MovimientoException{
		if (movimiento != null){
			MovimientoDañoEntity aux = new MovimientoDañoEntity();
			aux.setTipoMovimiento("DAÑO");
			aux.setArticulo(movimiento.getArticulo().toEntityUpdate());
			aux.setEncargado(movimiento.getEncargado());
			aux.setFecha(movimiento.getFecha());
			aux.setAutorizante(movimiento.getAutorizante());
			aux.setDescripcion(movimiento.getDescripcion());
			aux.setDestino(movimiento.getDestino());
			Session s = sf.openSession();
			s.beginTransaction();
			s.save(aux);
			s.getTransaction().commit();
			s.close();
		}else{
			throw new MovimientoException("Error en el guardado de un movimiento por daño en la BD");
		}
	}

	public void save(MovimientoAjuste movimiento) throws MovimientoException{
		if (movimiento != null){
			MovimientoAjusteEntity aux = new MovimientoAjusteEntity();
			aux.setTipoMovimiento("AJUSTE");
			aux.setArticulo(movimiento.getArticulo().toEntityUpdate());
			aux.setEncargado(movimiento.getEncargado());
			aux.setFecha(movimiento.getFecha());
			aux.setDescripcion(movimiento.getDescripcion());		
			Session s = sf.openSession();
			s.beginTransaction();
			s.save(aux);
			s.getTransaction().commit();
			s.close();
		}else{
			throw new MovimientoException("Error en el guardado de un movimiento por ajuste en la BD");
		}
	}


	public void update(MovimientoSimple movimiento) throws MovimientoException{
		if (movimiento != null){
			MovimientoSimpleEntity aux = new MovimientoSimpleEntity();
			aux.setIdMov(movimiento.getIdMov());
			aux.setArticulo(movimiento.getArticulo().toEntityUpdate());
			aux.setFecha(movimiento.getFecha());
			aux.setTipoMovimiento("SIMPLE");
			aux.setDescripcion(movimiento.getDescripcion());
			Session s = sf.openSession();
			s.beginTransaction();
			s.update(aux);
			s.getTransaction().commit();
			s.close();
		}else{
			throw new MovimientoException("Error al actualizar un movimiento simple en la BD");
		}
	}

	public void update(MovimientoDaño movimiento) throws MovimientoException{
		if (movimiento != null){
			MovimientoDañoEntity aux = new MovimientoDañoEntity();
			aux.setTipoMovimiento("DAÑO");
			aux.setArticulo(movimiento.getArticulo().toEntityUpdate());
			aux.setEncargado(movimiento.getEncargado());
			aux.setFecha(movimiento.getFecha());
			aux.setAutorizante(movimiento.getAutorizante());
			aux.setDescripcion(movimiento.getDescripcion());
			aux.setDestino(movimiento.getDestino());
			aux.setIdMov(movimiento.getIdMov());
			Session s = sf.openSession();
			s.beginTransaction();
			s.update(aux);
			s.getTransaction().commit();
			s.close();
		}else{
			throw new MovimientoException("Error al actualizar un movimiento por daño en la BD");
		}
	}

	public void update(MovimientoAjuste movimiento) throws MovimientoException{
		if (movimiento != null){
			MovimientoAjusteEntity aux = new MovimientoAjusteEntity();
			aux.setTipoMovimiento("AJUSTE");
			aux.setArticulo(movimiento.getArticulo().toEntityUpdate());
			aux.setEncargado(movimiento.getEncargado());
			aux.setFecha(movimiento.getFecha());
			aux.setDescripcion(movimiento.getDescripcion());
			aux.setIdMov(movimiento.getIdMov());		
			Session s = sf.openSession();
			s.beginTransaction();
			s.update(aux);
			s.getTransaction().commit();
			s.close();
		}else{
			throw new MovimientoException("Error al actualizar un movimiento por ajuste en la BD");
		}
	}

	public void delete(MovimientoSimple movimiento) throws MovimientoException{
		if (movimiento != null){
			MovimientoSimpleEntity aux = new MovimientoSimpleEntity();
			aux.setIdMov(movimiento.getIdMov());
			aux.setArticulo(movimiento.getArticulo().toEntityUpdate());
			aux.setFecha(movimiento.getFecha());
			aux.setTipoMovimiento("SIMPLE");
			aux.setDescripcion(movimiento.getDescripcion());
			Session s = sf.openSession();
			s.beginTransaction();
			s.delete(aux);
			s.getTransaction().commit();
			s.close();
		}else{
			throw new MovimientoException("Error al borrar un movimiento simple en la BD");
		}
	}

	public void delete(MovimientoDaño movimiento) throws MovimientoException{
		if (movimiento != null){
			MovimientoDañoEntity aux = new MovimientoDañoEntity();
			aux.setTipoMovimiento("DAÑO");
			aux.setArticulo(movimiento.getArticulo().toEntityUpdate());
			aux.setEncargado(movimiento.getEncargado());
			aux.setFecha(movimiento.getFecha());
			aux.setAutorizante(movimiento.getAutorizante());
			aux.setDescripcion(movimiento.getDescripcion());
			aux.setDestino(movimiento.getDestino());
			aux.setIdMov(movimiento.getIdMov());
			Session s = sf.openSession();
			s.beginTransaction();
			s.delete(aux);
			s.getTransaction().commit();
			s.close();
		}else{
			throw new MovimientoException("Error al borrar un movimiento por daño en la BD");
		}
	}

	public void delete(MovimientoAjuste movimiento) throws MovimientoException{
		if (movimiento != null){
			MovimientoAjusteEntity aux = new MovimientoAjusteEntity();
			aux.setTipoMovimiento("AJUSTE");
			aux.setArticulo(movimiento.getArticulo().toEntityUpdate());
			aux.setEncargado(movimiento.getEncargado());
			aux.setFecha(movimiento.getFecha());
			aux.setDescripcion(movimiento.getDescripcion());
			aux.setIdMov(movimiento.getIdMov());		
			Session s = sf.openSession();
			s.beginTransaction();
			s.delete(aux);
			s.getTransaction().commit();
			s.close();
		}else{
			throw new MovimientoException("Error al borrar un movimiento por ajuste en la BD");
		}
	}

	public List<MovimientoSimpleDTO> buscarMovimientosSimple() {
		Session s = sf.openSession();
		List<MovimientoSimpleDTO> mov = new ArrayList<MovimientoSimpleDTO>();
		Query query = s.createQuery("select m from MovimientoEntity m"); //agregar que ordene por tipo y fecha para mostrarlos agrupados
		@SuppressWarnings("unchecked")
		List<MovimientoEntity> movimientos = query.list();
		for (MovimientoEntity m : movimientos) {
			if (m.getTipoMovimiento().equals("SIMPLE")) {
			mov.add((MovimientoSimpleDTO) m.toNegocio().toDTO());
			}
		}
		return mov;
	}
	
	
	
	public List<MovimientoAjusteDTO> buscarMovimientosAjuste() {
		Session s = sf.openSession();
		List<MovimientoAjusteDTO> mov =  new ArrayList<MovimientoAjusteDTO>();
		Query query = s.createQuery("select m from MovimientoEntity m"); //agregar que ordene por tipo y fecha para mostrarlos agrupados
		@SuppressWarnings("unchecked")
		List<MovimientoEntity> movimientos = query.list();
		for (MovimientoEntity m : movimientos) {
			if (m.getTipoMovimiento().equals("AJUSTE")) {
				mov.add((MovimientoAjusteDTO) m.toNegocio().toDTO());
			}
		}
		return mov;
	}
	
	
	public List<MovimientoDañoDTO> buscarMovimientosDaño() {
		Session s = sf.openSession();
		List<MovimientoDañoDTO> mov= new ArrayList<MovimientoDañoDTO>();
		Query query = s.createQuery("select m from MovimientoEntity m"); //agregar que ordene por tipo y fecha para mostrarlos agrupados
		@SuppressWarnings("unchecked")
		List<MovimientoEntity> movimientos = query.list();
		for (MovimientoEntity m : movimientos) {
			if (m.getTipoMovimiento().equals("DAÑO")) {
			mov.add((MovimientoDañoDTO) m.toNegocio().toDTO());
			}
		}
		return mov;
	}
}