package ru.devstend.getqr;

import static java.nio.charset.StandardCharsets.UTF_8;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitArray;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import java.awt.Color;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.Map;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import net.glxn.qrgen.javase.QRCode;
import org.apache.commons.io.FileUtils;
import org.jfree.graphics2d.svg.SVGGraphics2D;
import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

@Service
public class ServiceQr {

  public static int w = 128;
  public static int h = 128;

  public String generateQRCodeSvg(String payload) throws Exception {

    StopWatch stopWatch = new StopWatch();

    stopWatch.start("QRCode");
    File qrCode = QRCode.from(payload)
        .withSize(128, 128)
        .withHint(EncodeHintType.MARGIN, 0)
        .withErrorCorrection(ErrorCorrectionLevel.H)
        .withCharset(Charset.defaultCharset().toString())
        .svg();
    stopWatch.stop();

    stopWatch.start("readFileToString");
    String result = FileUtils.readFileToString(qrCode);
    stopWatch.stop();

    printTimeSummary(stopWatch, result);
    saveQr(result);

    return result;
  }

  public String createQrSb(String qrPayload, String logo, int logoW, int logoH) throws Exception {

    StopWatch stopWatch = new StopWatch();

    stopWatch.start("createQrBitMatrix");
    BitMatrix matrix = createQrBitMatrix(qrPayload, w, h);
    stopWatch.stop();

    stopWatch.start("bitMatrixToSvgString");
    String qrSvgString = bitMatrixToSvgString(matrix);
    stopWatch.stop();

    stopWatch.start("overlayQrSb");
    String result = overlayQrSb(qrSvgString, logo);
    stopWatch.stop();

    printTimeSummary(stopWatch, result);
    saveQr(result);

    return result;
  }

  public String createQrGraphics(String qrPayload, String logo, int logoW, int logoH)
      throws Exception {

    StopWatch stopWatch = new StopWatch();

    stopWatch.start("createQrBitMatrix");
    BitMatrix matrix = createQrBitMatrix(qrPayload, w, h);
    stopWatch.stop();

    stopWatch.start("convertBitMatrixToSvgGraphics2D");
    String qrSvgString = convertBitMatrixToSvgGraphics2D(matrix);
    stopWatch.stop();

    stopWatch.start("overlayQrSb");
    String result = overlayQrSb(qrSvgString, logo);
    stopWatch.stop();

    printTimeSummary(stopWatch, result);
    saveQr(result);

    return result;
  }

  public String createQrDoc(String qrPayload, String logo, int logoW, int logoH) throws Exception {

    StopWatch stopWatch = new StopWatch();

    stopWatch.start("createQrBitMatrix");
    BitMatrix matrix = createQrBitMatrix(qrPayload, w, h);
    stopWatch.stop();

    stopWatch.start("bitMatrixToSvgString");
    String qrSvgString = bitMatrixToSvgString(matrix);
    stopWatch.stop();

    stopWatch.start("stringToDocument");
    Document qrDoc = stringToDocument(qrSvgString);
    Document logoDoc = stringToDocument(logo);
    stopWatch.stop();

    // position element
    stopWatch.start("overlayQr");
    int positionX = (w - logoW) / 2;
    int positionY = (h - logoH) / 2;
    overlayQr(qrDoc, logoDoc, positionX, positionY);
    stopWatch.stop();

    stopWatch.start("documentToString");
    String result = documentToString(qrDoc);
    stopWatch.stop();

    printTimeSummary(stopWatch, result);
    saveQr(result);

    return result;
  }

  private BitMatrix createQrBitMatrix(String payload, int width, int height)
      throws WriterException {
    Map<EncodeHintType, Object> encodeHints = new HashMap<>();
    encodeHints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
    encodeHints.put(EncodeHintType.MARGIN, 0);
    encodeHints.put(EncodeHintType.CHARACTER_SET, Charset.defaultCharset());

    return new QRCodeWriter().encode(
        payload,
        BarcodeFormat.QR_CODE, width, height,
        encodeHints
    );
  }

  private static String bitMatrixToSvgString(BitMatrix bitMatrix) {
    int width = bitMatrix.getWidth();
    int height = bitMatrix.getHeight();
//    int rowSize = bitMatrix.getRowSize();

    StringBuilder sb = new StringBuilder();
    sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
    sb.append("<svg xmlns=\"http://www.w3.org/2000/svg\" version=\"1.1\"")
        .append(" height=\"").append(height).append("px\"")
        .append(" width=\"").append(width).append("px\"")
        .append(" viewBox=\"0 0 ")
        .append(width).append(" ").append(height).append("\" stroke=\"none\">\n");
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

  private String convertBitMatrixToSvgGraphics2D(BitMatrix matrix) {
    int matrixWidth = matrix.getWidth();
    int matrixHeight = matrix.getHeight();
    SVGGraphics2D g2 = new SVGGraphics2D(matrixWidth, matrixWidth);
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

  private Document stringToDocument(String stringDocument)
      throws SAXException, IOException, ParserConfigurationException {

    return DocumentBuilderFactory
        .newInstance()
        .newDocumentBuilder()
        .parse(
            new ByteArrayInputStream(stringDocument.getBytes(UTF_8))
        );
  }

  private String overlayQrSb(String qrSvgString, String logo) {

    int lastIndex = qrSvgString.lastIndexOf("</svg>");

    return qrSvgString.substring(0, lastIndex)
        + logo
        + qrSvgString.substring(lastIndex);
  }

  private void overlayQr(Document qrDoc, Document logoDoc, int logoX, int logoY) {
    logoDoc.getDocumentElement().setAttribute("x", String.valueOf(logoX));
    logoDoc.getDocumentElement().setAttribute("y", String.valueOf(logoY));

    Node logoNode = qrDoc.importNode(logoDoc.getDocumentElement(), true);
    qrDoc.getDocumentElement().appendChild(logoNode);
  }

  private String documentToString(Document qrDoc) throws TransformerException {
    TransformerFactory tf = TransformerFactory.newInstance();
    Transformer transformer = tf.newTransformer();
    StringWriter writer = new StringWriter();
    transformer.transform(new DOMSource(qrDoc), new StreamResult(writer));

    return writer.toString();
  }

  private void printTimeSummary(StopWatch stopWatch, String result) {
    System.out.println("Total time ms: " + stopWatch.getTotalTimeMillis());
    System.out.println("Total size kb: " + result.getBytes().length / 1000);
    System.out.println(stopWatch.prettyPrint());
  }

  private void saveQr(String result) throws IOException {
    Files.copy(
        new ByteArrayInputStream(result.getBytes(UTF_8)),
        Paths.get("qrcode.svg"),
        StandardCopyOption.REPLACE_EXISTING);
  }
}
