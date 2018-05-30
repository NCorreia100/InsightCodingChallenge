
/* This is an Insight challenges which pertains creating a pipeline for processing EDGAR weblogs, then creating a new document that identifies each visit, duration and no. of documents requested
 * 
 * Author: Nuno Correia (NCorreia1@my.devry.edu / 646-256-9326)
 *  
 * Date: 5/26/2018 - 5/29/2018
 *   
 * Description of class: SessionizationInput handles all input which is made by reading log.cvs and inactivity_period.txt
 */

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Date;
import java.text.SimpleDateFormat;


import javax.swing.JOptionPane;

public class SessionizationInput {

	private String filePath;
	private BufferedReader br;
	private boolean header;      //just to skip the header of file
	private String ip;           // session ip
	private Date dateTime;       // date + time of request
	private String docInfo;      // central index key + accession# + doc filename | note that this var is only here because it was requested, it has no use for the output file
	private String csvLine;      // an individual line of the csv file
	private String[] csvFields;  // same as above, except that fields are splitted	
	private long timeOutVar;     // secs until timeout session

	// constructor:
	public SessionizationInput() {
		this.filePath = "./input/log.csv";
		this.header = true;
		this.ip = "";		
		this.csvLine = "";
		this.csvFields = new String[6];
		this.timeOutVar = 0;
		this.docInfo = "";

		try {
			this.dateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2000-01-30 23:30:01");
			// error handling
		} catch (Exception initdate) {
			JOptionPane.showMessageDialog(null, "Unable to generate date\n Code:#1\nMessage:\n");
			initdate.printStackTrace();
			SessionizationMain.errorMessage += initdate.getMessage();
			return;
		}
	}

	// getters
	protected String getIp() {
		return this.ip;
	}

	protected Date getDateTime() {
		return this.dateTime;
	}

	protected long getTimeOutVar() {
		return this.timeOutVar;
	}

	// get timeout value from file
	protected void getSessionTimeOut() {

		try {
			// initiate the reader and open file
			this.br = new BufferedReader(new FileReader("./input/inactivity_period.txt"));
			// get the value
			this.timeOutVar = Long.parseLong(br.readLine());

			// check if value is within acceptable range
			if (this.timeOutVar < 1 || this.timeOutVar > 86400) {
				JOptionPane.showMessageDialog(null, "Error: time out value must be a number between 1 and 86400 (24h)");
				System.exit(0);
			}
			// close reader
			br.close();

			// exception handling
		} catch (Exception gettimeoutvar) {
			JOptionPane.showMessageDialog(null,
					"Unable to load file\n Code:#2\nMessage:\n");
			gettimeoutvar.printStackTrace();
			SessionizationMain.errorMessage += gettimeoutvar.getMessage();
			return;
		}

	}

	// load file to be read
	protected void initializeReader() {
		try {
			this.br = new BufferedReader(new FileReader(this.filePath));

			// exception handling
		} catch (Exception loadfile) {
			JOptionPane.showMessageDialog(null, "Unable to load file\n Code:#3\nMessage:\n");
			loadfile.printStackTrace();
			SessionizationMain.errorMessage += loadfile.getMessage();
			return;
		}
	}

	// retrieve next log entry, returns false on end-of-file
	protected boolean readNextLine() {
		try {
			// skip the header
			if (this.header) {
				this.csvLine = br.readLine();
				this.header = false;
			}

			if ((this.csvLine = br.readLine()) != null) {	
				// we need to split the fields based on a delimiter(',') and we only need the first 6 fields
				this.csvFields = csvLine.split(",", 6);				
				// save the data according to its meaning
				this.ip = csvFields[0];
				String date = csvFields[1]+" "+csvFields[2];
				
				this.dateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(date);
				this.docInfo = csvFields[3] + csvFields[4] + csvFields[5];										
				return true;
			} else {
				System.out.println("e-o-f");
				// at end-of-file
				this.br.close();
				return false;
			}
			// exception handling
		} catch (Exception linereader) {
			System.out.println("Unable to retrieve log entry\n Code:#4\nMessage:\n");
			linereader.printStackTrace();
			SessionizationMain.errorMessage += linereader.getStackTrace();			
			return false;
		}		
	}
}
