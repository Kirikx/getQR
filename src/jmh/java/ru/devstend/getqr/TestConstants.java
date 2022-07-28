package ru.devstend.getqr;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;

@RequiredArgsConstructor
@Getter
//@State(Scope.Benchmark)
public class TestConstants {
  private final String one;
  private final String two;
  private final String tree;
  private final String four;
  private final String five;
  private final String six;

}
