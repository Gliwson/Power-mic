package pl.power.domain.xmlDomain;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.sql.Timestamp;

@Getter
@Setter
@ToString
public class Event {

    @JacksonXmlProperty(localName = "eventStop")
    private Timestamp eventStop;

    @JacksonXmlProperty(localName = "eventStatus")
    private String eventStatus;

    @JacksonXmlProperty(localName = "eventStart")
    private Timestamp eventStart;

    @JacksonXmlProperty(localName = "eventType")
    private String eventType;

}
