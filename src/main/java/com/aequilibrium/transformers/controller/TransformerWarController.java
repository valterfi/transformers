package com.aequilibrium.transformers.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aequilibrium.transformers.dto.BattleSummaryDTO;
import com.aequilibrium.transformers.exception.TransformerException;
import com.aequilibrium.transformers.service.TransformerWarService;

/**
 * @author valterfi
 * 
 * Controller responsible for controlling wars between transformers
 *
 */
@RestController
@RequestMapping("/api/transformers/war")
@CrossOrigin(origins = {"*"})
public class TransformerWarController {
	
	@Autowired
	private TransformerWarService transformerWarService;
	
	/**
	 * Perform a new battle between the transformers. Running a new battle is not allowed if any battles are still in progress.
	 */
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
	
	/**
	 * Returns the summary of the last battle that happened
	 */
	@GetMapping
	public BattleSummaryDTO summary() throws TransformerException {
		try {
			return transformerWarService.battleSummary(true);
		} catch (Exception e) {
			throw new TransformerException(HttpStatus.INTERNAL_SERVER_ERROR, e);
		}
	}
	
	/**
	 * Returns summary less details of the last battle that happened
	 */
	@GetMapping("/noVerbose")
	public BattleSummaryDTO noVerboseSummary() throws TransformerException {
		try {
			return transformerWarService.battleSummary(false);
		} catch (Exception e) {
			throw new TransformerException(HttpStatus.INTERNAL_SERVER_ERROR, e);
		}
	}

}
