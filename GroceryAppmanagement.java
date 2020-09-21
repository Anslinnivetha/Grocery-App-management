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
import java.util.regex.Matcher;
import java.util.UUID;
public class GroceryAppmanagement {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {  
       //Class.forName("com.mysql.jdbc.Driver");
       Scanner in=new Scanner(System.in);
       try{
            Connection con=DriverManager.getConnection("jdbc:derby://localhost:1527/mydatabase","Anslin","ansi1512");
            //Statement st=con.createStatement();
            //ResultSet rs;
            System.out.println("Enter the choice for \n1.Customer \n2.Shop Owner \n3.Admin");
            int sign=in.nextInt();
            //in.nextLine();
            //Customer Sign in
            if(sign==1){
                System.out.println("\n1.Create account \n2.login");
                int c=in.nextInt();
                in.nextLine();
                //rs=st.executeQuery("INSERT INTO CUSTOMER CUSTOMER_NAME VALUES "+name);
                //rs.next();
                //System.out.println(rs.getString(1));
                if(c==1){
                    System.out.println("Enter name: ");
                    String u_name="";
                    u_name=in.nextLine();
                    String u_email="";
                    boolean f=false;
                    while(f==false){
                        System.out.println("Enter email: ");
                        u_email=in.nextLine();
                        String regex="^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$";
                        f=u_email.matches(regex);
                    }
                    String u_address="";
                    System.out.println("Enter address: ");
                    u_address=in.nextLine();
                    String u_pin="";
                    boolean f1=false;
                    while(f1==false){
                        System.out.println("Enter pincode: ");
                        u_pin=in.nextLine();
                        f1=u_pin.matches("\\d{6}");
                    }
                    String u_mobile="";
                    boolean f2=false;
                    while(f2==false){
                        System.out.println("Enter mobile no: ");
                        u_mobile=in.nextLine();
                        f2=u_mobile.matches("\\d{10}");
                    }
                    String u_password="";
                    boolean f3=false;
                    while(f3==false){
                        System.out.println("Enter a Strong password: ");
                        u_password=in.nextLine();
                        String regex="^(?=.*[0-9])"+"(?=.*[a-z])(?=.*[A-Z])"+"(?=.*[@#$%^&+=])"+"(?=\\S+$).{8,20}$";
                        f3=u_password.matches(regex);
                    }
                    UUID g=UUID.randomUUID();
                    String str=""+g;
                    int a=str.hashCode();
                    String h=""+a;
                    str=h.replaceAll("-","");
                    int u_id=Integer.parseInt(str);
                    Customer c1=new Customer(u_id,u_name,u_email,u_address,u_pin,u_mobile,u_password);
                    c1.userSignup(con);
                }
                if(c==2){
                    String u_email="";
                    boolean f=false;
                    while(f==false){
                        System.out.println("Enter email: ");
                        u_email=in.nextLine();
                        String regex="^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$";
                        f=u_email.matches(regex);
                    }
                    System.out.println("Enter password: ");
                    String u_password="";
                    u_password=in.nextLine();
                    Customer c2=new Customer(u_email,u_password);
                    c2.userLogin(con);
                }
            }
            //Shop Owner Sign in
            if(sign==2){
                in.nextLine();
                System.out.println("Enter E-mail: ");
                String sh_email=in.nextLine();
                System.out.println("Enter Password: ");
                String sh_password=in.nextLine();
                Shop s1=new Shop(sh_email,sh_password);
                s1.shopLogin(con); 
            }
            //Admin Sign in
            if(sign==3){
                Admin.adminLogin(con);
            }
        }
        catch(Exception e){
            System.out.println(e);
       }
    }
}
