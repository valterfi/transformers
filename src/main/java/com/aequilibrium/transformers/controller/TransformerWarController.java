package com.aequilibrium.transformers.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aequilibrium.transformers.dto.BattleSummaryDTO;
import com.aequilibrium.transformers.exception.TransformerException;
import com.aequilibrium.transformers.service.TransformerWarService;

@RestController
@RequestMapping("/api/transformers/war")
public class TransformerWarController {
	
	@Autowired
	private TransformerWarService transformerWarService;
	
	@PostMapping
	public BattleSummaryDTO execute(@RequestBody List<Long> ids) throws TransformerException {
		if (!transformerWarService.existBattleRunning()) {
			try {
				if (ids.size() > 1000) {
					transformerWarService.runAsync(ids);
					return transformerWarService.asyncBattleSummary();
				} else {
					transformerWarService.run(ids);
					return transformerWarService.battleSummary(true);
				}
				
			} catch (Exception e) {
				throw new TransformerException(HttpStatus.INTERNAL_SERVER_ERROR, e);
			}
		} else {
			throw new TransformerException(HttpStatus.INTERNAL_SERVER_ERROR, "There is already a battle running");
		}
	}
	
	@GetMapping
	public BattleSummaryDTO summary() throws TransformerException {
		try {
			return transformerWarService.battleSummary(true);
		} catch (Exception e) {
			throw new TransformerException(HttpStatus.INTERNAL_SERVER_ERROR, e);
		}
	}
	
	@GetMapping("/noverbose")
	public BattleSummaryDTO detailedSummary() throws TransformerException {
		try {
			return transformerWarService.battleSummary(false);
		} catch (Exception e) {
			throw new TransformerException(HttpStatus.INTERNAL_SERVER_ERROR, e);
		}
	}

}
