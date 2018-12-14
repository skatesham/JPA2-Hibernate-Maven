package br.sham.maven.web.financas.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
public @Data class Conta {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	private String numero;
	private String banco;
	private String agencia;
	
	@OneToMany(mappedBy="conta")
	private List<Movimentacao> movimentacoes;
	
	public void print() {
		System.out.println(this.toString());
	}
	
	public List<Movimentacao> getMovimentacoes() {
	    //TODO Auto-generated method stub
	    return movimentacoes;
	}
	
	
}
