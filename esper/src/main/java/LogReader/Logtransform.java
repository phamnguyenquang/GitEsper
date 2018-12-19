package LogReader;

import java.util.ArrayList;

public class Logtransform {
	private String authLine = "";
	private String sysLine = "";

	public Logtransform(LogCombi log, int i) {
		authLine = log.authLine(i);
		sysLine = log.sysLine(i);
//		System.out.println("logTransform initiated");
	}

	public String getAuthLine() {
		return authLine;
	}

	public String getSysLine() {
		return sysLine;
	}

}
