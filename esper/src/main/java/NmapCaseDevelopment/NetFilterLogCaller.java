package NmapCaseDevelopment;

public class NetFilterLogCaller {
	private NetFilterLogParser log;
	private String message;

	public NetFilterLogCaller(NetFilterLogParser logParser, int i) {
		log = logParser;
		message = log.getLogAt(i);
	}

	public String getMessage() {
		return message;
	}

}
