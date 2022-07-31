package ru.devstend.getqr.service.creators;

import static java.nio.charset.StandardCharsets.UTF_8;

import java.io.ByteArrayInputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import org.slf4j.Logger;
import org.springframework.util.StopWatch;
import ru.devstend.getqr.dto.QrContextDto;
import ru.devstend.getqr.enums.QrCreationMethodEnum;

public interface QrCreator {

  /**
   * Creator name
   *
   * @return creator name
   */
  QrCreationMethodEnum getName();

  /**
   * Crete QR by payload with logo
   *
   * @param context QR context
   * @return svg string QR with logo
   */
  String createQr(QrContextDto context);

  /**
   * Implement class logger
   *
   * @return Logger
   */
  Logger getLogger();

  default void saveQr(String result){
    try {
      Files.copy(
          new ByteArrayInputStream(result.getBytes(UTF_8)),
          Paths.get("qrcode.svg"),
          StandardCopyOption.REPLACE_EXISTING);
    } catch (Exception ex) {
      // pass
    }
  }

  default void printTimeSummary(StopWatch stopWatch, String result) {
    Logger logger = getLogger();
    String report = stopWatch.prettyPrint();
    logger.info(getName().name());
    logger.info("Total time ms: {}", stopWatch.getTotalTimeMillis());
    logger.info("Total size kb: {}", result.getBytes().length / 1000);
    logger.info(report);
  }

}
