package com.maif.taskmanagerplus.api.v1.controller;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.maif.taskmanagerplus.api.v1.assembler.ProvinceInputDisassembler;
import com.maif.taskmanagerplus.api.v1.assembler.ProvinceModelAssembler;
import com.maif.taskmanagerplus.api.v1.model.ProvinceModel;
import com.maif.taskmanagerplus.api.v1.model.input.ProvinceInput;
import com.maif.taskmanagerplus.api.v1.openapi.controller.ProvinceControllerOpenApi;
import com.maif.taskmanagerplus.core.data.PageWrapper;
import com.maif.taskmanagerplus.core.data.PageableTranslator;
import com.maif.taskmanagerplus.core.security.CheckSecurity;
import com.maif.taskmanagerplus.domain.filter.ProvinceFilter;
import com.maif.taskmanagerplus.domain.model.Province;
import com.maif.taskmanagerplus.domain.repository.ProvinceRepository;
import com.maif.taskmanagerplus.domain.service.RegisterProvinceService;
import com.maif.taskmanagerplus.infrastructure.repository.spec.ProvinceSpecs;

@RestController
@RequestMapping(path = "/v1/provinces", produces = MediaType.APPLICATION_JSON_VALUE)
public class ProvinceController implements ProvinceControllerOpenApi {

	@Autowired
	private ProvinceRepository provinceRepository;
	
	@Autowired
	private RegisterProvinceService registerProvince;
	
	@Autowired
	private ProvinceModelAssembler provinceModelAssembler;
	
	@Autowired
	private ProvinceInputDisassembler provinceInputDisassembler;
	
	@Autowired
	private PagedResourcesAssembler<Province> pagedResourcesAssembler;
	
	
	@CheckSecurity.Provinces.CanConsult
	@Override
	@GetMapping
	public PagedModel<ProvinceModel> list(ProvinceFilter filter, Pageable pageable) {
		
		Pageable pageableTranslate = translatePageable(pageable);
		Page<Province> provincesPage = null;
		
		if(filter.getName()!=null || filter.getAbbreviation()!=null ) {
			provincesPage = provinceRepository.findAll(ProvinceSpecs.withFilter(filter), pageableTranslate);
		}
		else
			provincesPage = provinceRepository.findAll(pageable);
		
		provincesPage = new PageWrapper<>(provincesPage, pageable);
		
		return pagedResourcesAssembler.toModel(provincesPage, provinceModelAssembler);
	}
	
	@CheckSecurity.Provinces.CanConsult
	@GetMapping("/noPagination")
	public CollectionModel<ProvinceModel> listNoPagination(ProvinceFilter filter) {
		
		List<Province> allProvinces;
		
		if(filter.getName()!=null || filter.getAbbreviation()!=null ) {
			allProvinces = provinceRepository.findAll(ProvinceSpecs.withFilter(filter));
		}
		else
			allProvinces = provinceRepository.findAll();
		
		return provinceModelAssembler.toCollectionModel(allProvinces);
	}
	
	@CheckSecurity.Provinces.CanConsult
	@Override
	@GetMapping("/{provinceId}")
	public ProvinceModel find(@PathVariable Long provinceId) {
		Province province = registerProvince.findOrFail(provinceId);
		
		return provinceModelAssembler.toModel(province);
	}
	
	@CheckSecurity.Provinces.CanEdit
	@Override
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ProvinceModel add(@RequestBody @Valid ProvinceInput provinceInput) {
		Province province = provinceInputDisassembler.toDomainObject(provinceInput);
		
		province = registerProvince.save(province);
		
		return provinceModelAssembler.toModel(province);
	}
	
	@CheckSecurity.Provinces.CanEdit
	@Override
	@PutMapping("/{provinceId}")
	public ProvinceModel update(@PathVariable Long provinceId, @RequestBody @Valid ProvinceInput provinceInput) {
		Province currentProvince = registerProvince.findOrFail(provinceId);
		
		provinceInputDisassembler.copyToDomainObject(provinceInput, currentProvince);
		
		currentProvince = registerProvince.save(currentProvince);
		
		return provinceModelAssembler.toModel(currentProvince);
	}
	
	@CheckSecurity.Provinces.CanEdit
	@Override
	@DeleteMapping("/{provinceId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remove(@PathVariable Long provinceId) {
		
		Province province = registerProvince.findOrFail(provinceId);
		String nameProvince = province.getName();
		
		registerProvince.delete(provinceId, nameProvince);	
	}
	
	private Pageable translatePageable(Pageable apiPageable) {
		var mapping = Map.of(
				"id", "code",
				"name", "name"
			);
		
		return PageableTranslator.translate(apiPageable, mapping);
	}
	
}
