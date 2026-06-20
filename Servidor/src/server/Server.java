package server;

import interfazRemota.InterfazRemota;
import objetoRemoto.ObjetoRemoto;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;
import java.net.MalformedURLException;
import java.rmi.RemoteException;
import javax.swing.JOptionPane;

import delegado.BusinessDelegate;

public class Server {

	public Server() {
		publicar();
	}
	
	private void publicar() {
		try {
    		LocateRegistry.createRegistry(1099);
            InterfazRemota ir = new ObjetoRemoto();
            Naming.rebind ("//localhost/circuitoPedidos", ir);
            System.out.println("Servidor Iniciado en //localhost/circuitoPedidos");
		} catch (RemoteException e) {
			e.printStackTrace();
			//JOptionPane.showMessageDialog(null, "Error de iniciaización del objeto remoto");
		} catch (MalformedURLException e) {
			JOptionPane.showMessageDialog(null, "Error de publicación del objeto");
		}
    }
	
	public static void main(String[] args) throws RemoteException {
		new Server();
		try {
			BusinessDelegate.getInstancia().cargarTodasUbicacionesYArticulos();
			System.out.println("Ubicaciones y Articulos Deposito cargados con exito");
		} catch (RemoteException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
}
