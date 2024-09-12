package com.namung.cazinou.web.controller.seq;

import com.namung.cazinou.seq.service.CustomSequenceService;
import com.namung.cazinou.web.dto.CommonRes;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/seq")
@RequiredArgsConstructor
public class SequenceController {

  private final CustomSequenceService customSequenceService;

  @PostMapping("/create")
  public ResponseEntity<?> createSequence(String sequenceName) {
    return ResponseEntity.status(HttpStatus.CREATED).body(
      CommonRes.success(customSequenceService.createSequence(sequenceName))
    );
  }

  @GetMapping("/get")
  public ResponseEntity<?> getSequenceNumber(
    @RequestParam("seq") String sequenceName
  ) {
    return ResponseEntity.ok(
      CommonRes.success(customSequenceService.getSequenceNumber(sequenceName))
    );
  }

  @PutMapping("/set")
  public ResponseEntity<?> setSequenceNumber(
    @RequestParam("seq") String sequenceName
  ) {
    return ResponseEntity.ok(
      CommonRes.success(customSequenceService.setSequenceNumber(sequenceName))
    );
  }
}
