package com.keycloak.UserIdBasedAuthentication.dao;

import com.keycloak.UserIdBasedAuthentication.entity.Location;
import com.keycloak.UserIdBasedAuthentication.entity.Staff;
import java.util.List;

public interface TokenServiceDao {
    Location getLocationDetails(String locationId);

    List<Staff> getStaffByMerchantId(String merchantId);
}
