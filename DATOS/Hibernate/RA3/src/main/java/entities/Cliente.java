package entities;

public class Cliente {
    private long id;
    private String firstName;
    private String lastName;
    private String email;
    private int edad;

    public Cliente() {
    }

    public Cliente(long id, String firstName, String lastName, String email, int edad) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.edad = edad;
    }

    public long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public int getEdad() {
        return edad;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }
}
