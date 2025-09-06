import http from "k6/http";
import { check, sleep } from "k6";
import { textSummary } from "https://jslib.k6.io/k6-summary/0.0.1/index.js";

export const options = {
  stages: [
    { duration: "30s", target: 50 },
  ],
  thresholds: {
    http_req_failed: ["rate<0.01"],
    http_req_duration: ["p(95)<500"],
  },
};

const BASE_URL = "http://localhost:8081";

export default function () {
  const homeResponse = http.get(`${BASE_URL}/`);
  check(homeResponse, {
    "GET / returns status 200": (r) => r.status === 200,
    "GET / has HTML content": (r) => r.headers["Content-Type"] === "text/html",
  });

  const staticResponse = http.get(`${BASE_URL}/static/img/koshka.png`);
  check(staticResponse, {
    "GET /static/ returns status 200": (r) => r.status === 200,
  });

  const timeResponse = http.get(`${BASE_URL}/api/time`);
  check(timeResponse, {
    "GET /api/time returns status 200": (r) => r.status === 200,
    "GET /api/time returns JSON": (r) =>
      r.headers["Content-Type"] === "application/json",
    "GET /api/time has valid timestamp": (r) => {
      try {
        const json = r.json();
        return json.currentTime && typeof json.currentTime === "string";
      } catch (e) {
        return false;
      }
    },
  });

  const statsResponse = http.get(`${BASE_URL}/api/stats`);
  check(statsResponse, {
    "GET /api/stats returns status 200": (r) => r.status === 200,
    "GET /api/stats returns JSON": (r) =>
      r.headers["Content-Type"] === "application/json",
    "GET /api/stats has valid stats data": (r) => {
      try {
        const json = r.json();
        return (
          json.requestsServed !== undefined && json.uptimeMillis !== undefined
        );
      } catch (e) {
        return false;
      }
    },
  });

  const testPayload = JSON.stringify({
    message: "Hello, World!",
    timestamp: new Date().toISOString(),
  });
  const echoResponse = http.post(`${BASE_URL}/api/echo`, testPayload, {
    headers: { "Content-Type": "application/json" },
  });
  check(echoResponse, {
    "POST /api/echo returns status 200": (r) => r.status === 200,
    "POST /api/echo returns JSON": (r) =>
      r.headers["Content-Type"] === "application/json",
    "POST /api/echo echoes the payload": (r) => r.body === testPayload,
  });

  sleep(1);
}

export function handleSummary(data) {
  return {
    stdout: textSummary(data, { indent: " ", enableColors: true }),
    "summary.json": JSON.stringify(data),
  };
}
