package com.agh.roboscarasterowanie;

import android.app.Activity;
import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {

    //obiekty statyczne aby po zamknieciu aplikacji byly nadal w pamieci
    private static ServerThread serverThread = null;
    private static ConnectToServerThread connectToServerThread = null;

    static BluetoothAdapter mBluetoothAdapter;
    static BluetoothDevice device;


    public final static String UUID = "00001101-0000-1000-8000-00805F9B34FB";

    Button connectButton;

    Button a;
    Button g;
    Button b;
    Button p;
    Button l;
    Button d;
    Button x;
    Button y;
    Button z;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        IntentFilter filter = new IntentFilter();
        filter.addAction(BluetoothDevice.ACTION_ACL_CONNECTED);
        filter.addAction(BluetoothDevice.ACTION_ACL_DISCONNECTED);
        registerReceiver(mReceiver, filter);

        connectButton = (Button) findViewById(R.id.connect_button);

        a.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN) {
                    if(connectToServerThread != null)
                        new WriteTask().execute('a');
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    if(connectToServerThread != null)
                        new WriteTask().execute('A');
                }
                return false;
            }
        });

        b.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN) {
                    if(connectToServerThread != null)
                        new WriteTask().execute('b');
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    if(connectToServerThread != null)
                        new WriteTask().execute('B');
                }
                return false;
            }
        });

        g.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN) {
                    if(connectToServerThread != null)
                        new WriteTask().execute('g');
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    if(connectToServerThread != null)
                        new WriteTask().execute('G');
                }
                return false;
            }
        });

        l.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN) {
                    if(connectToServerThread != null)
                        new WriteTask().execute('l');
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    if(connectToServerThread != null)
                        new WriteTask().execute('L');
                }
                return false;
            }
        });

        p.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN) {
                    if(connectToServerThread != null)
                        new WriteTask().execute('p');
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    if(connectToServerThread != null)
                        new WriteTask().execute('P');
                }
                return false;
            }
        });

        d.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN) {
                    if(connectToServerThread != null)
                        new WriteTask().execute('d');
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    if(connectToServerThread != null)
                        new WriteTask().execute('D');
                }
                return false;
            }
        });

        x.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN) {
                    if(connectToServerThread != null)
                        new WriteTask().execute('x');
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    if(connectToServerThread != null)
                        new WriteTask().execute('G');
                }
                return false;
            }
        });

        y.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN) {
                    if(connectToServerThread != null)
                        new WriteTask().execute('y');
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    if(connectToServerThread != null)
                        new WriteTask().execute('Y');
                }
                return false;
            }
        });


        z.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN) {
                    if(connectToServerThread != null)
                        new WriteTask().execute('z');
                } else if (event.getAction() == MotionEvent.ACTION_UP) {

                    if(connectToServerThread != null)
                        new WriteTask().execute('Z');
                }
                return false;
            }
        });

        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        return super.onTouchEvent(event);
    }

    public void onConnect(View view) {
        if(connectToServerThread != null) {
            AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
            alertDialog.setTitle("Połączenie");
            alertDialog.setMessage("Czy chcesz się rozłączyć z " + device.getName());
            alertDialog.setButton(DialogInterface.BUTTON_POSITIVE,
                    "Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    connectToServerThread.cancel();
                    serverThread.cancel();
                    connectToServerThread = null;
                    serverThread = null;
                    connectButton.setText("Trwa rozłączanie...");
                    connectButton.setBackgroundResource(android.R.color.holo_blue_dark);
                    connectButton.setEnabled(false);
                }
            });
            alertDialog.setCancelable(true);
            alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Anuluj",
                    new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    dialog.cancel();
                }
            });
            alertDialog.show();
        }
        else {
            connectButton.setBackgroundResource(android.R.color.holo_blue_dark);
            connectButton.setText("Łączenie...");
            connectButton.setEnabled(false);
            Intent intent = new Intent(getBaseContext(), FindDevicesDialog.class);
            startActivityForResult(intent, 101);
        }
    }

    public static void setDevice(BluetoothDevice newDevice) {
        device = newDevice;
    }


    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();

            if (BluetoothDevice.ACTION_ACL_CONNECTED.equals(action)) {
                connectButton.setBackgroundResource(android.R.color.holo_green_dark);
                connectButton.setEnabled(true);
                connectButton.setText("Połączono");

            } else if (BluetoothDevice.ACTION_ACL_DISCONNECTED.equals(action)) {
                connectButton.setBackgroundResource(android.R.color.holo_red_dark);
                connectButton.setEnabled(true);
                connectButton.setText("Połącz urządzenie");
            }
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == requestCode){
            if (device != null) {
                serverThread = new ServerThread(mBluetoothAdapter);
                serverThread.start();

                connectToServerThread = new ConnectToServerThread(device, mBluetoothAdapter);
                connectToServerThread.start();
            }
        }
        else {
            if (connectToServerThread == null) {
                connectButton.setBackgroundResource(android.R.color.holo_red_dark);
                connectButton.setEnabled(true);
                connectButton.setText("Połącz urządzenie");
            }
        }
    }


    public static class WriteTask extends AsyncTask<Character, Void, Void> {
        @Override
        protected Void doInBackground(Character... args) {
            try {
                connectToServerThread.commsThread.write(args[0]);
            } catch (Exception e) {
                Log.d("TaskService", e.getLocalizedMessage());
            }
            return null;
        }
    }
}
