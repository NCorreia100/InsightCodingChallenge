/* This is an Insight challenges which pertains creating a pipeline for processing EDGAR weblogs, then creating a new document that identifies each visit, duration and no. of documents requested
 * 
 * Author: Nuno Correia (NCorreia1@my.devry.edu / 646-256-9326)
 *  
 * Date: 5/26/2018 - 5/29/2018
 *   
 * Description of class: Sessionization creates an object per user session (which is identified by an unique IP) in order to store and compute values by other classes
*/

import java.text.SimpleDateFormat;
import java.util.Date;

public class Sessionization {

	private String ip;                  // session ip
	private Date firstRequest;          // time of first request
	private Date lastRequest;           // time of last request
	private long duration;              // duration of session
	private int docsQty;                // amount of docs requested during session
	
	// constructor:
	public Sessionization(String ip, Date dateTime) {
		this.ip = ip;
		this.docsQty = 1;              // the session starts when user makes a doc request (or returns after session times out)
		this.duration = 1;             // the minimum value must be 1sec
		this.firstRequest = dateTime;
		this.lastRequest = dateTime;
	}

	// getters and setters:
	protected void setLastRequest(Date request) {
		this.lastRequest = request;
	}

	protected String getIp() {
		return this.ip;
	}

	protected int getDocsQty() {
		return this.docsQty;
	}

	protected Date getFirstDate() {
		return this.firstRequest;
	}

	protected Date getLastDate() {
		return this.lastRequest;
	}
	
	// increment quantity of documents requested
	protected void IncreaseDocsQty() {
		this.docsQty += 1;
	}

	// compute duration between first request and last request and convert to sec
	protected void computeDuration() {
				
		this.duration = (this.lastRequest.getTime() - this.firstRequest.getTime())/1000;		
		if(this.duration<1) {
			this.duration=1;    //minimum is 1
		}
	}

	// get all session data into a string
	protected String getAllSessionData() {

		String data = this.ip + ",";
		//this extra step is a workaround for the simpleDateFormat bug not formating as requested
		data+= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(this.firstRequest) + ",";
		data+= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(this.lastRequest) + ",";
		data+= String.valueOf(this.duration) + "," + String.valueOf(this.docsQty);
		return data;	
	}
}
