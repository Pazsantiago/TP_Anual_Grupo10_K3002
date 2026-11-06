package org.example.servicios;
import java.util.Scanner;
public class ServicioRegistro {
    


	
	public PersonaHumana registrarPersonaHumana() { 
		teclado = new Scanner(System.in);
        String nombre, apellido,documento, genero, direccion, correoElectronico;
        int edad; 
        System.out.println("Ingrese el nombre:");
        nombre = teclado.nextLine();
        System.out.println("Ingrese el apellido:");
        apellido = teclado.nextLine();
        System.out.println("Ingrese el documento:");
        documento = teclado.nextLine();
        System.out.println("Ingrese el género:");
        genero = teclado.nextLine();
        System.out.println("Ingrese la dirección:");
        direccion = teclado.nextLine();
        System.out.println("Ingrese el correo electrónico:");
        correoElectronico = teclado.nextLine();
        System.out.println("Ingrese la edad:");
        edad = teclado.nextInt();
        PersonaHumana temp = new PersonaHumana(nombre, apellido, edad, documento, genero, direccion, correoElectronico);
        return temp;
	}
	
	public PersonaJuridica registrarPersonaJuridica() {
		teclado = new Scanner(System.in);
        String razonSocial, rubro, contactoInicial;
        TipoEmpresa tipoEmpresa;
        int opcion; 
        int cuit; 
        System.out.println("Seleccione el tipo de Ente Juridico:");
        System.out.println("1. Gubernamental");       
        System.out.println("2. ONG");
        System.out.println("3. Empresa");
        System.out.println("4. Institución"); 
        opcion = teclado.nextInt();
        teclado.nextLine(); // Consumir el salto de línea
        switch (opcion) {
            case 1 -> tipoEmpresa = TipoEmpresa.GUBERNAMENTAL;
            case 2 -> tipoEmpresa = TipoEmpresa.ONG;
            case 3 -> tipoEmpresa = TipoEmpresa.EMPRESA;
            case 4 -> tipoEmpresa = TipoEmpresa.INSTITUCION;
            default -> throw new IllegalArgumentException("Opción inválida. Debe ser entre 1 y 4.");
        }
        System.out.println("Ingrese la razón social:");
        razonSocial = teclado.nextLine();
        System.out.println("Ingrese la dirección:");
        direccion = teclado.nextLine();
        System.out.println("Ingrese el correo electrónico:");
        correoElectronico = teclado.nextLine();
        System.out.println("Ingrese el CUIT:");
        cuit = teclado.nextInt();
        PersonaJuridica temp = new PersonaJuridica(razonSocial, tipoEmpresa, rubro, cuit, contactoInicial);
        return temp;
	}
}
