//DATA SENT FROM THE CLIENT TO THE SERVER DURING REMOTE INVOCATION
import java.io.Serializable;



public class LoanParams implements Serializable {

        private String ClientId;

	private double inrestRate;

	private int years;

	private String curencyCode;

	private double amount;



	public LoanParams(String ClientId, double inrestRate, int years, String curencyCode, double amount) {
		setClientId(ClientId);
                setInrestRate(inrestRate);
		setYears(years);
		setCurencyCode(curencyCode);
		setAmount(amount);
	}

        public String getClientId() {
            return ClientId;
        }

         public void setClientId(String ClientId) {
            this.ClientId = ClientId;
         }

	public double getInrestRate() {
		return inrestRate;
	}

	public void setInrestRate(double inrestRate) {
		this.inrestRate = inrestRate;
	}

	public int getYears() {
		return years;
	}

	public void setYears(int years) {
		this.years = years;
	}

	public String getCurencyCode() {
		return curencyCode;
	}

	public void setCurencyCode(String curencyCode) {
		this.curencyCode = curencyCode;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}
}
