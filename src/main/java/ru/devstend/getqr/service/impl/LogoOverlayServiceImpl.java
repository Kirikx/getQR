package ru.devstend.getqr.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import ru.devstend.getqr.service.LogoOverlayService;

@Slf4j(topic = "LogoOverlayService")
@Service
public class LogoOverlayServiceImpl implements LogoOverlayService {

  @Override
  public String stringBuilderOverlay(String svgQr, String svgLogo, String anchor) {

    int lastIndex = svgQr.lastIndexOf(anchor); // "</svg>"

    return svgQr.substring(0, lastIndex)
        + svgLogo
        + svgQr.substring(lastIndex);
  }

  public Document documentOverlay(
      Document qrDoc,
      Document logoDoc,
      int logoPositionX,
      int logoPositionY
  ) {

    logoDoc.getDocumentElement().setAttribute("x", String.valueOf(logoPositionX));
    logoDoc.getDocumentElement().setAttribute("y", String.valueOf(logoPositionY));

    Node logoNode = qrDoc.importNode(logoDoc.getDocumentElement(), true);
    qrDoc.getDocumentElement().appendChild(logoNode);

    return qrDoc;
  }
}
