package Development;

import java.util.Map;

public class AbnormalStuffDev {
	public String getStatement() {
		String log2 = "select * from LogEventDev match_recognize( "
				+ "measures B as LogEventDev1 pattern ( B) define B as B.message.contains('/etc/passwd'))";
		return log2;
	}

	public void update(Map<String, LogEventDev> Eventmap) {
		LogEventDev LogEventDev1 = (LogEventDev) Eventmap.get("LogEventDev1");
		StringBuffer sb = new StringBuffer();
		sb.append("password file was tempted");
		System.out.println(sb.toString());
	}
}
