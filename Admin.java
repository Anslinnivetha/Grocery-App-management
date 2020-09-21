/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package groceryappmanagement;

/**
 *
 * @author anbu
 */
import java.util.*;
import java.sql.*;
public class Admin {
    static Scanner s=new Scanner(System.in);
    public static void adminLogin(Connection con)throws Exception{
        try{
            String a_mail="admin@gmail.com";
            String a_pw="Admin1234";
            System.out.println("Enter Admin Email: ");
            String e=s.nextLine();
            if(e.equals(a_mail)){
                System.out.println("Enter Password: ");
                String p=s.nextLine();
                if(p.equals(a_pw)){
                    System.out.println("Enter choice: \n 1-view Stock \n 2-view Order");
                    int c=s.nextInt();
                    if(c==1){
                        Shop.viewStock(con);
                    }
                    else if(c==2){
                        Shop.viewOrder(con);
                    }
                    else{
                        System.out.println("***********EXIT**********");
                    }
                }
                else{
                    System.out.println("Invalid password");
                }
            }
            else{
                System.out.println("Invalid Email");
            }
        }
        catch(Exception e){
            System.out.println("Invalid login "+e);
        }
    }
}
