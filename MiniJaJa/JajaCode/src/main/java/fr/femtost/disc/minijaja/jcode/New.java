package fr.femtost.disc.minijaja.jcode;

import fr.femtost.disc.minijaja.*;

public class New extends JCode {

    private String ident;
    private Sorte type; //Memoire
    private JCSorte sorte;
    private int val; //Profondeur dans la pile où chercher la valeur

    public New(String ident, Sorte type, JCSorte sorte, int val) {
        this.ident = ident;
        this.type = type;
        this.sorte = sorte;
        this.val = val;
    }

    @Override
    public String rewrite() {
        return "new(" + ident + "," + type.name() + "," + sorte.name() + "," + val + ")";
    }

    @Override
    public int interpreter(Memoire m, int current) {
        try {
            switch (sorte) {
                case VARIABLE:
                    m.getPile().IdentVal(ident, type, val);
                    return current + 1;
                case CONSTANTE:
                    m.getPile().DeclCst(ident, m.getPile().Depiler().getVAL(), type);
                    return current + 1;
                case METHODE:
                    m.getPile().DeclMeth(ident, m.getPile().Depiler().getVAL(), type);
                    return current + 1;
            }
            ASTLogger.getInstance().logWarning("Unexpected sort for new: " + type.name());
            return -1;
        } catch (PileException e) {
            ASTLogger.getInstance().logError(e.getMessage());
            return -1;
        }
    }
}
