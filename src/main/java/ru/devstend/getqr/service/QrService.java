package ru.devstend.getqr.service;

import javax.annotation.Nullable;
import ru.devstend.getqr.enums.QrCreationMethodEnum;

public interface QrService {

  /**
   * Create QR by method from payload with logo
   *
   * @param payload payload
   * @param method creation method
   * @param logoName logo name
   * @return QR with logo
   */
  String getQr(String payload, QrCreationMethodEnum method, @Nullable String logoName);

  String getQrWithDefaultLogo(String payload, QrCreationMethodEnum method);

}
