# Projeto-SIN141
Projeto de disciplina de SIN 141 - Computação Orientada a Objetos (27/11/20)

Executando o projeto:

  1. Descompactar o arquivo (.zip) do projeto.
  2. Crie um projeto na IDE que você esteja usando.
  3. Vá na pasta do projeto que você criou (passo 2) e encontre a pasta que deveria conter os arquivos .java.
  4. Cole os arquivos descompactados do passo 1.
  5. Vá para a sua IDE e acesse o projeto criado,e na classe que contém o método main execute o projeto.
  
Iniciando o Jogo:

Quando executar o projeto você terá três opções de escolha: 
1. Um Jogador (jogar somente de um jogador), 
2. Dois Jogadores (jogar com dois jogadores), 
3. Sair (sair do jogo).

Após sua escolha (exceto sair), surgirá na tela o tabuleiro, logo abaixo o setor contendo o(s) jogador(es). E também um bloco de opções para a sua jogada. 

O jogo se inicia e começa o turno do jogador 1, onde ele poderá se movimentar e terá duas jogadas pra fazer. Assim que ele se movimentar o setor será criado,
gerando as portas, os inimigos e o tipo de setor. O jogador terá as seguintes possibilidades para utilizar suas jogadas:

  * Caso o tipo de setor seja normal ele poderá:
  > X - Atacar e
  > C - Procurar
  
  * Caso seja privado ele poderá: 
  > C - Procurar
  
  * caso seja oculto: 
  > X - Atacar e
  > C - Procurar (sem garantia de acerto quanto atacar o inimigo). 
  
Após suas duas jogadas serem realizadas, a vez passa para o próximo ator do jogo. A rotação dos turnos é sempre: Jogador 1 -> Jogador 2 -> Inimigos (1 a 5);
