package legacy;
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
//public class Application extends Thread {
//
//	public static void main(String[] args) {
////			LogReader log = new LogReader("/var/log/auth.log");
////			log.refreshLog();
//
//		// TODO Auto-generated method stub
//		Thread t = new Thread();
//		System.out.println("test");
//		String eplC = "select count(*), sum(property) from EventC";
//		String eplD = "select count(*), sum(property) from EventD";
//		String schemaCreateC = "create schema C as "+EventC.class.getName();
//		String schemaCreateD = "create schema D as "+EventD.class.getName();
//		String schemaInsertFromA = "Insert into C select property from EventA where property < 60";
//		String schemaInsertFromB = "Insert into C select property from EventB where property > 70";
//		String schemaInsertFromC = "Insert into D select property from EventC where property > 30";
//		
//		EPServiceProvider engine = EPServiceProviderManager.getDefaultProvider();
//		engine.getEPAdministrator().getConfiguration().addEventType(EventA.class);
//		engine.getEPAdministrator().getConfiguration().addEventType(EventB.class);
//		engine.getEPAdministrator().getConfiguration().addEventType(EventC.class);
//		engine.getEPAdministrator().getConfiguration().addEventType(EventD.class);
//
//		EPStatement SchemastatementC = engine.getEPAdministrator().createEPL(schemaCreateC);
//		EPStatement SchemastatementD = engine.getEPAdministrator().createEPL(schemaCreateD);
//		EPStatement schemaInsertStatement1 = engine.getEPAdministrator().createEPL(schemaInsertFromA);
//		EPStatement schemaInsertStatement2 = engine.getEPAdministrator().createEPL(schemaInsertFromB);
//		EPStatement schemaInsertStatement3 = engine.getEPAdministrator().createEPL(schemaInsertFromC);
//		EPStatement statementC = engine.getEPAdministrator().createEPL(eplC);
//		EPStatement statementD = engine.getEPAdministrator().createEPL(eplD);
//
//		schemaInsertStatement1.addListener((newData, oldData) -> {
//			int property = (int) newData[0].get("sum(property)");
////			int count = (int)newData[0].get("count(*)");
//			System.out.println("Event A " + property);
//		});
//		schemaInsertStatement2.addListener((newData, oldData) -> {
//			int property = (int) newData[0].get("sum(property)");
////			int count = (int)newData[0].get("count(*)");
//			System.out.println("Event B " + property+ " count: ");
//		});
//		schemaInsertStatement3.addListener((newData, oldData) -> {
//			int property = (int) newData[0].get("sum(property)");
////			int count = (int)newData[0].get("count(*)");
//			System.out.println("Event C " + property+ " count: ");
//		});
//		statementC.addListener((newData, oldData) -> {
//			int property = (int) newData[0].get("sum(property)");
////			int count = (int)newData[0].get("count(*)");
//			System.out.println("Event C " + property+ " count: ");
//		});
//		statementD.addListener((newData, oldData) -> {
//			int property = (int) newData[0].get("sum(property)");
////			int count = (int)newData[0].get("count(*)");
//			System.out.println("Event D " + property+ " count: ");
//		});
//		for (int i = 0; i < 1000; ++i) {
//			engine.getEPRuntime().sendEvent(new EventB(100));
//			try {
//				Thread.sleep(1000);
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
////			engine.getEPRuntime().sendEvent(new EventB(100));
////			engine.getEPRuntime().sendEvent(new EventC(100));
//		}
//	}
//
//}
