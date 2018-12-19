package Development;

import java.util.ArrayList;

public class logReaderDev {
	private ArrayList<String> Messages = new ArrayList<String>();
	private ArrayList<String> timeStamp = new ArrayList<String>();
	private jsonIO logger;

	public logReaderDev(String path) {
		logger = new jsonIO(path);
		Messages = logger.getMessageLog();
		timeStamp = logger.getTimeStampLog();
		logger.update();
//		System.out.println(logger.size());
	}

	public String MessageAt(int i) {
		return Messages.get(i);
	}

	public String getTimeStampAt(int i) {
		return timeStamp.get(i);
	}

	public int size() {
		return Messages.size();
	}

}
