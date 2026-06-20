package dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.Session;

import entities.ArticuloEntity;
import entities.ProveedorEntity;
import excepciones.ProveedorException;
import hibernate.HibernateUtil;
import negocio.Articulo;
import negocio.Proveedor;

public class ProveedoresDao {

	private static ProveedoresDao instancia;
	private static SessionFactory sf = null;

	public static ProveedoresDao getInstancia(){
		if (instancia == null){
			sf = HibernateUtil.getSessionFactory();
			instancia = new ProveedoresDao();
		}	
		return instancia;
	}
	
	public Proveedor buscarProveedorByCuit(int cuit) throws	ProveedorException  {
		ProveedorEntity prov = null;
		Session session = sf.openSession();
		Query query = session.createQuery("select p from ProveedorEntity p where p.cuit=?");
		query.setParameter(0, cuit); 
		prov = (ProveedorEntity) query.uniqueResult();
		if (prov == null) 
			throw new ProveedorException("Error al buscar el proveedor en la BD");
		else
			return prov.toNegocio();		
	}

	public List<Proveedor> buscarProveedores(int idArticulo) {
		Session session = sf.openSession();
		Query query = session.createQuery("select p from ProveedorEntity p");
		List<ProveedorEntity> provs = query.list();
		List<Proveedor> devolver = new ArrayList<Proveedor>();
		for(ProveedorEntity p : provs) {
			for(ArticuloEntity a : p.getArticulos()) {
				if(a.getIdArticulo() == idArticulo) {
					devolver.add(p.toNegocio());
				}
			}
		}
		return devolver;
	}
}
