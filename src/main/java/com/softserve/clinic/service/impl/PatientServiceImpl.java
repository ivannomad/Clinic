package com.softserve.clinic.service.impl;


import com.softserve.clinic.dto.PatientDto;
import com.softserve.clinic.exception.NullEntityReferenceException;
import com.softserve.clinic.mapper.PatientMapper;
import com.softserve.clinic.model.Patient;
import com.softserve.clinic.repository.PatientRepository;
import com.softserve.clinic.service.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PatientServiceImpl implements PatientService {

    private final PatientRepository patientRepository;
    private final PatientMapper patientMapper;

    @Override
    public PatientDto createPatient(PatientDto patientDto) {
        if (patientDto != null) {
            patientRepository.save(patientMapper.patientDtoToPatient(patientDto));
            return patientDto;
        }
        throw new NullEntityReferenceException("Patient cannot be 'null'");
    }

    @Override
    public List<Patient> getAllPatients() {
        return patientRepository.findAll();
    }

    @Override
    public Patient getPatientById(UUID id) {
        return patientRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Patient with id " + id + " not found"));
    }

    @Override
    public void deletePatientById(UUID id) {
        patientRepository.deleteById(id);
    }
}
