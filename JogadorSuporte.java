import java.util.Scanner;
import java.util.InputMismatchException;

public class JogadorSuporte extends JogadorSimples{
    
    //Construtor
    public JogadorSuporte(byte ataque, byte defesa, byte posM, byte posN){
        super(ataque, defesa, posM, posN);
    }
    
    //Método recuperarDefesa
    public void recuperarDefesa(JogadorSimples p1) throws InterruptedException{   //O método recuperarDefesa recebe o jogador simples como parâmetro
        Scanner input = new Scanner(System.in);
        boolean erro = true;
        
        byte jogadorAlvo = 0;
        
        //Tratamento da exceção do tipo InputMismatch
        do{
            try{
                System.out.printf("|---------------------|\n"
                                + "| > Jogador 1: %s    |\n"
                                + "| > Jogador 2: %s    |\n"
                                + "|---------------------|\n\n", p1.toString(), this.toString());
                System.out.printf("> Recuperar a defesa de qual jogador?\n");
                jogadorAlvo = input.nextByte();
                System.out.println();
                Thread.sleep(1000);
                
                erro = false;
            }
            catch(InputMismatchException e){
                System.err.println(e);
                System.err.println("Entrada de tipo inválido - insira um número inteiro\n");
                input.nextLine();
            }
        }while(erro);
        
        switch(jogadorAlvo){
            case 1:     //O jogador escolhido foi o jogador 1
                if(p1.getPosM()==this.getPosM() && p1.getPosN()==this.getPosN()){   //Se o jogador 1 e o jogador 2 estiverem no mesmo setor
                    p1.setDefesa((byte)(p1.getDefesa()+2));     //O jogador 1 ganha +2 DEF
                    
                    System.out.printf("|---------------------------|\n"
                                    + "|    Jogador 1 recuperou    |\n"
                                    + "|        +2 DEFESA          |\n"
                                    + "|---------------------------|\n\n");
                    
                    this.setAcoesJogador((byte)(this.getAcoesJogador()-1));     //Como a ação foi realizada, o jogador usou uma de suas ações no turno
                }
                else{
                    System.out.printf("|------------------------------|\n"
                                    + "| O jogador escolhido não está |\n"
                                    + "|           no setor           |\n"
                                    + "|------------------------------|\n\n");
                }
                break;
                
            case 2:     //O jogador escolhido foi o jogador 2
                this.setDefesa((byte)(this.getDefesa()+2));
                
                System.out.printf("|---------------------------|\n"
                                + "|    Jogador 2 recuperou    |\n"
                                + "|        +2 DEFESA          |\n"
                                + "|---------------------------|\n\n");
                
                this.setAcoesJogador((byte)(this.getAcoesJogador()-1));     //Como a ação foi realizada, o jogador usou uma de suas ações no turno
                break;
            
            default:    //Entrada inválida
                System.out.printf("|------------------------------|\n"
                                + "|       Jogador inválido!      |\n"
                                + "|------------------------------|\n\n");
                break;
        }
    }
}