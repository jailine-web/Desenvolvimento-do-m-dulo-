package com.example.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.app.DTO.CadastroLoginDTO;
import com.example.app.DTO.DadosLoginDTO;
import com.example.app.model.entities.Usuario;
import com.example.app.repositories.UsuarioRepository;
import com.example.app.utils.Regras;

import jakarta.validation.Valid;

@RestController
@CrossOrigin("*")
@RequestMapping(value = "/hisig10/auth")
public class UsuarioAutenticacaoController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private UsuarioRepository usuarioRepository;

	@PostMapping(value = "/logar")
	public ResponseEntity<DadosLoginDTO> login(@RequestBody @Valid DadosLoginDTO data) {

		var usernamePassword = new UsernamePasswordAuthenticationToken(data.usuario().toUpperCase(), data.senha());
		var auth = authenticationManager.authenticate(usernamePassword);
		System.out.println(auth);

		return ResponseEntity.ok().build();
	}

	@PostMapping(value = "/cadastro")
	public ResponseEntity cadastro(@RequestBody @Valid CadastroLoginDTO login) {

		if (usuarioRepository.findByUsuario(login.usuario()) != null)
			return ResponseEntity.badRequest().build();

		String senhaEncriptada = new BCryptPasswordEncoder().encode(login.senha());

		Usuario usuario = new Usuario(login.email(), login.usuario().toUpperCase(), senhaEncriptada, Regras.ADMIN);

		usuarioRepository.save(usuario);

		return ResponseEntity.ok().build();

	}

}
