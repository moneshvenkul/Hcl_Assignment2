import java.sql.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

public class bankApp {



public static void main(String[] args) throws SQLException {

Connection myConn = null;
Statement myStmt = null;
ResultSet myRs = null;

class bank{  
 String name;  
 int money;
 int bal_amount;
 bank(String name,int money, int bal_amount){  
  this.name=name;    
  this.money=money;
  this.bal_amount=bal_amount;
 }  
}  


bank d1=new bank("monesh",8000,100000);  

 //creating arraylist  
 ArrayList<bank> al=new ArrayList<bank>();  
 al.add(d1);//adding Student class object  
 
     
 

try {
// 1. Get a connection to database
myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/demo", "root" , "password");

System.out.println("Database connection successful!\n");

// 2. Create a statement
myStmt = myConn.createStatement();

DatabaseMetaData meta = myConn.getMetaData();
 ResultSet res = meta.getTables(null, null, "trans", null);
 if (res.next()) {
 System.out.println("1. Display the last 5 transactions\n" +
        "2. Balance Amount\n" +
        "3. Transfer 1000rs\n"+"4. Deposit 1000rs\n"+"5. Exit\n"+"Choose your option:");
       
        String sql2="SELECT * FROM trans order by sno DESC LIMIT 5" ;
        String sql3="SELECT * FROM trans order by sno DESC LIMIT 1" ;
       
       
        boolean flag = true;
        while(flag) {
       
        Scanner scan = new Scanner(System.in);
        int num = scan.nextInt();
       
        switch(num) {
         case 1:
       
        myRs=myStmt.executeQuery(sql2);
        System.out.println("The last 5 transactions are:");
        while (myRs.next()){
         int id = myRs.getInt("sno");
         Timestamp date = myRs.getTimestamp("date");
               String name = myRs.getString("name");
               int money = myRs.getInt("money");
               int bal_amount = myRs.getInt("bal_amount");
               System.out.println(+id + "\t" + date+"\t"+name +"\t" + money+"\t" + bal_amount);
       
        }
       
           break;
         case 2:
         myRs=myStmt.executeQuery(sql3);
        if(myRs.next()){
        System.out.println("The Balance Amount is:"+ myRs.getString("bal_amount"));
        }
       
           break;
           
         case 3:
         myRs=myStmt.executeQuery(sql3);
         if(myRs.next()){
         int bal_amount=myRs.getInt("bal_amount");
         if (bal_amount!=0) {
         bal_amount=bal_amount-1000;
         }else {
         System.out.println("No sufficient amount");
         }
         
         String sql5 = ("INSERT INTO `trans`(name,money,bal_amount) VALUE ('"+"venkul"+"','"+1000+"','"+bal_amount+"')");
         myStmt.executeUpdate(sql5);
         System.out.println("Transferred");
         }
         
         myRs=myStmt.executeQuery(sql2);
        System.out.println("The last 5 transactions are:");
        while (myRs.next()){
         int id = myRs.getInt("sno");
         Timestamp date = myRs.getTimestamp("date");
               String name = myRs.getString("name");
               int money = myRs.getInt("money");
               int bal_amount = myRs.getInt("bal_amount");
               System.out.println(+id + "\t" + date+"\t"+name +"\t" + money+"\t" + bal_amount);
        }
       
           break;
           
         case 4:
         myRs=myStmt.executeQuery(sql3);
         if(myRs.next()){
         int bal_amount=myRs.getInt("bal_amount");
         bal_amount=bal_amount+1000;
         
         String sql5 = ("INSERT INTO `trans`(name,money,bal_amount) VALUE ('"+"self"+"','"+1000+"','"+bal_amount+"')");
         myStmt.executeUpdate(sql5);
         System.out.println("Deposited");
         }
         
         myRs=myStmt.executeQuery(sql2);
        System.out.println("The last 5 transactions are:");
        while (myRs.next()){
         int id = myRs.getInt("sno");
         Timestamp date = myRs.getTimestamp("date");
               String name = myRs.getString("name");
               int money = myRs.getInt("money");
               int bal_amount = myRs.getInt("bal_amount");
               System.out.println(+id + "\t" + date+"\t"+name +"\t" + money+"\t" + bal_amount);
        }
       
           break;
           
         case 5:
         System.out.println("You will be exited now");
         flag = false;
         break;
         
           
         default:
           System.out.println("Please check your choice");
         break;
        }
        }
 }
 else {
 System.out.println("Database is empty, so creating database for you..."); //prints this message if your resultset is empty
           String sql = "CREATE TABLE `trans` (\n" +
            "  `sno` int(10) NOT NULL AUTO_INCREMENT PRIMARY KEY,\n" +
            "  `date` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,\n" +
            "  `name` varchar(64) NOT NULL,\n" +
            "  `money` int(11) NOT NULL ,\n" +
            "  `bal_amount` int(11) NOT NULL\n" +
            ")";
           myStmt.executeUpdate(sql);
           Iterator itr1=al.iterator();
while(itr1.hasNext()){
            System.out.println("Entering");
           bank st=(bank)itr1.next();
           String sql1 = ("INSERT INTO `trans`(name,money,bal_amount) VALUE ('"+st.name+"','"+st.money+"','"+st.bal_amount+"')");
           myStmt.executeUpdate(sql1);
           }

 
 }


}
catch (Exception exc) {
exc.printStackTrace();
}
finally {
if (myRs != null) {
myRs.close();
}

if (myStmt != null) {
myStmt.close();
}

if (myConn != null) {
myConn.close();
}
}
}

}
