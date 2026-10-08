-- Desabilita restrições, triggers e constraints
SET session_replication_role = replica;


-- Remove dados existentes
DELETE FROM cliente_funcionarios;
DELETE FROM role_usuario;
DELETE FROM usuarios;
DELETE FROM roles;
DELETE FROM categoria;
DELETE FROM empresas;
DELETE FROM pessoas;
DELETE FROM planos;


-- Remove constraint para permitir alteração temporária
ALTER TABLE planos
DROP CONSTRAINT IF EXISTS plano_valor_mensal_check;


-- Reinsere a constraint
ALTER TABLE IF EXISTS public.planos
    ADD CONSTRAINT plano_valor_mensal_check
    CHECK (
    (tipo_plano = 'FREE' AND valor_mensal = 0)
    OR
    (
    tipo_plano <> 'FREE'
    AND valor_mensal >= 49::double precision
    AND valor_mensal <= 200::double precision
    )
    );


-- =========================================================
-- PLANOS
-- =========================================================

INSERT INTO public.planos(
    id,
    ativo,
    descricao,
    limite_cliente,
    limite_usuario,
    nome,
    tipo_plano,
    valor_mensal
)
VALUES (
           1,
           true,
           'Plano free para teste',
           2,
           4,
           'Plano Free',
           'FREE',
           0
       );

SELECT nextval('seq_planos');


INSERT INTO public.planos(
    id,
    ativo,
    descricao,
    limite_cliente,
    limite_usuario,
    nome,
    tipo_plano,
    valor_mensal
)
VALUES (
           2,
           true,
           'Plano Pro nível médio',
           15,
           30,
           'Plano Pro',
           'PRO',
           50
       );

SELECT nextval('seq_planos');


-- =========================================================
-- PESSOAS
-- =========================================================

INSERT INTO public.pessoas(
    id,
    ativo,
    bairro,
    cep,
    cidade,
    cnpj,
    complemento,
    cpf,
    data_cadastro,
    email,
    estado,
    logradouro,
    nome,
    nome_fantasia,
    observacao,
    pais,
    razao_social,
    telefone,
    tipo_pessoa,
    empresa_id,
    inscricao_estadual
)
VALUES (
           1,
           true,
           'Jd Dias 1',
           '87025-758',
           'Maringá',
           '26.934.453/0001-89',
           'perto do mercado Katayma',
           '059.486.784-85',
           '2026-07-15',
           'contato@jdevtreinamento.com.br',
           'PR',
           'Rua Pioneiro Antonio',
           'Alex Fernando Egidio',
           'Jdev Treinamento LTDA',
           'Nenhuma',
           'Brasil',
           'JDev Treinamento',
           '44 9 8821-2355',
           'JURIDICA',
           1,
           '878787-787'
       );

SELECT nextval('seq_pessoas');


-- =========================================================
-- EMPRESAS
-- =========================================================

INSERT INTO public.empresas(
    id,
    bloqueio,
    logo_marca,
    plano_ativo,
    total_cliente,
    total_usuario,
    vigencia_plano,
    pessoa_id,
    plano_id
)
VALUES (
           1,
           false,
           'não existe',
           true,
           10,
           20,
           '2030-10-10',
           1,
           1
       );

SELECT nextval('seq_empresas');


-- Habilita restrições, triggers e constraints
SET session_replication_role = origin;


-- =========================================================
-- CATEGORIAS
-- =========================================================

INSERT INTO categoria (id, nome, empresa_id)
VALUES (nextval('seq_categoria'), 'Informática', 1);

INSERT INTO categoria (id, nome, empresa_id)
VALUES (nextval('seq_categoria'), 'Eletrônicos', 1);

INSERT INTO categoria (id, nome, empresa_id)
VALUES (nextval('seq_categoria'), 'Celulares', 1);

INSERT INTO categoria (id, nome, empresa_id)
VALUES (nextval('seq_categoria'), 'Notebooks', 1);

INSERT INTO categoria (id, nome, empresa_id)
VALUES (nextval('seq_categoria'), 'Computadores', 1);

INSERT INTO categoria (id, nome, empresa_id)
VALUES (nextval('seq_categoria'), 'Monitores', 1);

INSERT INTO categoria (id, nome, empresa_id)
VALUES (nextval('seq_categoria'), 'Impressoras', 1);

INSERT INTO categoria (id, nome, empresa_id)
VALUES (nextval('seq_categoria'), 'Periféricos', 1);

INSERT INTO categoria (id, nome, empresa_id)
VALUES (nextval('seq_categoria'), 'Acessórios', 1);

INSERT INTO categoria (id, nome, empresa_id)
VALUES (nextval('seq_categoria'), 'Games', 1);

INSERT INTO categoria (id, nome, empresa_id)
VALUES (nextval('seq_categoria'), 'Consoles', 1);

INSERT INTO categoria (id, nome, empresa_id)
VALUES (nextval('seq_categoria'), 'Smart TV', 1);

INSERT INTO categoria (id, nome, empresa_id)
VALUES (nextval('seq_categoria'), 'Áudio', 1);

INSERT INTO categoria (id, nome, empresa_id)
VALUES (nextval('seq_categoria'), 'Vídeo', 1);

INSERT INTO categoria (id, nome, empresa_id)
VALUES (nextval('seq_categoria'), 'Câmeras', 1);

INSERT INTO categoria (id, nome, empresa_id)
VALUES (nextval('seq_categoria'), 'Redes', 1);

INSERT INTO categoria (id, nome, empresa_id)
VALUES (nextval('seq_categoria'), 'Segurança', 1);

INSERT INTO categoria (id, nome, empresa_id)
VALUES (nextval('seq_categoria'), 'Automação', 1);

INSERT INTO categoria (id, nome, empresa_id)
VALUES (nextval('seq_categoria'), 'Ferramentas', 1);

INSERT INTO categoria (id, nome, empresa_id)
VALUES (nextval('seq_categoria'), 'Construção', 1);

INSERT INTO categoria (id, nome, empresa_id)
VALUES (nextval('seq_categoria'), 'Casa e Decoração', 1);

INSERT INTO categoria (id, nome, empresa_id)
VALUES (nextval('seq_categoria'), 'Móveis', 1);

INSERT INTO categoria (id, nome, empresa_id)
VALUES (nextval('seq_categoria'), 'Escritório', 1);

INSERT INTO categoria (id, nome, empresa_id)
VALUES (nextval('seq_categoria'), 'Papelaria', 1);

INSERT INTO categoria (id, nome, empresa_id)
VALUES (nextval('seq_categoria'), 'Brinquedos', 1);

INSERT INTO categoria (id, nome, empresa_id)
VALUES (nextval('seq_categoria'), 'Esportes', 1);

INSERT INTO categoria (id, nome, empresa_id)
VALUES (nextval('seq_categoria'), 'Moda', 1);

INSERT INTO categoria (id, nome, empresa_id)
VALUES (nextval('seq_categoria'), 'Calçados', 1);

INSERT INTO categoria (id, nome, empresa_id)
VALUES (nextval('seq_categoria'), 'Beleza e Saúde', 1);

INSERT INTO categoria (id, nome, empresa_id)
VALUES (nextval('seq_categoria'), 'Alimentos e Bebidas', 1);


-- Extensão para busca sem acentuação
CREATE EXTENSION IF NOT EXISTS unaccent;


-- =========================================================
-- ROLES
-- =========================================================

INSERT INTO public.roles(
    id,
    acesso
)
VALUES (
           1,
           'ROLE_USER'
       );

SELECT nextval('seq_roles');


INSERT INTO public.roles(
    id,
    acesso
)
VALUES (
           2,
           'ROLE_ADMIN'
       );

SELECT nextval('seq_roles');


-- =========================================================
-- CLIENTE FUNCIONÁRIO
-- =========================================================

ALTER TABLE cliente_funcionarios
    ALTER COLUMN usuario_id DROP NOT NULL;


INSERT INTO public.cliente_funcionarios(
    id,
    tipo_cliente,
    empresa_id,
    pessoa_id,
    usuario_id
)
VALUES (
           1,
           'FUNCIONARIO',
           1,
           1,
           NULL
       );

SELECT nextval('seq_cliente_funcionarios');


-- =========================================================
-- USUÁRIO
-- =========================================================

/*
Senha de teste: &164Al#
*/

INSERT INTO public.usuarios(
    id,
    bloqueado,
    login,
    refresh_token,
    senha,
    token_sessao,
    cliente_funcionario_id,
    empresa_id
)
VALUES (
           1,
           false,
           'alex.fernando.egidio@gmail.com',
           '',
           '$2a$10$IR1YyIRLugk3QwPC/RQMz.oF2XEttkg9XRRwZlQJGHG90T/vP7vve',
           '',
           1,
           1
       );

SELECT nextval('seq_usuarios');


UPDATE cliente_funcionarios
SET usuario_id = 1
WHERE id = 1;


-- =========================================================
-- ALTERAÇÕES NA TABELA DE USUÁRIOS
-- =========================================================

ALTER TABLE IF EXISTS public.usuarios
ALTER COLUMN refresh_token TYPE TEXT,
    ALTER COLUMN token_sessao TYPE TEXT;