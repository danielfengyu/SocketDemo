package com.fengyu.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class SocketActivity extends ActionBarActivity implements OnClickListener,Runnable{

	private Button button;
    private EditText editText;
    private Socket socket;
    private PrintWriter pw;
    private BufferedReader br;
    private Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        handler=new Handler();
        button=(Button)findViewById(R.id.button);
        button.setOnClickListener(this);
        editText=(EditText)findViewById(R.id.edittext);
        try {
            socket=new Socket("169.254.228.186",5648);        //连接到tobacco5648.xicp.net的5648端口
} catch (UnknownHostException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            Log.e("socket","unknown host");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            Log.e("socket","io execption");
        }
        if(socket==null){
            Log.e("socket","null");
        }
        else
            try {
            pw=new PrintWriter(socket.getOutputStream());
            br=new BufferedReader(new InputStreamReader(socket.getInputStream()));
            if(pw!=null&&br!=null){
                new Thread(this).start();
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.socket, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
            String str;
            while((str=br.readLine())!=null){
                final String s=str;
                handler.post(new Runnable(){
 
                    public void run() {
                        Toast.makeText(SocketActivity.this, s, Toast.LENGTH_LONG).show();
 
                    }});
 
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
		
	}


	@Override
	public void onClick(View view) {
		// TODO Auto-generated method stub
		if(view==button){
            String str;
            str=editText.getText().toString();
            pw.println(str);
            pw.flush();
        }
	}
}
