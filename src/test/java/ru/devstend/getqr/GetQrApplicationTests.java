//package ru.devstend.getqr;
//
//import java.util.concurrent.TimeUnit;
//import org.junit.runner.RunWith;
//import org.openjdk.jmh.annotations.Benchmark;
//import org.openjdk.jmh.annotations.BenchmarkMode;
//import org.openjdk.jmh.annotations.Mode;
//import org.openjdk.jmh.annotations.OutputTimeUnit;
//import org.openjdk.jmh.annotations.Scope;
//import org.openjdk.jmh.annotations.State;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringRunner;
//import ru.devstend.getqr.enums.QrCreationMethodEnum;
//import ru.devstend.getqr.service.QrService;
//
//@SpringBootTest
//@RunWith(SpringRunner.class)
//@State(Scope.Benchmark)
//@BenchmarkMode(Mode.AverageTime)
//@OutputTimeUnit(TimeUnit.MILLISECONDS)
//public class GetQrApplicationTests extends AbstractBenchmark {
//
//  public static final String TEST_PAYLOAD = "http://example.org/path/foobar";
//
//  private static QrService qrService;
//
//  @Autowired
//  public void setServiceQr(QrService qrService) {
//    GetQrApplicationTests.qrService = qrService;
//  }
//
//  @Benchmark
//  public void qrBuilderTest() {
//    qrService.getQrWithDefaultLogo(TEST_PAYLOAD, QrCreationMethodEnum.KENGLXN_BUILDER);
//  }
//
//  @Benchmark
//  public void qrGraphicsTest() {
//    qrService.getQrWithDefaultLogo(TEST_PAYLOAD, QrCreationMethodEnum.SVG_GRAPHICS);
//  }
//
//  @Benchmark
//  public void qrDocTest() {
//    qrService.getQrWithDefaultLogo(TEST_PAYLOAD, QrCreationMethodEnum.DOCUMENT);
//  }
//
//  @Benchmark
//  public void qrSbTest() {
//    qrService.getQrWithDefaultLogo(TEST_PAYLOAD, QrCreationMethodEnum.STRING_BUILDER);
//  }
//}
