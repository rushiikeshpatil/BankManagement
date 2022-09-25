
import java.math.BigInteger;
import java.util.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class User extends App{
    int login_acc_no;

    PreparedStatement ps;
    Statement stmt;
    ResultSet rs;
    Scanner sc = new Scanner(System.in);

    public void register()
    {
        try
        {
        System.out.print("Enter First Name: ");
        String fname = sc.next();

        System.out.print("Enter Last Name: ");
        String lname = sc.next();

        System.out.print("Enter Email: ");
        String email = sc.next();

        System.out.print("Enter Phone No.: ");
        BigInteger phone = sc.nextBigInteger();

        System.out.print("Enter PAN No.: ");
        String pan = sc.next();

        System.out.print("Enter Aadhar No.: ");
        BigInteger aadhar = sc.nextBigInteger(); 
        
        System.out.print("Enter Address: ");
        String address = sc.next();

        System.out.print("Enter Password: ");
        String password = sc.next();

        double acc_bal = 0.0;

        
            dbConnection();
            ps = (PreparedStatement) conn.prepareStatement("INSERT INTO user (fname, lname, email, phone, pan, aadhar, address, password, acc_bal) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)");
            
            ps.setString(1, fname);
            ps.setString(2, lname);
            ps.setString(3, email);
            ps.setString(4, phone.toString());
            ps.setString(5, pan);
            ps.setString(6, aadhar.toString());
            ps.setString(7, address);
            ps.setString(8, password);
            ps.setDouble(9, acc_bal);
        
            ps.executeUpdate();

            
            stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
            
            String query = "select acc_no from user";
            
            rs = stmt.executeQuery(query);
            
            rs.last();
            
            System.out.println("\nCongratulations! "+fname+", Account Created Successfully.");
            System.out.println("Account Number: "+rs.getInt("acc_no"));
            
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
    }

    public void login() throws SQLException
    {
        System.out.print("Enter Account No.: ");
        int acc_no = sc.nextInt();

        System.out.print("Enter Password: ");
        String password = sc.next();

        dbConnection();
        
        PreparedStatement ps = (PreparedStatement) conn.prepareStatement("Select acc_no, password from user where acc_no=? and password=?");
        
        ps.setInt(1, acc_no);
        ps.setString(2, password);
        
        rs = ps.executeQuery();
        
        if (rs.next())
        {
            System.out.println("\nYou have successfully logged in");
            login_acc_no = acc_no;
            login_menu();
        }
        else 
            System.out.println("Invalid Credentials");
    }
    
    public void login_menu() throws SQLException
    {
        try 
        {
            dbConnection();
            int options=1;
            while(options==1)
            {
                System.out.println("\n1. Check Balance");
                System.out.println("2. Transfer Amonut");
                System.out.println("3. Check Transactions");
                System.out.println("4. Update Details");
                System.out.println("5. Logout");
                System.out.print("Enter Choice: ");
                int choice = sc.nextInt();

                switch(choice)
                {
                    case 1:
                        System.out.println("\nBalance is RS. "+check_balance());
                        break;
                    case 2:
                        transfer();
                        break;
                    case 3:
                        check_trans();
                        break;
                    case 4:
                        updateProfile();
                        break;
                    case 5:
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

    public double check_balance()
    {
        try 
        {
            dbConnection();
            ps = (PreparedStatement) conn.prepareStatement("Select acc_bal from user where acc_no=?");
            
            ps.setInt(1, login_acc_no);
            //st.setString(2, password);
            
            rs = ps.executeQuery();
            rs.next();
            
            return rs.getDouble("acc_bal");
            
        } catch (Exception e) {
            System.out.println(e);
        }
        return 0.0;
    }
    public boolean acc_exists(int acc_num1) throws SQLException
    {
        try 
        {
            int acc_no = acc_num1;
            dbConnection();
            ps = (PreparedStatement) conn.prepareStatement("Select acc_no from user where acc_no=?");
            
            ps.setInt(1, acc_no);
            //st.setString(2, password);
            
            rs = ps.executeQuery();
            if(rs.next())
                return true;
            else
                return false;
            
        } catch (Exception e) {
            
            System.out.println(e);
        }
        return true;

    }
    public void transfer()
    {
        System.out.print("Enter Recipient Account no: ");
        int to_acc = sc.nextInt();

        System.out.print("Enter Amount to be Transfered: ");
        double amt = sc.nextDouble();

        try
        { 
            if(acc_exists(to_acc))
            {
                if((check_balance()-amt)>=0)
                {
                    final Date date = new Date();
                    java.sql.Date sqlDate = new java.sql.Date(date.getTime());
                    dbConnection();
                    ps = (PreparedStatement) conn.prepareStatement("INSERT INTO trans (from_acc, to_acc, date) VALUES (?, ?, ?)");
                    
                    ps.setInt(1, login_acc_no);
                    ps.setInt(2, to_acc);
                    ps.setDate(3, sqlDate);

                    ps.executeUpdate();

                    String sender_query = "update user set acc_bal=acc_bal-? where acc_no=?";
                    ps = conn.prepareStatement(sender_query);
                    ps.setDouble(1, amt);
                    ps.setInt(2, login_acc_no);
                    ps.executeUpdate();

                    String reciever_query = "update user set acc_bal=acc_bal+? where acc_no=?";
                    ps = conn.prepareStatement(reciever_query);
                    ps.setDouble(1, amt);
                    ps.setInt(2, to_acc);
                    ps.executeUpdate();
                    
                    System.out.println("\nTransfer Successful!");
                }
                else
                    System.out.println("\nInsufficient Amount!");
            }
            else
                System.out.println("\nAccount Doesn't Exists");
        }
        catch(Exception e)
        {
            System.out.println(e);
        }

    }
    public void check_trans()
    {
        try 
        {
            dbConnection();
            ps = (PreparedStatement) conn.prepareStatement("Select * from trans where from_acc=?");
            
            ps.setInt(1, login_acc_no);
            
            
            rs = ps.executeQuery();
            while(rs.next())
            {
                System.out.println("\nTransaction ID: "+rs.getInt("trans_id"));
                System.out.println("Recipient's Account Number: "+rs.getInt("to_acc"));
                System.out.println("Transaction Type: Debited");
                System.out.println("Date: "+rs.getDate("date"));
            }

            ps = (PreparedStatement) conn.prepareStatement("Select * from trans where to_acc=?");
            
            ps.setInt(1, login_acc_no);
            
            
            rs = ps.executeQuery();
            while(rs.next())
            {
                System.out.println("\nTransaction ID: "+rs.getInt("trans_id"));
                System.out.println("Recipient's Account Number: "+rs.getInt("to_acc"));
                System.out.println("Transaction Type: Credited");
                System.out.println("Date: "+rs.getDate("date"));
            }
        } 
        catch (Exception e) {
            System.out.println(e);
        }
    }
    public void updateProfile() throws SQLException
    {
        try
        {
            dbConnection();
            Admin admin = new Admin();
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
                        admin.firstname(login_acc_no);
                        break;
                    case 2:
                        admin.lastname(login_acc_no);
                        break;
                    case 3:
                        admin.email(login_acc_no);
                        break;
                    case 4: 
                        admin.phone(login_acc_no);
                        break;
                    case 5:
                        admin.pan(login_acc_no);
                        break;
                    case 6:
                        admin.aadhar(login_acc_no);
                        break;
                    case 7:
                        admin.address(login_acc_no);
                        break;
                    case 8:
                        admin.password(login_acc_no);
                        break;
                    case 9:
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
}