package ru.devstend.getqr.converter;

import static java.nio.charset.StandardCharsets.UTF_8;

import java.io.ByteArrayInputStream;
import javax.xml.parsers.DocumentBuilderFactory;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import ru.devstend.getqr.exception.ApplicationException;

@Slf4j(topic = "StringToDocumentConverter")
@Component
public class StringToDocumentConverter implements Converter<String, Document> {

  @Override
  public Document convert(@NotNull String stringDocument) {

    Document document;
    try {
      document = DocumentBuilderFactory
          .newInstance()
          .newDocumentBuilder()
          .parse(
              new ByteArrayInputStream(stringDocument.getBytes(UTF_8))
          );
    } catch (Exception ex) {
      log.error("Error convert string to document");
      throw new ApplicationException("Error convert string to document");
    }

    return document;
  }

}
