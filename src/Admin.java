import java.math.BigInteger;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Admin extends App{

    PreparedStatement ps;
    Statement stmt;
    ResultSet rs;
    Scanner sc = new Scanner(System.in);
    
    public void admin_menu() throws SQLException
    {
        try
        {
            User user = new User();
            dbConnection();
            int options=1;
            while(options==1)
            {
                //System.out.println("\n1. ");
                System.out.println("\n1. Add User");
                System.out.println("2. Update User details");
                System.out.println("3. View User Details");
                System.out.println("4. Exit");
                System.out.print("Enter Choice: ");
                int choice = sc.nextInt();
                int acc_num;
                switch(choice)
                {
                    case 1:
                        user.register();
                        break;

                    case 2:
                        System.out.print("\nEnter Account Number of User: ");
                        acc_num = sc.nextInt();
                        if(user.acc_exists(acc_num))
                            updateDetails(acc_num);
                        else
                            System.out.println("\nAccount not found.");
                        break;

                    case 3:
                        System.out.print("\nEnter Account Number of User: ");
                        acc_num = sc.nextInt();
                        if(user.acc_exists(acc_num))
                            viewDetails(acc_num);
                        else
                            System.out.println("\nAccount not found.");
                        break;
                        
                    case 4:
                        options=0;
                        break;
                    default:
                        System.out.println("\nInvalid Choice!");                
                }
            }
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
    }
    public void viewDetails(int acc_num)
    {
        try 
        {
            dbConnection();
            PreparedStatement ps = (PreparedStatement) conn.prepareStatement("Select * from user where acc_no=?");
            
            ps.setInt(1, acc_num);
            
            
            rs = ps.executeQuery();
            while(rs.next())
            {
                System.out.println("\nFirst name: "+rs.getString("fname"));
                System.out.println("Last name: "+rs.getString("lname"));
                System.out.println("Email: "+rs.getString("email"));
                System.out.println("Phone: "+rs.getString("phone"));
                System.out.println("PAN: "+rs.getString("pan"));
                System.out.println("Aadhar: "+rs.getString("aadhar"));
                System.out.println("Address: "+rs.getString("address"));
                System.out.println("Balance: Rs. "+rs.getDouble("acc_bal"));
            }
        } 
        catch (Exception e) {
            System.out.println(e);
        }
    }
    public void updateDetails(int acc_num)
    {
        try 
        {
            sc = new Scanner(System.in);
    
            int options=1;
            while(options==1)
            {
                System.out.println("\n1. Update First Name");
                System.out.println("2. Update Last Name");
                System.out.println("3. Update Email");
                System.out.println("4. Update Phone");
                System.out.println("5. Update PAN");
                System.out.println("6. Update Aadhar");
                System.out.println("7. Update Address");
                System.out.println("8. Update Password");
                System.out.println("9. Exit Menu");
                System.out.print("Enter Choice: ");
                int choice = sc.nextInt();
    
                switch(choice)
                {
                    case 1:
                        firstname(acc_num);
                        break;
                    case 2:
                        lastname(acc_num);
                        break;
                    case 3:
                        email(acc_num);
                        break;
                    case 4: 
                        phone(acc_num);
                        break;
                    case 5:
                        pan(acc_num);
                        break;
                    case 6:
                        aadhar(acc_num);
                        break;
                    case 7:
                        address(acc_num);
                        break;
                    case 8:
                        password(acc_num);
                        break;
                    case 9:
                        options=0;
                        break;
                    default:
                        System.out.println("\nInvalid Choice!");                
                }
            }
        } 
        catch (Exception e) 
        {
            System.out.println(e);
        }
    }
    public void firstname(int acc_num)
    {
        try
        {
            System.out.print("Enter First Name to be Updated: ");
            String str = sc.next();
            String query = "update user set fname=? where acc_no=?";
            ps = conn.prepareStatement(query);
            ps.setString(1, str);
            ps.setInt(2, acc_num);
            ps.executeUpdate();
            System.out.println("\nUpdated Successfully!");
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
        
    }
    public void lastname(int acc_num)
    {
        try
        {
            dbConnection();
            System.out.print("Enter Last Name to be Updated: ");
            String str = sc.next();
            String query = "update user set lname=? where acc_no=?";
            ps = conn.prepareStatement(query);
            ps.setString(1, str);
            ps.setInt(2, acc_num);
            ps.executeUpdate();
            System.out.println("\nUpdated Successfully!");
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
    }
    public void email(int acc_num)
    {
        try
        {
            dbConnection();
            System.out.print("Enter Email to be Updated: ");
            String str = sc.next();
            String query = "update user set email=? where acc_no=?";
            ps = conn.prepareStatement(query);
            ps.setString(1, str);
            ps.setInt(2, acc_num);
            ps.executeUpdate();
            System.out.println("\nUpdated Successfully!");
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
        
    }
    public void phone(int acc_num)
    {
        try
        {
            dbConnection();
            System.out.print("Enter Phone to be Updated: ");
            BigInteger phone = sc.nextBigInteger();
            String query = "update user set phone=? where acc_no=?";
            ps = conn.prepareStatement(query);
            ps.setString(1, phone.toString());
            ps.setInt(2, acc_num);
            ps.executeUpdate();
            System.out.println("\nUpdated Successfully!");
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
        
    }
    public void pan(int acc_num)
    {
        try
        {
            dbConnection();
            System.out.print("Enter PAN to be Updated: ");
            String str = sc.next();
            String query = "update user set pan=? where acc_no=?";
            ps = conn.prepareStatement(query);
            ps.setString(1, str);
            ps.setInt(2, acc_num);
            ps.executeUpdate();
            System.out.println("\nUpdated Successfully!");
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
        
    } 
    public void aadhar(int acc_num)
    {
        try
        {
            dbConnection();
            System.out.print("Enter Phone number to be Updated: ");
            BigInteger aadhar = sc.nextBigInteger();
            String query = "update user set aadhar=? where acc_no=?";
            ps = conn.prepareStatement(query);
            ps.setString(1, aadhar.toString());
            ps.setInt(2, acc_num);
            ps.executeUpdate();
            System.out.println("\nUpdated Successfully!");
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
        
    }
    public void address(int acc_num)
    {
        try
        {
            dbConnection();
            System.out.print("Enter Address to be Updated: ");
            String str = sc.next();
            String query = "update user set address=? where acc_no=?";
            ps = conn.prepareStatement(query);
            ps.setString(1, str);
            ps.setInt(2, acc_num);
            ps.executeUpdate();
            System.out.println("\nUpdated Successfully!");
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
        
    }
    public void password(int acc_num)
    {
        try
        {
            dbConnection();
            System.out.print("Enter Password to be Updated: ");
            String str = sc.next();
            String query = "update user set password=? where acc_no=?";
            ps = conn.prepareStatement(query);
            ps.setString(1, str);
            ps.setInt(2, acc_num);
            ps.executeUpdate();
            System.out.println("\nUpdated Successfully!");
        }
        catch(Exception e)
        {
            System.out.println(e);
        } 
    }
}

