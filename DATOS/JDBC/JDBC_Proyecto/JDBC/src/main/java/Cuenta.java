public class Cuenta {
    private int id;
    private String codigo;
    private int saldo;

    public Cuenta(){}

    public Cuenta(int id, String codigo, int saldo) {
        this.id = id;
        this.codigo = codigo;
        this.saldo = saldo;
    }

    public int getId() {
        return id;
    }

    public String getCodigo() {
        return codigo;
    }

    public int getSaldo() {
        return saldo;
    }

    public void setId(int id){
        this.id = id;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public void setSaldo(int saldo) {
        this.saldo = saldo;
    }

    @Override
    public String toString() {
        return "Cuenta{" +
                "id=" + id +
                ", codigo='" + codigo + '\'' +
                ", saldo=" + saldo +
                '}';
    }
}
