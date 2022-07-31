package ru.devstend.getqr.service;

import com.google.zxing.common.BitMatrix;

public interface BitMatrixService {

  /**
   * Generate bit matrix from payload
   *
   * @param payload payload for generate qr
   * @param width width of QR
   * @param height height of QR
   * @return encoded bit matrix
   */
  BitMatrix createBitMatrix(String payload, int width, int height);

  /**
   * Convert bit matrix to SVG string
   *
   * @param bitMatrix QR bit matrix
   * @return SVG string
   */
  String convertBitMatrixToSvgString(BitMatrix bitMatrix);

}
