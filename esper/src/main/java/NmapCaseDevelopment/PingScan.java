package NmapCaseDevelopment;

import java.util.Map;

import Development.LogEventDev;

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

public class PingScan {
	private int state = 0;

	public String getStatement() {
		String log2 = "select * from NetFilterLogCaller match_recognize("
				+ "measures A as NetFilterLogCaller1, B as NetFilterLogCaller2 pattern (A B) define A as A.message.contains('PROTO=TCP SPT=80') and A.message.contains('ACK RST'), B as B.message.contains('PROTO=TCP SPT=443')? or B.message.contains('ACK RST')?)";
		return log2;
	}

	public void update(Map<String, NetFilterLogCaller> Eventmap) {
		NetFilterLogCaller LogEventDev1 = (NetFilterLogCaller) Eventmap.get("NetFilterLogCaller1");
		NetFilterLogCaller LogEventDev2 = (NetFilterLogCaller) Eventmap.get("NetFilterLogCaller2");
		StringBuffer sb = new StringBuffer();
		sb.append("scan detected (-PA -PS)");
		state = 1;
		System.out.println(sb.toString());
	}

	public int getState() {
		return state;
	}
}
