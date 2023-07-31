package com.example.app.configuracao;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import com.example.app.model.entities.Candidato;
import com.example.app.model.entities.Vaga;
import com.example.app.repositories.CandidatoRepository;
import com.example.app.repositories.CandidatoVagaRepository;
import com.example.app.repositories.RecrutadorRepository;
import com.example.app.repositories.VagaRepository;

@Configuration
public class Config implements CommandLineRunner{
	
	@Autowired
	private CandidatoRepository cp;
	
	@Autowired
	private RecrutadorRepository rr;
	
	@Autowired
	private VagaRepository vr;
	
	@Autowired
	private CandidatoVagaRepository cvr;
	
	@Override
	public void run(String... args) throws Exception {
		
		Candidato c1 = new Candidato(null, "Mavie", " 32 9 9867- 4657", "mavie@gmail.com", false, "img", "semestre", "curso", "Terminio", "Anhanguera");
		Candidato c2 = new Candidato(null, "Felipe", " 45 9 9097- 4755", "fevie@gmail.com", false, "img", "semestre", "curso", "Terminio", "Unip");
		
		cp.saveAll(Arrays.asList(c1,c2));
		
		Vaga v1 = new Vaga(null, "Programador", "Desenvolver programas na linguagem C#", "Conhecimento em C# e Banco de dados", "Bahia", "Home office");
		
		vr.save(v1);
		
		c1.addVaga(v1);
	}

}
