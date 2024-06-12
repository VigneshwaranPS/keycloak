package com.keycloak.UserIdBasedAuthentication.repository;

import com.keycloak.UserIdBasedAuthentication.entity.Staff;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface StaffRepository extends JpaRepository<Staff,String> {


    List<Staff> findAllByMerchantId(String merchantId);

//    @Query(
//            nativeQuery = true,
//            value = "Select * from mh_staff where merchant_id = :merchantId"
//    )
//    List<Staff> getAllByMerchantId(String merchantId);
}
