package com.eim.oauth.entity;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class JwtUser implements UserDetails {

    private static final long serialVersionUID = 1L;

    private final String id;
    private final String username;
    private final String password;
    private final Collection<? extends GrantedAuthority> authorities;
    private final int enabled;
    private final User user;

    public JwtUser(
            String id,
            String username,
            String password, List<String> authorities,
            int enabled,User user
    ) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.authorities = mapToGrantedAuthorities(authorities);
        this.enabled = enabled;
        this.user = user;
    }
    public JwtUser(
            String id,
            String username,
            String password, String authoritie,
            int enabled,User user
    ) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.authorities = mapToGrantedAuthorities(authoritie);
        this.enabled = enabled;
        this.user = user;
    }
    private List<GrantedAuthority> mapToGrantedAuthorities(List<String> authorities) {
        return authorities.stream()
                .map(authority -> new SimpleGrantedAuthority(authority))
                .collect(Collectors.toList());
    }
    private List<GrantedAuthority> mapToGrantedAuthorities(String authoritie) {
        return Arrays.asList(new SimpleGrantedAuthority(authoritie));
    }
    public String getId() {
        return id;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public boolean isEnabled() {
        return enabled > 0;
    }

    public User getUser() {
        return user;
    }
}