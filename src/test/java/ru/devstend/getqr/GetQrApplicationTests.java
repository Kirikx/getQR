package ru.devstend.getqr;

import java.util.concurrent.TimeUnit;
import org.junit.runner.RunWith;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
@State(Scope.Benchmark)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
public class GetQrApplicationTests extends AbstractBenchmark {

  public static final String TEST_PAYLOAD = "http://example.org/path/foobar";

  private static ServiceQr serviceQr;

  @Autowired
  public void setServiceQr(ServiceQr serviceQr) {
    GetQrApplicationTests.serviceQr = serviceQr;
  }

  @Benchmark
  public void qrBuilderTest() throws Exception {
    serviceQr.generateQRCodeSvg(TEST_PAYLOAD);
  }

  @Benchmark
  public void qrGraphicsTest() throws Exception {
    serviceQr.createQrGraphics(TEST_PAYLOAD);
  }

  @Benchmark
  public void qrDocTest() throws Exception {
    serviceQr.createQrDoc(TEST_PAYLOAD);
  }

  @Benchmark
  public void qrSbTest() throws Exception {
    serviceQr.createQrSb(TEST_PAYLOAD);
  }
}
