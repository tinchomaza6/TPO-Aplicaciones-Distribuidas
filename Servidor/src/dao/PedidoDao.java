package dao;


import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import dto.PedidoDTO;
import entities.PedidoEntity;
import excepciones.PedidoException;
import hibernate.HibernateUtil;
import negocio.Pedido;

public class PedidoDao {

	private static PedidoDao instancia = null;
	private static SessionFactory sf = null;
	//t

	public static PedidoDao getInstancia() {
		if(instancia==null) {
			sf = HibernateUtil.getSessionFactory();
			instancia = new PedidoDao();
		}
		return instancia;
	}

	public int save(Pedido p) throws PedidoException {
		if (p != null) {
			Session session = sf.openSession();
			session.beginTransaction();
			Integer id =  (Integer) session.save(p.toEntitySave());
			session.flush();
			session.getTransaction().commit();
			session.close();
			return id;
		}
		else {
			throw new PedidoException("Error en el guardado del Pedido");
		}	
	}

	public void update(Pedido p) throws PedidoException {
		if (p != null) {
			Session session = sf.openSession();
			session.beginTransaction();
			session.update(p.toEntityUpdate());
			session.flush();
			session.getTransaction().commit();
			session.close();
		} else {
			throw new PedidoException("Error al actualizar el Pedido");
		}	
	}

	public void delete(Pedido p) throws PedidoException {
		if (p != null) {
			Session session = sf.openSession();
			session.beginTransaction();
			session.delete(p.toEntityUpdate());
			session.flush();
			session.getTransaction().commit();
			session.close();
		} else{
			throw new PedidoException("Error al borrar el Pedido");
		}	
	}

	public Pedido buscarPedidoById(int idPedido) throws PedidoException{
		PedidoEntity aux;
		Session s = sf.openSession();
		aux = (PedidoEntity) s.createQuery("Select p From PedidoEntity p where p.nroPedido = ?").setParameter(0, idPedido).uniqueResult();
		if (aux != null) 
			return aux.toNegocio();
		else
			throw new PedidoException("Error al buscar pedido en la BD");
	}

	@SuppressWarnings("unchecked")
	public List<Pedido> buscarPedidosByEstado(String estado) throws PedidoException{
		List<Pedido> devolver = new ArrayList<Pedido>();
		Session s = sf.openSession();
		Query query = s.createQuery("Select p From PedidoEntity p where p.estado = ?");
		query.setParameter(0, estado);
		List<PedidoEntity> aux = query.list();
		if (aux != null) { 
			for(PedidoEntity ped : aux)
				devolver.add(ped.toNegocio());
			return devolver;
		}else{
			throw new PedidoException("Error al buscar lista de pedidos en la BD");
		}
	}

	public List<Pedido> buscarPedidosByEstadoPorOrden(String estado, int nroPedido) throws PedidoException {
		List<Pedido> devolver = new ArrayList<Pedido>();
		Session s = sf.openSession();
		s.beginTransaction();
		Query query = s.createQuery("Select p From PedidoEntity p where p.estado = ? and p.nroPedido>?");
		query.setParameter(0, estado);
		query.setParameter(1, nroPedido);
		@SuppressWarnings("unchecked")
		List<PedidoEntity> aux = query.list();
		if (aux != null) {
			for(PedidoEntity ped : aux)
				devolver.add(ped.toNegocio());
			s.flush();
			s.getTransaction().commit();
			s.close();
			return devolver;
		}else{
			throw new PedidoException("Error al buscar lista de pedidos en la BD");
		}
	}

	public List<Pedido> buscarPedidosByCliente(int dni) throws PedidoException {
		List<Pedido> devolver = new ArrayList<Pedido>();
		Session s = sf.openSession();
		Query query = s.createQuery("Select p From PedidoEntity p");
		List<PedidoEntity> aux = query.list();
		if (aux != null) {
			for(PedidoEntity ped : aux) {
				if(ped.getCliente().getDni()==dni)
					devolver.add(ped.toNegocio());
			}
			return devolver;
		}else{
			throw new PedidoException("Error al buscar lista de pedidos en la BD");
		}
	}
}