package Development;

import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonStreamParser;

import Background.CommandExecutor;

public class jsonIO {
	private String path;
	private ArrayList<String> Messages = new ArrayList<String>();
	private ArrayList<String> timeStamp = new ArrayList<String>();
	private String tempString = "";
	private String tempInt = "";
	private CommandExecutor cmd = new CommandExecutor();

	public jsonIO(String pathFile) {
		path = pathFile;
		System.out.println("json reader initiated " + path);
	}

	private void getContent() {
		try {
			cmd.startCommand("rm " + path);
//			cmd.startCommand("sudo journalctl -b -o json-pretty > " + path);
			cmd.startCommand("sudo journalctl --since '10 minutes ago' -o json-pretty > " + path);
			cmd.startCommand("sudo chmod 777 " + path);
			InputStream jis = new FileInputStream(path);
			Reader r = new InputStreamReader(jis, "UTF-8");
			Gson gson = new Gson();
			JsonStreamParser p = new JsonStreamParser(r);
			Messages.clear();
			while (p.hasNext()) {
				JsonElement e = p.next();
				if (e.isJsonObject()) {
					Map m = gson.fromJson(e, Map.class);
					String mm = (String) m.get("MESSAGE");
					tempString = mm;
					Messages.add(tempString);
					tempInt = (String) m.get("__MONOTONIC_TIMESTAMP");
					timeStamp.add(tempInt);
				}
			}
			jis.close();
		} catch (

		Exception e) {
			e.printStackTrace();
		}
	}

	public ArrayList<String> getMessageLog() {
		getContent();

		return Messages;
	}

	public ArrayList<String> getTimeStampLog() {
		return timeStamp;
	}

	public void printMessage() {
		for (int i = 0; i < Messages.size(); ++i) {
			System.out.println("IO " + Messages.get(i));
		}
	}

	public int size() {
		return Messages.size();
	}

	public void update() {
		getContent();
//		printMessage();
	}

}
