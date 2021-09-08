import java.util.Random;

public class Inimigo extends Personagem{
    
    //Construtor
    public Inimigo(byte ataque, byte defesa){
        super(ataque, defesa);
    }
    
    //Método atacar
    @Override
    public void atacar(Personagem jogadorAlvo){    //O método atacar recebe um jogador alvo como parâmetro
        Random rand = new Random();
        int dado;
        dado = rand.nextInt(6) + 1;     //Gera um número entre [0 e 6[ e depois soma-se um, para que o número esteja no intervalo de [1 e 6]
        
        if(this.getDefesa()>0){     //Se o inimigo ainda não foi derrotado (defesa > 0)
            if(dado%2 == 0){    //Se o número entre [1 e 6] aleatório for par, o ataque é realizado
                jogadorAlvo.setDefesa((byte)(jogadorAlvo.getDefesa()-this.getAtaque()));    //A defesa do jogador alvo é reduzida pelo valor de ataque do inimigo atacante
                
                if(jogadorAlvo instanceof JogadorSuporte){
                    System.out.printf("|---------------------------|\n"
                                    + "| O jogador 2 foi atingido! |\n"
                                    + "|         -%d DEFESA         |\n"
                                    + "|---------------------------|\n\n", this.getAtaque());
                }
                else{
                    System.out.printf("|---------------------------|\n"
                                    + "| O jogador 1 foi atingido! |\n"
                                    + "|         -%d DEFESA         |\n"
                                    + "|---------------------------|\n\n", this.getAtaque());
                }  
            }
            else{
                System.out.printf("|--------------------------|\n"
                                + "| O inimigo errou o ataque |\n"
                                + "|--------------------------|\n\n");
            }
        }
        //Se o ataque não for realizado, a defesa do alvo não é reduzida
    }
}