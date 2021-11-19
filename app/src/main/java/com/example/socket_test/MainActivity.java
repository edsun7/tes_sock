package com.example.socket_test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import androidx.appcompat.app.AppCompatActivity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity {

    private Socket socket;
    TextView textResponse;
    EditText editTextAddress, editTextPort, editTextMsg;
    Button buttonConnect, buttonSend;

    private static final int SERVERPORT = 65238;
    private static final String SERVER_IP = "10.106.126.221";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("myTag", "This is my message");
        editTextAddress = (EditText)findViewById(R.id.address);
        editTextPort = (EditText)findViewById(R.id.port);
        editTextMsg = (EditText) findViewById(R.id.msgtosend);
        buttonConnect = (Button)findViewById(R.id.connect);
        textResponse = (TextView)findViewById(R.id.response);
        new Thread(new ClientThread()).start();
        buttonConnect.setOnClickListener(buttonConnectOnClickListener);
       // buttonSend.setOnClickListener(buttonSendOnClickListener);

    }

    class ClientThread implements Runnable {

        @Override
        public void run() {


            try {
                //InetAddress serverAddr = InetAddress.getByName(SERVER_IP);

                socket = new Socket("10.106.126.221", 65237);
                Log.d("myTag22", "socket established");


            } catch (UnknownHostException e1) {
                e1.printStackTrace();
            } catch (IOException e1) {
                e1.printStackTrace();
            }

        }

    }
    OnClickListener buttonConnectOnClickListener =
            new OnClickListener(){

                @Override
                public void onClick(View arg0) {
                    /*
                     * You have to verify editTextAddress and
                     * editTextPort are input as correct format.
                     */
                    MyClientTask myClientTask = new MyClientTask(editTextMsg.getText().toString());
                    myClientTask.execute();
                }};

    public class MyClientTask extends AsyncTask<Void, Void, Void> {

        String dstmsg;

        MyClientTask(String msgtosend){
            dstmsg=msgtosend;
        }

        @Override
        protected Void doInBackground(Void... arg0) {

            try {
                //socket = new Socket("10.106.126.221", 64238);
                Log.d("myTag22", "click");
                Log.d("myTag22", String.valueOf(dstmsg.length()));

                if (dstmsg.length()>2)
                {
                    Log.d("myTag22", "input_num");

                    OutputStream outputStream = socket.getOutputStream();
                    PrintStream printStream = new PrintStream(outputStream);
                    printStream.print(dstmsg);
                }
                else
                {
                    Log.d("myTag22", "close2");
                    socket.close();
                    Log.d("myTag22", "closed2");
                }




            } catch (UnknownHostException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return null;
        }



    }
/*
    OnClickListener buttonSendOnClickListener =
            new OnClickListener(){

                @Override
                public void onClick(View arg0) {
                    /*
                     * You have to verify editTextAddress and
                     * editTextPort are input as correct format.

                    MySendTask mySendTask = new MySendTask(editTextMsg.getText().toString());
                    mySendTask.execute();
                }};

    public class MySendTask extends AsyncTask<Void, Void, Void> {

        String dstmsg;

        MySendTask(String msgtosend){
            dstmsg=msgtosend;
        }

        @Override
        protected Void doInBackground(Void... arg0) {

            try {
                OutputStream outputStream = socket.getOutputStream();
                PrintStream printStream = new PrintStream(outputStream);
                printStream.print(dstmsg);
                socket.close();

            } catch (UnknownHostException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return null;
        }



    }*/

}
