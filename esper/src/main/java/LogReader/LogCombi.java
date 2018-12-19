package LogReader;

import java.util.ArrayList;

public class LogCombi {
	private ArrayList<String> auth = (new LogEvent("/var/log/auth.log")).getContent();
	private ArrayList<String> sys = (new LogEvent("/var/log/syslog")).getContent();

	public LogCombi() {
		System.out.println("log combi initiated");
	}

	public String authLine(int i) {
		return auth.get(i);
	}

	public String sysLine(int i) {
		return sys.get(i);
	}
	public int authLength()
	{
		return auth.size();
	}
	public int sysLength()
	{
		return sys.size();
	}
}
