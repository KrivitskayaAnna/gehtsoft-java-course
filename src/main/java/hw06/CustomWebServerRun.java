package hw06;

import java.io.IOException;

public class CustomWebServerRun {
    public static void main(String[] args) throws IOException {
        // Test with virtual threads
        CustomWebServer virtualServer = new CustomWebServer(8080, 100, true);

        // Test with platform threads
        CustomWebServer platformServer = new CustomWebServer(8081, 50, false);

        try {
            virtualServer.start();
            platformServer.start();

            System.out.println("Servers started:");
            System.out.println("Virtual thread server: http://localhost:8080");
            System.out.println("Platform thread server: http://localhost:8081");

            // Keep servers running
            Thread.sleep(100000); // Run for 1 minute

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            virtualServer.stop();
            platformServer.stop();
        }
    }
}

//virtual threads server has higher failed requests ratio (0.1%) compared to physical threads (0.05%)
//but more than twice lower avg duration of requests (4.32ms compared to 11.31ms with physical server)

//     virtual threads load testing with k6 (duration: 30s, target: 50)
//     ✓ GET / returns status 200
//        ✓ GET / has HTML content
//     ✗ GET /static/ returns status 200
//        ↳  99% — ✓ 758 / ✗ 1
//        ✗ GET /api/time returns status 200
//        ↳  99% — ✓ 756 / ✗ 3
//        ✗ GET /api/time returns JSON
//      ↳  99% — ✓ 756 / ✗ 3
//              ✗ GET /api/time has valid timestamp
//      ↳  99% — ✓ 756 / ✗ 3
//              ✓ GET /api/stats returns status 200
//        ✓ GET /api/stats returns JSON
//     ✓ GET /api/stats has valid stats data
//     ✓ POST /api/echo returns status 200
//        ✓ POST /api/echo returns JSON
//     ✓ POST /api/echo echoes the payload
//
//checks.........................: 99.89% ✓ 9098       ✗ 10
//data_received..................: 1.7 GB 54 MB/s
//data_sent......................: 393 kB 13 kB/s
//http_req_blocked...............: avg=272.47µs min=9.21µs   med=181.87µs max=10ms    p(90)=562.53µs p(95)=754.37µs
//http_req_connecting............: avg=186.9µs  min=0s       med=127.23µs max=2.88ms  p(90)=389.65µs p(95)=524.13µs
//   ✓ http_req_duration..............: avg=4.32ms   min=129.08µs med=800.43µs max=70.66ms p(90)=16.72ms  p(95)=19.87ms
//{ expected_response:true }...: avg=4.32ms   min=129.08µs med=800.88µs max=70.66ms p(90)=16.72ms  p(95)=19.88ms
//   ✓ http_req_failed................: 0.10%  ✓ 4          ✗ 3791
//http_req_receiving.............: avg=932.54µs min=17.56µs  med=146.96µs max=37.71ms p(90)=2.9ms    p(95)=5.08ms
//http_req_sending...............: avg=45.16µs  min=8.26µs   med=29.21µs  max=6.88ms  p(90)=91.77µs  p(95)=118.84µs
//http_req_tls_handshaking.......: avg=0s       min=0s       med=0s       max=0s      p(90)=0s       p(95)=0s
//http_req_waiting...............: avg=3.34ms   min=49.85µs  med=594.75µs max=33.92ms p(90)=13.04ms  p(95)=15.21ms
//http_reqs......................: 3795   122.340477/s
//iteration_duration.............: avg=1.02s    min=1s       med=1.02s    max=1.1s    p(90)=1.03s    p(95)=1.04s
//iterations.....................: 759    24.468095/s
//vus............................: 6      min=2        max=49

//     physical threads load testing with k6 (duration: 30s, target: 50)
//     ✓ GET / returns status 200
//        ✓ GET / has HTML content
//     ✓ GET /static/ returns status 200
//        ✗ GET /api/time returns status 200
//        ↳  99% — ✓ 737 / ✗ 1
//        ✗ GET /api/time returns JSON
//      ↳  99% — ✓ 737 / ✗ 1
//              ✗ GET /api/time has valid timestamp
//      ↳  99% — ✓ 737 / ✗ 1
//              ✓ GET /api/stats returns status 200
//        ✓ GET /api/stats returns JSON
//     ✓ GET /api/stats has valid stats data
//     ✗ POST /api/echo returns status 200
//        ↳  99% — ✓ 737 / ✗ 1
//        ✗ POST /api/echo returns JSON
//      ↳  99% — ✓ 737 / ✗ 1
//              ✗ POST /api/echo echoes the payload
//      ↳  99% — ✓ 737 / ✗ 1
//
//checks.........................: 99.93% ✓ 8850       ✗ 6
//data_received..................: 1.6 GB 52 MB/s
//data_sent......................: 382 kB 12 kB/s
//http_req_blocked...............: avg=311.11µs min=4.87µs   med=178.48µs max=12.2ms   p(90)=554.52µs p(95)=815.2µs
//http_req_connecting............: avg=206.99µs min=0s       med=121.74µs max=10.49ms  p(90)=371.32µs p(95)=569.18µs
//   ✓ http_req_duration..............: avg=11.31ms  min=106.05µs med=4.06ms   max=129ms    p(90)=29.45ms  p(95)=45.51ms
//{ expected_response:true }...: avg=11.32ms  min=139.66µs med=4.06ms   max=129ms    p(90)=29.46ms  p(95)=45.52ms
//   ✓ http_req_failed................: 0.05%  ✓ 2          ✗ 3688
//http_req_receiving.............: avg=1.16ms   min=13.4µs   med=154.76µs max=43.83ms  p(90)=3.36ms   p(95)=5.75ms
//http_req_sending...............: avg=46.92µs  min=7.97µs   med=27.94µs  max=3.75ms   p(90)=85.82µs  p(95)=121.35µs
//http_req_tls_handshaking.......: avg=0s       min=0s       med=0s       max=0s       p(90)=0s       p(95)=0s
//http_req_waiting...............: avg=10.09ms  min=25.05µs  med=3.78ms   max=128.35ms p(90)=26.31ms  p(95)=40.65ms
//http_reqs......................: 3690   118.696913/s
//iteration_duration.............: avg=1.05s    min=1.01s    med=1.04s    max=1.39s    p(90)=1.11s    p(95)=1.14s
//iterations.....................: 738    23.739383/s
//vus............................: 21     min=2        max=49