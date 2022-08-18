package com.example.minitest.config.service.impl;

import com.example.minitest.config.model.Country;
import com.example.minitest.config.repository.ICountryRepository;
import com.example.minitest.config.service.ICountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CountryService implements ICountryService {
    @Autowired
    private ICountryRepository iCountryRepository;

    @Override
    public void save(Country country) {
        iCountryRepository.save(country);
    }

    @Override
    public void delete(Long id) {
        iCountryRepository.deleteById(id);
    }

    @Override
    public Optional<Country> findById(Long id) {
        return iCountryRepository.findById(id);
    }

    @Override
    public List<Country> findAll() {
        return iCountryRepository.findAll();
    }
}
