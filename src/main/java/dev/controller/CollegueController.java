/**
 * 
 */
package dev.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.controller.dto.CollegueDto;
import dev.services.CollegueService;

@RestController
@RequestMapping("collegue")
public class CollegueController {

	private CollegueService collegueService;

	public CollegueController(CollegueService collegueService ) {
		this.collegueService = collegueService;
	}

	@GetMapping
	public List<CollegueDto> getAllCollegue() { 
		return this.collegueService.getAllCollegues();
	}
	

}
