package com.exemplo3.demo;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


import java.util.Optional;
import java.util.Scanner;

@SpringBootApplication
@EntityScan(basePackages = "com.exemplo3.demo")
public class DemoApplication implements CommandLineRunner {

	private final AlunoRepository alunoRepository;
	private Iterable<Aluno> alunos;

	public DemoApplication(AlunoRepository alunoRepository) {
		this.alunoRepository = alunoRepository;
	}

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		int entry = -1;

		while (entry != 0) {
			Scanner scanner = new Scanner(System.in);
			printWelcomeMessage();
			entry = scanner.nextInt();

			if (entry != 0) {
				handleAction(entry);
				askForAnotherAction();
				alunos = alunoRepository.findAll();
			}
		}
	}
	private void handleAction(int entry) {
		switch (entry) {
			case 1:
				try {
					System.out.println("Digite a matrícula do aluno");
					Long matricula = new Scanner(System.in).nextLong();
					Optional<Aluno> alunoExistente = alunoRepository.findById(matricula);

					if (alunoExistente.isPresent()) {
						Aluno aluno = alunoExistente.get();
						System.out.println("Digite o nome do aluno (pressione Enter se não quiser alterar):");
						String nome = new Scanner(System.in).nextLine();
						if (!nome.isEmpty()) {
							aluno.setNome(nome);
						}
						System.out.println("Digite a nota do aluno:");
						int nota = new Scanner(System.in).nextInt();
						aluno.setNota(nota);
						alunoRepository.save(aluno);
						System.out.println("Aluno atualizado com sucesso!");
					} else {
						System.out.println("Digite o nome do aluno:");
						String nome = new Scanner(System.in).nextLine();
						System.out.println("Digite a nota do aluno:");
						int nota = new Scanner(System.in).nextInt();
						Aluno novoAluno = new Aluno(matricula, nome, nota);
						alunoRepository.save(novoAluno);
						System.out.println("Novo aluno cadastrado com sucesso!");
					}
				} catch (Exception e) {
					System.out.println("Erro ao processar ação: Tente novamente uma matricula válida " + e.getMessage());
				}
				break;
			case 2:
				try {
					System.out.println("Lista de alunos cadastrados:");
					for (Aluno aluno : alunos) {
						System.out.println("Matrícula: " + aluno.getMatricula() +
								", Nome: " + aluno.getNome() +
								", Nota: " + aluno.getNota());
					}
				} catch (Exception e) {
					System.out.println("Erro ao listar alunos: " + e.getMessage());
				}
				break;
			case 3:
				System.out.println("Calculando a média da turma...");

				int totalNotas = 0;
				int totalAlunos = 0;

				for (Aluno aluno : alunos) {
					totalNotas += aluno.getNota();
					totalAlunos++;
				}

				if (totalAlunos > 0) {
					int average = totalNotas / totalAlunos;
					System.out.println("Média da turma: " + average);
				} else {
					System.out.println("Não há alunos cadastrados para calcular a média.");
				}
				break;
			case 4:
				System.out.println("Mostrando todos os alunos aprovados:");

				for (Aluno aluno : alunos) {
					if (aluno.getNota() > 7) {
						System.out.println("Matrícula: " + aluno.getMatricula() +
								", Nome: " + aluno.getNome() +
								", Nota: " + aluno.getNota());
					}
				}
				break;
			case 5:
				System.out.println("Mostrando todos os alunos reprovados:");

				for (Aluno aluno : alunos) {
					if (aluno.getNota() < 7) {
						System.out.println("Matrícula: " + aluno.getMatricula() +
								", Nome: " + aluno.getNome() +
								", Nota: " + aluno.getNota());
					}
				}
				break;
			default:
				System.out.println("Opção inválida. Tente novamente.");
		}

	}
	private void askForAnotherAction() {
		System.out.println("Deseja realizar outra ação? (S/N)");
		String resposta = new Scanner(System.in).nextLine();

		if (resposta.equalsIgnoreCase("N") || resposta.equalsIgnoreCase("n")) {
			System.out.println("Obrigado por usar o programa!");
			System.exit(0);
		}
	}
	private void printWelcomeMessage() {
		System.out.println("\nBem vindo ao programa de cadastro de notas de alunos\n\n" +
				"Digite 0 para sair\n" +
				"Digite 1 para adicionar uma nota\n" +
				"Digite 2 para listar as notas\n" +
				"Digite 3 para calcular a média da turma\n" +
				"Digite 4 para mostrar todos os alunos aprovados\n" +
				"Digite 5 para mostrar todos os alunos reprovados\n");
	}
}


