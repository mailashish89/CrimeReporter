package crimeReporter;

//Install the Java helper library from twilio.com/docs/java/install
import com.twilio.sdk.TwilioRestClient;
import com.twilio.sdk.TwilioRestException;
import com.twilio.sdk.resource.instance.Message;
import com.twilio.sdk.resource.list.MessageList;

public class GetPage {

	// Find your Account Sid and Token at twilio.com/user/account
	public static final String ACCOUNT_SID = "ACb0b7d6637c87327e8babe7fc97664476";
	public static final String AUTH_TOKEN = "239458a4aa4e9496b473a569f19e2015";

	public MessageList returnIt() throws TwilioRestException {
		 TwilioRestClient client = new TwilioRestClient(ACCOUNT_SID, AUTH_TOKEN);
		 
	     
	     
		    MessageList messages = client.getAccount().getMessages();
		
		    return messages;
	

		// Loop over messages and print out a property for each one.


	}
	
}