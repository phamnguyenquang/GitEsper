package NmapBeta;

import java.util.Map;

import Development.LogEventDev;
import Development.LogMessageExtractor;

public class SynScanClosedPort {
	private String message = "";
	private double Time = 0.0;
	private LogMessageExtractor event;
	private String srcIP="";
	private String destIP="";
	private String srcPt="";
	private String dstPt="";
	private String flag="";
	private String Proto="";
	
	public SynScanClosedPort(LogEventDev ee)
	{
		message = ee.getMessage();
		srcIP =ee.getSrcIP();
		destIP=ee.getDestIP();
		srcPt=ee.getSrcPt();
		dstPt=ee.getDstPt();
		flag=ee.getFlag();
		Proto=ee.getProto();
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
