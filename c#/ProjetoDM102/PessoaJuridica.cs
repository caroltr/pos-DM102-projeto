using System;
namespace ProjetoDM102
{
    public class PessoaJuridica : Cliente
    {
        public String cnpj { get; }
		private String razaoSocial;
		private String inscricaoEstadual;

        public PessoaJuridica(string nome,string endereco, string telefone,
                              string cnpj, string razaoSocial, string inscricaoEstadual) : base (nome, endereco, telefone)
        {
            this.cnpj = cnpj;
            this.razaoSocial = razaoSocial;
            this.inscricaoEstadual = inscricaoEstadual;
        }

		public override string ToString()
		{
			return "[Pessoa Jurídica] " + base.ToString() + " CNPJ: " + cnpj + " Razão Social: " + razaoSocial + " Inscrição estadual: " + inscricaoEstadual;
		}
    }
}
