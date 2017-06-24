using System;
namespace ProjetoDM102
{
    public class Atendimento
    {
        private DateTime data;
		private String descricao;
		private Cliente cliente;

        public Atendimento(Cliente cliente, DateTime data, string descricao)
        {
			this.data = data;
			this.descricao = descricao;
			this.cliente = cliente;

			cliente.InserirAtendimento(this);
        }

		public override string ToString()
		{
			return "[Atendimento] " + " Data: " + data + ", Descrição: " + descricao + ", Cliente: [" + cliente + "]";
        }
    }
}
