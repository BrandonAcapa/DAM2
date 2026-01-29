package T4S1Exemple1_SerializacioAFitxer;

import java.io.*;

public class EmpleatPrincipal {
	public static void main(String[] args) {
		try {

			// CREE I OMPLIC L'OBJECTE DE LA CLASSE empleat
			Empleat empleatGuardar = new Empleat();
			empleatGuardar.setId(1);
			empleatGuardar.setNom("Joan");
			empleatGuardar.setDepartment("Informàtica");
			empleatGuardar.setNivell(5.0);
			System.out.println("Valors de l'objecte a serialitzar = " + empleatGuardar.getNom() + ";"
					+ empleatGuardar.getId() + ";" + empleatGuardar.getDepartment() + ";" + empleatGuardar.getNivell());

			// ESCRITURA AL FITXER
			File file = new File("Serialitzacio_A_Fitxer.txt");
			FileOutputStream foStream = new FileOutputStream(file);
			ObjectOutputStream oStream = new ObjectOutputStream(foStream);
			// GUARDE L'OBJECTE DE LA CLASSE Empleat AL FITXER
			oStream.writeObject(empleatGuardar);

			// LECTURA DEL FITXER
			ObjectInputStream iStream = new ObjectInputStream(new FileInputStream(file));
			// LLIG L'OBJECTE DE LA CLASSE Empleat DEL FITXER
			Empleat empleatRecuperar = (Empleat) iStream.readObject();
			System.out.println("Valors de l'objecte a deserialitzar = " + empleatRecuperar.getNom() + ";"
					+ empleatRecuperar.getId() + ";" + empleatRecuperar.getDepartment() + ";"
					+ empleatRecuperar.getNivell());

			// TANQUE FLUXES
			oStream.close();
			iStream.close();

		} catch (FileNotFoundException fn) {
			fn.printStackTrace();
		} catch (IOException io) {
			io.printStackTrace();
		} catch (ClassNotFoundException cnf) {
			cnf.printStackTrace();
		}
	}
}