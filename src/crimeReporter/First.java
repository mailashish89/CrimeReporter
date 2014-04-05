package crimeReporter;

import java.awt.Desktop;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;

import org.json.simple.JSONValue;

import com.twilio.sdk.TwilioRestException;
import com.twilio.sdk.resource.instance.Message;
import com.twilio.sdk.resource.list.MessageList;

public class First {

	String location = "";
	String crime = "";
	ArrayList<String> locations;
	ArrayList<String> crimes;
	ArrayList<String> times;

	public void first() {
		// TODO Auto-generated method stub
		locations = new ArrayList<>();
		crimes = new ArrayList<>();
		times = new ArrayList<>();

		ArrayList<String> mr = new ArrayList<>();
		GetPage page = new GetPage();
		MessageList me = null;
		try {
			me = page.returnIt();
		} catch (TwilioRestException e) {
			System.out.print("error: " + e);
		}

		for (Message m : me) {
			if (m.getDirection().equals("inbound")) {
				mr.add(m.getBody());
				times.add(m.getDateSent().toString());
			}
		}

		for (String temp : mr) {
			seperate(temp);
		}

		String printer = toString();

		String loc = JSONValue.toJSONString(locations);
		String cri = JSONValue.toJSONString(crimes);
		String tim = JSONValue.toJSONString(times);

		save(loc, "locations");
		save(cri, "crimes");
		save(tim, "times");

		if (Desktop.isDesktopSupported()) {
			try {
				Desktop.getDesktop().browse(
						new URI("http://localhost:8000/map.html"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (URISyntaxException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	public void seperate(String temp) {
		try {
			int i = 0;
			while (true) {

				if (temp.charAt(i) == ',') {
					crime = temp.substring(0, i);
					location = temp.substring(i + 1);
					locations.add(location.trim());
					crimes.add(crime.trim());
					break;
				}
				i++;

				crimes.add(crime);
			}
		} catch (Exception e) {
			//
		}
	}

	public String toString() {
		String print = "[";

		for (String l : locations) {
			print += "\"" + l + "\"" + ",";
		}

		print += print.substring(0, print.length() - 1) + "]";
		return print;
	}

	public void save(String x, String name) {
		Writer writer = null;

		try {
			writer = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(name + ".json")));
			writer.write(x);
		} catch (IOException ex) {
			System.err.print("ERROR");
		} finally {
			try {
				writer.close();
			} catch (Exception ex) {
				System.err.print("ERROR");
			}
		}
	}

}