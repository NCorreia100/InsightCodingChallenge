/* This is an Insight challenges which pertains creating a pipeline for processing EDGAR weblogs, then creating a new document that identifies each visit, duration and no. of documents requested
 * 
 * Author: Nuno Correia (NCorreia1@my.devry.edu / 646-256-9326)
 *  
 * Date: 5/26/2018 - 5/29/2018
 *   
 * Description of class: SessionizationOutput handles all output appending logs to the file and and generates reports
*/

import java.io.BufferedWriter;
import java.io.FileWriter;
import javax.swing.JOptionPane;

public class SessionizationOutput {

	private String filePath;
	private BufferedWriter bw;
	private int writeCounter;            // counts number of lines written
	private String[] processFeedback;    // contents to display in the feedback message [[Success y/n],[test index], [date and time],[no. of records retrieved and saved],[error message if any]]

	// constructor:
	public SessionizationOutput() {
		this.filePath = "./output/sessionization.txt";
		this.writeCounter = 0;
		this.processFeedback = new String[5];
	}

	// initialize writer
	protected void initializeWriter() {
		try {

			this.bw = new BufferedWriter(new FileWriter(this.filePath, true));

			// exception handling
		} catch (Exception createfile) {
			JOptionPane.showMessageDialog(null,
					"Unable to create file\n Code:#5\nMessage:\n");
			createfile.printStackTrace();
			SessionizationMain.errorMessage += createfile.getMessage();
			return;
		}
	}

	// append next record
	protected void appendRecord(String line) {

		try {
			bw.append(line);				
			this.writeCounter += 1;
			//move to next line
			bw.newLine();
			// exception handling
		} catch (Exception linewriter) {
			JOptionPane.showMessageDialog(null,
					"Unable to append record\n Code:#6\nMessage:\n");
			linewriter.printStackTrace();
			SessionizationMain.errorMessage += linewriter.getMessage();
			return;
		}

	}

	// feedback message setters
	protected void setSuccess(int readCounter) {
		if (readCounter == writeCounter) {
			this.processFeedback[0] = "[Pass]: ";
		} else {
			this.processFeedback[0] = "[Fail]: ";
		}
	}

	protected void setTestTitle() {
		this.processFeedback[1] = "Test_1";
	}

	protected void setProcessDuration(long processDuration) {
		this.processFeedback[2] = "[" + processDuration + " ms] ";
	}

	protected void setReadWriteComparison(int readCounter) {
		this.processFeedback[3] = String.valueOf(writeCounter) + " of " + String.valueOf(readCounter) + "sessions recorded";
	}

	protected void setErrorMessage(String error) {
		this.processFeedback[4] = error;
	}
	
	
	//close writer
	protected void closeWriter() {
		try {
			bw.close();
		}catch (Exception closewriter) {
			System.out.println("Unable to close writer\n Code:#7\nMessage:\n");
			closewriter.printStackTrace();
			SessionizationMain.errorMessage += closewriter.getStackTrace();
		}
	}

	// write results and error message on console
	protected void writeFeedback() {

		JOptionPane.showMessageDialog(null, this.processFeedback[0] + this.processFeedback[1] + "\n"
				+ this.processFeedback[2] + this.processFeedback[3] + "\n" + this.processFeedback[4]);

	}
}
