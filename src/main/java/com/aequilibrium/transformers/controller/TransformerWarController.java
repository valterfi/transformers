package com.aequilibrium.transformers.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aequilibrium.transformers.exception.TransformerException;
import com.aequilibrium.transformers.service.TransformerWarService;

@RestController
@RequestMapping("/api/transformers/war")
public class TransformerWarController {
	
	@Autowired
	private TransformerWarService transformerWarService;
	
	@PostMapping
	public void execute(@RequestBody List<Long> ids) throws TransformerException {
		if (!transformerWarService.existBattleRunning()) {
			transformerWarService.run(ids);
		} else {
			throw new TransformerException(HttpStatus.INTERNAL_SERVER_ERROR, "There is already a battle running");
		}
	}

}
