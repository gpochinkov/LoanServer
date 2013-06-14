//DATA SENT FROM THE SERVER DURING REMOTE INVOCATION
import java.io.Serializable;

public class LoanData implements Serializable{

	private double monthlyPayment;

	private double totalPayment;

        public LoanData(double monthlyPayment, double totalPayment) {
                 setMonthlyPayment(monthlyPayment);
                 setTotalPayment(totalPayment);
        }

	public double getMonthlyPayment() {
		return monthlyPayment;
	}

	public void setMonthlyPayment(double monthlyPayment) {
		this.monthlyPayment = monthlyPayment;
	}

	public double getTotalPayment() {
		return totalPayment;
	}

	public void setTotalPayment(double totalPayment) {
		this.totalPayment = totalPayment;
	}
}
