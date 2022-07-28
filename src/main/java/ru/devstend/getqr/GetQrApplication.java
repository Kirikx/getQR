package ru.devstend.getqr;

import io.dekorate.kubernetes.annotation.ImagePullPolicy;
import io.dekorate.kubernetes.annotation.Ingress;
import io.dekorate.kubernetes.annotation.KubernetesApplication;
import io.dekorate.kubernetes.annotation.Port;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@KubernetesApplication
@SpringBootApplication
public class GetQrApplication {

  public static void main(String[] args) {
    SpringApplication.run(GetQrApplication.class, args);
  }

}
