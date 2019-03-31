package com.itheima.test;

import oracle.jdbc.driver.OracleTypes;
import oracle.jdbc.oracore.OracleType;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.*;

public class OracleTest1 {

    private Connection connection;
    private PreparedStatement pds;
    private ResultSet resultSet;


  //  @Before
    public void before() throws Exception {
        //加载驱动
        Class.forName("oracle.jdbc.driver.OracleDriver");
        //获取连接对象
        Connection connection = DriverManager.getConnection("jdbc:oracle:thin:@192.168.182.2:1521:orcl", "itheima", "itheima");
    }

 //   @After
    public void after() throws Exception {
        //释放资源
        resultSet.close();
        pds.close();
        connection.close();
    }

    /**
     * 测试连接数据库
     */
    @Test
    public void oracleTestJdbc() throws Exception {
        //创建sql语句
        pds = connection.prepareStatement("select *from emp where empno=?");
        //传入参数
        pds.setObject(1, 5000);
        //执行sql语句
        resultSet = pds.executeQuery();
        while (resultSet.next()) {
            System.out.println(resultSet.getString("ename"));
        }
    }

    /**
     * java调用存储过程
     * {?= call <procedure-name>[(<arg1>,<arg2>, ...)]}   调用存储函数使用
     * {call <procedure-name>[(<arg1>,<arg2>, ...)]}   调用存储过程使用
     *
     * @throws Exception
     */
    @Test
    public void oracleTest2() throws Exception {
        //加载驱动
        Class.forName("oracle.jdbc.driver.OracleDriver");
        //获取连接对象
        Connection connection = DriverManager.getConnection("jdbc:oracle:thin:@192.168.182.2:1521:orcl", "itheima", "itheima");

        //调用oracle中的存储函数
        CallableStatement pdst = connection.prepareCall("{call p2(?,?)}");
        //传入参数
        pdst.setObject(1, 100);
        pdst.registerOutParameter(2,OracleTypes.VARCHAR);
        pdst.execute();
        System.out.println(pdst.getObject(2));
        pdst.close();
        connection.close();
    }
}
