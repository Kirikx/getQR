package ru.devstend.getqr.service;

import org.w3c.dom.Document;

public interface LogoOverlayService {

  /**
   * Logo overlay using SringBuilder
   *
   * @param svgQr svg string QR
   * @param svgLogo svg string logo
   * @param anchor anchor position logo
   * @return QR with overlay logo svg string
   */
  String stringBuilderOverlay(String svgQr, String svgLogo, String anchor);

  /**
   * Logo overlay using Document
   *
   * @param qrDoc qr document
   * @param logoDoc logo document
   * @param logoPositionX logo position X
   * @param logoPositionY logo position Y
   * @return QR with overlay logo document
   */
  Document documentOverlay(
      Document qrDoc,
      Document logoDoc,
      int logoPositionX,
      int logoPositionY
  );

}
