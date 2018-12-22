package com.datatable.controller;

import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.datatable.service.UserService;

@Controller
@RequestMapping("/")
public class DataTableController {
	@Autowired
	UserService userService;
	@RequestMapping("/")
	public String home() {
		return "datatable";
	}
	
	@ResponseBody
	@RequestMapping("/getData")
	public ResponseEntity<Object> getData(HttpServletRequest request) {
		Map<String, String[]> rMap = request.getParameterMap();
		for(Entry<String, String[]> entry: rMap.entrySet()) {
			System.out.println(entry.getKey() + " => " + entry.getValue()[0]);
		}
		return new ResponseEntity<>(userService.getPaginatedData(request), HttpStatus.OK);
	}
}
