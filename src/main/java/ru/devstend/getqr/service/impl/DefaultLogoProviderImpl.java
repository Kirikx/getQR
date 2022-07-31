package ru.devstend.getqr.service.impl;

import static java.nio.charset.StandardCharsets.UTF_8;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UncheckedIOException;
import javax.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import ru.devstend.getqr.dto.LogoDto;
import ru.devstend.getqr.service.LogoProvider;

@Slf4j(topic = "DefaultLogoProvider")
@Service
public class DefaultLogoProviderImpl implements LogoProvider {

  // QR size settings
  private static final int DEFAULT_WIDTH = 48;
  private static final int DEFAULT_HEIGHT = 48;
  @Value("classpath:icons8-futurama-bender-48.svg")
  private Resource resource;
  private String defaultLogo;

  @PostConstruct
  public void init() {
    defaultLogo = asString(resource);
  }

  @Override
  public LogoDto getDefaultLogo() {
    return LogoDto.builder()
        .svgLogo(defaultLogo)
        .width(DEFAULT_WIDTH)
        .height(DEFAULT_HEIGHT)
        .build();
  }

  @Override
  public LogoDto getLogoByName(String logoName) {
    return null;
  }

  private String asString(Resource resource) {
    try (Reader reader = new InputStreamReader(resource.getInputStream(), UTF_8)) {
      return FileCopyUtils.copyToString(reader);
    } catch (IOException e) {
      throw new UncheckedIOException(e);
    }
  }
}
