package com.maif.taskmanagerplus.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.maif.taskmanagerplus.domain.exception.EntityInUseException;
import com.maif.taskmanagerplus.domain.exception.ProvinceNotFoundException;
import com.maif.taskmanagerplus.domain.model.Province;
import com.maif.taskmanagerplus.domain.repository.ProvinceRepository;

@Service
public class RegisterProvinceService {

	private static final String MSG_PROVINCE_IN_USE_NAME 
	= "Province with the name \""+ "%s" +"\" can´t be removed, because it is in use";
	
	
	@Autowired
	private ProvinceRepository provinceRepository;
	
	@Transactional
	public Province save(Province province) {
		return provinceRepository.save(province);
	}
	
	@Transactional
	public void delete(Long provinceId, String nameProvince) {
		try {
			provinceRepository.deleteById(provinceId);
			provinceRepository.flush();
			
		} catch (EmptyResultDataAccessException e) {
			throw new ProvinceNotFoundException(provinceId);
		
		} catch (DataIntegrityViolationException e) {
			throw new EntityInUseException(
				String.format(MSG_PROVINCE_IN_USE_NAME, nameProvince));
		}
	}

	public Province findOrFail(Long provinceId) {
		return provinceRepository.findById(provinceId)
			.orElseThrow(() -> new ProvinceNotFoundException(provinceId));
	}
	
}
