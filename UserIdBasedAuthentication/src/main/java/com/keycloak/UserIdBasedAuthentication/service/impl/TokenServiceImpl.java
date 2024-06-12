package com.keycloak.UserIdBasedAuthentication.service.impl;

import com.keycloak.UserIdBasedAuthentication.customException.AdminTokenException;
import com.keycloak.UserIdBasedAuthentication.customException.UserNotFoundException;
import com.keycloak.UserIdBasedAuthentication.dao.TokenServiceDao;
import com.keycloak.UserIdBasedAuthentication.dto.StaffDTO;
import com.keycloak.UserIdBasedAuthentication.dto.UserTokenRequestDto;
import com.keycloak.UserIdBasedAuthentication.entity.Location;
import com.keycloak.UserIdBasedAuthentication.entity.Staff;
import com.keycloak.UserIdBasedAuthentication.service.KeycloakService;
import com.keycloak.UserIdBasedAuthentication.service.TokenService;
import java.util.List;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class TokenServiceImpl implements TokenService {

    private static final Logger logger = LoggerFactory.getLogger(TokenServiceImpl.class);

    private TokenServiceDao tokenServiceDao;
    private KeycloakService keycloakService;

    @Override
    public ResponseEntity<StaffDTO> getUserToken(UserTokenRequestDto userTokenRequestDto) throws UserNotFoundException, AdminTokenException {
        Location location = tokenServiceDao.getLocationDetails(userTokenRequestDto.getLocationId());

        logger.info("Location {} ",location);

        List<Staff> staffs = tokenServiceDao.getStaffByMerchantId(location.getMerchantId());

        for(Staff staff : staffs){
            if (staff.getDevicePin().equals(userTokenRequestDto.getDevicePin())){
                logger.info("Staff {} ",staff);
                logger.info("Staff Auth Id {} ",staff.getAuthUserId());
                 StaffDTO staffDTO = StaffDTO.builder()
                         .accessToken(keycloakService.useKeycloakIdentity(staff.getAuthUserId()))
                         .id(staff.getId())
                         .email(staff.getEmail())
                         .merchantId(staff.getMerchantId())
                         .authUserId(staff.getAuthUserId())
                         .customId(staff.getCustomId())
                         .fullName(staff.getFullName())
                         .mobilePhone(staff.getMobilePhone())
                         .statusId(staff.getStatusId())
                         .isOwner(staff.getIsOwner())
                         .attributes(staff.getAttributes())
                         .createdTime(staff.getCreatedTime())
                         .modifiedTime(staff.getModifiedTime())
                         .build();
                 return ResponseEntity.status(HttpStatus.OK).body(staffDTO);
            }
        }
        throw new UserNotFoundException("User Not Found");
    }
}
