package entity.assets;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "ulaz")
public class LogEntry {

    private String poruka;

    public LogEntry() {}

    public LogEntry(String poruka) {
        this.poruka = poruka;
    }

    @XmlElement(name = "element")
    public String getAction() {
        return poruka;
    }

    public void setAction(String poruka) {
        this.poruka = poruka;
    }
}
