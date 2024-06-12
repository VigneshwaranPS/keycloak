package com.keycloak.UserIdBasedAuthentication.service.impl;

import com.keycloak.UserIdBasedAuthentication.entity.Staff;
import com.keycloak.UserIdBasedAuthentication.repository.StaffRepository;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TokenServiceImplTest {

    @Autowired
    private StaffRepository staffRepository;

    @Test
    public void getStaffsByMerchantId(){
        List<Staff> staffs = staffRepository.findAllByMerchantId("123e4567-e89b-12d3-a456-426614174000");

        for(Staff staff : staffs){
            System.out.println("Staffs : "+staff);
        }
    }
}

