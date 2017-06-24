using System;
using System.Collections.Generic;

namespace ProjetoDM102
{
    public class Cliente
	{
		private string nome;
		private string endereco;
		private string telefone;

        private List<Atendimento> atendimentos = new List<Atendimento>();

        public Cliente(string nome, string endereco, string telefone)
        {
			this.nome = nome;
			this.endereco = endereco;
			this.telefone = telefone;
        }

		public void InserirAtendimento(Atendimento atendimento)
		{
			atendimentos.Add(atendimento);
		}

		public List<Atendimento> ListarAtendimentos()
		{
            return atendimentos;
		}

		public override string ToString()
		{
			return "Nome: " + nome + ", Endereco: " + endereco + ", Telefone: " + telefone;
		}
    }
}
