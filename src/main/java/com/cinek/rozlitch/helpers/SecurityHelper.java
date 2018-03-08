package com.cinek.rozlitch.helpers;

import com.cinek.rozlitch.config.CustomUserDetails;
import org.springframework.security.core.context.SecurityContextHolder;
/**
 * Created by Cinek on 17.11.2017.
 */
public class SecurityHelper {
    public static String getLoggedInUsername() {
       CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
       return userDetails.getUsername();

    }
}
