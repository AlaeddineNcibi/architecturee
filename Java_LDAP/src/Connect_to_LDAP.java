/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author eleve
 */

import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.directory.Attributes;
import javax.naming.directory.Attribute;
import javax.naming.directory.BasicAttribute;
import javax.naming.directory.BasicAttributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
 
public class Connect_to_LDAP {
 
	public static void main(String[] args) {
 
		//Adresse du serveur sur lequel se trouve l'annuaire LDAP
		String serverIP = "localhost";
		//Port du serveur sur lequel se trouve l'annuaire LDAP
		String serverPort = "10389";
		//Login de connexion à l'annuaire LDAP : Le login dois être sous forme de "distinguished name"
		//ce qui signifie qu'il dois être affiché sous la forme de son arborescence LDAP
				//String serverLogin = "uid=bvincent,ou=professors, ou=people,DC=ensta,DC=fr";
              // String serverLogin = "cn= Directory Manager";
		//String serverLogin = "uid=admin,ou=system";
		String serverLogin = "employeeNumber=3,ou=users,o=company";
		//Mot de passe de connexion à l'annuaire LDAP
		//String serverPass = "secret";
               String serverPass = "password";
 
		//On remplis un tableau avec les parametres d'environement et de connexion au LDAP
		Hashtable environnement = new Hashtable();
		environnement.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
		environnement.put(Context.PROVIDER_URL, "ldap://"+serverIP+":"+serverPort+"/");
                // Enable connection pooling
                environnement.put("com.sun.jndi.ldap.connect.pool", "true");
		environnement.put(Context.SECURITY_AUTHENTICATION, "simple");
		environnement.put(Context.SECURITY_PRINCIPAL, serverLogin);
		environnement.put(Context.SECURITY_CREDENTIALS, serverPass);
		try {
			//On appelle le contexte à partir de l'environnement
			DirContext contexte = new InitialDirContext(environnement);
			//Si ça ne plante pas c'est que la connexion est faite
			System.out.println("Connexion au serveur : SUCCES");
			try {
				//On recupere l'attribut de DUPONT JEAN
				//Attributes attrs = contexte.getAttributes("uid=bvincent,ou=Professors, ou= People,DC=ensta,DC=fr");
				Attributes attrs = contexte.getAttributes("employeeNumber=2,ou=users,o=company");

				System.out.println("Recuperation de dupont : SUCCES");
				//On affiche le nom complet de dupont
				//System.out.println(attrs.get("Firstname"));
				//On affiche le mail de dupont
				//System.out.println(attrs.get("userPassword"));
                                System.out.println(attrs.get("cn"));
                               
                 Attributes new_attrs = new BasicAttributes();
                 Attribute new_attr= new BasicAttribute("cn");
                 new_attr.add("Haji");
                 new_attrs.put(new_attr);
               //  new_attrs.put("telephoneNumber", "0621317497");
                 
                              //  System.out.println(new_attrs.get("uid"));
                 contexte.modifyAttributes("employeeNumber=2,ou=users,o=company", DirContext.REPLACE_ATTRIBUTE, new_attrs);
                                

                                contexte.close();
			} catch (NamingException e) {
				System.out.println("Recuperation de dupont : ECHEC");
				e.printStackTrace();
			}
		} catch (NamingException e) {
			System.out.println("Connexion au serveur : ECHEC");
			e.printStackTrace();
		}
	}
}