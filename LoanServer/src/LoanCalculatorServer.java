
import java.awt.BorderLayout;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

public class LoanCalculatorServer extends JFrame implements ServerLogger {

    private JTextArea log;

    public LoanCalculatorServer() {
        super("Loan Server");
        log = new JTextArea(); // create displayArea
        log.setEditable(false);
        add(new JScrollPane(log), BorderLayout.CENTER);

        setSize(400, 200); // set size of window
        setVisible(true); // show window

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    @Override
    // print log
    public void printLog(String message) {
        displayMessage("\n" + new Date().toString() + ": " + message);
        saveLog("\n" + new Date().toString() + ": " + message);
    }

    // log to file
    public void saveLog(String message){
         try {
            FileWriter fstream = new FileWriter("log.txt", true);
            BufferedWriter out = new BufferedWriter(fstream);
            try{
            String logWithLines = message.replaceAll("\n",System.getProperty("line.separator"));
            out.write(logWithLines);
            }
            finally{
                out.close();
                fstream.close();
            }
        } catch (Exception e) {
            printLog("Error: " + e.getMessage());
        }
    }

    //display message and update moving of the JScrollPane
    private void displayMessage(final String messageToDisplay) {
        SwingUtilities.invokeLater(
                new Runnable() {

                    public void run() // updates displayArea
                    {
                        log.append(messageToDisplay); // append message
                    } // end method run
                } // end anonymous inner class
                ); // end call to SwingUtilities.invokeLater
    } // end method displayMessage


    private static Map<String, Double> readExchangeRates(String file, ServerLogger logger) {
        Map<String, Double> rates = new HashMap<String, Double>();
        try {
            BufferedReader buff = new BufferedReader(new FileReader(file));
            try {
                String line = null;

                while ((line = buff.readLine()) != null) {
                    String[] parts = line.split(" ");
                    try{
                    rates.put(parts[0], Double.parseDouble(parts[1]));
                    }
                    catch(NumberFormatException ex){
                        logger.printLog("Incorrect "+file+"content.");
                    }
                }
            }
            finally {
                buff.close();
            }
        } catch (IOException ex) {
            logger.printLog("Problem detected during reading "+file+".\n" + ex.getMessage());
        }

        return rates;
    }

    public static void main(String[] args) {
        LoanCalculatorServer app = new LoanCalculatorServer();

        Map<String, Double> rates = readExchangeRates("rates.txt", app);

        try {
            CalculateLoan service = new CalculateLoanImpl(rates, app);
            Registry registry = LocateRegistry.createRegistry(3232);
            registry.rebind("LoanCalulator", service);
            app.printLog("Loan server started.\n\n");
        } catch (Exception ex) {
            app.printLog("Problem detected during starting the server.\n" + ex.getMessage());
            ex.printStackTrace();
        }
    }
}
