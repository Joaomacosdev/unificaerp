package br.com.unificaerp.cliente_funcionario.model;

import br.com.unificaerp.cliente_funcionario.model.enums.TipoCliente;
import br.com.unificaerp.empresa.model.Empresa;
import br.com.unificaerp.pessoa.model.Pessoa;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "cliente_funcionarios")
@SequenceGenerator(name = "seq_cliente_funcionario", sequenceName = "seq_cliente_funcionario", allocationSize = 1, initialValue = 1)
public class ClienteFuncionario {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_cliente", nullable = false)
    private TipoCliente tipoCliente;

    @JsonIgnore
    @ManyToOne()
    @JoinColumn(name = "empresa_id", nullable = false)
    private Empresa empresa;

    @JsonManagedReference
    @OneToOne
    @JoinColumn(name = "pessoa_id", nullable = false, unique = true)
    private Pessoa pessoa;

    public ClienteFuncionario() {
    }


    public void associarEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    public void desassociarEmpresa(){
        this.empresa = null;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        ClienteFuncionario that = (ClienteFuncionario) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
