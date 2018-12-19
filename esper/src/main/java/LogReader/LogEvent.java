package LogReader;

import java.util.ArrayList;

public class LogEvent {
	private String logLine = "";
	private LogReader logRead;
	private ArrayList<String> content = new ArrayList<String>();

	public LogEvent(String path) {
		logRead = new LogReader(path);
	}

	public String getLogLine() {
		logLine = logRead.getLastLogLine();
		return logLine;
	}

	public ArrayList<String> getContent() {
		content = logRead.getContent();
		return content;
	}

	public String getContent(int index) {
		logLine = content.get(index);
		return logLine;
	}
}
