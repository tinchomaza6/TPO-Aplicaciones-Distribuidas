package dao;

import org.hibernate.SessionFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import excepciones.LoteException;
import hibernate.HibernateUtil;
import negocio.Lote;
import entities.LoteEntity;

public class LoteDao {

	private static LoteDao instancia;
	private static SessionFactory sf = null;

	public static LoteDao getInstancia(){
		if (instancia == null){
			sf = HibernateUtil.getSessionFactory();
			instancia = new LoteDao();
		}	
		return instancia;
	}

	public int save(Lote lote) throws LoteException{
		if (lote != null){
			Session s = sf.openSession();
			s.beginTransaction();
			Integer id = (Integer) s.save(lote.toEntitySave());
			s.flush();
			s.getTransaction().commit();
			s.close();
			return id;
		}else{
			throw new LoteException("Error al guardar un lote en la BD");
		}
	}

	public void update(Lote lote) throws LoteException  {
		if (lote != null){
			Session s = sf.openSession();
			s.beginTransaction();
			s.update(lote.toEntityUpdate());
			s.flush();
			s.getTransaction().commit();
			s.close();
		}else{
			throw new LoteException("Error al actualizar un lote en la BD");
		}
	}


	public void delete(Lote lote) throws LoteException  {
		if (lote != null){
			Session session = sf.openSession();
			session.beginTransaction();
			session.delete(lote.toEntityUpdate());
			session.flush();
			session.getTransaction().commit();
			session.close();
		} else {
			throw new LoteException("Error en el borrado de un lote en la BD");
		}	
	}

	public Lote buscarLoteById(int idLote) throws LoteException {
		LoteEntity loteEntity = null;
		Session session = sf.openSession();
		Query query = session.createQuery("select l from LoteEntity l where l.idLote=?");
		query.setParameter(0, idLote);
		loteEntity = (LoteEntity) query.uniqueResult();
		if (loteEntity == null) 
			throw new LoteException("Error al buscar el lote en la BD");
		else
			return loteEntity.toNegocio();
	}

}
