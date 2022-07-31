package ru.devstend.getqr.converter;

import com.google.zxing.common.BitArray;
import com.google.zxing.common.BitMatrix;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class BitMatrixToSvgStringConverter implements Converter<BitMatrix, String> {

  @Override
  public String convert(BitMatrix bitMatrix) {

    int width = bitMatrix.getWidth();
    int height = bitMatrix.getHeight();
//    int rowSize = bitMatrix.getRowSize();

    StringBuilder sb = new StringBuilder();
    sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
    sb.append("<svg xmlns=\"http://www.w3.org/2000/svg\" version=\"1.1\"")
        .append(" height=\"").append(height).append("px\"")
        .append(" width=\"").append(width).append("px\"")
//        .append(" viewBox=\"0 0 ").append(width).append(" ").append(height).append("\"")
        .append(" stroke=\"none\">\n");
    sb.append("<style type=\"text/css\">\n");
    sb.append(".black {fill:#000000;}\n");
    sb.append("</style>\n");
    sb.append("<path class=\"black\"  d=\"");

    BitArray row = new BitArray(width);
    for (int y = 0; y < height; ++y) {
      row = bitMatrix.getRow(y, row);
      for (int x = 0; x < width; ++x) {
        if (row.get(x)) {
          sb.append(" M").append(x).append(",").append(y).append("h1v1h-1z");
        }
      }
    }

    sb.append("\"/>\n");
    sb.append("</svg>\n");

    return sb.toString();
  }
}
