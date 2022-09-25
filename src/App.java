import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Scanner;

public class App {
    
    public Connection conn;
    
    public void dbConnection()
    {
        try 
        {
            String url1 = "jdbc:mysql://localhost:3306/hotel";
            String user = "root";
            String password = "root";

            conn = DriverManager.getConnection(url1, user, password);
            //if (conn != null) 
                //System.out.println("Connected to the database");
        } 
        catch (Exception e){
            System.out.println(e);
        }
    } 
    public static void main(String[] args) throws Exception 
    {
        //App obj = new App();
        User user = new User();
        Admin admin = new Admin();
        
        Scanner sc = new Scanner(System.in);

        int options =1;
        while(options==1)
        {
            System.out.println("\n1. Login");
            System.out.println("2. Register");

            System.out.println("\n3. Admin");
            System.out.print("\nEnter Choice: ");
            int choice = sc.nextInt();

            switch(choice)
            {
                case 1:
                    user.login();
                    break;
                case 2:
                    user.register();

                    break;
                case 3:
                    admin.admin_menu();
                    break;
                default:
                    System.out.println("\nInvalid Choice!");                
            }
        }            
    }
}
