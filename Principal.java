import java.util.Scanner;
import java.util.Random;
import java.util.InputMismatchException;

public class Principal {

    //Método para modificar a matriz de print
    public static void modificarMatrizPrint(Setor[][] matrizSetor, String[][] matrizPrint, JogadorSimples jogador) {

        //Vai verificar se, no setor que o jogador está, há portas abertas, se sim a matriz de print recebe strings que simbolizam portas abertas
        if (matrizSetor[jogador.getPosM()][jogador.getPosN()].getParedeCima()) {
            matrizPrint[(2*jogador.getPosM()+2)-1][(2*jogador.getPosN()+2)] = "-*-";
        }
        if (matrizSetor[jogador.getPosM()][jogador.getPosN()].getParedeBaixo()) {
            matrizPrint[(2*jogador.getPosM()+2)+1][(2*jogador.getPosN()+2)] = "-*-";
        }
        if (matrizSetor[jogador.getPosM()][jogador.getPosN()].getParedeEsquerda()) {
            matrizPrint[(2*jogador.getPosM()+2)][(2*jogador.getPosN()+2)-1] = "*";
        }
        if (matrizSetor[jogador.getPosM()][jogador.getPosN()].getParedeDireita()) {
            matrizPrint[(2*jogador.getPosM()+2)][(2*jogador.getPosN()+2)+1] = "*";
        }
    }
    
    //Método para imprimir matriz de string
    public static void imprimirMatrizString(String[][] matrizString){
        for (int i = 0; i < matrizString.length; i++) {
            for (int j = 0; j < matrizString[i].length; j++) {
                System.out.printf("%s", matrizString[i][j]);
            }
            System.out.printf("\n");
        }
                    
    }
    
    public static void turnoInimigos(Setor setor, JogadorSimples jogador) throws InterruptedException{      //O método recebe um setor e um jogador
        
        if(!setor.getInimigosDerrotados()){     //Se ainda houver inimigos não derrotados no setor
            for(int i=0; i<setor.getVetInimigos().length; i++){
                if(setor.getVetInimigos()[i].getDefesa()>0){
                    if(jogador.getDefesa()>0){      //Se o jogador ainda não foi derrotado
                        if(jogador instanceof JogadorSuporte){
                            System.out.printf("> Inimigo %d tenta atacar P2\n", i+1);
                            Thread.sleep(1000);
                        }
                        else{
                            System.out.printf("> Inimigo %d tenta atacar P1\n", i+1);
                            Thread.sleep(1000);
                        }
                        setor.getVetInimigos()[i].atacar(jogador);      //Os inimigos não derrotados tentam atacar o jogador
                        Thread.sleep(1000);
                    }
                }
            }
        }
    }
    
    public static void main(String[] args) throws InterruptedException{

        Scanner input = new Scanner(System.in);
        Random rand = new Random();

        byte inputMenu = 0;         //Armazena a opção selecionada no menu
        byte turno;             //Conta os turnos durante o jogo
        byte iFonte, jFonte;    //Coordenadas da fonte de infecção
        char inputJogador;      //Armazena a ação do jogador
        byte inimigoAtacado;    //Armazena qual inimigo o jogador quis atacar

        do {
            boolean erro = true;
            
            //Tratamento da exceção do tipo InputMismatch
            do{
                try{
                    //Menu inicial
                    System.out.printf("|----------------------------|\n"
                                    + "|    Antivírus por um dia    |\n"
                                    + "|----------------------------|\n");
                    Thread.sleep(1000);
                    System.out.printf("\nSelecione uma das opções abaixo: ");
                    Thread.sleep(1000);
                    System.out.println("\n> 1 - Um Jogador\n> 2 - Dois Jogadores\n> 3 - Sair");
                    inputMenu = input.nextByte();
                    Thread.sleep(1000);

                    if (inputMenu < 1 || inputMenu > 3) {     //Verifica se a opção selecionada é válida, se não for, pede para o usuário inserir uma opção até que ela seja válida
                        while (inputMenu < 1 || inputMenu > 3) {
                            System.out.printf("\n> Selecione uma opção válida\n");
                            Thread.sleep(1000);
                            System.out.printf("\nSelecione uma das opções abaixo: ");
                            Thread.sleep(1000);
                            System.out.println("\n> 1 - Um Jogador\n> 2 - Dois Jogadores\n> 3 - Sair");
                            inputMenu = input.nextByte();
                            Thread.sleep(1000);
                        }
                    }
                    
                    erro = false;
                }
                catch(InputMismatchException e){
                    System.err.println(e);
                    System.err.println("Entrada de tipo inválido - insira um número inteiro\n");
                    input.nextLine();
                }
            }while(erro);
            
            if(inputMenu == 3){     //Se a opção selecionada for a 3, sai do loop e encerra o programa
                break;
            }
            
            String[][] matrizPrint = {{"  ", " ", " 1 ", " ", " 2 ", " ", " 3 ", " ", " 4 ", " ", " 5 ", " "},
                                      {"  ", "|", "---", "|", "---", "|", "---", "|", "---", "|", "---", "|"},
                                      {"1 ", "|", "   ", "|", "   ", "|", "   ", "|", "   ", "|", "   ", "|"},
                                      {"  ", "|", "---", "|", "---", "|", "---", "|", "---", "|", "---", "|"},
                                      {"2 ", "|", "   ", "|", "   ", "|", "   ", "|", "   ", "|", "   ", "|"},
                                      {"  ", "|", "---", "|", "---", "|", "---", "|", "---", "|", "---", "|"},
                                      {"3 ", "|", "   ", "|", "   ", "|", "   ", "|", "   ", "|", "   ", "|"},
                                      {"  ", "|", "---", "|", "---", "|", "---", "|", "---", "|", "---", "|"},
                                      {"4 ", "|", "   ", "|", "   ", "|", "   ", "|", "   ", "|", "   ", "|"},
                                      {"  ", "|", "---", "|", "---", "|", "---", "|", "---", "|", "---", "|"},
                                      {"5 ", "|", "   ", "|", "   ", "|", "   ", "|", "   ", "|", "   ", "|"},
                                      {"  ", "|", "---", "|", "---", "|", "---", "|", "---", "|", "---", "|"}};
            
            Setor[][] matrizSetor = new Setor[5][5];

            //(Re)Inicia o contador de turnos
            turno = 1;

            //Cria um jogador simples p1, com 2 de ataque e 6 de defesa na posição (2,2)
            JogadorSimples p1 = new JogadorSimples((byte)2, (byte)6, (byte)2, (byte)2);

            //Cria um jogador suporte p2, com 1 de ataque e 7 de defesa na posição (2,2)
            JogadorSuporte p2 = new JogadorSuporte((byte)1, (byte)7, (byte)2, (byte)2);
            
            //Inicializa os setores da matriz com os valores padrão, para que seja possível fazer a verificação se um setor já foi visitado ou não, na hora de gerar um setor
            for (int i = 0; i < matrizSetor.length; i++) {
                for (int j = 0; j < matrizSetor[i].length; j++) {
                    matrizSetor[i][j] = new Setor();
                }
            }

            //Gera o setor inicial, do tipo normal (1), com todas as 4 portas abertas
            Personagem[] vetI = new Personagem[1];
            vetI[0] = new Inimigo((byte)0, (byte)0);
                    
            matrizSetor[2][2] = new Setor((byte) 1, true, true, true, true, true, vetI, (byte) 2, (byte) 2, matrizSetor);
            matrizSetor[2][2].setInimigosDerrotados(true);
            
            //Modifica a matriz de print para que seja exibido no setor inicial as 4 portas abertas e a letra C
            modificarMatrizPrint(matrizSetor, matrizPrint, p1);
            matrizPrint[2 * 2 + 2][2 * 2 + 2] = " C ";

            //Escolhe aleatóriamente um setor para ser a fonte do vírus
            do {
                iFonte = (byte) (rand.nextInt(5));      //Gera um número aleatório entre [0 a 5[
                jFonte = (byte) (rand.nextInt(5));      //Gera um número aleatório entre [0 a 5[
            } while ((iFonte == 2) && (jFonte == 2));        //Verifica se o setor escolhido foi o inicial, se sim, a escolha é feita novamente
            
            //O setor selecionado recebe true para o atributo fonte de vírus
            matrizSetor[iFonte][jFonte].setFonteVirus(true);
            //matrizPrint[2*iFonte+2][2*jFonte+2] = " X ";     //Exibe a fonte de vírus na matriz (para fins de teste)

            System.out.printf("\n\n\n");
               
            //O jogo começa e dura 25 turnos
            while (turno<=25) {
                System.out.printf("> Turno: %d\n\n", turno);
                Thread.sleep(1000);
                
                //Começa o turno do jogador 1
                p1.setAcoesJogador((byte) 2);
                while(p1.getAcoesJogador() != 0 && p1.getDefesa()>0){   //Enquanto o jogador tiver ações para realizar e não tiver sido derrotado (defesa>0)
                    
                    //Imprime a matriz
                    imprimirMatrizString(matrizPrint);
                    Thread.sleep(1000);
                    
                    System.out.printf("\nSetor [%d,%d] - T%d\n", p1.getPosM()+1, p1.getPosN()+1, matrizSetor[p1.getPosM()][p1.getPosN()].getTipoSetor());
                    
                    //Imprime o setor
                    matrizSetor[p1.getPosM()][p1.getPosN()].modificarSetorPrint(p1, p2, inputMenu);
                    imprimirMatrizString(matrizSetor[p1.getPosM()][p1.getPosN()].getSetorPrint());
                    Thread.sleep(1000);
                    
                    System.out.printf("\n      *** Turno do Jogador 1 ***\n");
                    System.out.printf("|-----------------------------------|\n"
                                    + "| > Z - Movimentar     > X - Atacar |\n"
                                    + "| > C - Procurar                    |\n"
                                    + "|-----------------------------------|\n");
                    System.out.printf("> Ações restantes: %d\n", p1.getAcoesJogador());
                    inputJogador = input.next().charAt(0);
                    System.out.println();
                    Thread.sleep(1000);
                    
                    switch(inputJogador){
                        case 'z':   //Movimentar
                            p1.movimentar(matrizSetor, matrizPrint);
                            modificarMatrizPrint(matrizSetor, matrizPrint, p1);
                            Thread.sleep(1000);
                            break;

                        case 'x':   //Atacar
                            p1.atacar(matrizSetor[p1.getPosM()][p1.getPosN()]);
                            Thread.sleep(1000);
                            break;

                        case 'c':   //Procurar
                            p1.procurar(matrizSetor[p1.getPosM()][p1.getPosN()]);
                            Thread.sleep(1000);
                            break;

                        default:
                            break;
                    }
                    
                    //Após uma ação, verifica se o jogador 1 chegou na fonte do vírus
                    if(p1.getPosM() == iFonte && p1.getPosN() == jFonte){
                        break;
                    }
                }
                
                //Termina o turno do jogador 1 e verifica se ele encontrou a fonte do vírus
                if(p1.getPosM() == iFonte && p1.getPosN() == jFonte){       //Se sim, exibe um mensagem de vitória e sai do loop dos 25 turnos
                    System.out.printf("|-----------------------|\n"
                                    + "|        VITÓRIA        |\n"
                                    + "|                       |\n"
                                    + "| O jogador 1 encontrou |\n"
                                    + "|    a fonte do vírus   |\n"
                                    + "|-----------------------|\n\n\n\n\n\n");
                    Thread.sleep(1000);
                    break;
                }
                
                System.out.printf("\nSetor [%d,%d] - T%d\n", p1.getPosM(), p1.getPosN(), matrizSetor[p1.getPosM()][p1.getPosN()].getTipoSetor());
                
                //Imprime o setor
                matrizSetor[p1.getPosM()][p1.getPosN()].modificarSetorPrint(p1, p2, inputMenu);
                imprimirMatrizString(matrizSetor[p1.getPosM()][p1.getPosN()].getSetorPrint());
                System.out.println("\n\n");
                Thread.sleep(1000);
                
                if(inputMenu == 2){     //Se a partida for de dois jogadores
                    
                    //Começa o turno do jogador 2
                    p2.setAcoesJogador((byte) 2);
                    while(p2.getAcoesJogador() != 0 && p2.getDefesa()>0){   //Enquanto o jogador tiver ações para realizar e não tiver sido derrotado (defesa>0)
                        
                        //Imprime a matriz
                        imprimirMatrizString(matrizPrint);
                        Thread.sleep(1000);
                        
                        System.out.printf("\nSetor [%d,%d] - T%d\n", p2.getPosM(), p2.getPosN(), matrizSetor[p2.getPosM()][p2.getPosN()].getTipoSetor());
                        
                        //Imprime o setor
                        matrizSetor[p2.getPosM()][p2.getPosN()].modificarSetorPrint(p2, p1, inputMenu);
                        imprimirMatrizString(matrizSetor[p2.getPosM()][p2.getPosN()].getSetorPrint());
                        Thread.sleep(1000);
                        
                        System.out.printf("\n           *** Turno do Jogador 2 ***\n");
                        System.out.printf("|---------------------------------------------|\n"
                                        + "| > Z - Movimentar     > X - Atacar           |\n"
                                        + "| > C - Procurar       > V - Recuperar defesa |\n"
                                        + "|---------------------------------------------|\n");
                        System.out.printf("> Ações restantes: %d\n", p2.getAcoesJogador());
                        inputJogador = input.next().charAt(0);
                        Thread.sleep(1000);
                        
                        switch(inputJogador){
                            case 'z':   //Movimentar
                                p2.movimentar(matrizSetor, matrizPrint);
                                modificarMatrizPrint(matrizSetor, matrizPrint, p2);
                                Thread.sleep(1000);
                                break;

                            case 'x':   //Atacar
                                p2.atacar(matrizSetor[p2.getPosM()][p2.getPosN()]);
                                Thread.sleep(1000);
                                break;

                            case 'c':   //Procurar
                                p2.procurar(matrizSetor[p2.getPosM()][p2.getPosN()]);
                                Thread.sleep(1000);
                                break;
                                
                            case 'v':   //Restaurar defesa
                                p2.recuperarDefesa(p1);
                                Thread.sleep(1000);
                                break;
                                
                            default:
                                break;
                        }
                        
                        //Após uma ação, verifica se o jogador 2 chegou na fonte do vírus
                        if(p2.getPosM() == iFonte && p2.getPosN() == jFonte){
                            break;
                        }
                    }
                    
                    //Termina o turno do jogador 2 e verifica se ele encontrou a fonte do vírus
                    if(p2.getPosM() == iFonte && p2.getPosN() == jFonte){   //Se sim, exibe um mensagem de vitória e sai do loop dos 25 turnos
                        System.out.printf("|-----------------------|\n"
                                        + "|        VITÓRIA        |\n"
                                        + "|                       |\n"
                                        + "| O jogador 2 encontrou |\n"
                                        + "|    a fonte do vírus   |\n"
                                        + "|-----------------------|\n\n\n\n\n\n");
                        Thread.sleep(1000);
                        break;
                    }
                    
                    System.out.printf("\nSetor [%d,%d] - T%d\n", p2.getPosM(), p2.getPosN(), matrizSetor[p2.getPosM()][p2.getPosN()].getTipoSetor());
                    
                    //Imprime o setor
                    matrizSetor[p2.getPosM()][p2.getPosN()].modificarSetorPrint(p2, p1, inputMenu);
                    imprimirMatrizString(matrizSetor[p2.getPosM()][p2.getPosN()].getSetorPrint());
                    System.out.println("\n\n");
                    Thread.sleep(1000); 
                }
                
                //Inicia o turno dos inimigos
                turnoInimigos(matrizSetor[p1.getPosM()][p1.getPosN()], p1);
                
                if(inputMenu == 2){     //Se a partida for de dois jogadores
                    turnoInimigos(matrizSetor[p2.getPosM()][p2.getPosN()], p2);
                }
                
                //Após o turno dos inimigos, verifica se os jogadores foram derrotados
                
                if(inputMenu == 2){     //Se a partida for de dois jogadores
                    if(p1.getDefesa()<=0 && p2.getDefesa()<=0){     //Se os jogadores não possuirem mais defesa
                        System.out.printf("|----------------------|\n"
                                        + "|        DERROTA       |\n"
                                        + "|                      |\n"
                                        + "|  Os jogadores foram  |\n"
                                        + "|      derrotados      |\n"
                                        + "|----------------------|\n\n\n\n\n\n");
                        Thread.sleep(1000);
                        break;
                    }
                }
                else{   //Se a partida for de um jogador
                    if(p1.getDefesa()<=0){      //Se o jogador 1 não possuir mais defesa
                        System.out.printf("|----------------------|\n"
                                        + "|        DERROTA       |\n"
                                        + "|                      |\n"
                                        + "|     O jogador foi    |\n"
                                        + "|       derrotado      |\n"
                                        + "|----------------------|\n\n\n\n\n\n");
                        Thread.sleep(1000);
                        break;
                    }
                }
                turno++;
            }
            
            if(turno>25){    //Se terminarem os 25 turnos, os jogadores perdem
                System.out.printf("|-------------------------|\n"
                                + "|         DERROTA         |\n"
                                + "|                         |\n"
                                + "|     Os jogadores não    |\n"
                                + "|   encontraram a fonte   |\n"
                                + "|-------------------------|\n\n\n\n\n\n");
                Thread.sleep(1000);
            }
        }while(inputMenu != 3);
    }
}