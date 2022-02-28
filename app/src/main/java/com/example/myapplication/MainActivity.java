package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MainActivity extends AppCompatActivity {
    private static String ip = "103.197.221.123";
    private static String port = "1433";
    private static String Classes = "net.sourceforge.jtds.jdbc.Driver";
    private static String database = "LogMemoMS";
    private static String username = "RestrictedUser";
    private static String password = "IFMS$pswd";
    private static String url = "jdbc:jtds:sqlserver://" + ip + ":" + port + "/" + database;
    private TextView tv;

    private Connection connection = null;

    private int inputParameter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv = findViewById(R.id.condition);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        try {
            Class.forName(Classes);
            connection = DriverManager.getConnection(url, username, password);
            tv.setText("SUCCESS");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            tv.setText("FAILURE");
        } catch (SQLException e) {
            e.printStackTrace();
            tv.setText("FAILURE");
        }
    }

    public void onClick(View view) {

        EditText getUsername = (EditText) findViewById(R.id.Username);
        String username = getUsername.getText().toString();

        EditText getpassword = (EditText) findViewById(R.id.Password);
        String password = getpassword.getText().toString();

        inputParameter = 0;
        String temp = null;
        try {
            if(connection!=null){
                String query = "Select * from SysUser where UsrLogin = '" + username + "' and UsrPswd = '" + password + "'";

                Statement st = connection.createStatement();
                ResultSet rs = st.executeQuery(query);

                while (rs.next()){
                    temp = rs.getString(1);
                }

                if(temp!=null){
                    tv.setText("Login Successful");
                    inputParameter = Integer.parseInt(temp);
                }
                else {
                    tv.setText("Login Failed");
                }
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        }
    }

