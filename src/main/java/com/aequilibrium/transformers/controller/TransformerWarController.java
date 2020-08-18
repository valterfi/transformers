package com.aequilibrium.transformers.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aequilibrium.transformers.service.TransformerWarService;

@RestController
@RequestMapping("/api/transformers/war")
public class TransformerWarController {
	
	@Autowired
	private TransformerWarService transformerWarService;
	
	@PostMapping
	public void execute(@RequestBody List<Long> ids) {
		transformerWarService.run(ids);
	}

}
