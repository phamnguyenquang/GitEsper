package Test;

import java.util.ArrayList;

import com.espertech.esper.client.EPAdministrator;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EPStatement;
import com.espertech.esper.client.scopetest.SupportSubscriber;

import Development.LogEventDev;
import Development.jsonIO;
import Development.logReaderDev;
import NmapBeta.SynScanEventSubcriber;
import NmapBeta.SynScanEventSubscriberWithACK;
import NmapBeta.UdpScanEventSubscriber;
import NmapBeta.AckScanEventSubscriber;
import NmapBeta.PingScan;
import NmapBeta.SAACKScan;
import NmapBeta.SSSynScan;
import NmapCaseDevelopment.NetFilterLogCaller;
import NmapCaseDevelopment.NetFilterLogParser;
import NmapCaseDevelopment.PortScanDetection;
import NmapCaseDevelopment.TestClass;
import legacy.EventA;
import legacy.EventB;

public class App {

	public static void main(String[] args) {

		/*
		 * Preserve the log by copying it into ArrayList<String> line by line everytime
		 * "sudo cat /var/log/*.log" is called, root session is logged first, then the
		 * command is executed
		 */
		NetFilterLogParser log = new NetFilterLogParser();
		int size = log.getLogSize();

		logReaderDev jlog = new logReaderDev("/home/quang/journal.log");
		int size1 = jlog.size();


		EPServiceProvider engine = EPServiceProviderManager.getDefaultProvider();
		EPAdministrator admin = engine.getEPAdministrator();
		
		SynScanEventSubcriber syn = new SynScanEventSubcriber();
		SynScanEventSubscriberWithACK synA= new SynScanEventSubscriberWithACK();
		AckScanEventSubscriber ack = new AckScanEventSubscriber();
		UdpScanEventSubscriber udp = new UdpScanEventSubscriber();
		
		String createSchemaSyn = "create schema syn_scan as(srcIP string, dstIP string, srcPt string, dstPt string, Proto string, flag string) ";
		String createSchemaClosedPort ="create schema closed_port as(srcIP string, dstIP string, srcPt string, dstPt string, Proto string, flag string) ";
		
		String insertSyn = "insert into syn_scan select srcIP "
				+ "from pattern [ "
				+ "              every EventA = LogEventDev(proto = 'TCP' and flag = ' SYN ')                "
				+ "                 -> EventB = LogEventDev((proto = 'TCP' and flag = ' ACK RST '              "
				+ "                                                       and srcPt = EventA.dstPt    		  "
				+ "                                                        and dstPt = EventA.srcPt     	   "
				+ "                                                         and srcIP = EventA.destIP            "
				+ "                                                          and destIP = EventA.srcIP) "
				+ "	)        " 
				+ "             ]";

		
		admin.getConfiguration().addEventType(LogEventDev.class);
		admin.getConfiguration().addEventType(SynScanEventSubcriber.class);
		admin.getConfiguration().addEventType(AckScanEventSubscriber.class);
		admin.getConfiguration().addEventType(UdpScanEventSubscriber.class);
		admin.getConfiguration().addEventType(SynScanEventSubscriberWithACK.class);


		EPStatement schemasyn = admin.createEPL(createSchemaSyn);
		EPStatement schemaclosed = admin.createEPL(createSchemaClosedPort);
		EPStatement insertsyn = admin.createEPL(insertSyn);
		EPStatement synStatement = admin.createEPL(syn.getStatement());
		EPStatement AckStatement = admin.createEPL(ack.getStatement());
		EPStatement UDPStatement = admin.createEPL(udp.getStatement());
		EPStatement synAStatement = admin.createEPL(synA.getStatement());

//		synStatement.setSubscriber(syn);
//		synAStatement.setSubscriber(synA);
//		AckStatement.setSubscriber(ack);
//		UDPStatement.setSubscriber(udp);
		
		
		insertsyn.addListener((newData, oldData) -> {
			String srcIP = (String) newData[0].get("srcIP");
			String dstP = (String) newData[0].get("destIP");
			String srcPt = (String) newData[0].get("srcPt");
			String dstPt = (String) newData[0].get("dstPt");
			String Proto = (String) newData[0].get("Proto");
			System.out.println("srcIP");
		});

		for (int i1 = 0; i1 < size1; ++i1) {
			engine.getEPRuntime().sendEvent(new LogEventDev(jlog, i1));
		}

	}
}
