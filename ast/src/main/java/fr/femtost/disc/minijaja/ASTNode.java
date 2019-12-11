package fr.femtost.disc.minijaja;

import fr.femtost.disc.minijaja.jcodes.JNil;

public abstract class ASTNode {

    private int line ;
    private int column ;

    public abstract String rewrite();

//    public void typeCheck(Memoire m){
//        //remettre en abstract a la fin du dev
//    }
    public abstract void typeCheck(Memoire m);

    public int getLine(){
        return this.line;
    }
    public int getColumn()
    {
        return this.column;
    }

    public void setLine(int line){
        this.line = line;
    }

    public void setColumn(int column){
        this.column = column;
    }

    public void setPosition (int line , int column){
        this.column = column;
        this.line = line;

    }


    public CompilationCouple compiler(int actual) {
        throw new IllegalArgumentException("Compilation not implemented in " + this.getClass().getName());
    }

    public CompilationCouple retirerCompile(int actual) {
    return new CompilationCouple(new JNil(), 0);
    }

    public abstract void interpreter(Memoire m);
    public abstract void retirer(Memoire m);

}

