import java.util.Scanner;
import java.util.Random;
import java.util.InputMismatchException;

public class JogadorSimples extends Inimigo{
    
    //Atributos dos personagens
    protected byte posM, posN;    //Além do ataque e defesa, os jogadores possuem um atributo que informa suas posições no tabuleiro de m linhas e n colunas
    protected byte acoesJogador;    //Possuem também um atributo que informa quantas ações o jogador ainda pode realizar num turno
    
    //Construtor
    public JogadorSimples(byte ataque, byte defesa, byte posM, byte posN){
        super(ataque, defesa);
        this.setPosM(posM);
        this.setPosN(posN);
    }
    
    //Métodos get
    public byte getPosM(){
        return this.posM;
    }
    public byte getPosN(){
        return this.posN;
    }
    public byte getAcoesJogador(){
        return this.acoesJogador;
    }
    
    //Métodos set
    public void setPosM(byte posM){
        if(posM>=0 && posM<=4){     //Verifica se a posM está dentro da matriz
            this.posM = posM;
        }
    }
    public void setPosN(byte posN){
        if(posN>=0 && posN<=4){     //Verifica se a posN está dentro da matriz
            this.posN = posN;
        }
    }
    public void setAcoesJogador(byte acoesJogador){
        if(acoesJogador>=0 && acoesJogador<=2){     //O jogador pode ter de [0 a 2] ações no turno
            this.acoesJogador = acoesJogador;
        }
    }
    
    //Método atacar
    public void atacar(Setor setor){    //O método atacar recebe um setor
        Random rand = new Random();
        Scanner input = new Scanner(System.in);
        boolean erro = true;
        
        byte inimigoAlvo = -1;
        
        if(setor.getInimigosDerrotados()){      //Não há inimigos a serem atacados
            System.out.printf("|--------------------------|\n"
                            + "| Não há inimigos no setor |\n"
                            + "|--------------------------|\n\n");
        }
        else{
            
            //Tratamento da exceção do tipo InputMismatch
            do{
                try{
                    System.out.printf("> Qual inimigo deseja atacar?\n");
            
                    inimigoAlvo = input.nextByte();
                    System.out.println();
                    
                    inimigoAlvo--;
                    erro = false;
                }
                catch(InputMismatchException e){
                   System.err.println(e);
                   System.err.println("Entrada de tipo inválido - insira um número inteiro\n");
                   input.nextLine();
                }
            }while(erro);
            
            if(inimigoAlvo >= 0 && inimigoAlvo < setor.getVetInimigos().length) {   //Verifica se o jogador selecionou um inimigo válido
                if(setor.getVetInimigos()[inimigoAlvo].getDefesa() > 0) {     //Se o inimigo já não foi derrotado (defesa>0), realiza o ataque
                    if(setor.getTipoSetor() == 2){  //Se o jogador estiver em um setor do tipo 2 (oculto) o ataque pode ou não acertar o inimigo escolhido
                        int dado;
                        dado = rand.nextInt(10)+1;   //Gera um número entre [0 e 10[ e depois soma-se 1, para que o número esteja no intervalo de [1 e 10]
                        
                        if(dado>3){  //Se o número gerado for maior que 3, o jogador acerta o ataque
                            
                            //A defesa do inimigo alvo é reduzida pelo valor de ataque do jogador atacante
                            setor.getVetInimigos()[inimigoAlvo].setDefesa((byte)(setor.getVetInimigos()[inimigoAlvo].getDefesa()-this.getAtaque()));
                        }
                        else{   //Se o número gerado for menor que 3, o jogador erra o ataque
                            System.out.printf("|---------------------------|\n"
                                            + "| O jogador errou o ataque! |\n"
                                            + "|---------------------------|\n\n");
                        }
                        //this.setAcoesJogador((byte)(this.getAcoesJogador()-1));    //Como a ação foi realizada, o jogador usou uma de suas ações no turno (acertando ou não)
                    }
                    else{   //Se o jogador não estiver em um setor do tipo 2 (oculto) o ataque é realizado normalmente
                        
                        //A defesa do inimigo alvo é reduzida pelo valor de ataque do jogador atacante
                        setor.getVetInimigos()[inimigoAlvo].setDefesa((byte)(setor.getVetInimigos()[inimigoAlvo].getDefesa()-this.getAtaque()));
                        //this.setAcoesJogador((byte)(this.getAcoesJogador()-1));    //Como a ação foi realizada, o jogador usou uma de suas ações no turno
                    }
                    this.setAcoesJogador((byte)(this.getAcoesJogador()-1));    //Como a ação foi realizada, o jogador usou uma de suas ações no turno
                }
            }
            else{
                System.out.printf("|-------------------------|\n"
                                + "|     Inimigo inválido!   |\n"
                                + "|-------------------------|\n\n");
            }
            setor.setInimigosDerrotados(true);      //Assume-se que depois do ataque os inimigos foram derrotados, o valor muda para false se não foram
        }
    }
    
    //Método movimentar
    public void movimentar(Setor[][] matrizSetor, String[][] matrizPrint){     //O método movimentar recebe a matriz de setores e a matriz de print
        Scanner input = new Scanner(System.in);
        
        char inputMovimento;
        byte posM_Anterior, posN_Anterior;  //Guarda a posição do setor anterior do jogador
        
        if(matrizSetor[this.getPosM()][this.getPosN()].getInimigosDerrotados()){    //O jogador pode se movimentar se não houver inimigos no setor
            System.out.printf("|--------------------------------|\n"
                            + "|            > W - Cima          |\n"
                            + "| > A - Esquerda   > D - Direita |\n"
                            + "|            > S - Baixo         |\n"
                            + "|--------------------------------|\n");
            System.out.printf("> Para qual direção deseja ir?\n");
            inputMovimento = input.next().charAt(0);
            System.out.println();
            posM_Anterior = this.getPosM();
            posN_Anterior = this.getPosN();
            
            switch(inputMovimento){
                case 'w':
                    if(matrizSetor[this.getPosM()][this.getPosN()].getParedeCima()){    //Se a parede de cima possuir uma porta aberta
                        this.setPosM((byte)(this.getPosM()-1));     //A posição no sentido das linhas é decrementada (o jogador sobe)
                    }
                    break;
            
                case 's':
                    if(matrizSetor[this.getPosM()][this.getPosN()].getParedeBaixo()){   //Se a parede de baixo possuir uma porta aberta
                        this.setPosM((byte)(this.getPosM()+1));     //A posição no sentido das linhas é incrementada (o jogador desce)
                    }
                    break;
                
                case 'a':
                    if(matrizSetor[this.getPosM()][this.getPosN()].getParedeEsquerda()){    //Se a parede esquerda possuir uma porta aberta
                        this.setPosN((byte)(this.getPosN()-1));     //A posição no sentido das colunas é decrementada (o jogador vai pra esquerda)
                    }
                    break;
                
                case 'd':
                    if(matrizSetor[this.getPosM()][this.getPosN()].getParedeDireita()){     //Se a parede direita possuir uma porta aberta
                        this.setPosN((byte)(this.getPosN()+1));     //A posição no sentido das colunas é incrementada (o jogador vai pra direita)
                    }
                    break;
            
                default:
                    break;
            }
            
            //Após realizar o movimento, o setor na nova posição do jogador é gerado, se ele já não tiver sido visitado
            if(!matrizSetor[this.getPosM()][this.getPosN()].getVisitado()){
                matrizSetor[this.getPosM()][this.getPosN()] = Setor.gerarSetor(this.getPosM(), this.getPosN(), matrizSetor);
            }
            
            if(posM_Anterior != this.getPosM() || posN_Anterior != this.getPosN()){     //Se a posição anterior for diferente da atual, o jogador se movimentou
                if(this instanceof JogadorSuporte){     //Se o objeto for uma instância de JogadorSuporte, ele vai ser representado na matriz de print como P2
                    
                    //Adicionando uma representação do jogador suporte (p2) na matriz de print
                    if(matrizPrint[2*this.getPosM()+2][2*this.getPosN()+2].equals("P1 ")){  //Se o jogador 1 já estiver no setor que o jogador 2 se movimentou
                        matrizPrint[2*this.getPosM()+2][2*this.getPosN()+2] = " P ";    //Adiciona a representação de ambos o jogadores no mesmo setor na matriz de print
                    }
                    else{
                        if(this.getPosM() != 2 || this.getPosN() != 2){     //Se o jogador 2 não estiver no setor inicial (C)
                            matrizPrint[2*this.getPosM()+2][2*this.getPosN()+2] = "P2 ";    //Adiciona a representação do jogador 2 no setor que ele se movimentou na matriz de print
                        }
                    }
                    
                    //Removendo uma representação do jogador suporte (p2) na matriz de print
                    if(matrizPrint[2*posM_Anterior+2][2*posN_Anterior+2].equals(" P ")){    //Se ambos os jogadores estiverem no mesmo setor
                        matrizPrint[2*posM_Anterior+2][2*posN_Anterior+2] = "P1 ";      //Remove a representação do jogador 2 e deixa a do jogador 1, quando o jogador 2 movimentar
                    }
                    else{
                        if(matrizPrint[2*posM_Anterior+2][2*posN_Anterior+2].equals("P2 ")){
                            matrizPrint[2*posM_Anterior+2][2*posN_Anterior+2] = "   ";       //Remove a representação do jogador 2, quando ele se movimentar    
                        }
                    }
                }
                else{   //Se o objeto for uma instância de JogadorSimples, ele vai ser representado na matriz de print como P1
                    
                    //Adicionando uma representação do jogador simples (p1) na matriz de print
                    if(matrizPrint[2*this.getPosM()+2][2*this.getPosN()+2].equals("P2 ")){  //Se o jogador 2 já estiver no setor que o jogador 1 se movimentou
                        matrizPrint[2*this.getPosM()+2][2*this.getPosN()+2] = " P ";
                    }
                    else{
                        if(this.getPosM() != 2 || this.getPosN() != 2){     //Se o jogador 1 não estiver no setor inicial (C)
                            matrizPrint[2*this.getPosM()+2][2*this.getPosN()+2] = "P1 ";    //Adiciona a representação do jogador 1 no setor que ele se movimentou na matriz de print
                        }
                    }
                    
                    //Removendo uma representação do jogador simples (p1) na matriz de print
                    if(matrizPrint[2*posM_Anterior+2][2*posN_Anterior+2].equals(" P ")){    //Se ambos os jogadores estiverem no mesmo setor
                        matrizPrint[2*posM_Anterior+2][2*posN_Anterior+2] = "P2 ";      //Remove a representação do jogador 1 e deixa a do jogador 2, quando o jogador 1 se movimentar
                    }
                    else{
                        if(matrizPrint[2*posM_Anterior+2][2*posN_Anterior+2].equals("P1 ")){
                            matrizPrint[2*posM_Anterior+2][2*posN_Anterior+2] = "   ";       //Remove a representação do jogador 1, quando ele se movimentar
                        }
                    }
                }
            }
            else{   //Se a posição não mudou, então o movimento é inválido (input inválido, ou não há uma porta aberta da direção escolhida)
                System.out.printf("|------------------------------|\n"
                                + "|      Movimento inválido!     |\n"
                                + "|------------------------------|\n\n");
            }
        }
        else{   //Se houver inimigos no setor, o jogador não pode se movimentar
            System.out.printf("|------------------------------|\n"
                            + "| Não é possível se movimentar |\n"
                            + "|     com inimigos no setor    |\n"
                            + "|------------------------------|\n\n");
        }
    }
    
    //Método procurar
    public void procurar(Setor setor){     //O método procurar recebe um setor como parâmetro
        Random rand = new Random();
        int dado;
        dado = rand.nextInt(6) + 1;     //Gera um número entre [0 e 6[ e depois soma-se 1, para que o número esteja no intervalo de [1 e 6]
        
        if(setor.getTipoSetor() == 3){  //Se o setor for do tipo 3 (privado), a procura não pode ser realizada
            System.out.printf("|---------------------------|\n"
                            + "| Não foi possível realizar |\n"
                            + "|         a procura         |\n"
                            + "|---------------------------|\n\n");
        }
        else{   //Se o setor não for do tipo 3 (privado), a procura é realizada
            if(dado<=3){    //Se o valor gerado for menor ou igual a 3, nada é encontrado no setor
                System.out.printf("|--------------------------|\n"
                                + "| Nada encontrado no setor |\n"
                                + "|--------------------------|\n\n");
            }
            else{
                if(dado == 4){      //Se o valor gerado for igual a 4, o jogador que realizou a procura recebe +1 de defesa
                    System.out.printf("|---------------------------|\n"
                                    + "|      Item encontrado!     |\n"
                                    + "|         +1 DEFESA         |\n"
                                    + "|---------------------------|\n\n");
                    this.setDefesa((byte)(this.getDefesa()+1));
                }
                else{
                    if(dado == 5){      //Se o valor gerado for igual a 5, o jogador que realizou a procura recebe +2 de defesa
                        System.out.printf("|---------------------------|\n"
                                        + "|      Item encontrado!     |\n"
                                        + "|         +2 DEFESA         |\n"
                                        + "|---------------------------|\n\n");
                        this.setDefesa((byte)(this.getDefesa()+2));
                    }
                    else{   //Se o valor gerado for igual a 6, os inimigos do setor onde o jogador realizou a procura perdem -1 de defesa
                        System.out.printf("|-------------------------------|\n"
                                        + "|        Item encontrado!       |\n"
                                        + "| -1 DEFESA a todos os inimigos |\n"
                                        + "|-------------------------------|\n\n");
                        for(int i=0; i<setor.getVetInimigos().length; i++){
                            setor.getVetInimigos()[i].setDefesa((byte)(setor.getVetInimigos()[i].getDefesa()-1));
                        }
                        setor.setInimigosDerrotados(true);      //Assume-se que depois do ataque os inimigos foram derrotados, o valor muda para false se não foram
                    }
                }
            }
        this.setAcoesJogador((byte)(this.getAcoesJogador()-1));     //Como a ação foi realizada, o jogador usou uma de suas ações no turno
        }
    }
}