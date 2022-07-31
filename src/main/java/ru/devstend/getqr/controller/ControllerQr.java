package ru.devstend.getqr.controller;

import static java.nio.charset.StandardCharsets.UTF_8;

import com.google.common.net.HttpHeaders;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.devstend.getqr.enums.QrCreationMethodEnum;
import ru.devstend.getqr.exception.ApplicationException;
import ru.devstend.getqr.service.QrService;

@RestController
@RequiredArgsConstructor
public class ControllerQr {

  private final QrService qrService;

  @GetMapping(value = "/servlet/builder")
  public void getQrServlet(HttpServletResponse response, @RequestParam("payload") String payload) {

    String qr = qrService.getQrWithDefaultLogo(payload, QrCreationMethodEnum.KENGLXN_BUILDER);

    response.addHeader(HttpHeaders.CONTENT_TYPE, "image/svg+xml");
    try {
      response.getOutputStream().write(qr.getBytes(UTF_8));
    } catch (IOException e) {
      throw new ApplicationException("Error write response");
    }
  }

  @GetMapping(value = "/spring/sb", produces = "image/svg+xml")
  public ResponseEntity<?> getQrSpringQRCodeBuilder(@RequestParam("payload") String payload) {

    String qr = qrService.getQrWithDefaultLogo(payload, QrCreationMethodEnum.STRING_BUILDER);

    return ResponseEntity.ok(qr.getBytes(UTF_8));
  }

  @GetMapping(value = "/spring/doc", produces = "image/svg+xml")
  public ResponseEntity<?> getQrSpringDoc(@RequestParam("payload") String payload) {

    String qr = qrService.getQrWithDefaultLogo(payload, QrCreationMethodEnum.DOCUMENT);

    return ResponseEntity.ok(qr.getBytes(UTF_8));
  }

  @GetMapping(value = "/spring/graphics", produces = "image/svg+xml")
  public ResponseEntity<?> getQrSpring(@RequestParam("payload") String payload) {

    String qr = qrService.getQrWithDefaultLogo(payload, QrCreationMethodEnum.SVG_GRAPHICS);

    return ResponseEntity.ok(qr.getBytes(UTF_8));
  }

}
