import html
import zipfile
from pathlib import Path


ARQUIVO_SAIDA = Path("docs/relatorio-a3.docx")


def p(texto="", estilo=None):
    props = ""
    if estilo:
        props = f"<w:pPr><w:pStyle w:val=\"{estilo}\"/></w:pPr>"
    return f"<w:p>{props}<w:r><w:t xml:space=\"preserve\">{html.escape(texto)}</w:t></w:r></w:p>"


def bullet(texto):
    return p(f"- {texto}")


def tabela(linhas):
    xml = ["<w:tbl>"]
    for linha in linhas:
        xml.append("<w:tr>")
        for celula in linha:
            xml.append(f"<w:tc>{p(celula)}<w:tcPr><w:tcW w:w=\"3000\" w:type=\"dxa\"/></w:tcPr></w:tc>")
        xml.append("</w:tr>")
    xml.append("</w:tbl>")
    return "".join(xml)


conteudo = [
    p("Relatorio de Acompanhamento da A3", "Title"),
    p("Gestao e Qualidade de Software", "Subtitle"),
    p("Projeto: Controle de Estoque", "Subtitle"),
    p(""),
    p("1. Introducao", "Heading1"),
    p(
        "Este documento registra as atualizacoes realizadas no sistema legado de controle de estoque, "
        "com o objetivo de apoiar a elaboracao do trabalho final da A3 da unidade curricular de Gestao "
        "e Qualidade de Software."
    ),
    p(
        "O documento deve ser atualizado a cada melhoria, correcao, configuracao de qualidade ou teste "
        "automatizado implementado no projeto."
    ),
    p("2. Contexto do Sistema Legado", "Heading1"),
    p(
        "O sistema analisado e uma aplicacao Java com interface grafica Swing, banco de dados MySQL e "
        "camada DAO para acesso aos dados. O sistema possui funcionalidades de login, cadastro de "
        "usuarios, cadastro de categorias, cadastro de produtos, movimentacao de estoque e relatorios."
    ),
    p("3. Repositorio e Controle de Versao", "Heading1"),
    p("Repositorio utilizado: https://github.com/JcMauro/controledeestoque_a3"),
    p("Branch principal utilizada: main"),
    p("Commits iniciais registrados:"),
    bullet("chore: adicionar versao inicial do sistema legado"),
    bullet("refactor: aplicar melhorias simples de qualidade"),
    bullet("docs: atualizar readme do projeto"),
    bullet("docs: atualizar github dos integrantes"),
    p("4. Integrantes", "Heading1"),
    tabela([
        ["Nome", "GitHub", "RA"],
        ["Julio Cesar de Souza Mauro", "JcMauro", "10724269838"],
        ["Gustavo de Espindola Martins", "gustavodees", "10724238393"],
        ["Nycolle Vieira", "NycolleVieira", "A confirmar"],
        ["Layse Gabrielly Silva Lima", "LAYSEGABI", "A confirmar"],
    ]),
    p("5. Melhorias de Qualidade Ja Aplicadas", "Heading1"),
    p("5.1 Ajuste no calculo do valor total do produto", "Heading2"),
    p(
        "O metodo Produto.getValorTotal() retornava Object e convertia o resultado para int. "
        "A melhoria alterou o retorno para double, preservando o valor financeiro com casas decimais."
    ),
    p("5.2 Protecao da senha no metodo Login.toString()", "Heading2"),
    p(
        "O metodo Login.toString() exibia a senha do usuario. A melhoria substituiu esse valor por "
        "senha=<oculta>, reduzindo exposicao de dados sensiveis."
    ),
    p("5.3 Uso de PreparedStatement em operacoes de remocao", "Heading2"),
    p(
        "Os metodos ProdutoDAO.removerProduto() e CategoriaDAO.removerCategoria() montavam comandos SQL "
        "concatenando o ID informado. A melhoria passou essas operacoes para PreparedStatement, mantendo "
        "o funcionamento original com mais seguranca."
    ),
    p("5.4 Melhoria na configuracao da conexao com o banco", "Heading2"),
    p(
        "A classe ConexaoDAO manteve os dados padrao do ambiente local, mas passou a permitir configuracao "
        "por variaveis de ambiente: DB_URL, DB_USER e DB_PASSWORD."
    ),
    p("5.5 Atualizacao do README", "Heading2"),
    p(
        "O README foi atualizado com os integrantes atuais, repositorio correto, dados do banco, ferramenta "
        "Maven e referencia ao documento de melhorias de qualidade."
    ),
    p("6. Validacoes Realizadas Ate o Momento", "Heading1"),
    bullet("O projeto compila com Java 17 via Maven."),
    bullet("O comando mvn clean test executa com BUILD SUCCESS."),
    bullet("Ainda nao existem testes automatizados implementados; por isso o Maven informa No tests to run."),
    bullet("A aplicacao abriu a tela de login Controle de Estoque - Acesso."),
    bullet("A conexao com o banco local db_controledeestoque foi validada."),
    p("7. Planejamento dos Testes", "Heading1"),
    p(
        "Os testes serao implementados usando dados reais do banco de dados. Por esse motivo, parte dos "
        "testes sera classificada como teste de integracao, pois validara o comportamento das classes DAO "
        "junto ao MySQL."
    ),
    p("Ordem planejada de implementacao dos testes:"),
    bullet("Conexao e estrutura do banco de dados."),
    bullet("Login e cadastro de usuarios."),
    bullet("Categorias."),
    bullet("Produtos."),
    bullet("Movimentacao de estoque."),
    bullet("Relatorios."),
    bullet("Cobertura com JaCoCo."),
    bullet("Integracao continua com GitHub Actions."),
    p("8. Proximas Etapas", "Heading1"),
    bullet("Configurar JUnit 5 e JaCoCo no Maven."),
    bullet("Criar a estrutura src/test/java."),
    bullet("Implementar testes automatizados usando dados reais do banco."),
    bullet("Criar workflow de CI no GitHub Actions."),
    bullet("Configurar analise estatica com SonarCloud ou ferramenta equivalente."),
    p("9. Historico de Atualizacoes do Documento", "Heading1"),
    tabela([
        ["Data", "Atualizacao", "Responsavel"],
        ["05/06/2026", "Criacao do documento inicial com melhorias ja realizadas.", "JcMauro / Codex"],
    ]),
]


document_xml = f"""<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<w:document xmlns:w="http://schemas.openxmlformats.org/wordprocessingml/2006/main">
  <w:body>
    {''.join(conteudo)}
    <w:sectPr>
      <w:pgSz w:w="11906" w:h="16838"/>
      <w:pgMar w:top="1440" w:right="1440" w:bottom="1440" w:left="1440"/>
    </w:sectPr>
  </w:body>
</w:document>
"""

content_types = """<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<Types xmlns="http://schemas.openxmlformats.org/package/2006/content-types">
  <Default Extension="rels" ContentType="application/vnd.openxmlformats-package.relationships+xml"/>
  <Default Extension="xml" ContentType="application/xml"/>
  <Override PartName="/word/document.xml" ContentType="application/vnd.openxmlformats-officedocument.wordprocessingml.document.main+xml"/>
  <Override PartName="/word/styles.xml" ContentType="application/vnd.openxmlformats-officedocument.wordprocessingml.styles+xml"/>
</Types>
"""

rels = """<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<Relationships xmlns="http://schemas.openxmlformats.org/package/2006/relationships">
  <Relationship Id="rId1" Type="http://schemas.openxmlformats.org/officeDocument/2006/relationships/officeDocument" Target="word/document.xml"/>
</Relationships>
"""

styles = """<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<w:styles xmlns:w="http://schemas.openxmlformats.org/wordprocessingml/2006/main">
  <w:style w:type="paragraph" w:styleId="Title"><w:name w:val="Title"/><w:rPr><w:b/><w:sz w:val="36"/></w:rPr></w:style>
  <w:style w:type="paragraph" w:styleId="Subtitle"><w:name w:val="Subtitle"/><w:rPr><w:sz w:val="24"/></w:rPr></w:style>
  <w:style w:type="paragraph" w:styleId="Heading1"><w:name w:val="heading 1"/><w:rPr><w:b/><w:sz w:val="28"/></w:rPr></w:style>
  <w:style w:type="paragraph" w:styleId="Heading2"><w:name w:val="heading 2"/><w:rPr><w:b/><w:sz w:val="24"/></w:rPr></w:style>
</w:styles>
"""


ARQUIVO_SAIDA.parent.mkdir(exist_ok=True)
with zipfile.ZipFile(ARQUIVO_SAIDA, "w", zipfile.ZIP_DEFLATED) as docx:
    docx.writestr("[Content_Types].xml", content_types)
    docx.writestr("_rels/.rels", rels)
    docx.writestr("word/document.xml", document_xml)
    docx.writestr("word/styles.xml", styles)

print(f"Documento gerado em {ARQUIVO_SAIDA}")
