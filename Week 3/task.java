import java.sql.*;
import java.util.*;

class Admin{
	static void loginValidation(Connection conn) throws SQLException {
		String sql = "select * from admin where user = ? AND pass =?";
		PreparedStatement query = conn.prepareStatement(sql);
		Scanner sc = new Scanner(System.in);

        String user, pass = "";
        System.out.println("\nEnter username:\n");
        user = sc.nextLine();
        System.out.println("\nEnter pass :\n");
        pass = sc.nextLine();
        
        query.setString(1, user);
        query.setString(2, pass);
        boolean loginflag = false;
        
        ResultSet rs = query.executeQuery();
        if(rs.next()) {
        	System.out.println("\nlogin success\n");
        	loginflag = true;
        	adminContext(conn);
        }
        
        //failed login
        if(!loginflag) System.out.println("\nincorrect credentials\n");
        sc.close();
	}
	
	static void adminContext(Connection conn) throws SQLException {
		boolean exit = false;
		Scanner sc = new Scanner(System.in);
		
		while(!exit) {
			System.out.println("\nAdmin Actions :\n1.Add accountant\n2.View accountant\n3.Edit accountant\n4.Delete accountant\n5.Logout\n");
			System.out.println("\nEnter choice:");
			int choice = 0;
			choice = sc.nextInt();
			
			switch(choice) {
				
			case 1:{ //add acc
				int id = 0;
				String name, email, phone, pass ="";
				
				System.out.println("\nEnter input of the form : ID, Name, Email, Phone, Pass");
				id = sc.nextInt();
				sc.nextLine();
				name = sc.nextLine();
				email = sc.nextLine();
				phone = sc.nextLine();
				pass = sc.nextLine();
				
				String sql = "insert into accountant values(?, ?, ?, ?, ?);";
				
				PreparedStatement query = conn.prepareStatement(sql);
				query.setInt(1, id);
				query.setString(2, name);
				query.setString(3, email);
				query.setString(4, phone);
				query.setString(5, pass);
				
				query.executeUpdate();
				System.out.println("done \n");
				
				break;
			}
			
			case 2:{
				Statement stmt = conn.createStatement();
	             ResultSet rs = stmt.executeQuery("select * from accountant"); 

	            ResultSetMetaData rsmd = rs.getMetaData();
	            int columnCount = rsmd.getColumnCount();
	            
	            System.out.println("\n");
	
	            for (int i = 1; i <= columnCount; i++) {
	                System.out.print(rsmd.getColumnName(i) + "\t");
	            }
	            System.out.println("\n-------------------------------------------------");
	            
	            while (rs.next()) {
	                for (int i = 1; i <= columnCount; i++) {
	                    System.out.print(rs.getString(i) + "\t");
	                }
	                System.out.println();
	            }
	            
	            System.out.println("\n");
	            break;
			}
			
			
			case 3:{
				int id = 0;
				System.out.println("\nEnter id to be updated:\n");
				id = sc.nextInt();
				sc.nextLine();
				
				System.out.println("\nEnter name, email, phone and pass to be updated :\n");
				String name = sc.nextLine();
				String email = sc.nextLine();
				String phone = sc.nextLine();
				String pass = sc.nextLine();
				
				String sql = "update accountant set name= ?, email = ?, phone = ?, pass = ? where id ="+id;
				PreparedStatement query = conn.prepareStatement(sql);
				query.setString(1, name);
				query.setString(2, email);
				query.setString(3, phone);
				query.setString(4, pass);
				
				query.executeUpdate();
				
				break;
			}
			
			case 4:{
				int id = 0;
				System.out.println("Enter id to be deleted :");
				id = sc.nextInt();
				sc.nextLine();
				
				String sql = "delete from accountant where id = ?";
				PreparedStatement query = conn.prepareStatement(sql);
				query.setInt(1, id);
				
				query.executeUpdate();
				break; 
			}
			
			case 5:{
				exit = true;
				System.out.println("Logged out successfully\n");
				break;
			}
			
			default:{
				System.out.println("invalid input.");
				break;
			}
			
			}
		}
		
		sc.close();
	}
}

class Accountant{
	public static void loginValidation(Connection conn) throws SQLException {
		Scanner sc = new Scanner(System.in);
		
		
		System.out.println("Enter name:");
		String user = sc.nextLine();
		System.out.println("Enter password :");
		String pass = sc.nextLine();
		
		String sql = "select * from accountant where name = ? AND pass = ?";
		PreparedStatement query = conn.prepareStatement(sql);
		query.setString(1, user);
		query.setString(2, pass);
		ResultSet rs = query.executeQuery();
		
		boolean loginflag = false;
		
		if(rs.next()) {
			loginflag = true;
			System.out.println("\nLogged in successfully\n");
			accountantContext(conn);
		}
		
		if(!loginflag) {
			System.out.println("\nInvalid credentials\n");
		}
		
		sc.close();
		
	}
	
	public static void accountantContext(Connection conn) throws SQLException{
		Scanner sc = new Scanner(System.in);
		boolean exit = false;
		
		while(!exit) {
			System.out.println("\nAccountant Actions:\n1. Add student\n2. View students\n3. Edit students\n4. Delete student\n5. Check pending fees\n6. Logout");
			int choice = 0;
			choice = sc.nextInt();
			
			switch(choice) {
			
			case 1:{ //add student
				System.out.println("\nEnter student data of the form : ID, name, email, course, fees, paid, due, address, phone\n :");
				int id = sc.nextInt();
				sc.nextLine();
				String name = sc.nextLine();
				String email = sc.nextLine();
				String course = sc.nextLine();
				
				double fees = sc.nextDouble();
				sc.nextLine();
				double paid = sc.nextDouble();
				sc.nextLine();
				double due = sc.nextDouble();
				sc.nextLine();
				
				String address = sc.nextLine();
				String phone = sc.nextLine();
				
				String sql = "insert into student values(?, ?, ?, ?, ?, ?, ?, ?, ?);";
				PreparedStatement query = conn.prepareStatement(sql);
				query.setInt(1, id);
				query.setString(2, name);
				query.setString(3,  email);
				query.setString(4,  course);
				query.setDouble(5, fees);
				query.setDouble(6,  paid);
				query.setDouble(7, due);
				query.setString(8, address);
				query.setString(9, phone);
				query.executeUpdate();
				
				break;
			}
			
			case 2:{ //view all
				Statement stmt = conn.createStatement();
	            ResultSet rs = stmt.executeQuery("select * from student"); 

	            ResultSetMetaData rsmd = rs.getMetaData();
	            int columnCount = rsmd.getColumnCount();

	            System.out.println("\n");
	            for (int i = 1; i <= columnCount; i++) {
	                System.out.print(rsmd.getColumnName(i) + "\t");
	            }
	            System.out.println("\n-------------------------------------------------");
	            
	            while (rs.next()) {
	                for (int i = 1; i <= columnCount; i++) {
	                    System.out.print(rs.getString(i) + "\t");
	                }
	                System.out.println();
	            }
	            System.out.println("\n");
				break;
			}
			
			case 3:{//edit
				System.out.println("\nEnter id to be updated :\n");
				int id = sc.nextInt();
				sc.nextLine();
				
				String name = sc.nextLine();
				String email = sc.nextLine();
				String course = sc.nextLine();
				
				double fees = sc.nextDouble();
				sc.nextLine();
				double paid = sc.nextDouble();
				sc.nextLine();
				double due = sc.nextDouble();
				sc.nextLine();
				
				String address = sc.nextLine();
				String phone = sc.nextLine();
				
				
				String sql = "update student set name=?, email=?, course=?, fees=?, paid = ?, due=?, address=?, phone=? where id="+id;
				PreparedStatement query = conn.prepareStatement(sql);
				query.setString(1, name);
				query.setString(2,  email);
				query.setString(3,  course);
				query.setDouble(4, fees);
				query.setDouble(5,  paid);
				query.setDouble(6, due);
				query.setString(7, address);
				query.setString(8, phone);
				
				System.out.println("\nUpdated successfully.\n");
				
				break;
			}
			
			case 4:{ //del
				System.out.println("\nEnter id to delete:\n");
				int id = sc.nextInt();
				sc.nextLine();
				
				String sql = "delete from student where id ="+id;
				PreparedStatement query = conn.prepareStatement(sql);
				query.executeUpdate();
				System.out.println("\nDeleted successfully.\n");
				
				break;
			}
			
			case 5:{ //pending  fees
				System.out.println("\nStudents with due fees : \n");
				String sql = "select name, due from student where due > 0;";
				PreparedStatement query = conn.prepareStatement(sql);
				ResultSet rs = query.executeQuery();
				
				ResultSetMetaData rsmd = rs.getMetaData();
	            int columnCount = rsmd.getColumnCount();

	
	            for (int i = 1; i <= columnCount; i++) {
	                System.out.print(rsmd.getColumnName(i) + "\t");
	            }
	            System.out.println("\n-------------------------------------------------");
	            
	            while (rs.next()) {
	                for (int i = 1; i <= columnCount; i++) {
	                    System.out.print(rs.getString(i) + "\t");
	                }
	                System.out.println();
	            }
				
				break;
			}
			
			case 6 :{ //logout
				exit = true;
				System.out.println("\nLogged out successfully\n");
				break;
			}
			
			default:{
				System.out.println("\nInvalid input\n");
				break;
			}
			}
		}
		sc.close();
	}
	
}

public class task {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/practice";
        String user = "root";
        String pass = "jonathan";
        Scanner sc = new Scanner(System.in);
        
        //connecting to db
        try {
            Connection conn = DriverManager.getConnection(url, user, pass);
            boolean exit = false;

            
            while(!exit){
            	System.out.println("\nLOGIN SCREEN\nYou may log in as :\n1. Admin\n2. Accountant\n3. Exit\nEnter choice:");
            	
            	int choice = 0;
            	choice = sc.nextInt();
            	sc.nextLine();
            	
            	switch(choice){
            		
            		case 1:{
            			Admin.loginValidation(conn);
            			break;
            		}
            		
            		case 2:{
            			Accountant.loginValidation(conn);
            			break;
            		}
            		
            		case 3:{
            			exit=true;
            			System.out.println("\nExiting..\n");
            			break;
            		}
            		
            		default:{
            			System.out.println("\nInvalid input\n");
            			break;
            		}
            	}
            	
            	
            }
            //Admin.loginValidation(conn);
//            
            conn.close();  
        } 
        catch (SQLException e) {
            e.printStackTrace();  
        }
        
        sc.close();
    }
}
