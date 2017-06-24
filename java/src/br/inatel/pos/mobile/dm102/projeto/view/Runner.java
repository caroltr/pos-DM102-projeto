package br.inatel.pos.mobile.dm102.projeto.view;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

import br.inatel.pos.mobile.dm102.projeto.model.Atendimento;
import br.inatel.pos.mobile.dm102.projeto.model.AtendimentoDAO;
import br.inatel.pos.mobile.dm102.projeto.model.Cliente;
import br.inatel.pos.mobile.dm102.projeto.model.PessoaFisica;
import br.inatel.pos.mobile.dm102.projeto.model.PessoaFisicaDAO;
import br.inatel.pos.mobile.dm102.projeto.model.PessoaJuridica;
import br.inatel.pos.mobile.dm102.projeto.model.PessoaJuridicaDAO;

public class Runner {

	public static void main(String[] args) {
		
		try (Scanner scanner = new Scanner(System.in)) {
			int opcaoInicial;
			do {
				System.out.println("--------------------------------------");
				System.out.println("Digite a opção:\n");

				System.out.println("1 - Cadastrar cliente pessoa física");
				System.out.println("2 - Cadastrar cliente pessoa jurídica");
				System.out.println("3 - Listar clientes cadastrados");
				System.out.println("4 - Cadastrar atendimento");
				System.out.println("5 - Listar todos os atendimentos");
				System.out.println("6 - Listar atendimentos por cliente");
				
				System.out.println("0 - Sair");
				
				opcaoInicial = lerOpcoesInteiro(scanner, 6);
				
				switch(opcaoInicial) {
				case 0:
					break;
					
				case 1:
					
					cadastrarPessoaFisica(scanner);
					
					break;
					
				case 2:
					
					cadastrarPessoaJuridica(scanner);
					
					break;
					
				case 3:
					
					listarClientes();
					
					break;
					
				case 4:
					
					cadastrarAtendimento(scanner);
					
					break;
					
				case 5:
					
					listarAtendimentos();
					
					break;
					
				case 6:
					
					listarAtendimentosPorCliente(scanner);
					
					break;
					
				default:
					break;
				
				}
				
			} while(opcaoInicial != 0);
			
		} catch (InputMismatchException e) {
			System.err.println("Algo errado aconteceu!");
		}
	}
	
	/**
	 * Realiza o cadastro de um cliente que é pessoa física no banco de dados
	 * @param scanner
	 */
	private static void cadastrarPessoaFisica(Scanner scanner) {
		System.out.println("---------- CADASTRAR CLIENTE PESSOA FÍSICA ----------");
		
		PessoaFisica pessoaFisica = lerDadosPessoaFisica(scanner);
		PessoaFisicaDAO pfDao = new PessoaFisicaDAO();
		pfDao.criar(pessoaFisica); // TODO tratar erro aqui?
	}
	
	/**
	 * Realiza o cadastro de um cliente que é pessoa jurídica no banco de dados
	 * @param scanner
	 */
	private static void cadastrarPessoaJuridica(Scanner scanner) {
		System.out.println("---------- CADASTRAR CLIENTE PESSOA JURÍDICA ----------");
		
		PessoaJuridica pessoaJuridica = lerDadosPessoaJuridica(scanner);
		PessoaJuridicaDAO pjDao = new PessoaJuridicaDAO();
		pjDao.criar(pessoaJuridica); // TODO tratar erro aqui?
	}
	
	/**
	 * Exibe todos os clientes cadastrados no banco de dados
	 */
	private static void listarClientes() {
		System.out.println("---------- LISTAR CLIENTES ----------");
		
		PessoaFisicaDAO pfDao = new PessoaFisicaDAO();
		ArrayList<PessoaFisica> clientesPessoaFisica = pfDao.listar();
		
		PessoaJuridicaDAO pjDao = new PessoaJuridicaDAO();
		ArrayList<PessoaJuridica> clientesPessoaJuridica = pjDao.listar();
		
		if(clientesPessoaFisica.isEmpty() && clientesPessoaJuridica.isEmpty()) {
			System.out.println("Nenhum cliente cadastrado.");
		} else {
		
			for(PessoaFisica pf : clientesPessoaFisica) {
				System.out.println(pf);
			}
			
			for(PessoaJuridica pj : clientesPessoaJuridica) {
				System.out.println(pj);
			}
		}
	}
	
	/**
	 * Realiza o cadastro de um atendimento no banco de dados
	 * @param scanner
	 */
	private static void cadastrarAtendimento(Scanner scanner) {
		System.out.println("---------- CADASTRAR ATENDIMENTO ----------");
		
		Atendimento atendimento = lerDadosAtendimento(scanner);
		if(atendimento != null) {
			AtendimentoDAO atDao = new AtendimentoDAO();
			atDao.criar(atendimento);
		}
	}
	
	/**
	 * Exibe todos os atendimentos cadastrados no banco de dados
	 */
	private static void listarAtendimentos() {
		System.out.println("---------- LISTAR ATENDIMENTOS ----------");
		
		AtendimentoDAO atDao = new AtendimentoDAO();
		ArrayList<Atendimento> atendimentos = atDao.listar();
		
		if(atendimentos.isEmpty()) {
			System.out.println("Nenhum atendimeneto cadastrado.");
		} else {
			for(Atendimento a : atendimentos) {
				System.out.println(a);
			}
		}
	}
	
	/**
	 * Exibe todos os atendimentos realizados por um determinado cliente
	 * @param scanner
	 */
	private static void listarAtendimentosPorCliente(Scanner scanner) {
		System.out.println("---------- LISTAR ATENDIMENTOS POR CLIENTE ----------");
		
		Cliente cliente = lerDadosBuscarCliente(scanner);
		
		if(cliente != null) {
			AtendimentoDAO atDao = new AtendimentoDAO();
			ArrayList<Atendimento> atendimentos = atDao.listar(cliente);
			
			if(atendimentos.isEmpty()) {
				System.out.println("Nenhum atendimeneto cadastrado para este cliente.");
			} else {
				for(Atendimento a : atendimentos) {
					System.out.println(a);
				}
			}
		}
	}
	
	/**
	 * Realiza a leitura de um valor inteiro, inserido pelo usuário, realizando todas as validações necessárias.
	 * 
	 * @param scanner
	 * @param quantidadeOpcoes quantidade de opções que o usuário possui, ou 0, caso ele possa entrar com qualquer número.
	 * @return um inteiro representando a entrada válida do usuário.
	 */
	private static int lerOpcoesInteiro(Scanner scanner, int quantidadeOpcoes) {
		Integer opcaoInicial = null;
		do {
			try {
				opcaoInicial = scanner.nextInt();
				if((opcaoInicial < 0) || (opcaoInicial > quantidadeOpcoes)) {
					System.err.println("Opção inválida.");
					opcaoInicial = null;
				}
				
			} catch (InputMismatchException e) {
				System.err.println("O valor informado não é um inteiro válido!");
				scanner.nextLine();
			}
		} while (opcaoInicial == null);
		
		return opcaoInicial;
	}
	

	/**
	 * Realiza a leitura de uma data inserida pelo usuário no formato dd/MM/yyyy
	 * 
	 * @param scanner
	 * @return um objeto Date contendo 
	 */
	private static Date lerData(Scanner scanner) {
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");

		Date date = null;
		do {
			try {
				String line = scanner.next() + scanner.nextLine();
				java.util.Date dataUtil = format.parse(line);
				java.util.Date agora = new java.util.Date();
				
				if(dataUtil.after(agora)) {
					System.err.println("Não é possível cadastrar um atendimento para o futuro.");
				} else {
					date = new Date(dataUtil.getTime());
				}
				
			} catch (ParseException e) {
				System.out.println("A data informada nao está no formato correto.");
			}
		} while (date == null);
		
		return date;
	}
	
	/**
	 * Realiza a leitura dos dados inseridos pelo usuário, necessários para o cadastro de um cliente que é pessoa física
	 * 
	 * @param scanner
	 * @return um objeto PessoaFisica
	 */
	private static PessoaFisica lerDadosPessoaFisica(Scanner scanner) {
		
		Cliente cliente = lerDadosCliente(scanner);
		
		String cpf = null;
		String identidade = null;
		String tipoIdentidade = null;
		
		do {
			System.out.println("Digite o CPF:");
			cpf = scanner.next() + scanner.nextLine();
			
			PessoaFisicaDAO pfDao = new PessoaFisicaDAO();
			PessoaFisica pessoaFisica = pfDao.buscarPessoaFisica(cpf);
			if(pessoaFisica != null) {
				cpf = null;
				System.out.println("CPF já cadastrado.");
			}
			
		} while(cpf == null);
		
		System.out.println("Digite a identidade:");
		identidade = scanner.next() + scanner.nextLine();

		System.out.println("Digite o tipo da identidade:");
		tipoIdentidade = scanner.next() + scanner.nextLine();
		
		return new PessoaFisica(cliente, cpf, identidade, tipoIdentidade);
	}
	
	/**
	 * Realiza a leitura dos dados inseridos pelo usuário, necessários para o cadastro de um cliente que é pessoa jurídica
	 * 
	 * @param scanner
	 * @return um objeto PessoaJuridica
	 */
	private static PessoaJuridica lerDadosPessoaJuridica(Scanner scanner) {
		
		Cliente cliente = lerDadosCliente(scanner);
		
		String cnpj = null;
		String razaoSocial = null;
		String inscricaoEstadual = null;
		
		do {
			System.out.println("Digite o CNPJ:");
			cnpj = scanner.next() + scanner.nextLine();
			
			PessoaJuridicaDAO pjDao = new PessoaJuridicaDAO();
			PessoaJuridica pessoaJuridica = pjDao.buscarPessoaJuridica(cnpj);
			if(pessoaJuridica != null) {
				cnpj = null;
				System.out.println("CNPJ já cadastrado.");
			}
			
		} while(cnpj == null);
		
		
		System.out.println("Digite a razão social:");
		razaoSocial = scanner.next() + scanner.nextLine();
		

		System.out.println("Digite a inscrição estadual:");
		inscricaoEstadual = scanner.next() + scanner.nextLine();
			
		return new PessoaJuridica(cliente, cnpj, razaoSocial, inscricaoEstadual);
	}
	
	/**
	 * Realiza a leitura dos dados inseridos pelo usuário, necessários para o cadastro de todo cliente (pessoa física ou jurídica)
	 * 
	 * @param scanner
	 * @return um objeto Cliente
	 */
	private static Cliente lerDadosCliente(Scanner scanner) {
		
		String nome = null;
		String endereco = null;
		String telefone = null;

		System.out.println("Digite o nome:");
		nome = scanner.next() + scanner.nextLine();
	
		System.out.println("Digite o endereco:");
		endereco = scanner.next() + scanner.nextLine();
		
		System.out.println("Digite o telefone:");
		telefone = scanner.next() + scanner.nextLine();
			
		return new Cliente(nome, endereco, telefone);
	}
	
	/**
	 * Realiza a leitura dos dados inseridos pelo usuário, necessários para o cadastro de um atendimento.
	 * 
	 * @param scanner
	 * @return um objeto Atendimento
	 */
	private static Atendimento lerDadosAtendimento(Scanner scanner) {
		
		Atendimento atendimento = null;
		Cliente cliente = lerDadosBuscarCliente(scanner);

		if(cliente != null) {
			System.out.println("Digite a data no formato dd/mm/aaaa");
			Date data = lerData(scanner);
			
			System.out.println("Digite a descrição:");
			String descricao = scanner.next() + scanner.nextLine();
			
			atendimento = new Atendimento(data, descricao, cliente);
		}
		
		return atendimento;
	}
	
	/**
	 * Realiza a leitura dos dados inseridos pelo usuário, relativo a selecao de um cliente
	 * cadastrado no banco de dados
	 * 
	 * @param scanner
	 * @return um objeto Cliente, representando o cliente selecionado
	 */
	private static Cliente lerDadosBuscarCliente(Scanner scanner) {
		
		Cliente cliente = null;
		int tipoCliente;
		do {
			System.out.println("1 - Cliente é pessoa física");
			System.out.println("2 - Cliente é pessoa jurídica");
			System.out.println("0 - Voltar");
			
			tipoCliente = lerOpcoesInteiro(scanner, 2);
			if (tipoCliente == 1) {
				System.out.println("Digite o CPF do cliente que solicitou o atendimento:");
				String cpf = scanner.next() + scanner.nextLine();
				
				PessoaFisicaDAO pfDao = new PessoaFisicaDAO();
				PessoaFisica pessoaFisica = pfDao.buscarPessoaFisica(cpf);
				if(pessoaFisica == null) {
					cpf = null;
					System.out.println("Não existe cliente cadastrado com o CPF especificado.");
				} else {
					return pessoaFisica;
				}
			} else if(tipoCliente == 2) {
				System.out.println("Digite o CNPJ do cliente que solicitou o atendimento:");
				String cnpj = scanner.next() + scanner.nextLine();
				
				PessoaJuridicaDAO pjDao = new PessoaJuridicaDAO();
				PessoaJuridica pessoaJuridica = pjDao.buscarPessoaJuridica(cnpj);
				if(pessoaJuridica == null) {
					cnpj = null;
					System.out.println("Não existe cliente cadastrado com o CNPJ especificado.");
				} else {
					return pessoaJuridica;
				}
			}
		} while (cliente == null && tipoCliente != 0);
		
		return cliente;
	}
}
