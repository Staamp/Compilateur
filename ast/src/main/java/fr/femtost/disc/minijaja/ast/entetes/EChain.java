package fr.femtost.disc.minijaja.ast.entetes;

import fr.femtost.disc.minijaja.CompilationCouple;
import fr.femtost.disc.minijaja.JCodes;
import fr.femtost.disc.minijaja.Memoire;
import fr.femtost.disc.minijaja.ast.ASTEntete;
import fr.femtost.disc.minijaja.ast.ASTEntetes;
import fr.femtost.disc.minijaja.ast.type.Entier;

public class EChain extends ASTEntetes {
    private ASTEntetes successor;
    private ASTEntete node;
    private int position = 0;

    public EChain(ASTEntetes successor, ASTEntete node) {
        this.successor = successor;
        this.node = node;
    }

    public String rewrite() {
        StringBuilder sb = new StringBuilder();

        sb.append(node.rewrite());
        if(successor instanceof EChain) {
            sb.append(", ");
            sb.append(successor.rewrite());
        }

        return sb.toString();
    }

    @Override
    public CompilationCouple compiler(int actual) {
        CompilationCouple ens = successor.compiler(actual);
        this.position = successor instanceof Enil ? 0 : successor.getChainPosition();
        node.setPosition(position);
        CompilationCouple en = node.compiler(actual + ens.taille);
        return new CompilationCouple(JCodes.concatenate(ens.jCodes, en.jCodes), en.taille + ens.taille);
    }

    @Override
    public void interpreter(Memoire m) {

    }

    @Override
    public void retirer(Memoire m) {

    }

    @Override
    public int getChainPosition() {
        return position;
    }
}
