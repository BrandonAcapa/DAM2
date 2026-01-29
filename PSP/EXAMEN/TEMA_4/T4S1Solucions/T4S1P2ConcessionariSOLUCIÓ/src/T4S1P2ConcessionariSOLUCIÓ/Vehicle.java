package T4S1P2ConcessionariSOLUCIÓ;

import java.io.Serializable;

public class Vehicle implements Serializable {

	String DNI, matricula, marca, model, combustible, any;

	// serialVersionUID
	private static final long serialVersionUID = 1L;

	// constructor
	public Vehicle(String DNI, String matricula, String marca, String model, String combustible, String any) {
		super();
		this.DNI = DNI;
		this.matricula = matricula;
		this.marca = marca;
		this.model = model;
		this.combustible = combustible;
		this.any = any;
	}

	public String getDNI() {
		return DNI;
	}

	public void setDNI(String DNI) {
		this.DNI = DNI;
	}

	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public String getMarca() {
		return marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getCombustible() {
		return combustible;
	}

	public void setCombustible(String combustible) {
		this.combustible = combustible;
	}

	public String getAny() {
		return any;
	}

	public void setAny(String any) {
		this.any = any;
	}
}
