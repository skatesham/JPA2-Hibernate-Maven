package br.sham.maven.web.financas;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import br.sham.maven.web.financas.model.Categoria;
import br.sham.maven.web.financas.model.Conta;
import br.sham.maven.web.financas.model.ContaComNumeroEAgencia;
import br.sham.maven.web.financas.model.Movimentacao;
import br.sham.maven.web.financas.model.TipoMovimentacao;
import br.sham.maven.web.financas.model.Usuario;
import br.sham.maven.web.financas.util.JPAUtil;
import lombok.Cleanup;

/**
 * Classe de Testes do projeto
 *
 */
public class App {
	public static void main(String[] args) {

		System.out.println("Bem Vindo ao JPA2 com Hibernate!");

		// Popular e Criar Banco de Dados
		// populaCategoria();
		// populaConta();
		// populaCliente();
		// populaMovimentacao();

		testeJPQL();

	}

	public static void testeJPQL() {

		@Cleanup
		EntityManager em = JPAUtil.getEntityManager();

		em.getTransaction().begin();

		Conta conta = new Conta();
		conta.setId(2);

		/*
		 * SELECT EM UMA UNICA TABELA //String jpql =
		 * "select m from Movimentacao m where m.conta.id = 1"; //String jpql =
		 * "select m from Movimentacao m where m.conta = conta"; String jpql =
		 * "select m from Movimentacao m where m.conta = :pConta" +
		 * " and m.tipoMovimentacao = :pTipo" + " order by m.valor desc"; //String jpql
		 * = "select m from Movimentacao m where m.conta = :pConta" +
		 * " order by m.valor desc"; Query query = em.createQuery(jpql);
		 * query.setParameter("pConta", conta); query.setParameter("pTipo",
		 * TipoMovimentacao.SAIDA);
		 */

		// SELECT EM MUTIPLAS TABELAS
		/*
		 * Using JOIN Categoria categoria = new Categoria(); categoria.setId(1);
		 * 
		 * String jpql =
		 * "select m from Movimentacao m join m.categorias c where c = :pCategoria";
		 * 
		 * Query query = em.createQuery(jpql); query.setParameter("pCategoria",
		 * categoria);
		 * 
		 * List<Movimentacao> resultados = query.getResultList(); //resultados.forEach(r
		 * -> r.print()); for(Movimentacao m : resultados) { m.print(); }
		 */

		/*
		 * Data File Transfer
		 * 
		 * List<ContaComNumeroEAgencia> resultados = em
		 * .createQuery("select new br.sham.maven.web.financas.model.ContaComNumeroEAgencia(c.numero, c.agencia) from Conta c"
		 * , ContaComNumeroEAgencia.class) .getResultList();
		 * 
		 * // size System.out.println(resultados.size());
		 * 
		 * // resultados.forEach(r -> r.print()); for (ContaComNumeroEAgencia c :
		 * resultados) { System.out.println(c); }
		 */

		// EAGER Loading ( LAZY é Padrão)

		// String jpql = "select c from Conta c";
		// String jpql = "select c from Conta c join fetch c.movimentacoes";
		// String jpql = "select c from Conta c inner join c.movimentacoes";
		
		// EAGER
		Query query = em.createQuery("select distinct c from Conta c left join fetch c.movimentacoes");

		List<Conta> contas = query.getResultList();

		for (Conta conta1 : contas) {
			System.out.println("Titular: " + conta1.getNumero());
			System.out.println("Número de movimentações ...: " + conta1.getMovimentacoes().size());
		}

		em.getTransaction().commit();

		/*
		 * TESTE Bidirecional Movimentacao movimentacao = em.find(Movimentacao.class,
		 * 3); conta = movimentacao.getConta();
		 * 
		 * System.out.println(conta.getMovimentacoes().size());
		 * 
		 * 
		 */
	}

	public static void metodosMysql() {
		Conta conta = new Conta();
		// conta = new Conta("Sham Vinicius Fiorin", "9876.8520.1648.2315", "96482-5",
		// "8546");

		@Cleanup
		EntityManager em = JPAUtil.getEntityManager();

		// INSERT
		em.getTransaction().begin();
		em.persist(conta);
		em.getTransaction().rollback();
		// em.getTransaction().commit();

		// FIND
		em.getTransaction().begin();
		conta = em.find(Conta.class, 2);
		em.getTransaction().commit();
		conta.print();

		// UPDATE
		em.getTransaction().begin();
		conta = em.find(Conta.class, 1);
		conta.setNumero("9999.9999.9999.9999");
		em.getTransaction().commit();
		conta.print();

		// UPDATE
		// Detached to Managed
		em.getTransaction().begin();
		// conta = new Conta("Sham Vinicius Fiorin", "9876.8520.1648.2315", "96482-5",
		// "8546");
		conta.setId(1);
		conta.setAgencia("6666");
		em.merge(conta);
		em.getTransaction().commit();
		conta.print();

		// DELETE
		em.getTransaction().begin();
		conta = em.find(Conta.class, 2);
		em.remove(conta);
		em.getTransaction().commit();
	}

	public static void populaConta() {
		@Cleanup
		EntityManager manager = JPAUtil.getEntityManager();

		manager.getTransaction().begin();

		Conta conta1 = new Conta();
		Conta conta2 = new Conta();
		Conta conta3 = new Conta();
		Conta conta4 = new Conta();
		Conta conta5 = new Conta();

		conta1.setBanco("001 - BANCO DO BRASIL");
		conta1.setNumero("16987-8");
		conta1.setAgencia("6543");
		// conta1.setTitular("Maria dos Santos");

		conta2.setBanco("237 - BANCO BRADESCO");
		conta2.setNumero("86759-1");
		conta2.setAgencia("1745");
		// conta2.setTitular("Paulo Roberto Souza");

		conta3.setBanco("341 - BANCO ITAU UNIBANCO");
		conta3.setNumero("46346-3");
		conta3.setAgencia("4606");
		// conta3.setTitular("Antonio Duraes");

		conta4.setBanco("033 - BANCO SANTANDER");
		conta4.setNumero("12345-6");
		conta4.setAgencia("9876");
		// conta4.setTitular("Leandra Marques");

		conta5.setBanco("104 - CAIXA ECONOMICA FEDERAL");
		conta5.setNumero("98654-3");
		conta5.setAgencia("1234");
		// conta5.setTitular("Alexandre Duarte");

		// persistindo as contas
		manager.persist(conta1);
		manager.persist(conta2);
		manager.persist(conta3);
		manager.persist(conta4);
		manager.persist(conta5);

		manager.getTransaction().commit();
	}

	public static void populaMovimentacao() {
		EntityManager manager = JPAUtil.getEntityManager();

		manager.getTransaction().begin();

		Conta conta1 = manager.find(Conta.class, 1);
		Conta conta2 = manager.find(Conta.class, 2);
		Conta conta3 = manager.find(Conta.class, 3);
		Conta conta4 = manager.find(Conta.class, 4);
		Conta conta5 = manager.find(Conta.class, 5);

		Categoria categoria1 = manager.find(Categoria.class, 1);
		Categoria categoria2 = manager.find(Categoria.class, 2);

		List<Categoria> categorias = Arrays.asList(categoria1, categoria2);

		// Movimentacoes da conta1
		Movimentacao movimentacao1 = new Movimentacao();
		Movimentacao movimentacao2 = new Movimentacao();
		Movimentacao movimentacao3 = new Movimentacao();
		Movimentacao movimentacao4 = new Movimentacao();

		movimentacao1.setData(Calendar.getInstance());
		movimentacao1.setDescricao("Conta de luz - ABRIL/2012");
		movimentacao1.setValor(new BigDecimal("135"));
		movimentacao1.setTipoMovimentacao(TipoMovimentacao.SAIDA);
		movimentacao1.setConta(conta1);
		movimentacao1.setCategorias(categorias);

		manager.persist(movimentacao1);

		movimentacao2.setData(Calendar.getInstance());
		movimentacao2.setDescricao("Almoco no Restaurante - AGOSTO/2012");
		movimentacao2.setValor(new BigDecimal("175.80"));
		movimentacao2.setTipoMovimentacao(TipoMovimentacao.SAIDA);
		movimentacao2.setConta(conta1);
		movimentacao2.setCategorias(categorias);
		manager.persist(movimentacao2);

		movimentacao3.setData(Calendar.getInstance());
		movimentacao3.setDescricao("Aluguel - MAIO/2012");
		movimentacao3.setValor(new BigDecimal("680.00"));
		movimentacao3.setTipoMovimentacao(TipoMovimentacao.ENTRADA);
		movimentacao3.setConta(conta1);
		movimentacao3.setCategorias(categorias);
		manager.persist(movimentacao3);

		movimentacao4.setData(Calendar.getInstance());
		movimentacao4.setDescricao("Salario - FEVEREIRO/2012");
		movimentacao4.setValor(new BigDecimal("3830.68"));
		movimentacao4.setTipoMovimentacao(TipoMovimentacao.ENTRADA);
		movimentacao4.setConta(conta1);
		movimentacao4.setCategorias(categorias);
		manager.persist(movimentacao4);

		// Movimentacoes da conta2
		Movimentacao movimentacao5 = new Movimentacao();
		Movimentacao movimentacao6 = new Movimentacao();

		movimentacao5.setData(Calendar.getInstance());
		movimentacao5.setDescricao("Conta de telefone - SETEMBRO/2011");
		movimentacao5.setValor(new BigDecimal("168.27"));
		movimentacao5.setTipoMovimentacao(TipoMovimentacao.SAIDA);
		movimentacao5.setConta(conta2);
		movimentacao5.setCategorias(categorias);
		manager.persist(movimentacao5);

		movimentacao6.setData(Calendar.getInstance());
		movimentacao6.setDescricao("Aniversario - MAIO/2011");
		movimentacao6.setValor(new BigDecimal("200"));
		movimentacao6.setTipoMovimentacao(TipoMovimentacao.ENTRADA);
		movimentacao6.setConta(conta2);
		movimentacao6.setCategorias(categorias);

		manager.persist(movimentacao6);

		// Movimentacoes da conta3
		Movimentacao movimentacao7 = new Movimentacao();
		Movimentacao movimentacao8 = new Movimentacao();
		Movimentacao movimentacao9 = new Movimentacao();

		movimentacao7.setData(Calendar.getInstance());
		movimentacao7.setDescricao("Lanche - JULHO/2011");
		movimentacao7.setValor(new BigDecimal("28.50"));
		movimentacao7.setTipoMovimentacao(TipoMovimentacao.SAIDA);
		movimentacao7.setConta(conta3);
		movimentacao7.setCategorias(categorias);

		manager.persist(movimentacao7);

		movimentacao8.setData(Calendar.getInstance());
		movimentacao8.setDescricao("Presente - DEZEMBRO/2011");
		movimentacao8.setValor(new BigDecimal("49.99"));
		movimentacao8.setTipoMovimentacao(TipoMovimentacao.SAIDA);
		movimentacao8.setConta(conta3);
		movimentacao8.setCategorias(categorias);

		manager.persist(movimentacao8);

		movimentacao9.setData(Calendar.getInstance());
		movimentacao9.setDescricao("Bonus - JANEIRO/2012");
		movimentacao9.setValor(new BigDecimal("2000"));
		movimentacao9.setTipoMovimentacao(TipoMovimentacao.ENTRADA);
		movimentacao9.setConta(conta3);
		movimentacao9.setCategorias(categorias);

		manager.persist(movimentacao9);

		// Movimentacoes da conta4
		Movimentacao movimentacao10 = new Movimentacao();

		movimentacao10.setData(Calendar.getInstance());
		movimentacao10.setDescricao("Carnaval - MARCO/2012");
		movimentacao10.setValor(new BigDecimal("765.20"));
		movimentacao10.setTipoMovimentacao(TipoMovimentacao.SAIDA);
		movimentacao10.setConta(conta4);
		movimentacao10.setCategorias(categorias);

		manager.persist(movimentacao10);

		// Movimentacoes da conta5
		Movimentacao movimentacao11 = new Movimentacao();
		Movimentacao movimentacao12 = new Movimentacao();

		movimentacao11.setData(Calendar.getInstance());
		movimentacao11.setDescricao("Salario - ABRIL/2012");
		movimentacao11.setValor(new BigDecimal("2651.90"));
		movimentacao11.setTipoMovimentacao(TipoMovimentacao.ENTRADA);
		movimentacao11.setConta(conta5);
		movimentacao11.setCategorias(categorias);

		manager.persist(movimentacao11);

		movimentacao12.setData(Calendar.getInstance());
		movimentacao12.setDescricao("Bonus - JANEIRO/2012");
		movimentacao12.setValor(new BigDecimal("1000"));
		movimentacao12.setTipoMovimentacao(TipoMovimentacao.ENTRADA);
		movimentacao12.setConta(conta5);
		movimentacao12.setCategorias(categorias);

		manager.persist(movimentacao12);

		manager.getTransaction().commit();

		manager.close();
	}

	public static void populaCategoria() {
		Categoria categoria1 = new Categoria();
		Categoria categoria2 = new Categoria();

		categoria1.setNome("Viagem");
		categoria2.setNome("Negocios");

		@Cleanup
		EntityManager em = JPAUtil.getEntityManager();

		em.getTransaction().begin();
		em.persist(categoria1);
		em.getTransaction().commit();

		em.getTransaction().begin();
		em.persist(categoria2);
		em.getTransaction().commit();
	}

	public static void populaCliente() {

		EntityManager em = JPAUtil.getEntityManager();

		Usuario cliente1 = new Usuario();
		Usuario cliente2 = new Usuario();
		Usuario cliente3 = new Usuario();
		Usuario cliente4 = new Usuario();
		Usuario cliente5 = new Usuario();

		Conta conta1 = em.find(Conta.class, 1);
		Conta conta2 = em.find(Conta.class, 2);
		Conta conta3 = em.find(Conta.class, 3);
		Conta conta4 = em.find(Conta.class, 4);
		Conta conta5 = em.find(Conta.class, 5);

		String nome = "Sham ";
		String endereco = "Rua Minas Gerais 52";
		String profissao = "Desenvolvedor";

		cliente1.setConta(conta1);
		cliente1.setEndereco(endereco);
		cliente1.setNome(nome + "1");
		cliente1.setProfissao(profissao);

		cliente2.setConta(conta2);
		cliente2.setEndereco(endereco);
		cliente2.setNome(nome + "2");
		cliente2.setProfissao(profissao);

		cliente3.setConta(conta3);
		cliente3.setEndereco(endereco);
		cliente3.setNome(nome + "3");
		cliente3.setProfissao(profissao);

		cliente4.setConta(conta4);
		cliente4.setEndereco(endereco);
		cliente4.setNome(nome + "4");
		cliente4.setProfissao(profissao);

		cliente5.setConta(conta5);
		cliente5.setEndereco(endereco);
		cliente5.setNome(nome + "5");
		cliente5.setProfissao(profissao);

		for (Usuario u : Arrays.asList(cliente1, cliente2, cliente3, cliente4, cliente5)) {
			em.getTransaction().begin();
			em.persist(u);
			em.getTransaction().commit();
		}
	}

}
