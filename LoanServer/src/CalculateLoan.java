//INTERFACE FOR REMOTE INVOCATION

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface CalculateLoan extends Remote {

	public LoanData calculateLoan(LoanParams parameters) throws RemoteException;
        public List<String> getRates() throws RemoteException;
}
