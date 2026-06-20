package dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.Session;
import entities.OrdenDeCompraEntity;
import excepciones.OrdenDeCompraException;
import hibernate.HibernateUtil;
import negocio.OrdenDeCompra;
import negocio.OrdenDePedido;

public class OrdenDeCompraDao {
	private static OrdenDeCompraDao instancia;
	private static SessionFactory sf = null;

	public static OrdenDeCompraDao getInstancia(){
		if (instancia == null)
			sf = HibernateUtil.getSessionFactory();
		instancia = new OrdenDeCompraDao();
		return instancia;
	}

	public int save(OrdenDeCompra oc) throws OrdenDeCompraException{
		if (oc != null){
			Session s = sf.openSession();
			s.beginTransaction();
			int id = (Integer) s.save(oc.toEntitySave());
			s.getTransaction().commit();
			s.close();
			return id;
		}else{
			throw new OrdenDeCompraException("Error en el guardado de una orden de compra en la BD");
		}
	}

	public void update(OrdenDeCompra oc) throws OrdenDeCompraException{
		if (oc != null){
			Session s = sf.openSession();
			s.beginTransaction();
			s.update(oc.toEntityUpdate());
			s.getTransaction().commit();
			s.close();
		}else{
			throw new OrdenDeCompraException("Error al actualizar una orden de compra en la BD");
		}
	}

	public void delete(OrdenDeCompra oc) throws OrdenDeCompraException{
		if (oc != null){
			Session s = sf.openSession();
			s.beginTransaction();
			s.delete(oc.toEntityUpdate());
			s.getTransaction().commit();
			s.close();
		}else{
			throw new OrdenDeCompraException("Error al borrar una orden de compra en la BD");
		}
	}


	public OrdenDeCompra buscarOCById(int id) throws OrdenDeCompraException  {
		OrdenDeCompraEntity oc = null;
		Session session = sf.openSession();
		Query query = session.createQuery("select o from OrdenDeCompraEntity o where o.idOC=?");
		query.setParameter(0, id);
		oc = (OrdenDeCompraEntity) query.uniqueResult();
		if (oc == null) 
			throw new OrdenDeCompraException("Error al buscar la Orden de compra en la BD");
		else
			return oc.toNegocio();		
	}

	public List<OrdenDeCompra> buscarOCCompletadas() {
		Session session = sf.openSession();
		Query query = session.createQuery("select o from OrdenDeCompraEntity o order by o.fecha desc");
		List<OrdenDeCompraEntity> ordenes = query.list();
		List<OrdenDeCompra> devolver = new ArrayList<OrdenDeCompra>();
		for (OrdenDeCompraEntity ocent : ordenes) {
			if(ocent.getEstado().equals("COMPLETADA")) {
				devolver.add(ocent.toNegocio());
			}
		}
		return devolver;
	}

	public List<OrdenDeCompra> buscarOCS() {
		Session session = sf.openSession();
		Query query = session.createQuery("select o from OrdenDeCompraEntity o order by o.fecha desc");
		List<OrdenDeCompraEntity> ordenes = query.list();
		List<OrdenDeCompra> devolver = new ArrayList<OrdenDeCompra>();
		for (OrdenDeCompraEntity ocent : ordenes) {
			if(ocent.getEstado().equals("PENDIENTE"))
				devolver.add(ocent.toNegocio());
		}
		return devolver;
	}
}