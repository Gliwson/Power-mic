package pl.power.repository;


import feign.codec.Decoder;
import feign.codec.Encoder;
import feign.jaxb.JAXBContextFactory;
import feign.soap.SOAPDecoder;
import feign.soap.SOAPEncoder;
import org.springframework.context.annotation.Bean;


public class MySoapClientConfiguration {

    private static final JAXBContextFactory jaxbFactory = new JAXBContextFactory.Builder()
            .withMarshallerJAXBEncoding("UTF-8")
//            .withMarshallerSchemaLocation("http://s0314/gettask tasks.xsd")
            .build();

    @Bean
    public Encoder feignEncoder() {
        return new SOAPEncoder(jaxbFactory);
    }

    @Bean
    public Decoder feignDecoder() {
        return new SOAPDecoder(jaxbFactory);
    }

}
