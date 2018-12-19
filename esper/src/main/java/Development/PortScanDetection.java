package Development;

import java.util.Map;

public class PortScanDetection {
	public String getStatement() {
		String log2 = "select * from LogEventDev match_recognize( "
				+ "measures A as LogEventDev1, B as LogEventDev2 pattern (A B) define A as A.message.contains('Unable to negotiate with') or A.message.contains('port'), B as B.message.contains('Connection closed by'))";
		return log2;
	}

	public void update(Map<String, LogEventDev> Eventmap) {
		LogEventDev LogEventDev1 = (LogEventDev) Eventmap.get("LogEventDev1");
		LogEventDev LogEventDev2 = (LogEventDev) Eventmap.get("LogEventDev2");
		StringBuffer sb = new StringBuffer();
		sb.append("possible port scan");
		System.out.println(sb.toString());
	}
}