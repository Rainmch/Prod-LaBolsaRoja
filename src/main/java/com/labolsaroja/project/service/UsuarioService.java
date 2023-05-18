package com.labolsaroja.project.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.labolsaroja.project.model.ChangePassword;
import com.labolsaroja.project.model.Usuario;
import com.labolsaroja.project.repository.UsuarioRepository;


@Service
public class UsuarioService {
	
	
	private final UsuarioRepository usuarioRepository;
	
	@Autowired
	public UsuarioService(UsuarioRepository usuarioRepository) {
		this.usuarioRepository=usuarioRepository;
	}
	
	
	public Usuario addUsuario(Usuario usuario) {
		
			Usuario tmp=null;
		
					if (usuarioRepository.findByEmail(usuario.getEmail()).isEmpty()) {
						tmp=usuarioRepository.save(usuario);
					}
					return tmp;
		

	}

	public List<Usuario> getAllUsuarios() {
		return usuarioRepository.findAll();
	}
	
	public Usuario getUsuario(Long id) {
		return usuarioRepository.findById(id).orElseThrow(
				()-> new IllegalArgumentException("Usuario con id"
	+id+" no existe.")
);
	}

	public Usuario deleteUsuario(Long id) {
		Usuario userTmp=null;
		if(usuarioRepository.existsById(id)) {
userTmp=usuarioRepository.findById(id).get();
usuarioRepository.deleteById(id);;
		}
			
		return userTmp;
	}

	
	public Usuario updateUsuario(Long idUsuarios,ChangePassword changePassword) {
		Usuario tmp=null;
		if(usuarioRepository.existsById(idUsuarios)) {
			if((changePassword.getPassword()!=null)&&
			(changePassword.getNewPassword()!=null)) {
				
				tmp=usuarioRepository.findById(idUsuarios).get();
				if(tmp.getContrasena().equals(changePassword.getPassword())) {
					tmp.setContrasena(changePassword.getNewPassword());
					usuarioRepository.save(tmp);
				}else {
					tmp=null;
				}
			}
		}else {
			System.out.println("Update - El usuario con id "
					+idUsuarios+" no existe");
		}
		
		return tmp;
	}
	
	public Usuario validateUsuario(String email , String contrasena) {
		Optional<Usuario> userByEmail=
				usuarioRepository.findByEmail(email);
		if (userByEmail.isPresent()) {
			Usuario user=userByEmail.get();
			
				if(user.getContrasena().equals(contrasena)){ 
				return user;
				}
		}//if isPresent
		return null;
	}//validateUsuario


	public Usuario passwordReset(String email) {
		
		Optional<Usuario> userByEmail=
				usuarioRepository.findByEmail(email);
		if (userByEmail.isPresent()) {
			Usuario user=userByEmail.get();
			
				if(user.getEmail().equals(email)){ 
				return user;
				}
		}//if isPresent
		return null;
		
	}//passwordReset
	



}
