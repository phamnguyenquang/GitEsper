package NmapCaseDevelopment;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

import Background.CommandExecutor;

public class NetFilterLog {
	private String logPath;
	private String copiedLogPath;
	private File log;
	private BufferedReader reader;
	private CommandExecutor cmd = new CommandExecutor();
	private ArrayList<String> logFileContent = new ArrayList<String>();
	private String lastLine = "";

	public NetFilterLog() {
		try {
			cmd.startCommand("whoami");
			String currentUser = cmd.getResult();
			logPath = "/home/" + currentUser + "/networkFilter.log";
			cmd.startCommand("sudo journalctl -b | grep 192.168.20.129 > " + logPath);
			cmd.startCommand("sudo chmod 777 " + logPath);

			refreshLog();

		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
	}

	private void refreshLog() {
		try {
			logFileContent.clear();
			String st;
			log = new File(logPath);
			reader = new BufferedReader(new FileReader(log));
			while ((st = reader.readLine()) != null) {
				lastLine = st;
				logFileContent.add(lastLine);
			}
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
	}

	public String getLastLogLine() {
		refreshLog();
		cmd.startCommand("sudo cat " + logPath);
		lastLine = cmd.getResult();
		return lastLine;
	}

	public ArrayList<String> getContent() {
		refreshLog();
		return logFileContent;
	}
}
