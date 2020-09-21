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
public class Customer {
    static int u_id;
    static String u_name;
    static String u_email;
    static String u_address;
    static String u_pin;
    static String u_mobile;
    static String u_password;
    static Scanner s=new Scanner(System.in);
    Customer(int u_id,String name,String email,String address,String pin,String mobile,String password){
        this.u_id=u_id;
        this.u_name=name;
        this.u_email=email;
        this.u_address=address;
        this.u_pin=pin;
        this.u_mobile=mobile;
        this.u_password=password;
    }
    public static void userSignup(Connection con) throws Exception{
        try{
            PreparedStatement pst=con.prepareStatement("insert into Customer values(?,?,?,?,?,?,?)");
            //System.out.println(u_id);
            //System.out.println(u_name);
            pst.setInt(1,u_id);
            pst.setString(2,u_name);
            pst.setString(3,u_address);
            pst.setString(4,u_pin);
            pst.setString(5,u_email);
            pst.setString(6,u_mobile);
            pst.setString(7,u_password);
            pst.executeUpdate();
            pst.close();
            System.out.println("**********Signed up Successfull**********");
            System.out.println("Welcome to Grocery App Management\nView stock availability details");
            Shop.viewStock(con);
            System.out.println("\n Enter 1 for placeOrder");
            int ch=s.nextInt();
            if(ch==1){
                int c=u_id;
                Order.placeOrder(con,c);
            }
            else{
                System.out.println("**********EXIT**********");
            }
        }
        catch(Exception e){
            System.out.println("Error occured"+e);
        }
    }
    Customer(String email,String password){
        this.u_email=email;
        this.u_password=password;
    }
    public static void userLogin(Connection con) throws Exception{
        try{
            Statement st=con.createStatement();
            ResultSet rs=st.executeQuery("select * from Customer where Customer_email='"+u_email+"'");
            if(rs.next()){
                String Customer_password=rs.getString("Customer_password");
                if(u_password.equals(Customer_password)){
                    int Customer_id=rs.getInt("Customer_id");
                    String Customer_name=rs.getString("Customer_name");
                    System.out.println("Name: "+Customer_name+"\n"+"Id: "+Customer_id);
                    System.out.println("**********Login Successful**********");
                    System.out.println("Welcome to Grocery App Management\nView stock availability details");
                    Shop.viewStock(con);
                    System.out.println("\n Enter 1 for placeOrder");
                    int ch=s.nextInt();
                    if(ch==1){
                        Order.placeOrder(con,Customer_id);
                    }
                    else{
                        System.out.println("**********EXIT**********");
                    }
                }
                else{
                    System.out.println("Error password or email");
                }
            }
        }
        catch(Exception e){
            System.out.println("Error occured"+e);
        }
    }
}
