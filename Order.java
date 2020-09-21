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
import java.util.UUID;
public class Order {
    static Scanner in=new Scanner(System.in);
    public static void placeOrder(Connection con,int c_id)throws SQLException{
        UUID g=UUID.randomUUID();
        String str=""+g;
        int a=str.hashCode();
        String h=""+a;
        str=h.replaceAll("-","");
        int o_id=Integer.parseInt(str);
        try{
            System.out.println("Enter the product name for order: ");
            String prod_name=in.nextLine();
            Statement st=con.createStatement();
            ResultSet rs=st.executeQuery("select * from Shop where Product_name='"+prod_name+"'");
            if(rs.next()){
                int prod_id=rs.getInt("Product_id");
                double stock=rs.getDouble("Stock");
                double rate=rs.getDouble("Rate_perkg");
                if(stock==0){
                    System.out.println("Out of stock!!! Order cannot be placed!!!");
                }
                else{
                    System.out.println("Enter the Quantity required");
                    double q=in.nextDouble();
                    //in.nextLine();
                    if(q<=stock){
                        try{
                            PreparedStatement pst=con.prepareStatement("insert into Orders values(?,?,?,?,?,?,?)");
                            pst.setInt(1,o_id);
                            pst.setInt(2,c_id);
                            pst.setInt(3,prod_id);
                            pst.setString(4,prod_name);
                            pst.setDouble(5,rate);
                            pst.setDouble(6,q);
                            double amt=rate*q;
                            pst.setDouble(7,amt);
                            pst.executeUpdate();
                            pst.close();
                            st.close();
                            System.out.println("Order place Successfully!!!");
                            stock=stock-q;
                            Statement st1=con.createStatement();
                            st1.executeUpdate("update Shop set Stock="+stock+"where Product_name='"+prod_name+"'");
                            st1.close();  
                        }
                        catch(Exception e){
                            System.out.println(e);
                        }
                    }
                    else{
                        System.out.println("Quantity is greater than the availability of Stock!!!");
                    }
                }
            }
            else{
                System.out.println("Product is not found!!!");
            }
            System.out.println("Enter choice: \n 1- Place Order \n 2 - Billing");
            int c=in.nextInt();
            in.nextLine();
            if(c==1){
                placeOrder(con,c_id);
            }
            else{
                billing(con,c_id);
            }
        }
        catch(Exception e){
            System.out.println("Order is not placed!!!!");
        }
    }
    public static void billing(Connection con, int C_id)throws Exception{
        try{
            double total=0;
            Statement st=con.createStatement();
            Statement st1=con.createStatement();
            ResultSet rs=st.executeQuery("select * from Orders where Cust_id="+C_id);
            System.out.println("**********BILLING**********");
            System.out.println("Customer_ID: "+C_id);
            try{
                ResultSet rs1=st1.executeQuery("select * from Customer where Customer_id"+C_id);
                if(rs.next()){
                    String c_name=rs1.getString("Customer_name");
                    String c_email=rs1.getString("Customer_email");
                    System.out.println("Customer Name: "+c_name);
                    System.out.println("Customer Email: "+c_email);
                    st1.close();
                }
            }
            catch(Exception e){
                System.out.println(e);
            }
            System.out.println("Order_Id          Product_Id          Product_Name          Rate          Quantity          Amount");
            while(rs.next()){
                int o_id=rs.getInt("Order_id");
                int prod_id=rs.getInt("Prod_id");
                String p_name=rs.getString("Prod_name");
                double rate=rs.getDouble("Rate");
                double quan=rs.getDouble("Quantity");
                double amt=rs.getDouble("Amount");
                total=total+amt;
                System.out.println(o_id+"          "+prod_id+"          "+p_name+"          "+rate+"        "+quan+"          "+amt);
                System.out.println("\n          Total: "+total);
                System.out.println("\n For Delivery Contact: 87612 06653");
            }
            st.close();
        }
        catch(Exception e){
            System.out.println("Error Occured!!!");
        }
    }
}
