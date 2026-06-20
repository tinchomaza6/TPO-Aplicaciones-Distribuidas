package dao;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.Session;
import entities.OrdenDePedidoEntity;
import java.util.*;

import javax.swing.JOptionPane;

import excepciones.OrdenDePedidoException;
import excepciones.PedidoException;
import hibernate.HibernateUtil;
import negocio.Articulo;
import negocio.ItemOrdenDePedido;
import negocio.ItemPedido;
import negocio.OrdenDePedido;

public class OrdenDePedidoDao {

	private static OrdenDePedidoDao instancia;
	private static SessionFactory sf = null;

	public static OrdenDePedidoDao getInstancia(){
		if (instancia == null)
			sf = HibernateUtil.getSessionFactory();
		instancia = new OrdenDePedidoDao();
		return instancia;
	}

	public int save(OrdenDePedido op) throws OrdenDePedidoException{
		if (op != null){
			Session s = sf.openSession();
			s.beginTransaction();
			int id = (Integer) s.save(op.toEntitySave());
			s.getTransaction().commit();
			s.close();
			return id;
		}else{
			throw new OrdenDePedidoException("Error en el guardado de una orden de pedido en la BD");
		}
	}

	public void update(OrdenDePedido op) throws OrdenDePedidoException{
		if (op != null){
			Session s = sf.openSession();
			s.beginTransaction();
			s.update(op.toEntityUpdate());
			s.flush();
			s.getTransaction().commit();
			s.close();
		}else{
			throw new OrdenDePedidoException("Error en el guardado de una orden de pedido en la BD");
		}
	}


	public void delete(OrdenDePedido op) throws OrdenDePedidoException{
		if (op != null){
			Session s = sf.openSession();
			s.beginTransaction();
			s.delete(op.toEntityUpdate());
			s.getTransaction().commit();
			s.close();
		}else{
			throw new OrdenDePedidoException("Error en el guardado de una orden de compra en la BD");
		}
	}

	public List<OrdenDePedido> cargarTodasOrdenPedido() throws OrdenDePedidoException{
		List<OrdenDePedido> ordenes = new ArrayList<OrdenDePedido>();
		Session session = sf.openSession(); 
		Query query = session.createQuery("select o from OrdenDePedidoEntity o");
		@SuppressWarnings("unchecked")
		List<OrdenDePedidoEntity> ordenesEntity = query.list();
		if (ordenesEntity != null){
			for(OrdenDePedidoEntity oe : ordenesEntity)
				ordenes.add(oe.toNegocio2());
			return ordenes;
		} else {
			throw new OrdenDePedidoException("Error al cargar Ordenes de Pedido de la BD");
		}
	}

	public boolean existeOrdenPedido(Articulo articulo, int cantidad) throws OrdenDePedidoException {
		List<OrdenDePedido> ords = this.cargarTodasOrdenPedido();
		for (OrdenDePedido op : ords) {
			for (ItemOrdenDePedido item : op.getArticulos())
				for (ItemPedido ip : op.getPedido().getItemsPedido()) {
					if (item.getArticulo().getIdArticulo() == articulo.getIdArticulo() && item.getArticulo().getIdArticulo() == ip.getArticulo().getIdArticulo() &&
							(item.getCant() - (ip.getCant()-ip.getArticulo().cantidadReservada(ip.getPedido().getNroPedido()))) >= cantidad)
						return true;
				}
		}
		return false;
	}

	public OrdenDePedido buscarOPByID(int idOp) throws PedidoException {
		OrdenDePedidoEntity ord = null;
		Session session = sf.openSession();
		session.beginTransaction();
		Query query = session.createQuery("select o from OrdenDePedidoEntity o where o.idOP=?");
		query.setParameter(0, idOp); 
		ord = (OrdenDePedidoEntity) query.uniqueResult();
		session.flush();
		session.getTransaction().commit();
		session.close();
		if (ord == null) 
			throw new PedidoException("Error al buscar la orden de pedido en la BD");
		else
			return ord.toNegocio2();
	}

	public OrdenDePedido buscarOPbyPedido(int nroPedido) {
		Session session = sf.openSession();
		session.beginTransaction();
		Query query = session.createQuery("select o from OrdenDePedidoEntity o");
		@SuppressWarnings("unchecked")
		List<OrdenDePedidoEntity> ordenesEntity = query.list();
		session.flush();
		session.getTransaction().commit();
		session.close();
		for(OrdenDePedidoEntity ope : ordenesEntity) 
			if(ope.getPedido().getNroPedido() == nroPedido)
				return ope.toNegocio2();
		return null;
	}

	public List<OrdenDePedido> buscarOPS() {
		List<OrdenDePedido> ordenes = new ArrayList<OrdenDePedido>();
		Session session = sf.openSession();
		Query query = session.createQuery("select o from OrdenDePedidoEntity o");
		@SuppressWarnings("unchecked")
		List<OrdenDePedidoEntity> ordenesEntity = query.list();
		for(OrdenDePedidoEntity oe : ordenesEntity)
			if(oe.getEstado().equals("PENDIENTE DE PROVEEDOR")) {
				ordenes.add(oe.toNegocio2());
			}
		return ordenes;
	}
}