package dao;

import java.util.*;

import javax.swing.JOptionPane;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.Session;
import entities.ArticuloDepositoEntity;
import excepciones.ArticuloException;
import hibernate.HibernateUtil;
import negocio.Articulo;
import negocio.ArticuloDeposito;
import negocio.Pedido;

public class ArticuloDepositoDao {
	

	private static ArticuloDepositoDao instancia;
	private static SessionFactory sf = null;

	public static ArticuloDepositoDao getInstancia(){
		if (instancia == null)
			sf = HibernateUtil.getSessionFactory();
		instancia = new ArticuloDepositoDao();
		return instancia;
	}
	
	

	public int save(ArticuloDeposito articulo) throws ArticuloException{
		if (articulo != null){
			SessionFactory sf = HibernateUtil.getSessionFactory();
			Session s = sf.openSession();
			s.beginTransaction();
			int id = (Integer) s.save(articulo.toEntitySave());
			s.flush();
			s.getTransaction().commit();
			s.close();
			return id;
		}else{
			throw new ArticuloException("Error en el guardado del articulo en la BD");
		}
	}

	public void update(ArticuloDeposito articulo) throws ArticuloException{
		if (articulo != null){
			Session s = sf.openSession();
			s.beginTransaction();
			s.update(articulo.toEntityUpdate());
			s.flush();
			s.getTransaction().commit();
			s.close();
		}else{
			throw new ArticuloException("Error en el update del articulo en la BD");
		}
	}
	public void delete(ArticuloDeposito articulo) throws ArticuloException {
		if (articulo != null){
			Session session = sf.openSession();
			session.beginTransaction();
			ArticuloDepositoEntity a = articulo.toEntityDelete();
			Query query = session.createQuery("delete from ArticuloDepositoEntity where idArticuloDeposito = ?");
			query.setParameter(0, a.getIdArticuloDeposito());
			query.executeUpdate();
			session.flush();
			session.getTransaction().commit();
			session.close();
			
		} else { 
			throw new ArticuloException("Error en el borrado del articulo en la BD");
		}	
	}


	public ArticuloDeposito buscarArticuloById(int idArticulo) throws ArticuloException{
		ArticuloDepositoEntity ar = null;
		Session s = sf.openSession();
		s.beginTransaction();
		Query query = s.createQuery("select c from ArticuloDepositoEntity c where c.idArticuloDeposito=?");
		query.setParameter(0, idArticulo);
		ar = (ArticuloDepositoEntity) query.uniqueResult();
		if (ar == null)
			throw new ArticuloException("Error en la busqueda del Articulo en la BD");
		else
			return ar.toNegocio();	
	}



	public List<ArticuloDeposito> cargarArticulosDeposito() {
		Session session = sf.openSession();
		Query query = session.createQuery("select c from ArticuloDepositoEntity c");
		@SuppressWarnings("unchecked")
		List<ArticuloDepositoEntity> artDepoEnt = query.list();
		List<ArticuloDeposito> artsdep = new ArrayList<ArticuloDeposito>();
		for(ArticuloDepositoEntity ae : artDepoEnt)
			artsdep.add(ae.toNegocio());
		return artsdep;
	}



	public List<ArticuloDeposito> buscarArticulosReservados(Pedido pedido, Articulo a) {
		Session session = sf.openSession();
		Query query = session.createQuery("select c from ArticuloDepositoEntity c");
		@SuppressWarnings("unchecked")
		List<ArticuloDepositoEntity> artDepoEnt = query.list();
		List<ArticuloDeposito> devolver = new ArrayList<ArticuloDeposito>();
		for (ArticuloDepositoEntity art : artDepoEnt) {
			if (art.getArticulo().getIdArticulo() == a.getIdArticulo() && (art.getReservaIdPedido() != null && pedido.getNroPedido().compareTo(art.getReservaIdPedido())==0)) {
				devolver.add(art.toNegocio());
			}
		}
		return devolver;
	}
}
