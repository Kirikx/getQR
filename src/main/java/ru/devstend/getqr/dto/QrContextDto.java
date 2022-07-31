package ru.devstend.getqr.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class QrContextDto {

  @Builder.Default
  int width = 128;
  @Builder.Default
  int height = 128;
  String payload;
  LogoDto logo;

}
