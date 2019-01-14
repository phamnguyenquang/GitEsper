package legacy;

import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EPStatement;

//package cep;
//
//import java.io.FileNotFoundException;
//
//import com.espertech.esper.client.EPServiceProvider;
//import com.espertech.esper.client.EPServiceProviderManager;
//import com.espertech.esper.client.EPStatement;
//
//import Event_Study.EventA;
//import Event_Study.EventB;
//import Event_Study.EventC;
//import Event_Study.EventD;
//import LogReader.LogReader;
//
public class Application extends Thread {
//
	public static void main(String[] args) {
//			LogReader log = new LogReader("/var/log/auth.log");
//			log.refreshLog();

		// TODO Auto-generated method stub
		Thread t = new Thread();
		System.out.println("test");
		String schemaCreateC = "create schema C as (property int)";
		String schemaCreateD = "create schema D as ()";
		String schemaInsertFromA = "Insert into C select property from EventA where property > 60";
		String schemaInsertFromB = "Insert into C select property from EventB where property < 70";
		String schemaInsertFromC = "select property from C where C.property > 30";
		String sequentialInsert = "Insert into C select EventA('property') from pattern [every ( (EventA(property > 60)) -> (EventB(property < 70)) )]";

		EPServiceProvider engine = EPServiceProviderManager.getDefaultProvider();
		engine.getEPAdministrator().getConfiguration().addEventType(EventA.class);
		engine.getEPAdministrator().getConfiguration().addEventType(EventB.class);

		EPStatement SchemastatementC = engine.getEPAdministrator().createEPL(schemaCreateC);
		EPStatement SchemastatementD = engine.getEPAdministrator().createEPL(schemaCreateD);
		EPStatement schemaInsertStatement1 = engine.getEPAdministrator().createEPL(schemaInsertFromA);
		EPStatement schemaInsertStatement2 = engine.getEPAdministrator().createEPL(schemaInsertFromB);
		EPStatement schemaInsertStatement3 = engine.getEPAdministrator().createEPL(schemaInsertFromC);
		EPStatement schemaInsertStatement4 = engine.getEPAdministrator().createEPL(sequentialInsert);
		
//		schemaInsertStatement1.addListener((newData, oldData) -> {
//			int property = (int) newData[0].get("property");
////			int count = (int)newData[0].get("count(*)");
//			System.out.println("Event A " + property);
//		});
//		schemaInsertStatement2.addListener((newData, oldData) -> {
//			int property = (int) newData[0].get("property");
////			int count = (int)newData[0].get("count(*)");
//			System.out.println("Event B " + property);
//		});
//			int property = (int) newData[0].get("property");
//			System.out.println("Event C " + property);
//		});
		schemaInsertStatement4.addListener((newData, oldData) -> {
			int property = (int) newData[0].get("property");
			System.out.println("Event C " + property);
		});


		for (int i = 0; i < 1000; ++i) {
			engine.getEPRuntime().sendEvent(new EventA(100));
			engine.getEPRuntime().sendEvent(new EventB(50));
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
//			engine.getEPRuntime().sendEvent(new EventB(100));
		}
	}

}
