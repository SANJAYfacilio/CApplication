package org.example;
//package java.sql;
import com.mysql.cj.jdbc.PreparedStatementWrapper;
import com.mysql.cj.protocol.Resultset;
import java.util.Scanner;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;
import java.sql.*;

public class Main {
    static Scanner inp = new Scanner(System.in);
    public static void main(String[] args)
    {
        int ch;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/contact", "root", "19-Jun-02");
            //Statement st = con.createStatement();
            System.out.println("LOGIN TO SHOW CONTACTS");
            System.out.println("PLEASE ENTER THE FOLLOWING DETAILS");
            System.out.println("USER NAME : ");
            String user = inp.nextLine();
            //String user = getRequestString("username");
            //String pass = getRequestString("user password");

            //inp.next();
            System.out.println("USER PASSWORD : ");
            String pass = inp.nextLine();
            int count=0;
            String valid_query = "select * from login where user = ? and pas = ?;";
            PreparedStatement st ;
            st = conn.prepareStatement(valid_query);
            st.setString(1,user);
            st.setString(2,pass);
            ResultSet rs_valid = st.executeQuery(valid_query);
            while(rs_valid.next())
            {
                count++;
            }
            if(count>0)
            {
                Statement st1 = conn.createStatement();
            do{System.out.println("\n\nCHOOSE YOUR OPTION");
            System.out.println("1)SAVE NEW CONTACT\n2)DISPLAY CONTACTS\n3)UPDATE EXISITING CONTACT\n4)SEARCH FOR A CONTACT\n5)DELETE A CONTACT\n6)EXIT");
            ch = inp.nextInt();
            switch(ch) {
                case 1:
                    String cname, cmobile, cplace, cdob, cemail;
                    System.out.println("ENTER FOLLOWING DETAILS OF THE CONTACT");
                    System.out.println("NAME");
                    cname = inp.next();
                    System.out.println("MOBILE NUMBER");
                    cmobile = inp.next();
                    System.out.println("PLACE");
                    cplace = inp.next();
                    System.out.println("DATE OF BIRTH IN DD/MM/YYYY FORMAT");
                    cdob = inp.next();
                    System.out.println("EMAIL ID");
                    cemail = inp.next();
                    int cid = 0;
                    ResultSet rs1 = st1.executeQuery("select * from details;");
                    while (rs1.next()) {
                        cid = rs1.getInt("contact_id");
                    }
                    String sql = "insert into details values('" + cname + "','" + cmobile + "','" + cplace + "','" + cdob + "','" + cemail + "'," + (cid + 1) + ");";
                    // Statement stmt = conn.createStatement();
                    st.executeUpdate(sql);
                    break;
                case 2:
                    ResultSet rs = st1.executeQuery("select * from details;");
                    System.out.println("NAME\tMOBILE\t\tPLACE\tDATE OF BIRTH\tEMAIL ID");
                    while (rs.next()) {
                        System.out.println(rs.getString("name") + "\t" + rs.getString("Mobile") + "\t" + rs.getString("Place") + "\t" + rs.getString("DOB") + "\t" + rs.getString("email"));
                    }
                    break;
                case 3:
                    System.out.println("ENTER THE CONTACT ID TO BE MODIFIED");
                    int c_id = inp.nextInt();
                    ResultSet rs2 = st1.executeQuery("select * from details;");
//                    System.out.println("NAME\tMOBILE\t\tPLACE\tDATE OF BIRTH\tEMAIL ID");
                    while (rs2.next()) {
                        if(rs2.getInt("contact_id")==c_id)
                        System.out.println("Name\t:"+rs2.getString("name") + "\n" +"MOBILE\t:"+ rs2.getString("Mobile") + "\n" +"PLACE\t:"+ rs2.getString("Place") + "\n" +"DATE OF BIRTH\t:" +rs2.getString("DOB") + "\n" +"EMAIL ID\t:"+ rs2.getString("email"));
                    }
                    System.out.println("Enter which field to be modified");
                    System.out.println("\n1) NAME \n2) MOBILE NUMBER \n3)PLACE\n4)DATE OF BIRTH\n5)EMAIL ID");
                    int mod=inp.nextInt();
                    String update;
                    switch (mod)
                    {
                        case 1:
                            System.out.println("Enter the new name");
                            String mod_name = inp.next();
                            update="UPDATE details SET NAME = '"+mod_name+"' WHERE contact_id="+ c_id+";";
                            st1.executeUpdate(update);
                            System.out.println("SUCCESSFULLY UPDATED");
                            break;
                        case 2:
                            System.out.println("Enter the new mobile number");
                            String mod_mob = inp.next();
                            update="UPDATE details SET MOBILE = '"+mod_mob+"' WHERE contact_id="+ c_id+";";
                            st1.executeUpdate(update);
                            System.out.println("SUCCESSFULLY UPDATED");
                            break;
                        case 3:
                            System.out.println("Enter the new place");
                            String mod_place = inp.next();
                            update="UPDATE details SET place = '"+mod_place+"' WHERE contact_id="+ c_id+";";
                            st1.executeUpdate(update);
                            System.out.println("SUCCESSFULLY UPDATED");
                            break;
                        case 4:
                            System.out.println("Enter the new DOB");
                            String mod_dob = inp.next();
                            update="UPDATE details SET dob = '"+mod_dob+"' WHERE contact_id="+ c_id+";";
                            st1.executeUpdate(update);
                            System.out.println("SUCCESSFULLY UPDATED");
                            break;
                        case 5:
                            System.out.println("Enter the new mail id");
                            String mod_mail = inp.next();
                            update="UPDATE details SET email = '"+mod_mail+"' WHERE contact_id="+ c_id+";";
                            st1.executeUpdate(update);
                            System.out.println("SUCCESSFULLY UPDATED");
                            break;
                    }
                    break;
                case 4:
                    System.out.println("ENTER A FIELD TO BE SEARCHED");
                    String search = "Select * from details where ";
                    ResultSet rs_search;
                    System.out.println("\n1)SEARCH BY NAME\n2)SEARCH BY MOBILE\n3)SEARCH BY PLACE\n4)SEARCH BY MAIL ID\n5)SEARCH BY CONTACT ID");
                    int search_opt = inp.nextInt();
                    switch (search_opt)
                    {
                        case 1:
                            System.out.println("ENTER THE NAME OF THE CONTACT");
                            String search_name = inp.next();
                            rs_search = st1.executeQuery(search+"name = '"+search_name+"';");
                            while (rs_search.next()) {
                                System.out.println("Name\t:" + rs_search.getString("name") + "\n" + "MOBILE\t:" + rs_search.getString("Mobile") + "\n" + "PLACE\t:" + rs_search.getString("Place") + "\n" + "DATE OF BIRTH\t:" + rs_search.getString("DOB") + "\n" + "EMAIL ID\t:" + rs_search.getString("email"));
                            }
                            break;
                        case 2:
                            System.out.println("ENTER THE MOBILE NUMBER OF THE CONTACT");
                            String search_mob = inp.next();
                            rs_search = st1.executeQuery(search+"mobile = '"+search_mob+"';");
                            while (rs_search.next()) {
                                System.out.println("Name\t:" + rs_search.getString("name") + "\n" + "MOBILE\t:" + rs_search.getString("Mobile") + "\n" + "PLACE\t:" + rs_search.getString("Place") + "\n" + "DATE OF BIRTH\t:" + rs_search.getString("DOB") + "\n" + "EMAIL ID\t:" + rs_search.getString("email"));
                            }
                            break;
                        case 3:
                            System.out.println("ENTER THE PLACE OF THE CONTACT");
                            String search_place = inp.next();
                            rs_search = st1.executeQuery(search+"place = '"+search_place+"';");
                            while (rs_search.next()) {
                                System.out.println("Name\t:" + rs_search.getString("name") + "\n" + "MOBILE\t:" + rs_search.getString("Mobile") + "\n" + "PLACE\t:" + rs_search.getString("Place") + "\n" + "DATE OF BIRTH\t:" + rs_search.getString("DOB") + "\n" + "EMAIL ID\t:" + rs_search.getString("email")+"\n");
                            }
                            break;
                        case 4:
                            System.out.println("ENTER THE MAIL ID OF THE CONTACT");
                            String search_mail = inp.next();
                            rs_search = st1.executeQuery(search+"email = '"+search_mail+"';");
                            while (rs_search.next()) {
                                System.out.println("Name\t:" + rs_search.getString("name") + "\n" + "MOBILE\t:" + rs_search.getString("Mobile") + "\n" + "PLACE\t:" + rs_search.getString("Place") + "\n" + "DATE OF BIRTH\t:" + rs_search.getString("DOB") + "\n" + "EMAIL ID\t:" + rs_search.getString("email"));
                            }
                            break;
                        case 5:
                            System.out.println("ENTER THE CONTACT ID OF THE CONTACT");
                            int search_id = inp.nextInt();
                            rs_search = st1.executeQuery(search+"contact_id = "+search_id+";");
                            while (rs_search.next()) {
                                System.out.println("Name\t:" + rs_search.getString("name") + "\n" + "MOBILE\t:" + rs_search.getString("Mobile") + "\n" + "PLACE\t:" + rs_search.getString("Place") + "\n" + "DATE OF BIRTH\t:" + rs_search.getString("DOB") + "\n" + "EMAIL ID\t:" + rs_search.getString("email"));
                            }
                            break;

                        }
                        break;
                case 5:
                    System.out.println("TO DELETE A CONTACT ");
                    System.out.println("ENTER ANY ONE OPTION \n1) DELETE BY ENTERING NAME \n2) DELETE BY ENTERING CONTACT ID \n3) DELETE BY PLACE");
                    int del_opt = inp.nextInt();
                    String del_query = "delete from details where ";
                    switch (del_opt)
                    {
                        case 1:
                            System.out.println("ENTER THE NAME TO BE DELETED");
                            String del_name = inp.next();
                            st1.executeUpdate(del_query+"name = '"+del_name+"';");
                            System.out.println("DELETED SUCCESSFULLY");
                            break;
                    }
                    }


            }while(ch!=6);
            }
            else
            {
                System.out.println("INVALID USER");
            }
        }catch(Exception e)
        {
            //  System.out.println(e);
            e.printStackTrace();
        }
    }
}