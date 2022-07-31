package ru.devstend.getqr.service.creators.impl;

import com.google.zxing.EncodeHintType;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.glxn.qrgen.javase.QRCode;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch;
import ru.devstend.getqr.dto.LogoDto;
import ru.devstend.getqr.dto.QrContextDto;
import ru.devstend.getqr.enums.QrCreationMethodEnum;
import ru.devstend.getqr.exception.ApplicationException;
import ru.devstend.getqr.service.LogoOverlayService;
import ru.devstend.getqr.service.creators.QrCreator;

@Slf4j(topic = "KenglxnBuilderQrCreator")
@Service
@RequiredArgsConstructor
public class KenglxnBuilderQrCreatorImpl implements QrCreator {

  private final LogoOverlayService logoOverlayService;

  @Override
  public QrCreationMethodEnum getName() {
    return QrCreationMethodEnum.KENGLXN_BUILDER;
  }

  @Override
  public String createQr(QrContextDto context) {
    StopWatch stopWatch = new StopWatch();

    stopWatch.start("QRCode");
    File qrCode = QRCode.from(context.getPayload())
        .withSize(128, 128)
        .withHint(EncodeHintType.MARGIN, 0)
        .withErrorCorrection(ErrorCorrectionLevel.H)
        .withCharset(Charset.defaultCharset().toString())
        .svg();
    stopWatch.stop();

    stopWatch.start("readFileToString");
    String result;
    try {
      result = FileUtils.readFileToString(qrCode);
    } catch (IOException e) {
      throw new ApplicationException(e.getMessage());
    }
    stopWatch.stop();

    LogoDto logo = context.getLogo();
    if (Objects.nonNull(logo)) {
      stopWatch.start("overlayQrSb");
      result = logoOverlayService.stringBuilderOverlay(
          result,
          logo.getSvgLogo(),
          "</svg\n>"
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
