package com.softserve.clinic.repository;


import com.softserve.clinic.model.Patient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import static org.junit.jupiter.api.Assertions.*;

class PatientRepositoryTest {
    private final PatientRepository patientRepository;

   PatientRepositoryTest(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    @Test
    void save() {
       Patient patient = new Patient();
       patient.setFirstName("Klavdija");
       patientRepository.save(patient);
       assertTrue(patient.getId()!=null);
    }

    @Sql(statements = "insert into users ('username','password', 'first_name'," +
            " 'second_name', 'email', 'contact_number', 'role')" +
            "values ('bg_zn', '123456', 'Bogdan', 'Zinovii', 'bg_zn@mail.com', '223145666', 'PATIENT ')")
    @Test
    void update() {
       Patient actual =patientRepository.findByUsername("bg_zn");
        assertNotNull(actual);
        actual.setFirstName("Katerina");
       Patient expected =patientRepository.save(actual);
        assertNotNull(expected);
        assertEquals(expected, actual);
    }
}
