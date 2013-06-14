//Class used for remote object
import java.rmi.RemoteException;
import java.rmi.server.RemoteServer;
import java.rmi.server.ServerNotActiveException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CalculateLoanImpl extends UnicastRemoteObject implements CalculateLoan {
    //

    private Map<String, Double> exchangeRates;
    private ServerLogger logger;
    private int requestCount;

    public CalculateLoanImpl(Map<String, Double> exchangeRates, ServerLogger logger)
            throws RemoteException {

        this.exchangeRates = exchangeRates;
        this.logger = logger;
        requestCount = 0;
    }
    public List<String> getRates() throws RemoteException {
        return new ArrayList(exchangeRates.keySet());
    }

    @Override
    public LoanData calculateLoan(LoanParams parameters) throws RemoteException {

        double monthlyPayment = parameters.getAmount() *
                (parameters.getInrestRate() / 100) / (parameters.getYears() * 12);

        double totalPayment = parameters.getAmount() +
                parameters.getAmount() * (parameters.getInrestRate() / 100);

        if (!parameters.getCurencyCode().equals("BGN") &&
                exchangeRates.containsKey(parameters.getCurencyCode())) {

            monthlyPayment *= exchangeRates.get(parameters.getCurencyCode());
            totalPayment *= exchangeRates.get(parameters.getCurencyCode());

        }

        LoanData data = new LoanData(monthlyPayment, totalPayment);

        requestCount++;

        String clientIP = null;
        try {
            clientIP = RemoteServer.getClientHost();
        } catch (ServerNotActiveException ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }
        if (logger != null) {
            String message = String.format("\n  Request #" + requestCount + ":\n" +
                    "    %s with IP Address " + clientIP + " requested:\n" +
                    "     Anual Intrest Rate: %.2f\n" +
                    "     Number Of Years: %d\n" +
                    "     Loan Amoun: %.2f %s\n" +
                    "       Result:\n" +
                    "          Monthly Payment: %.2f BGN\n" +
                    "          Total Payment: %.2f BGN\n",parameters.getClientId(),
                    parameters.getInrestRate(), parameters.getYears(), parameters.getAmount(),
                    parameters.getCurencyCode(), data.getMonthlyPayment(), data.getTotalPayment());

            logger.printLog(message);
        }


        return data;
    }
}
