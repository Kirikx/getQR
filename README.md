# Test QrCode to svg creators



### Qr

```.text
BUILDER
Total time ms: 184
Total size kb: 496
StopWatch '': running time = 184807832 ns
---------------------------------------------
ns         %     Task name
---------------------------------------------
180693555  098%  QRCode
004114277  002%  readFileToString

DOCUMENT
Total time ms: 94
Total size kb: 111
StopWatch '': running time = 94590691 ns
---------------------------------------------
ns         %     Task name
---------------------------------------------
005832772  006%  createQrBitMatrix
001842057  002%  bitMatrixToSvgString
044221592  047%  stringToDocument
004088385  004%  overlayQr
038605885  041%  documentToString

GRAPHICS
Total time ms: 64
Total size kb: 787
StopWatch '': running time = 64031008 ns
---------------------------------------------
ns         %     Task name
---------------------------------------------
004948641  008%  createQrBitMatrix
058035744  091%  convertBitMatrixToSvgGraphics2D
001046623  002%  overlayQrSb

STRING_BUILDER
Total time ms: 5
Total size kb: 111
StopWatch '': running time = 5619757 ns
---------------------------------------------
ns         %     Task name
---------------------------------------------
004360162  078%  createQrBitMatrix
001139920  020%  bitMatrixToSvgString
000119675  002%  overlayQrSb
```

### Benchmark

```.text
Benchmark                             Mode  Cnt   Score    Error  Units
GetQrApplicationTests.qrBuilderTest   avgt    3  55,358 ± 28,268  ms/op
GetQrApplicationTests.qrDocTest       avgt    3   2,898 ±  2,585  ms/op
GetQrApplicationTests.qrGraphicsTest  avgt    3   9,150 ±  9,154  ms/op
GetQrApplicationTests.qrSbTest        avgt    3   1,349 ±  2,393  ms/op
```