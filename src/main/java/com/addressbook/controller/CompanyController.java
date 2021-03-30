package com.addressbook.controller;

import com.addressbook.exception.NotFoundException;
import com.addressbook.model.Company;
import com.addressbook.repositories.CompanyRepository;
import io.swagger.annotations.Api;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.Optional;

@Log4j2
@RestController
@RequestMapping("/api/v1")
@Api(tags = { "Company" })
public class CompanyController {

	private final CompanyRepository companyRepository;

	@Autowired
	public CompanyController(CompanyRepository companyRepository){
		this.companyRepository = companyRepository;
	}

	@GetMapping("company/:id")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Company> getCompany(@PathVariable("id") long id) throws NotFoundException {
		Optional<Company> company = this.companyRepository.findById(id);

		if (company.isPresent()) {
			return new ResponseEntity(company, HttpStatus.OK);
		}

		throw new NotFoundException("Company not found!");
	}

	@PostMapping("company")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<Company> postCompany(@Valid @RequestBody Company company) {
		this.companyRepository.save(company);

		return new ResponseEntity(company, HttpStatus.CREATED);
	}
}
