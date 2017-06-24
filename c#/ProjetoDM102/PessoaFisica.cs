using System;
namespace ProjetoDM102
{
    public class PessoaFisica : Cliente
    {
        public String cpf { get; }
		private String identidade;
		private String tipoIdentidade;

        public PessoaFisica(string nome,string endereco, string telefone,
                            string cpf, string identidade, string tipoIdentidade) : base ( nome,  endereco,  telefone)
        {
            this.cpf = cpf;
            this.identidade = identidade;
            this.tipoIdentidade = tipoIdentidade;
        }

		public override string ToString()
		{
		    return "[Pessoa Física] " + base.ToString() + " CPF: " + cpf + ", Identidade: " + identidade + ", Tipo de identidade: " + tipoIdentidade;
		}
    }
}
