package ru.devstend.getqr.service.creators.impl;

import com.google.zxing.common.BitMatrix;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch;
import org.w3c.dom.Document;
import ru.devstend.getqr.converter.DocumentToStringConverter;
import ru.devstend.getqr.converter.StringToDocumentConverter;
import ru.devstend.getqr.dto.LogoDto;
import ru.devstend.getqr.dto.QrContextDto;
import ru.devstend.getqr.enums.QrCreationMethodEnum;
import ru.devstend.getqr.service.BitMatrixService;
import ru.devstend.getqr.service.LogoOverlayService;
import ru.devstend.getqr.service.creators.QrCreator;

@Slf4j
@Service
@RequiredArgsConstructor
public class DocumentQrCreatorImpl implements QrCreator {

  private final BitMatrixService bitMatrixService;
  private final DocumentToStringConverter docToStrConverter;
  private final StringToDocumentConverter strToDocConverter;
  private final LogoOverlayService logoOverlayService;

  @Override
  public QrCreationMethodEnum getName() {
    return QrCreationMethodEnum.DOCUMENT;
  }

  @Override
  public String createQr(QrContextDto context) {
    StopWatch stopWatch = new StopWatch();

    stopWatch.start("createQrBitMatrix");
    BitMatrix matrix = bitMatrixService.createBitMatrix(
        context.getPayload(),
        context.getWidth(),
        context.getHeight()
    );
    stopWatch.stop();

    stopWatch.start("bitMatrixToSvgString");
    String result = bitMatrixService.convertBitMatrixToSvgString(matrix);
    stopWatch.stop();

    LogoDto logo = context.getLogo();
    if (Objects.nonNull(logo)) {
      stopWatch.start("stringToDocument");
      Document qrDoc = strToDocConverter.convert(result);
      Document logoDoc = strToDocConverter.convert(logo.getSvgLogo());
      stopWatch.stop();

      stopWatch.start("overlayQr");
      qrDoc = qrOverlay(context, qrDoc, logo, logoDoc);
      stopWatch.stop();

      stopWatch.start("documentToString");
      result = docToStrConverter.convert(qrDoc);
      stopWatch.stop();
    }

    printTimeSummary(stopWatch, result);
    saveQr(result);

    return result;
  }

  private Document qrOverlay(QrContextDto context, Document qrDoc, LogoDto logo, Document logoDoc) {
    // position element
    int positionX = (context.getWidth() - logo.getWidth()) / 2;
    int positionY = (context.getHeight() - logo.getHeight()) / 2;

    return logoOverlayService.documentOverlay(qrDoc, logoDoc, positionX, positionY);
  }

  @Override
  public Logger getLogger() {
    return log;
  }

}
