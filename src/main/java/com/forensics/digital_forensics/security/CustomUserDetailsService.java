
package com.forensics.digital_forensics.security;

import com.forensics.digital_forensics.identity.Investigator;
import com.forensics.digital_forensics.identity.InvestigatorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final InvestigatorRepository investigatorRepository;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {

        Investigator investigator = investigatorRepository
                .findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(
                        "Invalid username or password"
                ));

        return User.withUsername(investigator.getUsername())
                .password(investigator.getPasswordHash())
                .roles(investigator.getRole().name())
                .disabled(!investigator.isEnabled())
                .build();
    }
}

