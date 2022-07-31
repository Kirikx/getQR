package ru.devstend.getqr.service.impl;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.devstend.getqr.converter.BitMatrixToSvgStringConverter;
import ru.devstend.getqr.exception.ApplicationException;
import ru.devstend.getqr.service.BitMatrixService;

@Slf4j(topic = "BitMatrixService")
@Service
@RequiredArgsConstructor
public class BitMatrixServiceImpl implements BitMatrixService {

  private final BitMatrixToSvgStringConverter converter;

  @Override
  public BitMatrix createBitMatrix(String payload, int width, int height) {
    Map<EncodeHintType, Object> encodeHints = new HashMap<>();
    encodeHints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
    encodeHints.put(EncodeHintType.MARGIN, 0);
    encodeHints.put(EncodeHintType.CHARACTER_SET, Charset.defaultCharset());

    BitMatrix encode;
    try {
      encode = new QRCodeWriter().encode(
          payload,
          BarcodeFormat.QR_CODE, width, height,
          encodeHints
      );
    } catch (Exception ex) {
      log.error("Error creating bit matrix", ex);
      throw new ApplicationException("Error creating bit matrix");
    }
    return encode;
  }

  @Override
  public String convertBitMatrixToSvgString(BitMatrix bitMatrix) {

    return converter.convert(bitMatrix);
  }
}
