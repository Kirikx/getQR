package ru.devstend.getqr.service.creators.impl;

import com.google.zxing.common.BitMatrix;
import java.awt.Color;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jfree.graphics2d.svg.SVGGraphics2D;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch;
import ru.devstend.getqr.dto.LogoDto;
import ru.devstend.getqr.dto.QrContextDto;
import ru.devstend.getqr.enums.QrCreationMethodEnum;
import ru.devstend.getqr.service.BitMatrixService;
import ru.devstend.getqr.service.LogoOverlayService;
import ru.devstend.getqr.service.creators.QrCreator;

@Slf4j(topic = "SvgGraphicsQrCreator")
@Service
@RequiredArgsConstructor
public class SvgGraphicsQrCreatorImpl implements QrCreator {

  private final BitMatrixService bitMatrixService;
  private final LogoOverlayService logoOverlayService;

  @Override
  public QrCreationMethodEnum getName() {
    return QrCreationMethodEnum.SVG_GRAPHICS;
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

    stopWatch.start("convertBitMatrixToSvgGraphics2D");
    String result = convertBitMatrixToSvgGraphics2D(matrix);
    stopWatch.stop();

    LogoDto logo = context.getLogo();
    if (Objects.nonNull(logo)) {
      stopWatch.start("overlayQrSb");
      result = logoOverlayService.stringBuilderOverlay(
          result,
          logo.getSvgLogo(),
          "</svg>");
      stopWatch.stop();
    }

    printTimeSummary(stopWatch, result);
    saveQr(result);

    return result;
  }

  @Override
  public Logger getLogger() {
    return log;
  }

  private String convertBitMatrixToSvgGraphics2D(BitMatrix matrix) {
    int matrixWidth = matrix.getWidth();
    int matrixHeight = matrix.getHeight();
    SVGGraphics2D g2 = new SVGGraphics2D(matrixWidth, matrixHeight);
    g2.setColor(Color.BLACK);

    for (int i = 0; i < matrixWidth; i++) {
      for (int j = 0; j < matrixHeight; j++) {
        if (matrix.get(i, j)) {
          g2.fillRect(i, j, 1, 1);
        }
      }
    }
    return g2.getSVGDocument();
  }
}
