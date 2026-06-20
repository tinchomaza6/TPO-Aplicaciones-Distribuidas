package dao;


import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import entities.FacturaEntity;
import excepciones.FacturaException;
import hibernate.HibernateUtil;
import negocio.Factura;

public class FacturaDao {


	private static FacturaDao instancia;
	private static SessionFactory sf = null;

	public static FacturaDao getInstancia() {
		if(instancia==null) {
			instancia = new FacturaDao();
			sf = HibernateUtil.getSessionFactory();
		}
		return instancia;
	}

	public int save(Factura factura) throws FacturaException{
		if (factura != null){
			Session s = sf.openSession();
			s.beginTransaction();
			Integer nroFactura = (Integer) s.save(factura.toEntitySave());
			s.flush();
			s.getTransaction().commit();
			s.close();
			return nroFactura;
		}else{
			throw new FacturaException("Error al guardar la factura en la BD");
		}
	}

	public Factura buscarFacturaById(int idFactura) throws FacturaException{
		FacturaEntity aux = new FacturaEntity();
		Session s = sf.openSession();
		aux = (FacturaEntity) s.createQuery("select f from FacturaEntity f where f.nroFactura=?").setParameter(0, idFactura).uniqueResult();
		if (aux == null)
			throw new FacturaException("Error al buscar la Factura en la BD");
		else
			return aux.toNegocio();
	}

	public void update(Factura factura) throws FacturaException {
		if (factura != null){
			Session s = sf.openSession();
			s.beginTransaction();
			s.update(factura.toEntityUpdate());
			s.flush();
			s.getTransaction().commit();
			s.close();
		}else{
			throw new FacturaException("Error al actualizar la Factura en la BD");
		}
	}

	public void delete(Factura factura) throws FacturaException {
		if (factura != null){
			Session session = sf.openSession();
			session.beginTransaction();
			session.delete(factura.toEntityUpdate());
			session.flush();
			session.getTransaction().commit();
			session.close();
		} else {
			throw new FacturaException("Error en el borrado de factura en la BD");
		}	
	}

	public List<Factura> buscarFacturasByCliente(int dni) throws FacturaException {
		List<Factura> devolver = new ArrayList<Factura>();
		Session s = sf.openSession();
		s.beginTransaction();
		Query query =  s.createQuery("select f from FacturaEntity f order by f.nroFactura");
		@SuppressWarnings("unchecked")
		List<FacturaEntity> aux = query.list();
		if (aux != null){
			for(FacturaEntity fac : aux) {
				if(fac.getEstado().equals("IMPAGA") && fac.getCliente().getDni() == dni)
					devolver.add(fac.toNegocio());
			}
			s.flush();
			s.getTransaction().commit();
			s.close();
			return devolver;
		}else{
			throw new FacturaException("Error al obtener la lista de Facturas en la BD");
		}
	}

}
