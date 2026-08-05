-- Desabilita restrições, triggers e constraints
SET session_replication_role = replica;

-- 1. Limpeza de dados em ordem (tabelas dependentes primeiro)
DELETE FROM item_pedido;
DELETE FROM menssagens;
DELETE FROM cliente_funcionarios;
DELETE FROM role_usuario;
DELETE FROM usuarios;
DELETE FROM roles;
DELETE FROM categoria;
DELETE FROM empresas;
DELETE FROM pessoas;
DELETE FROM planos;

-- 2. Ajuste de Constraints
ALTER TABLE IF EXISTS public.planos
DROP CONSTRAINT IF EXISTS plano_valor_mensal_check;

ALTER TABLE IF EXISTS public.planos
    ADD CONSTRAINT plano_valor_mensal_check
    CHECK (
    (tipo_plano = 'FREE' AND valor_mensal = 0)
    OR
    (tipo_plano <> 'FREE' AND valor_mensal >= 49::double precision AND valor_mensal <= 200::double precision)
    );

-- 3. Inserção dos Planos
INSERT INTO public.planos (
    id, ativo, descricao, limite_cliente, limite_usuario, nome, tipo_plano, valor_mensal
) VALUES
      (1, true, 'Plano free para teste', 2, 4, 'Plano Free', 'FREE', 0),
      (2, true, 'Plano Pro nível médio', 15, 30, 'Plano Pro', 'PRO', 50);

SELECT nextval('seq_plano');
SELECT nextval('seq_plano');

-- 4. Inserção do Cadastro de Pessoa (inicialmente com empresa_id nulo para evitar dependência circular)
INSERT INTO public.pessoas (
    id, ativo, bairro, cep, cidade, cnpj, complemento, cpf, data_cadastro,
    email, estado, logradouro, nome, nome_fantasia, observacao, pais,
    razao_social, telefone, tipo_pessoa, empresa_id, inscricao_estadual
) VALUES (
             1, true, 'Jd Dias 1', '87025-758', 'Maringá', '26.934.453/0001-89',
             'perto do mercado Katayma', '059.486.784-85', '2026-07-15',
             'contato@jdevtreinamento.com.br', 'PR', 'Rua Pioneiro Antonio',
             'Alex Fernando Egidio', 'Jdev Treinamento LTDA', 'Nenhuma', 'Brasil',
             'JDev Treinamento', '44 9 8821-2355', 'JURIDICA', NULL, '878787-787'
         );

SELECT nextval('seq_pessoa');

-- 5. Inserção de Empresa
INSERT INTO public.empresas (
    id, bloqueio, logo_marca, plano_ativo, total_cliente, total_usuario,
    vigencia_plano, pessoa_id, plano_id
) VALUES (
             1, false, 'não existe', true, 10, 20, '2030-10-10', 1, 1
         );

SELECT nextval('seq_empresa');

-- 6. Atualização do vínculo de Pessoa com Empresa
UPDATE public.pessoas
SET empresa_id = 1
WHERE id = 1;

-- 7. Inserção de Categorias
INSERT INTO public.categoria (id, nome, empresa_id) VALUES
                                                        (nextval('seq_categoria'), 'Alimentos', 1),
                                                        (nextval('seq_categoria'), 'Bebidas', 1),
                                                        (nextval('seq_categoria'), 'Padaria', 1),
                                                        (nextval('seq_categoria'), 'Laticínios', 1),
                                                        (nextval('seq_categoria'), 'Carnes', 1),
                                                        (nextval('seq_categoria'), 'Hortifruti', 1),
                                                        (nextval('seq_categoria'), 'Congelados', 1),
                                                        (nextval('seq_categoria'), 'Limpeza', 1),
                                                        (nextval('seq_categoria'), 'Higiene Pessoal', 1),
                                                        (nextval('seq_categoria'), 'Perfumaria', 1),
                                                        (nextval('seq_categoria'), 'Pet Shop', 1),
                                                        (nextval('seq_categoria'), 'Papelaria', 1),
                                                        (nextval('seq_categoria'), 'Informática', 1),
                                                        (nextval('seq_categoria'), 'Eletrônicos', 1),
                                                        (nextval('seq_categoria'), 'Celulares e Acessórios', 1),
                                                        (nextval('seq_categoria'), 'Eletrodomésticos', 1),
                                                        (nextval('seq_categoria'), 'Ferramentas', 1),
                                                        (nextval('seq_categoria'), 'Construção', 1),
                                                        (nextval('seq_categoria'), 'Materiais Elétricos', 1),
                                                        (nextval('seq_categoria'), 'Materiais Hidráulicos', 1),
                                                        (nextval('seq_categoria'), 'Móveis', 1),
                                                        (nextval('seq_categoria'), 'Decoração', 1),
                                                        (nextval('seq_categoria'), 'Roupas', 1),
                                                        (nextval('seq_categoria'), 'Calçados', 1),
                                                        (nextval('seq_categoria'), 'Esportes', 1),
                                                        (nextval('seq_categoria'), 'Brinquedos', 1),
                                                        (nextval('seq_categoria'), 'Automotivo', 1),
                                                        (nextval('seq_categoria'), 'Jardinagem', 1),
                                                        (nextval('seq_categoria'), 'Farmácia', 1),
                                                        (nextval('seq_categoria'), 'Utilidades Domésticas', 1);

-- Reabilita restrições, triggers e constraints
SET session_replication_role = origin;