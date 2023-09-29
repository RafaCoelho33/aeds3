package externalStructures.Hash;

public class Hash {
    private int registers;
    private int[] tabela;
    private int n; //numero maximo de registros

    public Hash(){}

    public Hash(int size){
        tabela = new int[size];

    }

    public int getRegisters() {
        return registers;
    }

    public void setRegisters(int registers) {
        this.registers = registers;
    }

    public int[] getTabela() {
        return tabela;
    }

    public void setTabela(int[] tabela) {
        this.tabela = tabela;
    }

    public void extendTable(){
        this.n = n *2;
        int[] newTable = new int[n];
        tabela = newTable;
        
    }
}
