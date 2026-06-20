package dao;


import javax.swing.JOptionPane;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import entities.ClienteEntity;
import entities.CuentaCorrienteEntity;
import excepciones.CuentaCorrienteException;
import hibernate.HibernateUtil;
import negocio.CuentaCorriente;

public class CuentaCorrienteDao {

	private static CuentaCorrienteDao instancia = null;
	private static SessionFactory sf = null;

	public static CuentaCorrienteDao getInstancia() {
		if(instancia==null) {
			sf = HibernateUtil.getSessionFactory();
			instancia = new CuentaCorrienteDao();
		}
		return instancia;
	}

	public int save(CuentaCorriente cuentaCorriente) {
		if (cuentaCorriente!=null){
			Session session = sf.openSession();
			session.beginTransaction();
			int id= (Integer) session.save(cuentaCorriente.toEntitySave());
			session.flush();
			session.getTransaction().commit();
			session.close();
			return id;
		}
		return 0;
	
	}

	public void update(CuentaCorriente cuentaCorriente) throws CuentaCorrienteException {
		if (cuentaCorriente!=null){
			Session s = sf.openSession();
			s.beginTransaction();
			s.update(cuentaCorriente.toEntityUpdate());
			s.flush();
			s.getTransaction().commit();
			s.close();
		}
		else{
			throw new CuentaCorrienteException("Error al actualizar la cuenta corriente");
		}
	}

	public CuentaCorrienteEntity getCuentaCorrienteByDni(int dni) throws CuentaCorrienteException   {
		ClienteEntity cliente = null;
		Session session = sf.openSession();
		Query query = session.createQuery("select c from ClienteEntity c where c.dni=?");
		query.setParameter(0, dni);
		cliente = (ClienteEntity) query.uniqueResult();
		if (cliente.getCuentaCorriente()==null)
			throw new CuentaCorrienteException("Error al obtener la cuenta corriente en la BD");
		else
			return cliente.getCuentaCorriente();
	}
	
	
	/*public void updateSaldo(CuentaCorriente cuentaCorriente) {
		if (cuentaCorriente!=null){
			Session s = sf.openSession();
			Query query = s.createQuery("update CuentaCorrienteEntity set saldo = ? where idCuentaCorriente = ?");
			query.setParameter(0, cuentaCorriente.consultarSaldo());
			query.setParameter(1, cuentaCorriente.getidCuentaCorriente());
			query.executeUpdate();
			s.flush();
			s.close();
		}
	}*/
}