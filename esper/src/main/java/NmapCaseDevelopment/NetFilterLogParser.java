package NmapCaseDevelopment;

import java.util.ArrayList;

public class NetFilterLogParser {
	private NetFilterLog nLog;
	private ArrayList<String>logContent;
	
	public NetFilterLogParser()
	{
		nLog = new NetFilterLog();
		logContent = nLog.getContent();
	}
	
	public int getLogSize()
	{
		return logContent.size();
	}
	public String getLogAt(int index)
	{
		return logContent.get(index);
	}
}
