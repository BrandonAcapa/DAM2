public class Coche {
    long id;
    String modelo;
    String marca;

    public Coche(){};

    public Coche(long id, String modelo, String marca){
        this.id = id;
        this.modelo = modelo;
        this.marca = marca;
    };

    public long getId(){
        return this.id;
    }

    public String getModelo(){
        return this.modelo;
    }

    public String getMarca(){
        return this.marca;
    }

    public void setId(long id){
        this.id = id;
    }

    public void setModelo(String modelo){
        this.modelo = modelo;
    }

    public void setMarca(String marca){
        this.marca = marca;
    }
}
