package com.kosa.tikitaka.security;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.kosa.tikitaka.model.ProjectDTO;
import com.kosa.tikitaka.model.UserDTO;

public class UserDetailsImpl implements UserDetails {

	private final UserDTO userDTO;
	private ProjectDTO projectDTO;

	public UserDetailsImpl(UserDTO user, ProjectDTO project) {
        this.userDTO = user;
        this.projectDTO = project;
    }

	public UserDTO getUser() {
		return userDTO;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return null;
	}

	@Override
	public String getPassword() {
		return userDTO.getPwd();
	}

	@Override
	public String getUsername() {
		return "admin";
	}
	
	public void setProjectDTO(ProjectDTO projectDTO) {
		this.projectDTO = projectDTO;
	}

	public int getProjectNo() {
		return projectDTO.getProjNo();
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
	public boolean isEnabled() {
		return true;
	}

}
