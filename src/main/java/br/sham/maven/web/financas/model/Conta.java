package br.sham.maven.web.financas.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

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
	
	public void print() {
		System.out.println(this.toString());
	}
	
	
}
