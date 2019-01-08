package NmapCaseDevelopment;

import java.util.Map;

import Development.LogEventDev;

/*
 * -sn (aka -sP) option in Nmap.
 * TCP SYN SCAN
 *   try icmp echo reply (NOT LOGGED for some reason)
 *   if(no icmp) then try tcp syn ping (typicallt on port 80 and 443).
 *     port up: ACK received, RST send back
 * TCP ACK SCAN
 *   sending TCP ACK 
 *     RST is sent back from the machine (connection not valid)
 * UDP SCAN
 *   send UDP pack (typically port 31 338)
 *     host responding: ICMP port unreachable error (TYPE 3 CODE 3)
 *     else: some ICMP message.        
 */

public class sPsnScan {
	public String getStatement() {
		String log2 = "select * from LogEventDev match_recognize( "
				+ "measures A as LogEventDev1, B as LogEventDev2 pattern (A B) define A as A.message.contains('PROTO=TCP SPT=80') and A.message.contains('ACK RST'), B as B.message.contains('PROTO=TCP SPT=443') and B.message.contains('ACK RST'))";
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
