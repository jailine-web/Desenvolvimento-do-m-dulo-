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
import com.example.app.DTO.UsuarioTokenResponseDTO;
import com.example.app.model.entities.Login;
import com.example.app.repositories.UsuarioRepository;
import com.example.app.security.TokenService;
import com.example.app.utils.Regras;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/hisig10/auth")
public class LoginAutenticacaoController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private TokenService tokenService;

	//Passa usuário e senha no corpo da requisição para validação do usuário
	@PostMapping(value = "/logar")
	public ResponseEntity login(@RequestBody @Valid DadosLoginDTO data) {

		var usernamePassword = new UsernamePasswordAuthenticationToken(data.usuario().toUpperCase(), data.senha());
		var auth = authenticationManager.authenticate(usernamePassword);
		System.out.println(auth);

		var token = tokenService.gerarToken((Login) auth.getPrincipal());
		return ResponseEntity.ok(new UsuarioTokenResponseDTO(token));
	}

	//Passa o email, usuario e senha no corpo da requisição
	@CrossOrigin()
	@PostMapping(value = "/cadastro")
	@Transactional
	public ResponseEntity<Login> cadastro(@RequestBody @Valid CadastroLoginDTO login) {

		if (usuarioRepository.findByUsuario(login.usuario()) != null)
			return ResponseEntity.badRequest().build();

		String senhaEncriptada = new BCryptPasswordEncoder().encode(login.senha());

		Login usuario = new Login(login.email(), login.usuario().toUpperCase(), senhaEncriptada, login.regras());

		usuarioRepository.save(usuario);

		return ResponseEntity.ok().build();

	}

}
