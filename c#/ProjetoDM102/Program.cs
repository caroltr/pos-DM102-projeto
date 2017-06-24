using System;
using System.Collections.Generic;
using System.Globalization;
using System.Linq;

namespace ProjetoDM102
{
    class MainClass
    {
        private static List<Cliente> clientes = null;

        public static void Main(string[] args)
        {
			int opcaoInicial;
			do
			{
				Console.WriteLine("--------------------------------------");
				Console.WriteLine("Digite a opção:\n");

				Console.WriteLine("1 - Cadastrar cliente pessoa física");
				Console.WriteLine("2 - Cadastrar cliente pessoa jurídica");
				Console.WriteLine("3 - Listar clientes cadastrados");
				Console.WriteLine("4 - Cadastrar atendimento");
				Console.WriteLine("5 - Listar atendimentos por cliente");

				Console.WriteLine("0 - Sair");

                Boolean parseResult = false;
				do
                {
					String opacaoInicialStr = Console.ReadLine();
                    parseResult = Int32.TryParse(opacaoInicialStr, out opcaoInicial);

                    if(!parseResult) {
                        Console.WriteLine("O valor informado não é um inteiro válido!");
                    }

                } while (!parseResult);

				switch (opcaoInicial)
				{
					case 0:
						break;

					case 1:

						CadastrarPessoaFisica();

						break;

					case 2:

						CadastrarPessoaJuridica();

						break;

					case 3:

						ListarClientes();

						break;

					case 4:

						CadastrarAtendimento();

						break;

					case 5:

						ListarAtendimentosPorCliente();

						break;

					default:
						break;

				}

			} while (opcaoInicial != 0);
        }

        private static void CadastrarPessoaFisica()
        {

            // Dados Cliente
            string nome;
            do
            {
                Console.WriteLine("Digite o nome:");
                nome = Console.ReadLine();
            } while (nome.Equals(""));

            string telefone;
            do
            {
                Console.WriteLine("Digite o telefone:");
                telefone = Console.ReadLine();
            } while (telefone.Equals(""));

            string endereco;
            do
            {
                Console.WriteLine("Digite o endereco:");
                endereco = Console.ReadLine();
            } while (endereco.Equals(""));

            // Dados pessoa física
            string cpf;
            do {
	            Console.WriteLine("Digite o CPF:");
	            cpf = Console.ReadLine();
            } while (cpf.Equals(""));

            string identidade;
            do {
			    Console.WriteLine("Digite a identidade:");
			    identidade = Console.ReadLine();
            } while (identidade.Equals(""));

            string tipoIdentidade;
            do
			{
				Console.WriteLine("Digite o tipo da identidade:");
				tipoIdentidade = Console.ReadLine();
             } while (tipoIdentidade.Equals(""));

            PessoaFisica pf = new PessoaFisica(nome, endereco, telefone, cpf, identidade, tipoIdentidade);

            if(clientes == null)
            {
                clientes = new List<Cliente>();
            }

            clientes.Add(pf);

            Console.WriteLine("Cliente pessoa física cadastrado com sucesso.");
        }

		private static void CadastrarPessoaJuridica()
		{
			// Dados Cliente
			string nome;
			do
			{
				Console.WriteLine("Digite o nome:");
				nome = Console.ReadLine();
			} while (nome.Equals(""));

			string telefone;
			do
			{
				Console.WriteLine("Digite o telefone:");
				telefone = Console.ReadLine();
			} while (telefone.Equals(""));

			string endereco;
			do
			{
				Console.WriteLine("Digite o endereco:");
				endereco = Console.ReadLine();
			} while (endereco.Equals(""));

            // Dados pessoa jurídica
            string cnpj;
            do
            {
                Console.WriteLine("Digite o CNPJ:");
                cnpj = Console.ReadLine();
            } while (cnpj.Equals(""));

			string razaoSocial;
            do
            {
                Console.WriteLine("Digite a razão social:");
                razaoSocial = Console.ReadLine();
            } while (razaoSocial.Equals(""));

            string inscricaoEstadual;
            do
            {
                Console.WriteLine("Digite a inscrição estadual:");
                inscricaoEstadual = Console.ReadLine();
            } while (inscricaoEstadual.Equals(""));

			PessoaJuridica pf = new PessoaJuridica(nome, endereco, telefone, cnpj, razaoSocial, inscricaoEstadual);

			if (clientes == null)
			{
				clientes = new List<Cliente>();
			}

			clientes.Add(pf);

            Console.WriteLine("Cliente pessoa jurídica cadastrado com sucesso.");
		}

        private static void ListarClientes()
        {
            foreach(Cliente c in clientes)
            {
                Console.WriteLine(c);
            }
        }

        private static Atendimento CadastrarAtendimento()
		{
            Cliente cliente = null;
            do
            {
				// Dados Cliente

				string cpfCnpj;
                do
                {
                    Console.WriteLine("Digite o CPF/CNPJ do cliente:");
                    cpfCnpj = Console.ReadLine();
                } while (cpfCnpj == null);

                if (clientes != null && clientes.Count >= 0)
                {
                    foreach (Cliente c in clientes)
                    {
                        if (c is PessoaFisica)
                        {
                            PessoaFisica pessoaF = c as PessoaFisica;
                            pessoaF.cpf.Equals(cpfCnpj);

                            cliente = pessoaF;

                            break;

                        }
                        else if (c is PessoaJuridica)
                        {
                            PessoaJuridica pessoaJ = c as PessoaJuridica;
                            pessoaJ.cnpj.Equals(cpfCnpj);

                            cliente = pessoaJ;

                            break;
                        }
                    }
                }

                if(cliente == null)
                {
                    Console.WriteLine("Não foi encontrado cliente cadastrado com o CPF/CNPJ cadastrado");
                    return null;
                }

            } while (cliente == null);

            string data;
            DateTime dDate;
			do
			{
                do
                {
                    Console.WriteLine("Digite a data no formato dd/mm/aaaa:");
                    data = Console.ReadLine();
                } while (data == null);

				if (!DateTime.TryParse(data, out dDate))
				{
					Console.WriteLine("A data informada nao está no formato correto ou não é válida.");
                    data = null;
				}
			} while (data == null);

			Console.WriteLine("Digite a descrição:");
			String descricao = Console.ReadLine();

            Atendimento atendimento = new Atendimento(cliente, dDate, descricao);
            Console.WriteLine("Atendimento cadastrado com sucesso.");

            return atendimento;
		}

        private static void ListarAtendimentosPorCliente() {
            
			Cliente cliente = null;
			do
			{
				// Dados Cliente
				Console.WriteLine("Digite o CPF/CNPJ do cliente:");
				String cpfCnpj = Console.ReadLine();

				if (clientes != null && clientes.Count >= 0)
				{
					foreach (Cliente c in clientes)
					{
						if (c is PessoaFisica)
						{
							PessoaFisica pessoaF = c as PessoaFisica;
							pessoaF.cpf.Equals(cpfCnpj);

							cliente = pessoaF;

							break;

						}
						else if (c is PessoaJuridica)
						{
							PessoaJuridica pessoaJ = c as PessoaJuridica;
							pessoaJ.cnpj.Equals(cpfCnpj);

							cliente = pessoaJ;

							break;
						}
					}
				}

				if (cliente == null)
				{
					Console.WriteLine("Não foi encontrado cliente cadastrado com o CPF/CNPJ cadastrado");
					return;
				}

			} while (cliente == null);

            List<Atendimento> atendimentos = cliente.ListarAtendimentos();
            if (atendimentos.Count > 0)
            {
                foreach(Atendimento a in atendimentos) {
                    Console.WriteLine(a);
                }
            } else {
                Console.WriteLine("Não há atendimentos cadastrados para este cliente.");
            }
        }
    }
}
