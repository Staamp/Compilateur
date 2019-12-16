package fr.femtost.disc.minijaja.ast.instr;

import fr.femtost.disc.minijaja.*;
import fr.femtost.disc.minijaja.ast.ASTInstr;
import fr.femtost.disc.minijaja.ast.expr.ASTIdentGenerique;
import fr.femtost.disc.minijaja.ast.expr.identificateur.Tableau;
import fr.femtost.disc.minijaja.jcode.AInc;
import fr.femtost.disc.minijaja.jcode.Inc;
import fr.femtost.disc.minijaja.jcode.Push;
import fr.femtost.disc.minijaja.jcodes.JChain;
import fr.femtost.disc.minijaja.jcodes.JNil;

public class Increment extends ASTInstr {

    private ASTIdentGenerique identGenerique;

    public Increment(ASTIdentGenerique identGenerique) {
        this.identGenerique = identGenerique;
    }

    @Override
    public String rewrite() {
        StringBuilder sb = new StringBuilder();
        sb.append(identGenerique.rewrite());
        sb.append("++");

        return sb.toString();
    }

    @Override
    public CompilationCouple compiler(int actual) {
        if (identGenerique instanceof Tableau) {
            CompilationCouple index = ((Tableau)identGenerique).getIndex(actual);

            return new CompilationCouple(JCodes.concatenate(index.jCodes,
                    new JChain(new Push(1), new JChain(new AInc(identGenerique.getName()), new JNil()))),
                    index.taille + 2);
        }
        return new CompilationCouple(new JChain(new Push(1), new JChain(new Inc(identGenerique.getName()), new JNil())), 2);
    }
    @Override
    public void interpreter(Memoire m){

        if(identGenerique instanceof Tableau)
        {
            int v = ((Tableau) identGenerique).evalIndex(m);
            try{
                m.getPile().AffecterValT(identGenerique.getName(),(int)m.getPile().ValT(identGenerique.getName(),v)+1,v);
            } catch (PileException e){
                ASTLogger.getInstance().logError(this,e.getMessage());
            }
        }
        else {
            try {
                m.getPile().AffecterVal(identGenerique.getName(),(int)(m.getPile().Val(identGenerique.getName()))+1);
            } catch (PileException e) {
                ASTLogger.getInstance().logError(this,e.getMessage());
            }
        }
    }


    @Override
    public void typeCheck(Memoire m) {

    }
}
