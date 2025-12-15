package entity.assets;


import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;

import java.io.File;

public class XmlLogger {

    private static final File FILE = new File("files/log.xml");

    public static void log(String action) {
        try {
            JAXBContext context = JAXBContext.newInstance(LogFile.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();

            LogFile logFile;

            if (!FILE.exists() || FILE.length() == 0) {
                logFile = new LogFile();
            } else {
                logFile = (LogFile) unmarshaller.unmarshal(FILE);
            }

            logFile.getEntries().add(new LogEntry(action));

            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.marshal(logFile, FILE);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void printLog() {
        try {
            JAXBContext context = JAXBContext.newInstance(LogFile.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();

            LogFile logFile = (LogFile) unmarshaller.unmarshal(FILE);

            System.out.println("log.xml:");
            for (LogEntry entry : logFile.getEntries()) {
                System.out.println(entry.getAction());
            }

        } catch (Exception e) {
            System.err.println("Greška kod čitanja log-a.");
        }
    }
}

