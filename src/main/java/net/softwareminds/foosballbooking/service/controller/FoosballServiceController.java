package net.softwareminds.foosballbooking.service.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class FoosballServiceController {

  @RequestMapping("/appointments")
  public ResponseEntity<String> getAppointments() {
    HttpHeaders headers = new HttpHeaders();
    headers.set("Content-Type", "application/json");
    return new ResponseEntity<String>("succeeded", headers, HttpStatus.OK);
  }

  @RequestMapping("/usercontext")
  public ResponseEntity<String> getUserContext() {
    HttpHeaders headers = new HttpHeaders();
    headers.set("Content-Type", "application/json");
    return new ResponseEntity<String>("user context received", headers, HttpStatus.OK);
  }
}
