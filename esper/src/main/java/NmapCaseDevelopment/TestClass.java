package NmapCaseDevelopment;
/*
 * -sn (aka -sP) option in Nmap.
 * TCP SYN (-PS:ping -sS:scan)
 *   try icmp echo reply (NOT LOGGED for some reason)
 *   if(no icmp) then try tcp syn ping (typicallt on port 80 and 443).
 *     port up: ACK received, RST send back
 * TCP ACK SCAN (-PA:ping -sA:scan)
 *   sending TCP ACK 
 *     RST is sent back from the machine (connection not valid)
 * UDP SCAN (-PU:ping -sU:scan)
 *   send UDP pack (typically port 31 338)
 *     host responding: ICMP port unreachable error (TYPE 3 CODE 3)
 *     else: some ICMP message.      
 * SCTP INIT ping (-sY):
 *   SCTP: combine characteristic of TCP and UDP  
 *     SCTP INIT is sent
 *     if(SCTP ACK) then host online
 *     qif(ABORT) service is closed
 * ICMP ECHO ping(-PE):
 *   simple ICMP type 0
 *  IP PROTOCOL scan (-sO):
 *   scan for open protocol:
 *     ICMP type 3 code 2 ( protocol unreachable): closed
 *     Other ICMP unreachable errores (type 3, code: 1.3.9.10,13): filtered
 *     else: open/filtered.
 *         
 */

import java.util.Map;

import Development.LogEventDev;

public class TestClass {
	private int state = 0;
	private boolean icmp = false;
	private boolean tcp = false;
	private boolean udp = false;
	private double firstOccur = 0;
	private String pingLog = "select * from pattern [every (LogEventDev(message.contains('PROTO=ICMP TYPE=8 CODE=0')) -> LogEventDev(message.contains('PROTO=ICMP TYPE=0 CODE=0')))]";
	private String tcpLog = "select * from pattern [every (LogEventDev(message.contains('PROTO=TCP') and message.contains('ACK')) -> LogEventDev((message.contains('PROTO=TCP') and message.contains('RST'))))]";
	private String udpLog = "select * from pattern [every (LogEventDev(message.contains('PROTO=UDP')) -> LogEventDev((message.contains('PROTO=UDP'))))]";

	public String getPingStatement() {
		return pingLog;
	}

	public String getTCPStatement() {
		return tcpLog;
	}

	public String getUDPStatement() {
		return udpLog;

	}

	public void update(Map<String, LogEventDev> Eventmap) {
		LogEventDev LogEventDev1 = (LogEventDev) Eventmap.get("LogEventDev1");
		LogEventDev LogEventDev2 = (LogEventDev) Eventmap.get("LogEventDev2");
		state += 1;
		System.out.println(state);
		if (state >= 1) {
			StringBuffer sb = new StringBuffer();
			sb.append("scan detected (-PA -PS)");
			state = 1;
			System.out.println(sb.toString());
			icmp = true;
			firstOccur = LogEventDev1.getTime();
			System.out.println(firstOccur);
		}

	}

	public double getFristOccur() {
		return firstOccur;
	}

	public boolean check() {
		if (icmp && tcp && udp) {
			return true;
		} else {
			return false;
		}
	}

	public int getState() {
		return state;
	}
}
