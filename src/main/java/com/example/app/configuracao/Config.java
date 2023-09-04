package com.example.app.configuracao;

import java.time.LocalDateTime;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import com.example.app.model.entities.Candidato;
import com.example.app.model.entities.Candidatura;
import com.example.app.model.entities.Recrutador;
import com.example.app.model.entities.Vaga;
import com.example.app.repositories.CandidatoRepository;
import com.example.app.repositories.CandidaturaRepository;
import com.example.app.repositories.EntrevistaRepository;
import com.example.app.repositories.LembreteRepository;
import com.example.app.repositories.RecrutadorRepository;
import com.example.app.repositories.VagaRepository;
import com.example.app.utils.EstadoInscricao;
import com.example.app.utils.StatusCurriculoAvaliado;

@Configuration
//@Profile("test")
public class Config implements CommandLineRunner {

	@Autowired
	private CandidatoRepository cr;

	@Autowired
	private VagaRepository vr;

	@Autowired
	private CandidaturaRepository cd;

	@Autowired
	private RecrutadorRepository rr;

	@Autowired
	private EntrevistaRepository er;

	@Autowired
	private LembreteRepository lr;
	

	LocalDateTime teste = LocalDateTime.parse("2023-08-30T14:38:26");

	@Override
	public void run(String... args) throws Exception {

		Candidato c1 = new Candidato(null, "Paulo", "32 9 9867-4657", "paulo@gmail.com", false, "semestre", "curso",
				"Terminio", "Anhanguera", "São Paulo");
		c1.setCurriculoAvaliado(StatusCurriculoAvaliado.AVALIADO);
		Candidato c2 = new Candidato(null, "Felipe", "45 9 9097-4755", "felipe@gmail.com", false, "semestre", "curso",
				"Terminio", "Unip", "Bahia");
		c2.setCurriculoAvaliado(StatusCurriculoAvaliado.NAO_AVALIADO);
		Candidato c3 = new Candidato(null, "Felix", "47999999999", "felix@gmail.com", false, "semestre", "curso",
				"meio", "Estacio", "SC");
		c3.setCurriculoAvaliado(StatusCurriculoAvaliado.AVALIADO);

		cr.saveAll(Arrays.asList(c1, c2, c3));

		Vaga v1 = new Vaga(null, "Programador", "Desenvolver programas na linguagem C#",
				"Conhecimento em C# e Banco de dados", "Bahia", "Home office", "TI", teste);
		Vaga v2 = new Vaga(null, "Desenvolvedor Jr", "Desenvolver aplicativos web",
				"Conhecimento em java e Spring boot", "São Paulo", "Home office", "TI", teste);

		vr.saveAll(Arrays.asList(v1, v2));

		Recrutador r1 = new Recrutador(null, "Júlio Ramos", "71 9 9999-0909", "julio@gmail.com", "julio", "12345", true,
				"DevOps");

		rr.save(r1);

		c1.addVaga(v1);

		Candidatura cd1 = new Candidatura(null, v1, c1, EstadoInscricao.SELECIONADO, LocalDateTime.now());
		Candidatura cd2 = new Candidatura(null, v2, c1, EstadoInscricao.AGUARDANDO_ENTREVISTA,LocalDateTime.now());
		Candidatura cd4 = new Candidatura(null, v1, c3, EstadoInscricao.TALVEZ, LocalDateTime.now());

		cd.saveAll(Arrays.asList(cd1,cd2, cd4));


		
	}
}
