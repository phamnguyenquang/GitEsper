package Development;

public class LogEventDev {
	private String message = "";
	private double Time = 0.0;
	private LogMessageExtractor event;
	private String srcIP="";
	private String destIP="";
	private String srcPt="";
	private String dstPt="";
	private String flag="";
	private String Proto="";
	

	public LogEventDev(logReaderDev reader, int i) {
		
		Time = Double.parseDouble(reader.getTimeStampAt(i));
		event = reader.getEventAt(i);
		message = event.getMessage();
		srcIP=event.getSrcIp();
		destIP=event.getDesIp();
		srcPt=event.getScrPt();
		dstPt = event.getDstPt();
		flag = event.getFlag();
		Proto=event.getProto();
	}

	public String getMessage() {
		return message;
	}

	public double getTime() {
		return Time;
	}
	public String getSrcIP() {
		return srcIP;
	}
	public String getDestIP() {
		return destIP;
	}
	public String getSrcPt() {
		return srcPt;
	}
	public String getDstPt() {
		return dstPt;
	}
	public String getFlag() {
		return flag;
	}
	public String getProto() {
		return Proto;
	}
	
}
