package com.softserve.clinic.repository;

import com.softserve.clinic.model.Patient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class PatientRepositoryTest {

    @Autowired
    private PatientRepository patientRepository;

    @Test
    void shouldSavePatient() {
        Patient patient = new Patient();
        patient.setFirstName("Klavdija");

        patientRepository.save(patient);

        assertNotNull(patient.getId());
    }

    @Test
    void shouldUpdatePatient() {
        Patient patient = new Patient();
        patient.setFirstName("Klavdija");

        patientRepository.save(patient);

        patient.setFirstName("Denys");

        Patient expected = patientRepository.save(patient);

        assertEquals("Denys", expected.getFirstName());
    }
}
