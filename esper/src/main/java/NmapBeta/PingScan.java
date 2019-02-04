package NmapBeta;

import java.util.Map;

import Development.LogEventDev;

/*
 * -sn (aka -sP) option in Nmap.
 * TCP SYN (-PS:ping -sS:scan)
 *   try icmp echo reply
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

public class PingScan {
	private int count = 0;
	private boolean detected = false;
	private double firstOccur = 0;

	public String getStatement() {
		String log2 = "select * from LogEventDev match_recognize("
				+ "measures A as LogEventDev1, B as LogEventDev2 pattern (A B) define A as A.message.contains('PROTO=ICMP TYPE=8 CODE=0 ')?, B as B.message.contains('PROTO=ICMP TYPE=0 CODE=0')?)";
		return log2;
	}

	public void update(Map<String, LogEventDev> Eventmap) {
		LogEventDev LogEventDev1 = (LogEventDev) Eventmap.get("LogEventDev1");
		LogEventDev LogEventDev2 = (LogEventDev) Eventmap.get("LogEventDev2");
		detected = true;
		count += 1;
		if (count == 1) {
			firstOccur = LogEventDev1.getTime();
			System.out.println("P " + firstOccur);
		} else if (count > 1) {
			StringBuffer sb = new StringBuffer();
		}

	}

	public double getFristOccur() {
		return firstOccur;
	}

	public boolean check() {
		return detected;
	}

	public int getTotalOccur() {
		return count;
	}
}
