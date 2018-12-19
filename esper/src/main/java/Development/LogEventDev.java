package Development;

public class LogEventDev {
	private String message = "";
	private double Time = 0.0;

	public LogEventDev(logReaderDev reader, int i) {
		message = reader.MessageAt(i);
		Time = Double.parseDouble(reader.getTimeStampAt(i));
	}

	public String getMessage() {
		return message;
	}

	public double getTime() {
		return Time;
	}
}
