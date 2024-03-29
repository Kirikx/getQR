package ru.devstend.getqr;

import java.util.concurrent.TimeUnit;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Level;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.TearDown;
import org.openjdk.jmh.infra.Blackhole;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import ru.devstend.getqr.enums.QrCreationMethodEnum;
import ru.devstend.getqr.service.QrService;

@State(Scope.Benchmark)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
public class SpringBootJmhRunner {

  public static final String TEST_PAYLOAD = "http://example.org/path/foobar";
  public static final TestConstants CONSTANTS = new TestConstants(
      "RED",
      "GREEN",
      "ALFA",
      "BETA",
      "APPLE",
      "SAMSUNG");

  private ConfigurableApplicationContext context;
  private QrService qrService;

  @Setup(Level.Trial)
  public void setUp() {

    context = SpringApplication.run(GetQrApplication.class);

    qrService = context
        .getBean(QrService.class);
  }

  @TearDown(Level.Trial)
  public void closeContext() {
    context.close();
  }


  //  @Benchmark
  public void testConcatenatePlus(Blackhole bh) {

    String result = CONSTANTS.getOne() + " " +
        CONSTANTS.getTwo() + " " +
        CONSTANTS.getTree() + " " +
        CONSTANTS.getFour() + " " +
        CONSTANTS.getFive() + " " +
        CONSTANTS.getSix();

    bh.consume(result);
  }

  //  @Benchmark
  public void testStringBuilder(Blackhole bh) {

    StringBuilder stringBuilder = new StringBuilder();

    String result = stringBuilder
        .append(CONSTANTS.getOne())
        .append(" ")
        .append(CONSTANTS.getTwo())
        .append(" ")
        .append(CONSTANTS.getTree())
        .append(" ")
        .append(CONSTANTS.getFour())
        .append(" ")
        .append(CONSTANTS.getFive())
        .append(" ")
        .append(CONSTANTS.getSix())
        .toString();

    bh.consume(result);
  }

  //  @Benchmark
  public void testStringBuffer(Blackhole bh) {

    StringBuffer stringBuffer = new StringBuffer();

    String result = stringBuffer
        .append(CONSTANTS.getOne())
        .append(" ")
        .append(CONSTANTS.getTwo())
        .append(" ")
        .append(CONSTANTS.getTree())
        .append(" ")
        .append(CONSTANTS.getFour())
        .append(" ")
        .append(CONSTANTS.getFive())
        .append(" ")
        .append(CONSTANTS.getSix())
        .toString();

    bh.consume(result);
  }

  //  @Benchmark
  public void testConcatenate(Blackhole bh) {

    String result = CONSTANTS.getOne()
        .concat(" ")
        .concat(CONSTANTS.getTwo())
        .concat(" ")
        .concat(CONSTANTS.getTree())
        .concat(" ")
        .concat(CONSTANTS.getFour())
        .concat(" ")
        .concat(CONSTANTS.getFive())
        .concat(" ")
        .concat(CONSTANTS.getSix());

    bh.consume(result);
  }

  @Benchmark
  public void testBuilderVariant(Blackhole bh) {

    bh.consume(
        qrService.getQrWithDefaultLogo(TEST_PAYLOAD, QrCreationMethodEnum.KENGLXN_BUILDER)
    );
  }

  @Benchmark
  public void testGraphicsVariant(Blackhole bh) {

    bh.consume(
        qrService.getQrWithDefaultLogo(TEST_PAYLOAD, QrCreationMethodEnum.SVG_GRAPHICS)
    );
  }

  @Benchmark
  public void testDocVariant(Blackhole bh) {

    bh.consume(
        qrService.getQrWithDefaultLogo(TEST_PAYLOAD, QrCreationMethodEnum.DOCUMENT)
    );
  }

  @Benchmark
  public void testStringBuilderVariant(Blackhole bh) {

    bh.consume(
        qrService.getQrWithDefaultLogo(TEST_PAYLOAD, QrCreationMethodEnum.STRING_BUILDER)
    );
  }

}
