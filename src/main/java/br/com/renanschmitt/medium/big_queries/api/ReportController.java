package br.com.renanschmitt.medium.big_queries.api;

import br.com.renanschmitt.medium.big_queries.dto.ReportDto;
import br.com.renanschmitt.medium.big_queries.services.ReportService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

@Controller
@RestController
@Log4j2
@RequiredArgsConstructor
public class ReportController {

  private final ReportService reportService;

  @GetMapping("/v1/report")
  public ResponseEntity<List<ReportDto>> report() {
    var result = reportService.getResult();
    return ResponseEntity.ok(result);
  }

  @GetMapping("/v2/report")
  public ResponseEntity<List<ReportDto>> report2() {
    var result = reportService.getResult2();
    return ResponseEntity.ok(result);
  }

  @GetMapping("/v3/report")
  public ResponseEntity<StreamingResponseBody> report3() {
    var body = reportService.getResult3();
    return ResponseEntity.ok(body);
  }
}
