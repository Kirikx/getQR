package ru.devstend.getqr.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.devstend.getqr.dto.LogoDto;
import ru.devstend.getqr.dto.QrContextDto;
import ru.devstend.getqr.enums.QrCreationMethodEnum;
import ru.devstend.getqr.exception.ApplicationException;
import ru.devstend.getqr.service.LogoProvider;
import ru.devstend.getqr.service.QrService;
import ru.devstend.getqr.service.creators.QrCreator;

@Slf4j(topic = "QrService")
@Service
public class QrServiceImpl implements QrService {

  private final LogoProvider defaultLogoProvider;
  private final Map<QrCreationMethodEnum, QrCreator> qrCreators;

  public QrServiceImpl(
      LogoProvider defaultLogoProvider,
      List<QrCreator> creators
  ) {
    this.defaultLogoProvider = defaultLogoProvider;
    this.qrCreators = new HashMap<>();
    creators.forEach(value -> qrCreators.put(value.getName(), value));
  }

  @Override
  public String getQr(String payload, QrCreationMethodEnum method, String logoName) {

    QrContextDto qrContextDto = buildQrContext(payload, logoName);

    return Optional.ofNullable(qrCreators.get(method))
        .map(qrCreator -> qrCreator.createQr(qrContextDto))
        .orElseThrow(() -> new ApplicationException("Creating method not found"));
  }

  @Override
  public String getQrWithDefaultLogo(String payload, QrCreationMethodEnum method) {

    return this.getQr(payload, method, null);
  }

  private QrContextDto buildQrContext(String payload, String logoName) {
    LogoDto logo = resolveLogo(logoName);

    return QrContextDto.builder()
        .payload(payload)
        .logo(logo)
        .build();
  }

  private LogoDto resolveLogo(String logoName) {
    return Optional.ofNullable(logoName)
        .map(defaultLogoProvider::getLogoByName)
        .orElse(defaultLogoProvider.getDefaultLogo());
  }

}
