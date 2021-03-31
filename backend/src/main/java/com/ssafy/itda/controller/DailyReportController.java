package com.ssafy.itda.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.itda.domain.DailyReport;
import com.ssafy.itda.service.DailyReportService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api("DailyReportController")
@RestController
@RequestMapping("/report")
public class DailyReportController {

	private static final Logger logger = LoggerFactory.getLogger(DailyReportController.class);
	private static final String SUCCESS = "success";
	private static final String FAIL = "fail";
	
	@Autowired
	private DailyReportService dailyReportService;

	@ApiOperation(value = "레포트 목록 보기", notes = "이달의 레포트 목록을 반환한다.", response = DailyReport.class)
	@GetMapping
	public ResponseEntity<List<DailyReport>> getReportList(@RequestParam("userId") @ApiParam(value = "어르신 ID.", required = true) int userId,
			@RequestParam("curMonth") @ApiParam(value = "날짜", required = true) String curMonth) throws Exception {
		logger.info("getReportList - 호출");
		Map<String, Object> map = new HashMap<>();
		map.put("userId", userId);
		map.put("curMonth", curMonth);
	    return new ResponseEntity<List<DailyReport>>(dailyReportService.getReportList(map), HttpStatus.OK);
	}
	
	@ApiOperation(value = "레포트 목록 보기", notes = "이달의 레포트 목록을 반환한다.", response = DailyReport.class)
	@GetMapping
	public ResponseEntity<List<DailyReport>> getReportCalendar(@RequestParam("userId") @ApiParam(value = "어르신 ID.", required = true) int userId,
			@RequestParam("curMonth") @ApiParam(value = "날짜", required = true) String curMonth) throws Exception {
		logger.info("getReportList - 호출");
		Map<String, Object> map = new HashMap<>();
		map.put("userId", userId);
		map.put("curMonth", curMonth);
	    return new ResponseEntity<List<DailyReport>>(dailyReportService.getReportCalendar(map), HttpStatus.OK);
	}
	
	@ApiOperation(value = "데일리 레포트 보기", notes = "오늘의 데일리 레포트를 반환한다.", response = DailyReport.class)
	@GetMapping("/daily")
	public ResponseEntity<DailyReport> getReport(@RequestParam("userId") @ApiParam(value = "어르신 ID.", required = true) int userId,
			@RequestParam("curDay") @ApiParam(value = "날짜", required = true) String curDay) throws Exception {
		logger.info("getReport - 호출");
		Map<String, Object> map = new HashMap<>();
		map.put("userId", userId);
		map.put("curDay", curDay);
	    return new ResponseEntity<DailyReport>(dailyReportService.getReport(map), HttpStatus.OK);
	}
}
