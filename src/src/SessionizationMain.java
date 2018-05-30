/* This is an Insight challenges which pertains creating a pipeline for processing EDGAR weblogs, then creating a new document that identifies each visit, duration and no. of documents requested
 * 
 * Author: Nuno Correia (NCorreia1@my.devry.edu / 646-256-9326)
 *  
 *  Date: 5/26/2018 - 5/29/2018
 *   
 * Description of class: SessionizationMain starts the program, coordinates the execution of tasks and communicates the data between composite classes
*/

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import javax.swing.JOptionPane;

public class SessionizationMain {

	static String errorMessage;    // collects all error messages
	static long processingTime;    // records time spent by the program for optimization purposes

	// program initiation
	public static void main(String[] args){

		// instantiate objects
		errorMessage = "";
		processingTime = 0;
		List<Sessionization> sessionization = new ArrayList<Sessionization>(); // collection of objects that stores all records
		SessionizationInput input = new SessionizationInput();                 // object that processes input from files
		SessionizationOutput output = new SessionizationOutput();              // object that processes output to files and process feedback
		int sessionCounter = 0;                                                 //counts number of sessions
		
		// start timer
		try {
			processingTimer(true);
		//error handling	
		} catch (Exception timerStart) {
			JOptionPane.showMessageDialog(null, "Unable to start timer Code:#9\nMessage:\n");
			timerStart.printStackTrace();
			SessionizationMain.errorMessage += timerStart.getMessage();
		}

		// get inactivity period value
		input.getSessionTimeOut();
		// get reader and writer ready to read/write
		input.initializeReader();
		output.initializeWriter();
		// read first record before entering the loop
		input.readNextLine();
		sessionization.add(new Sessionization(input.getIp(), input.getDateTime()));			
		sessionCounter = 1;
		
		while (input.readNextLine()) {

			boolean newSession = true;         // identifies whether there's a new session
			
			// looping through the collection
			for (int i = 0; i < sessionization.size(); i++) {
				
				//check timed out sessions and output the session data
				if ((input.getDateTime().getTime() - sessionization.get(i).getLastDate().getTime()) / 1000 > input.getTimeOutVar()) {
					sessionization.get(i).computeDuration();
					output.appendRecord(sessionization.get(i).getAllSessionData());
					sessionization.remove(i);
					
				}
				// check if IP matches and increase quantity of doc requests and change time of last request
				if (sessionization.get(i).getIp().equals(input.getIp())) {
					sessionization.get(i).IncreaseDocsQty();
					sessionization.get(i).setLastRequest(input.getDateTime());
					newSession = false;				
				}
			}
			
			// if IP is not matched, register a new session
			if (newSession) {
				sessionization.add(new Sessionization(input.getIp(), input.getDateTime()));
				sessionCounter += 1;				
			}
		}

		// close sessions and append records at the end-of-file		
		for (int i = 0; i < sessionization.size(); i++) {

			sessionization.get(i).computeDuration();
			output.appendRecord(sessionization.get(i).getAllSessionData());
		}
		
		//close the writer
		output.closeWriter();
		// stop the timer
		try {
			processingTimer(false);
			System.out.println("timer stoped");
		} catch (Exception timerend) {
			JOptionPane.showMessageDialog(null, "Unable to start timer Code:#10\nMessage:\n");
			timerend.printStackTrace();
			SessionizationMain.errorMessage += timerend.getMessage();
		}

		// generate report
		output.setTestTitle();
		output.setSuccess(sessionCounter);
		output.setProcessDuration(processingTime);
		output.setReadWriteComparison(sessionCounter);
		output.setErrorMessage(errorMessage);
		output.writeFeedback();

		// close program
		System.exit(0);
	}

	
	// calculate the processing time, this method is called 2 times, at the start and at the end
	public static void processingTimer(boolean start) throws InterruptedException {

		if (start) {
			// get current time
			processingTime = System.currentTimeMillis();
			TimeUnit.SECONDS.sleep(0);
		} else {
			// get current time and subtract first time check
			long endTime = System.currentTimeMillis();			
			processingTime = 	endTime - processingTime;	
		}
	}

	
	// append errors to error message
	public static void addError(String error) {
		errorMessage += error + "\n+++++++++++++++++++++++++++++++++++++++++++++";
	}
}
