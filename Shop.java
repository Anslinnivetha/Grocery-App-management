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
public class Shop {
    static String sh_email;
    static String sh_password;
    static Scanner input=new Scanner(System.in);
    Shop(String email,String password){
        this.sh_email=email;
        this.sh_password=password;
    }
    public static void shopLogin(Connection con) throws Exception{
        try{
            if((sh_email.equals("shopowner@gmail.com"))&&(sh_password.equals("shop123"))){
               System.out.println("**********Login Successfull**********\n");
               System.out.println("Enter the choice: \n 1 - Add products \n 2 - Update stock & price \n 3 - Delete Stock \n 4 - View Stock \n 5 - View Orders \n 6- Exit");
               int ch=input.nextInt();
               //input.nextLine();
               while((ch>=1)&&(ch<=5)){
                   switch(ch){
                        case 1:
                            input.nextLine();
                            int p_id;
                            String p_name;
                            double p_rate;
                            String p_type;
                            double stock;
                            PreparedStatement pst=con.prepareStatement("insert into Shop values(?,?,?,?,?)");
                            UUID g=UUID.randomUUID();
                            String str=""+g;
                            int a=str.hashCode();
                            String h=""+a;
                            str=h.replaceAll("-","");
                            p_id=Integer.parseInt(str);
                            pst.setInt(1,p_id);
                            System.out.println("Enter Product name: ");
                            p_name=input.nextLine();
                            pst.setString(2,p_name);
                            System.out.println("Enter rate of the product per unit kg: ");
                            p_rate=input.nextDouble();
                            pst.setDouble(3,p_rate);
                            input.nextLine();
                            System.out.println("Enter type of the product");
                            p_type=input.nextLine();
                            pst.setString(4,p_type);
                            System.out.println("Enter the availability of stock");
                            stock=input.nextDouble();
                            pst.setDouble(5,stock);
                            System.out.println("**********Product added successfully**********\n");
                               //System.out.println("Enter choice:");
                               //System.out.println("Enter the choice: \n 1 - Add products \n 2 - Update stock & price");
                               //ch=input.nextInt();
                               //input.nextLine();
                            pst.executeUpdate();
                            pst.close();
                               //addProducts(con);
                            break;
                        case 2:
                            updateStock(con);
                            
                            break;
                        case 3:
                            //deleteStock(con);
                            try{
                                input.nextLine();
                                System.out.println("Enter the product that has to be elliminated: ");
                                p_name=input.nextLine();
                                Statement st=con.createStatement();
                                st.executeUpdate("delete from Shop where Product_name='"+p_name+"'");
                                st.close();
                                System.out.println("Deleted successfully!!!");
                                //shopLogin(con);
                            }
                            catch(Exception e){
                                System.out.println("Error Occured!!! unable to delete");
                            }
                            break;
                        case 4:
                            viewStock(con);
                            break;
                        case 5:
                            viewOrder(con);
                            break;
                   }
                   System.out.println("Enter the choice: \n 1 - Add products \n 2 - Update stock & price \n 3 - Delete Stock \n 4 - View Stock \n 5 - View Orders \n 6- Exit");
                   ch=input.nextInt();
                   //input.nextLine();
               }
               if(ch>=6){
                   System.out.println("**********Exit**********");
                   System.out.println("Try again");
               }
            }
            else{
                System.out.println("Invalid password or email");    
            }
        }
        catch(Exception e){
            System.out.println("Error Occurred"+e);
        }
    }
    public static void updateStock(Connection con)throws Exception{
        System.out.println("Enter the choice to: \n 1 - Update Stock \n 2 - Update rate");
        int ch1=input.nextInt();
        input.nextLine();
        if(ch1==1){
            try{  
                Statement st=con.createStatement();
                System.out.println("Enter the product name for which the stock should be updated: ");
                String p_name=input.nextLine();
                System.out.println("Enter the quantity of stock should be updated: ");
                ResultSet rs;
                rs=st.executeQuery("select * from Shop where Product_name='"+p_name+"'");
                double p_stock=0.0;
                if(rs.next()){
                    p_stock=rs.getDouble("Stock");
                }
                double q=input.nextDouble();
                p_stock=p_stock+q;
                st.executeUpdate("update Shop set Stock="+p_stock+" where Product_name='"+p_name+"'");
                st.close();
                System.out.println("Updated successfully!!!!");
                shopLogin(con);
            }
            catch(Exception e){
                System.out.println("Unable to update");
            }
        }
        if(ch1==2){
            try{
                Statement st=con.createStatement();
                System.out.println("Enter the product name for which the rate should be updated: ");
                String p_name=input.nextLine();
                System.out.println("Enter the quantity of rate should be updated: ");
                double p_rate=input.nextDouble();
                st.executeUpdate("update Shop set Rate_perkg="+p_rate+" where Product_name='"+p_name+"'");
                st.close();
                System.out.println("Updated successfully!!!!");
                shopLogin(con);
            }
            catch(Exception e){
                System.out.println("Unable to Update");
            }
        }
    }
    public static void viewStock(Connection con)throws Exception{
        try{
            //input.nextLine();
            //System.out.println("Enter the type of the product");
            //String type=input.nextLine();
            Statement st=con.createStatement();
            System.out.println("Product_Id          Product_Name          Rate_per_Unit          Type          Available_Stock");
            ResultSet rs=st.executeQuery("select * from Shop");
            while(rs.next()){
                int Product_id=rs.getInt("Product_id");
                String Product_name=rs.getString("Product_name");
                double Rate_perkg=rs.getDouble("Rate_perkg");
                String Type=rs.getString("Type");
                double Stock=rs.getDouble("Stock");
                System.out.println(Product_id+"          "+Product_name+"              "+Rate_perkg+"               "+Type+"          "+Stock);
            }
            shopLogin(con);
        }
        catch(Exception e){
            System.out.println("Error Occurred");
        }
    }
    public static void viewOrder(Connection con)throws Exception{
        try{
            Statement st1=con.createStatement();
            //ResultSet rs1=st1.executeQuery("select count(distinct Cust_id) from Orders");
            //int c_count=rs1.getInt("Cust_id");
            ResultSet rs1=st1.executeQuery("select * from Orders");
            //int o_count=rs1.getInt("Order_id");
            //System.out.println("Total no.of Orders: "+o_count);
            //System.out.println("No.of Customers placed Orders : "+c_count);
            //rs1.close();
            System.out.println("Order_id          Customer_id          Product_id          Product_name          Rate          Amount");
            while(rs1.next()){
                int o_id=rs1.getInt(1);
                int c_id=rs1.getInt(2);
                int p_id=rs1.getInt(3);
                String p_name=rs1.getString(4);
                double r=rs1.getDouble(5);
                double q=rs1.getDouble(6);
                double a=rs1.getDouble(7);
                System.out.println(o_id+"          "+c_id+"          "+p_id+"          "+p_name+"          "+r+"          "+q+"          "+a);
              
            }
            rs1.close();
        }
        catch(Exception e){
            System.out.println("Error Occurred!!! "+e);
        }
    }
}
