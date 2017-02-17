package com.xxx.xing.configuration.security;

import com.xxx.xing.constant.RoleKeys;
import com.xxx.xing.entity.Admin;
import com.xxx.xing.service.AdminService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.SpringSecurityMessageSource;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by xing on 2017/2/16.
 */
@Service
public class AdminDetailsService  implements UserDetailsService {

    private final Log log = LogFactory.getLog(getClass());
    private final MessageSourceAccessor messages = SpringSecurityMessageSource
            .getAccessor();

    @Autowired
    AdminService adminService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Admin admin = adminService.findByLoginname(username);
        if (admin == null) {
            log.debug("Query returned no results for user '" + username + "'");

            throw new UsernameNotFoundException(messages.getMessage(
                    "UserDetailsServiceImpl.notFound", new Object[]{username},
                    "Username {0} not found"));
        }

        Set<GrantedAuthority> dbAuthsSet = new HashSet<GrantedAuthority>();

        dbAuthsSet.addAll(loadUserAuthorities());
        List<GrantedAuthority> dbAuths = new ArrayList<GrantedAuthority>(dbAuthsSet);

        return createUserDetails(admin, dbAuths);
    }

    private User createUserDetails(Admin admin, List<GrantedAuthority> dbAuths) {
        AdminUser user = new AdminUser(admin.getLoginname(), admin.getPassword(), admin.getStatus().equals(1),
                true, true, true, dbAuths);
        user.setSalt(admin.getSalt());
        user.setNickname(admin.getNickname());
        return user;
    }

    protected List<GrantedAuthority> loadUserAuthorities() {
        GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + RoleKeys.ADMIN);
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        authorities.add(authority);
        return authorities;
    }
}
