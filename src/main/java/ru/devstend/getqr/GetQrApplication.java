package ru.devstend.getqr;

import io.dekorate.kubernetes.annotation.KubernetesApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@KubernetesApplication
@SpringBootApplication
public class GetQrApplication {

  public static void main(String[] args) {
    SpringApplication.run(GetQrApplication.class, args);
  }

}
