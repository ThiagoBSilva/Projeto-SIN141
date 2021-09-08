public abstract class Personagem{
    
    //Atributos dos personagens
    protected byte ataque, defesa;
    
    //Construtor
    public Personagem(byte ataque, byte defesa){
        this.setAtaque(ataque);
        this.setDefesa(defesa);
    }
    
    //Métodos get
    public byte getAtaque(){
        return this.ataque;
    }
    public byte getDefesa(){
        return this.defesa;
    }
    
    //Métodos set
    public void setAtaque(byte ataque){
        this.ataque = ataque;
    }
    public void setDefesa(byte defesa){
        if(defesa<0){
            this.defesa = 0;
        }
        else{
           this.defesa = defesa; 
        }
    }
    
    //Método toString
    @Override
    public String toString(){
        return String.format("%d/%d", this.getAtaque(), this.getDefesa());
    }
    
    //Método abstrato atacar
    public abstract void atacar(Personagem alvo);
}