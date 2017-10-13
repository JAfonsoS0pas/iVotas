import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RMI extends Remote {
	public void mainUDPConnect() throws RemoteException;
	public void backupConnect() throws RemoteException;
}

