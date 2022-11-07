import java.sql.*;
import java.util.Scanner;

public class Test {
    public static void main(String args[]) {
        try {
            //JDBC Driver Class 로딩 - com.mysql.jdbc.Driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            //데이터베이스와 연결 설정
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://192.168.3.3:4567/madang",
                    "mjkim",
                    "1234"
            );

            //데이터베이스로 SQL문을 보내기 위한 SQLServerStatement 개체를 만든다.
            Statement stmt = con.createStatement();
            ResultSet rs;  //지정된 SQL문을 실행하고 SQLServerResultSet 개체를 반환한다. : 데이터 출력

            Scanner scan = new Scanner(System.in);
            int func = 0;

            while (func != 4) {
                System.out.println("madang 데이터베이스 : 1. 삽입 2. 삭제 3. 검색 4. 종료");
                System.out.print("---->  ");
                func = scan.nextInt();
                switch (func) {
                    case 1:
                        System.out.println("<< 데이터 삽입 >>");
                        System.out.print("index :  ");
                        int bookid= scan.nextInt();
                        System.out.print("책 이름 :  ");
                        scan.nextLine();
                        String bookname= scan.nextLine();
                        System.out.print("출판사 :  ");
                        String publisher=scan.nextLine();
                        System.out.print("가격 :  ");
                        int price=scan.nextInt();

                        String sql = "INSERT INTO Book(bookid, bookname, publisher, price) VALUES(" + bookid + ",'" + bookname + "','" + publisher + "'," + price + ")";
                        try {
                            int res = stmt.executeUpdate(sql);
                            if (res > 0) {
                                System.out.println("삽입 성공");
                            }
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }

                        //데이터 삽입 후 테이블 출력
                        rs = stmt.executeQuery(
                                "SELECT * FROM Book"
                        );
                        while (rs.next())
                            System.out.println(rs.getInt(1) + " | " + rs.getString(2) +
                                    " - " + rs.getString(3) + "   " + rs.getInt(4) + " 원");

                        break;
                    case 2:
                        System.out.println("<< 데이터 삭제 >>");
                        //데이터 테이블 출력
                        rs = stmt.executeQuery(
                                "SELECT * FROM Book"
                        );
                        while (rs.next())
                            System.out.println(rs.getInt(1) + " | " + rs.getString(2) +
                                    " - " + rs.getString(3) + "   " + rs.getInt(4) + " 원");

                        //num에 해당하는 데이터 tuple 삭제
                        System.out.print("삭제할 데이터 번호 선택 : ");
                        int num = scan.nextInt();
                        sql = "DELETE FROM Book WHERE bookid = " + num;
                        try {
                            int res = stmt.executeUpdate(sql);
                            if (res > 0) {
                                System.out.println("삭제 성공");
                            }
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }

                        //데이터 삭제 후 테이블 출력
                        rs = stmt.executeQuery(
                                "SELECT * FROM Book"
                        );
                        while (rs.next())
                            System.out.println(rs.getInt(1) + " | " + rs.getString(2) +
                                    " - " + rs.getString(3) + "   " + rs.getInt(4) + " 원");
                        break;
                    case 3:
                        System.out.print("<< 데이터 검색 >> :  ");
                        String data = scan.next();
                        rs = stmt.executeQuery(
                                "SELECT * FROM Book WHERE bookname LIKE '%" + data + "%'"
                        );
                        while (rs.next())
                            System.out.println(rs.getInt(1) + " | " + rs.getString(2) +
                                    " - " + rs.getString(3) + "   " + rs.getInt(4) + " 원");
                        break;
                    case 4:
                        System.out.println("bye");
                        break;
                }
            }
            con.close();  //connection 해제
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}