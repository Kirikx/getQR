package ru.devstend.getqr.converter;

import java.io.StringWriter;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import ru.devstend.getqr.exception.ApplicationException;

@Slf4j(topic = "DocumentToStringConverter")
@Component
public class DocumentToStringConverter implements Converter<Document, String> {

  @Override
  public String convert(Document document) {

    TransformerFactory tf = TransformerFactory.newInstance();
    Transformer transformer;
    StringWriter writer = new StringWriter();
    try {
      transformer = tf.newTransformer();
      transformer.transform(new DOMSource(document), new StreamResult(writer));
    } catch (TransformerException e) {
      log.error("Error convert document to string");
      throw new ApplicationException("Error convert document to string");
    }

    return writer.toString();
  }
}
