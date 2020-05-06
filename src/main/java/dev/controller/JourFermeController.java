/**
 * 
 */
package dev.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.controller.dto.JourFermeDto;
import dev.entites.JourFerme;
import dev.services.JourFermeService;

@RestController
@RequestMapping("jourFerme")
public class JourFermeController {

	private JourFermeService jourFermeService;

	public JourFermeController(JourFermeService jourFermeService) {
		this.jourFermeService = jourFermeService;
	}

//	@GetMapping(("{id}"))
//	public List<JourFermeDto> listerJourFerme(@PathVariable Long id){
//		return jourFermeService.listerJourFerme(id);
//	}
	
	@GetMapping
	public List<JourFerme> getAllJourFermes() {

		return this.jourFermeService.getAllJourFermes();
	}
	 
	@PostMapping
	public JourFerme postJourFerme(@RequestBody @Valid JourFermeDto jourFermeDto) {
		return this.jourFermeService.postJourFerme(jourFermeDto);
	}
	
}
