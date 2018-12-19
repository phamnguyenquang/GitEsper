package LogReader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import Background.CommandExecutor;

public class LogReader {
	private String logPath;
	private String copiedLogPath;
	private File log;
	private BufferedReader br;
	private CommandExecutor linuxCommandExecutor = new CommandExecutor();
	private ArrayList<String>logFileContent = new ArrayList<String>();
	private String lastLine = "";

	public LogReader(String filePath) {
		try {
			this.logPath = filePath;
			setUpLog();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void setUpLog() {
		try {
			linuxCommandExecutor.startCommand("whoami");
			String user = linuxCommandExecutor.getResult();
			String alternateFile = "/home/" + user + "/auth.log";
			linuxCommandExecutor = new CommandExecutor();
			linuxCommandExecutor.startCommand("sudo cp " + logPath + " " + alternateFile);
			linuxCommandExecutor.startCommand("sudo chmod 777 " + alternateFile);
			this.copiedLogPath = alternateFile;
			log = new File(copiedLogPath);
			br = new BufferedReader(new FileReader(log));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void getLatestLogLine() {
		linuxCommandExecutor.startCommand("sudo cat " + logPath);
		lastLine = linuxCommandExecutor.getResult();
	}

	private void refreshLog() {
		try {
			logFileContent.clear();
			String st;
			while ((st = br.readLine()) != null) {
//				System.out.println(st);
				lastLine = st;
				logFileContent.add(lastLine);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String getLastLogLine() {
		refreshLog();
//		getLatestLogLine();
		return lastLine;
	}
	public ArrayList<String>getContent()
	{
		refreshLog();
		return logFileContent;
	}
}
