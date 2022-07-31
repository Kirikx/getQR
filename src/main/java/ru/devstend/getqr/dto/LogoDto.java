package ru.devstend.getqr.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class LogoDto {

  int width;
  int height;
  String svgLogo;

}
