package Development;

public class LogMessageExtractor {
	private String message;
	private String SrcIp;
	private String DesIp;
	private String ScrPt;
	private String DstPt;
	private String Proto;
	private String Flag;

	public LogMessageExtractor(String mESSAGE, String srcIp, String desIp, String scrPt, String dstPt, String proto,
			String flag) {
		message = mESSAGE;
		SrcIp = srcIp;
		DesIp = desIp;
		ScrPt = scrPt;
		DstPt = dstPt;
		Proto = proto;
		Flag = flag;
	}

	public String getSrcIp() {
		return SrcIp;
	}

	public void setSrcIp(String srcIp) {
		SrcIp = srcIp;
	}

	public String getDesIp() {
		return DesIp;
	}

	public void setDesIp(String desIp) {
		DesIp = desIp;
	}

	public String getScrPt() {
		return ScrPt;
	}

	public void setScrPt(String scrPt) {
		ScrPt = scrPt;
	}

	public String getDstPt() {
		return DstPt;
	}

	public void setDstPt(String dstPt) {
		DstPt = dstPt;
	}

	public String getProto() {
		return Proto;
	}

	public void setProto(String proto) {
		Proto = proto;
	}

	public String getFlag() {
		return Flag;
	}

	public void setFlag(String flag) {
		Flag = flag;
	}

	public String getMessage() {
		return message;
	}

}
