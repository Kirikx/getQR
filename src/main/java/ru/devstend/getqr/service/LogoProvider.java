package ru.devstend.getqr.service;

import ru.devstend.getqr.dto.LogoDto;

public interface LogoProvider {

  /**
   * Default logo
   *
   * @return svg logo with size
   */
  LogoDto getDefaultLogo();

  /**
   * Logo by name
   *
   * @param logoName logo name
   * @return svg logo with size
   */
  LogoDto getLogoByName(String logoName);

}
