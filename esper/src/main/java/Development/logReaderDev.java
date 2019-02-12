package Development;

import java.util.ArrayList;

public class logReaderDev {
	private ArrayList<String> Messages = new ArrayList<String>();
	private ArrayList<String> timeStamp = new ArrayList<String>();
	private jsonIO logger;
	private ArrayList<LogMessageExtractor> singleEvent = new ArrayList<LogMessageExtractor>();
	private String message;

	private String SrcIp;
	private String DesIp;
	private String ScrPt;
	private String DstPt;
	private String Proto;
	private String Flag;

	public logReaderDev(String path) {
		logger = new jsonIO(path);
		Messages = logger.getMessageLog();
		timeStamp = logger.getTimeStampLog();
		logger.update();
		setupEvent();
		System.out.println("toital event: " + singleEvent.size());
//		System.out.println(logger.size());
	}

	private void setupEvent() {
		for (int i = 0; i < Messages.size(); ++i) {
			if (Messages.get(i).contains("NetFilter")) {
				message = Messages.get(i);
				SrcIp = message.substring(message.indexOf("SRC="), message.indexOf(" ", message.indexOf("SRC=")))
						.replace("SRC=", "");
				DesIp = message.substring(message.indexOf("DST="), message.indexOf(" ", message.indexOf("DST=")))
						.replace("DST=", "");
				Proto = message.substring(message.indexOf("PROTO="), message.indexOf(" ", message.indexOf("PROTO=")))
						.replace("PROTO=", "");
				if (Proto.equalsIgnoreCase("TCP")) {
					Flag = message.substring(message.indexOf(" ", message.indexOf("RES=")), message.indexOf("URGP="));
					ScrPt = message.substring(message.indexOf("SPT="), message.indexOf(" ", message.indexOf("SPT=")))
							.replace("SPT=", "");
					DstPt = message.substring(message.indexOf("DPT="), message.indexOf(" ", message.indexOf("DPT=")))
							.replace("DPT=", "");
				}
				singleEvent.add(new LogMessageExtractor(message, SrcIp, DesIp, ScrPt, DstPt, Proto, Flag));
			}
		}

	}

	public LogMessageExtractor getEventAt(int i) {
		return singleEvent.get(i);
	}

	public String MessageAt(int i) {
		return Messages.get(i);
	}

	public String getTimeStampAt(int i) {
		return timeStamp.get(i);
	}

	public int size() {
		return singleEvent.size();
	}

}
