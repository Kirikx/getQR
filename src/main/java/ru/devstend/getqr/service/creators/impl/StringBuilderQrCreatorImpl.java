package ru.devstend.getqr.service.creators.impl;

import com.google.zxing.common.BitMatrix;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch;
import ru.devstend.getqr.dto.LogoDto;
import ru.devstend.getqr.dto.QrContextDto;
import ru.devstend.getqr.enums.QrCreationMethodEnum;
import ru.devstend.getqr.service.BitMatrixService;
import ru.devstend.getqr.service.LogoOverlayService;
import ru.devstend.getqr.service.creators.QrCreator;

@Slf4j(topic = "StringBuilderQrCreator")
@Service
@RequiredArgsConstructor
public class StringBuilderQrCreatorImpl implements QrCreator {

  private final BitMatrixService bitMatrixService;
  private final LogoOverlayService logoOverlayService;

  @Override
  public QrCreationMethodEnum getName() {
    return QrCreationMethodEnum.STRING_BUILDER;
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
      stopWatch.start("overlayQrSb");
      result = logoOverlayService.stringBuilderOverlay(
          result,
          logo.getSvgLogo(),
          "</svg>"
      );
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
}
