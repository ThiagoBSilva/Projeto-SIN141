import java.util.Random;

public class Setor{
    
    //Atributos do setores
    private byte tipoSetor;     //Pode assumir '1' quando for um setor normal, '2' quando for um setor oculto e '3' quando for um setor privado
    private boolean paredeCima, paredeBaixo, paredeEsquerda, paredeDireita;     //Pode assumir 'true' para quando tiver uma porta aberta e 'false' quando não tiver
    private boolean visitado;       //Recebe true ao ser visitado, para evitar que o setor em questão seja gerado novamente
    private boolean fonteVirus;     //No começo do jogo uma posição (m,n) da matriz é sorteada, se ela for diferente de (3,3) o atributo fonteVirus recebe true
    private Personagem[] vetInimigos;       //Vetor de inimigos no setor
    private boolean inimigosDerrotados;     //Recebe true se não houverem mais inimigos no setor
    
    private String[][] setorPrint = {{"|", "-", "-", "-", "-", "-", "-", "-", "-", "-", "-", "-", "-", "-", "|"},     //Todo setor tem uma representação como uma matriz de string
                                     {"|", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", "|"},
                                     {"|", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", "|"},
                                     {"|", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", "|"},
                                     {"|", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", "|"},
                                     {"|", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", "|"},
                                     {"|", "-", "-", "-", "-", "-", "-", "-", "-", "-", "-", "-", "-", "-", "|"}};
    
    //Construtor padrão
    public Setor(){     //Para inicializar os setores com valores padrão  
    }
    
    //Construtor
    public Setor(byte tipoSetor, boolean paredeCima, boolean paredeBaixo, boolean paredeEsquerda, boolean paredeDireita, boolean visitado, Personagem[] vetInimigos, byte m, byte n, Setor[][] matrizSetor){
        this.setTipoSetor(tipoSetor);
        this.setParedeCima(paredeCima, m, n, matrizSetor);
        this.setParedeBaixo(paredeBaixo, m, n, matrizSetor);
        this.setParedeEsquerda(paredeEsquerda, m, n, matrizSetor);
        this.setParedeDireita(paredeDireita, m, n, matrizSetor);
        this.setVisitado(visitado);
        this.setVetInimigos(vetInimigos);
    }
    
    //Métodos get
    public byte getTipoSetor(){
        return this.tipoSetor;
    }
    public boolean getParedeCima(){
        return this.paredeCima;
    }
    public boolean getParedeBaixo(){
        return this.paredeBaixo;
    }
    public boolean getParedeEsquerda(){
        return this.paredeEsquerda;
    }
    public boolean getParedeDireita(){
        return this.paredeDireita;
    }
    public boolean getVisitado(){
        return this.visitado;
    }
    public boolean getFonteVirus(){
        return this.fonteVirus;
    }
    public Personagem[] getVetInimigos(){
        return this.vetInimigos;
    }
    public boolean getInimigosDerrotados(){
        return this.inimigosDerrotados;
    }
    public String[][] getSetorPrint(){
        return this.setorPrint;
    }
    
    //Métodos set
    public void setTipoSetor(byte tipoSetor){
        if(tipoSetor >= 1 && tipoSetor <= 3){
            this.tipoSetor = tipoSetor;
        }
    }
    public void setParedeCima(boolean paredeCima, byte m, byte n, Setor[][] matrizSetor){    //Recebe um valor gerado aleatóriamente, a posição m e n do setor na matriz e a matriz de setores
        if(m == 0){     //Se m == 0, a parede de cima é o limite superior da matriz, logo, ela não deve ter portas abertas
            this.paredeCima = false;
        }
        else{   //Caso contrário,
            if(matrizSetor[m-1][n].getVisitado()){    //Verifica se o setor acima já foi visitado, se sim, isso significa que a parede de baixo do setor acima é a parede de cima do setor em questão
                this.paredeCima = matrizSetor[m-1][n].getParedeBaixo();
            }
            else{   //Se não, a parede de cima é atribuída com o valor aleatório
                this.paredeCima = paredeCima;
            }
        }
    }
    public void setParedeBaixo(boolean paredeBaixo, byte m, byte n, Setor[][] matrizSetor){   //Recebe um valor gerado aleatóriamente, a posição m e n do setor na matriz e a matriz de setores
        if(m == 4){     //Se m == 4, a parede de baixo é o limite inferior da matriz, logo, ela não deve ter portas abertas
            this.paredeBaixo = false;
        }
        else{   //Caso contrário,
            if(matrizSetor[m+1][n].getVisitado()){    //Verifica se o setor abaixo já foi visitado, se sim, isso significa que a parede de cima do setor abaixo é a parede de baixo do setor em questão
                this.paredeBaixo = matrizSetor[m+1][n].getParedeCima();
            }
            else{   //Se não, a parede de baixo é atribuída com o valor aleatório
                this.paredeBaixo = paredeBaixo;
            }
        }
    }
    public void setParedeEsquerda(boolean paredeEsquerda, byte m, byte n, Setor[][] matrizSetor){     //Recebe um valor gerado automáticamente, a posição m e n do setor na matriz e a matriz de setores
        if(n == 0){     //Se n == 0, a parede esquerda é o limite esquerdo da matriz, logo ela não deve ter portas abertas
            this.paredeEsquerda = false;
        }
        else{   //Caso contrário, 
            if(matrizSetor[m][n-1].getVisitado()){    //Verifica se o setor à esquerda já foi visitado, se sim, isso significa que a parede direita do setor à esquerda é a parede esquerda do setor em questão
                this.paredeEsquerda = matrizSetor[m][n-1].getParedeDireita();
            }
            else{   //Se não, a parede esquerda é atribuída com o valor aleatório
                this.paredeEsquerda = paredeEsquerda;
            }
        }
    }
    public void setParedeDireita(boolean paredeDireita, byte m, byte n, Setor[][] matrizSetor){      //Recebe um valor gerado automáticamente, a posição m e n do setor na matriz e a matriz de setores
        if(n == 4){     //Se n == 4, a parede direita é o limite direito da matriz, logo ela não deve ter portas abertas
            this.paredeDireita = false;
        }
        else{   //Caso contrário, 
            if(matrizSetor[m][n+1].getVisitado()){    //Verifica se o setor à direita já foi visitado, se sim, isso significa que aparede esquerda do setor à direita é a parede direita do setor em questão
                this.paredeDireita = matrizSetor[m][n+1].getParedeEsquerda();
            }
            else{   //Se não, a parede direita é atribuída com o valor aleatório
                this.paredeDireita = paredeDireita;
            }
        }
    }
    public void setVisitado(boolean visitado){
        this.visitado = visitado;
    }
    public void setFonteVirus(boolean fonteVirus){
        this.fonteVirus = fonteVirus;
    }
    public void setVetInimigos(Personagem[] vetInimigos){
        this.vetInimigos = vetInimigos;
    }
    public void setInimigosDerrotados(boolean inimigosDerrotados){
        
        this.inimigosDerrotados = inimigosDerrotados;       //Realiza a atribuição
        
        for(int i=0; i<this.getVetInimigos().length; i++){      //Corre o vetor de inimigos
            if(this.getVetInimigos()[i].getDefesa() > 0){   //Se houver inimigos vivos, o atributo recebe false
                this.inimigosDerrotados = false;
            }
        }
    }
    public void setSetorPrint(String[][] setorPrint){
        this.setorPrint = setorPrint;
    }
    
    //Método gerarSetor
    public static Setor gerarSetor(byte m, byte n, Setor[][] matrizSetor){
        
        //Criação do objeto da classe Random
        Random rand = new Random();
        
        //Variáveis locais para guardar os valores aleatórios
        byte tipo, nInimigos, atributoInimigo;
        boolean[] paredes = new boolean[4];     //Vetor de paredes, que representam as paredes de cima, de baixo, esquerda e direita, respectivamente
        Personagem[] vetI;
        
        //Gera valores aleatórios para os atributos dos setores
        tipo = (byte)(rand.nextInt(3)+1);   //tipo do setor
        
        for(int i=0; i<paredes.length; i++){
            if((rand.nextInt(10)+1) > 3){   //Se o valor gerado for maior que 3, a parede i possui uma porta aberta
                paredes[i] = true;
            }
            else{   //Se o valor gerado for menor que 3, a parede i não possui uma porta aberta
                paredes[i] = false;
            }
        }
        
        nInimigos = (byte)(rand.nextInt(5)+1); //número de inimigos no setor, varia de [1 a 5]
        vetI = new Personagem[nInimigos];
        
        for(int i=0; i<vetI.length; i++){   //Para cada inimigo no vetor de inimigos, gera atributos de ataque e defesa aleatórios, sendo que o valor do ataque = valor da defesa
            atributoInimigo = (byte)(rand.nextInt(3)+1);
            vetI[i] = new Inimigo(atributoInimigo, atributoInimigo);
        }
        
        //Retorna o setor gerado, agora com o atributo visitado = true
        return new Setor(tipo, paredes[0], paredes[1], paredes[2], paredes[3], true, vetI, m, n, matrizSetor);
    }
    
    //Método modificarSetorPrint
    public void modificarSetorPrint(JogadorSimples a, JogadorSimples b, byte inputMenu){    //Recebe um jogador a, que está no setor em questão, um jogador b que pode ou não estar no setor e a opção do menu
        
        //Vai verificar se neste setor há portas abertas, se sim o setor de print recebe strings que simbolizam portas abertas
        if(this.getParedeCima()){
            this.setorPrint[0][7] = "*";
        }
        if(this.getParedeBaixo()){
            this.setorPrint[6][7] = "*";
        }
        if(this.getParedeEsquerda()){
            this.setorPrint[3][0] = "*";
        }
        if(this.getParedeDireita()){
            this.setorPrint[3][14] = "*";
        }
        
        //Vai colocar o jogador ou os jogadores na representação do setor
        if(inputMenu != 2){     //Se a partida for de um jogador
            this.setorPrint[4][3] = "P";
            this.setorPrint[4][4] = "1";
            this.setorPrint[5][3] = Integer.toString(a.getAtaque());    //Transforma o valor de ataque em uma string
            this.setorPrint[5][4] = "/";
            this.setorPrint[5][5] = Integer.toString(a.getDefesa());    //Transforma o valor de defesa em uma string
        }
        else{       //Se a partida for de dois jogadores
            if(a.getPosM() != b.getPosM() || a.getPosN() != b.getPosN()){   //Se os jogadores estiverem em setores diferentes
                this.setorPrint[4][3] = "P";
                if(a instanceof JogadorSuporte){
                    this.setorPrint[4][4] = "2";
                }
                else{
                    this.setorPrint[4][4] = "1";
                }
                this.setorPrint[5][3] = Integer.toString(a.getAtaque());    //Transforma o valor de ataque em uma string
                this.setorPrint[5][4] = "/";
                this.setorPrint[5][5] = Integer.toString(a.getDefesa());    //Transforma o valor de defesa em uma string
                
                //Limpa a representação quando um dos jogadores deixa o setor
                this.setorPrint[4][9] = " ";    
                this.setorPrint[4][10] = " ";
                this.setorPrint[5][9] = " ";
                this.setorPrint[5][10] = " ";
                this.setorPrint[5][11] = " ";
            }
            else{   //Se os jogadores estiverem em setores iguais
                this.setorPrint[4][3] = "P";
                this.setorPrint[4][4] = "1";
                this.setorPrint[5][4] = "/";
                
                this.setorPrint[4][9] = "P";
                this.setorPrint[4][10] = "2";
                this.setorPrint[5][10] = "/";
                
                if(a instanceof JogadorSuporte){
                    this.setorPrint[5][3] = Integer.toString(b.getAtaque());    
                    this.setorPrint[5][5] = Integer.toString(b.getDefesa());
                    
                    this.setorPrint[5][9] = Integer.toString(a.getAtaque());  
                    this.setorPrint[5][11] = Integer.toString(a.getDefesa());
                }
                else{
                    this.setorPrint[5][3] = Integer.toString(a.getAtaque());    
                    this.setorPrint[5][5] = Integer.toString(a.getDefesa());
                    
                    this.setorPrint[5][9] = Integer.toString(b.getAtaque());  
                    this.setorPrint[5][11] = Integer.toString(b.getDefesa());
                }
            }
        }
        
        //Vai colocar os inimigos na representação do setor
        int n1 = 2;
        int n2 = 3;
        
        for(int i=0; i<this.getVetInimigos().length; i++){
            if(this.getVetInimigos()[i].getDefesa()>0){     //Se o inimigo não foi derrotado
                if(i<3){    //Representa os 3 primeiros inimigos na linha de cima
                    this.setorPrint[1][n1] =  Integer.toString(this.getVetInimigos()[i].getAtaque());
                    this.setorPrint[1][n1+1] = "/";
                    this.setorPrint[1][n1+2] = Integer.toString(this.getVetInimigos()[i].getDefesa());
                    n1 = n1+4;
                }
                else{   //Representa os 2 últimos inimigos na linha de baixo
                    this.setorPrint[2][n2] =  Integer.toString(this.getVetInimigos()[i].getAtaque());
                    this.setorPrint[2][n2+1] = "/";
                    this.setorPrint[2][n2+2] = Integer.toString(this.getVetInimigos()[i].getDefesa());
                    n2 = n2+6;
                }
            }
            else{   //Se o inimigo foi derrotado, ele é apagado da representação
                if(i<3){
                    this.setorPrint[1][n1] =  " ";
                    this.setorPrint[1][n1+1] = " ";
                    this.setorPrint[1][n1+2] = " ";
                    n1 = n1+4;
                }
                else{
                    this.setorPrint[2][n2] =  " ";
                    this.setorPrint[2][n2+1] = " ";
                    this.setorPrint[2][n2+2] = " ";
                    n2 = n2+6;
                }
            }
        }
    }
}