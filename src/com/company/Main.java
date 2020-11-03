package com.company;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Main
{

    public static void main(String[] args)
    {
        System.out.println("test main");

        String url = "jdbc:mysql://localhost:3306/entreprise?serverTimezone=UTC";//pour les problemes de time zone
        //String url = "jdbc:mysql://localhost:3306/entreprise";
        String user = "olivier"; String pwd = "Killian-69";
        java.sql.Connection connexion = null;
        try
        {
            connexion = java.sql.DriverManager.getConnection(url, user, pwd);
            //L'object connexion va nous permettre d'effectuer des requêtes...
            //effectuer des requetes
            System.out.println("on a réussi a se connecter en tant que "+user);
        }
        catch ( java.sql.SQLException e )
        {
            //Problème de connexion à la base !
            System.out.println("probleme de connexion");
            System.out.println(e.getMessage());
        }




        try
        {

//            String nomTest = "T'OR'1'='1"; // injection SQL pour vidanger une base de donnée
            String nomTest = "Lacroix";


//            //La variable de type Statement permettra de gérer des requêtes SQL   solution statment pas securisé
//            Statement statement = connexion.createStatement();
//            //La variable de type ResultSet contiendra les résultats de la requêtes
//            String query = "SELECT id,nom FROM employe WHERE nom ='"+nomTest+"'";
//            ResultSet resultSet = statement.executeQuery(query);



            //La solution ? Les Prepared Statements     plus sécurisée

            String queryPrep = "SELECT id,nom FROM employe WHERE nom = ? ";
            PreparedStatement statement = connexion.prepareStatement(queryPrep);
            statement.setString(1,nomTest);

            ResultSet resultSet =statement.executeQuery();



            //On parcours un à un les résultats grâche à next() qui renvoie un booléen
            //précisant s'il y a une ligne suivante dans nos résultats et récupère ce
            //résultat le cas échéant (au début, son curseur est situé avant le premier élément).
            while(resultSet.next())
            {
                System.out.print("Identifiant : " + resultSet.getInt("id"));
                System.out.println(" Nom : " + resultSet.getString("nom"));

            }
        }
        catch (SQLException throwables)
        {
            throwables.printStackTrace();
        }


        //Lorsqu'on a fini de l'utiliser :
        if(connexion != null)
        {
            try
            {
                connexion.close();
                System.out.println("on a réussi a se deconnecter");
            }
            catch ( java.sql.SQLException e )
            {
                System.out.println("probleme de deconnexion");
                //Problème de déconnexion, ce n'est pas très grave...
            }
        }

    }
}
