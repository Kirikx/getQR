package ru.devstend.getqr;

import static java.nio.charset.StandardCharsets.UTF_8;

import com.google.common.net.HttpHeaders;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UncheckedIOException;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ControllerQr {

  private final ServiceQr serviceQr;

  @GetMapping(value = "/servlet/builder")
  public void getQrServlet(HttpServletResponse response, @RequestParam("payload") String payload) throws Exception {

    String qr = serviceQr.generateQRCodeSvg(payload);

    response.addHeader(HttpHeaders.CONTENT_TYPE, "image/svg+xml");
    response.getOutputStream().write(qr.getBytes(UTF_8));
  }

  @GetMapping(value = "/spring/sb", produces = "image/svg+xml")
  public ResponseEntity<?> getQrSpringQRCodeBuilder(@RequestParam("payload") String payload) throws Exception {

    String qr = serviceQr.createQrSb(payload);

    return ResponseEntity.ok(qr.getBytes(UTF_8));
  }

  @GetMapping(value = "/spring/doc", produces = "image/svg+xml")
  public ResponseEntity<?> getQrSpringDoc(@RequestParam("payload") String payload) throws Exception {

    String qr = serviceQr.createQrDoc(payload);

    return ResponseEntity.ok(qr.getBytes(UTF_8));
  }

  @GetMapping(value = "/spring/graphics", produces = "image/svg+xml")
  public ResponseEntity<?> getQrSpring(@RequestParam("payload") String payload) throws Exception {

    String qr = serviceQr.createQrGraphics(payload);

    return ResponseEntity.ok(qr.getBytes(UTF_8));
  }

}
