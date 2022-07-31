package ru.devstend.getqr.service;

import org.w3c.dom.Document;

public interface LogoOverlayService {

  String stringBuilderOverlay(String svgQr, String svgLogo, String anchor);

  Document documentOverlay(
      Document qrDoc,
      Document logoDoc,
      int logoPositionX,
      int logoPositionY
  );

}
