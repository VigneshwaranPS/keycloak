package com.keycloak.UserIdBasedAuthentication.dao.daoImpl;

import com.keycloak.UserIdBasedAuthentication.dao.TokenServiceDao;
import com.keycloak.UserIdBasedAuthentication.entity.Location;
import com.keycloak.UserIdBasedAuthentication.entity.Staff;
import com.keycloak.UserIdBasedAuthentication.repository.LocationRepository;
import com.keycloak.UserIdBasedAuthentication.repository.StaffRepository;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class TokenServiceDaoImpl implements TokenServiceDao {

    private LocationRepository locationRepository;
    private StaffRepository staffRepository;


    @Override
    public Location getLocationDetails(String locationId) {
        return locationRepository.findById(locationId).get();
    }

    @Override
    public List<Staff> getStaffByMerchantId(String merchantId) {
        List<Staff> staffs = staffRepository.findAllByMerchantId(merchantId);
        return staffs;
    }
}
