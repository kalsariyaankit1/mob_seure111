package com.example.admin.mob_seure111;

import android.app.ActivityManager;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {
    final Context context = this;
    private EditText result;
    DevicePolicyManager deviceManger;
    ActivityManager activityManager;
    ComponentName compName;
    static final int RESULT_ENABLE = 1;

    LayoutInflater inflater;
    View alertLayout;
    AlertDialog dialog;
    EditText edCode,contact1,contact2,contact3,contact4,edopin,ednpin;
    Button btnApply;

    DatabaseHelper dbHelper;

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;

    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        Intent i;
        switch (menuItem.getItemId()) {
            case R.id.btnCls:
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
                alertDialogBuilder.setMessage("Are you sure, You wanted Close Account");
                alertDialogBuilder.setPositiveButton("yes",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                                Common.clearPre(getApplicationContext());
                                Intent i = new Intent(MainActivity.this, RegisterActivity.class);
                                startActivity(i);
                                finish();
                            }
                        });

                alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();

                break;
            case R.id.btnLogout:
                i = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(i);
                finish();
                break;
            case R.id.btnShow:
                i = new Intent(MainActivity.this, display.class);
                startActivity(i);
                break;
            case R.id.btnShow_contact:
                i = new Intent(MainActivity.this,display_contact.class);
                startActivity(i);
                break;
        }

        return true;

    }
    GridView grid;
    String[] web = {
            "Enter Cell No",
            "Add Primary No.",
            "Normal",
            "Silent",
            "Wifi On",
            "Wifi Off",
            "Data On",
            "Data Off",
            "Bluetooth On",
            "Bluetooth Off",
            "SOS Contact",
            "Location",
            "Ring out Loud",
            "Lock Screen",
            "Change Pin",
            "Change Recovery",
            "SIM Serial Key",
            "Track Record"
    } ;
    int[] imageId = {
            R.drawable.img,
            R.drawable.img,
            R.drawable.img,
            R.drawable.img,
            R.drawable.img,
            R.drawable.img,
            R.drawable.img,
            R.drawable.img,
            R.drawable.img,
            R.drawable.img,
            R.drawable.img,
            R.drawable.img,
            R.drawable.img,
            R.drawable.img,
            R.drawable.img,
            R.drawable.img,
            R.drawable.img,
            R.drawable.img


    };

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageAdapter adapter = new ImageAdapter(MainActivity.this, web, imageId);
        grid=(GridView)findViewById(R.id.grid);
        grid.setAdapter(adapter);

        dbHelper = new DatabaseHelper(getApplicationContext());

        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                //Toast.makeText(MainActivity.this, "You Clicked at " +web[+ position], Toast.LENGTH_SHORT).show();
                if(position==0){
                    dialog_Bind();
                    edCode.setText(Common.getPreferenceString(getApplicationContext(), "ecn", ""));
                    btnApply.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {


                            /*zString text = "Enter Cell No.";
                            String code = edCode.getText().toString();
                            dbHelper.open();
                            dbHelper.Insert(text,code);
                            dbHelper.close();*/
                            Common.setPreferenceString(getApplicationContext(), "ecn", edCode.getText().toString());
                            dialog.dismiss();

                        }
                    });
                    Toast.makeText(getApplicationContext(),"Enter Cell No",Toast.LENGTH_SHORT).show();
                }
                if(position==1){
                    dialog_Bind_contact();
                    contact1.setText(Common.getPreferenceString(getApplicationContext(), "pn1", ""));
                    contact2.setText(Common.getPreferenceString(getApplicationContext(), "pn2", ""));
                    contact3.setText(Common.getPreferenceString(getApplicationContext(), "pn3", ""));
                    contact4.setText(Common.getPreferenceString(getApplicationContext(), "pn4", ""));
                    btnApply.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String contact_name ="contact";
                            String contact_1 = contact1.getText().toString();
                            String contact_2=contact2.getText().toString();
                            String contact_3=contact3.getText().toString();
                            String contact_4=contact4.getText().toString();
                            dbHelper.open();
                            dbHelper.Insert_contact(contact_name,contact_1,contact_2,contact_3,contact_4);
                            Toast.makeText(getApplicationContext(),"addsuccessful",Toast.LENGTH_SHORT).show();
                            dbHelper.close();
                            Common.setPreferenceString(getApplicationContext(), "pn1",contact1.getText().toString());
                            Common.setPreferenceString(getApplicationContext(), "pn2",contact2.getText().toString());
                            Common.setPreferenceString(getApplicationContext(), "pn3",contact3.getText().toString());
                            Common.setPreferenceString(getApplicationContext(), "pn4",contact4.getText().toString());
                            dialog.dismiss();
                        }
                    });
                    Toast.makeText(getApplicationContext(),"Contact No",Toast.LENGTH_SHORT).show();
                }
                if(position==2){
                    dialog_Bind();
                    edCode.setText(Common.getPreferenceString(getApplicationContext(), "norm", ""));
                    btnApply.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String text = "Normal Mode";
                            String code = edCode.getText().toString();
                            dbHelper.open();
                            dbHelper.Insert(text,code);
                            dbHelper.close();
                            Common.setPreferenceString(getApplicationContext(), "norm", edCode.getText().toString());
                            dialog.dismiss();
                        }
                    });
                    Toast.makeText(getApplicationContext(),"NormalMode",Toast.LENGTH_SHORT).show();
                }

                if(position==3){
                    dialog_Bind();
                    edCode.setText(Common.getPreferenceString(getApplicationContext(), "sil", ""));
                    btnApply.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String text = "Silent Mode";
                            String code = edCode.getText().toString();
                            dbHelper.open();
                            dbHelper.Insert(text,code);
                            dbHelper.close();
                            Common.setPreferenceString(getApplicationContext(), "sil", edCode.getText().toString());
                            dialog.dismiss();
                        }
                    });
                    Toast.makeText(getApplicationContext(),"SilentMode",Toast.LENGTH_SHORT).show();
                }
                if(position==4){
                    dialog_Bind();
                    edCode.setText(Common.getPreferenceString(getApplicationContext(), "wio", ""));
                    btnApply.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String text = "Wifi On";
                            String code = edCode.getText().toString();
                            dbHelper.open();
                            dbHelper.Insert(text,code);
                            dbHelper.close();
                            Common.setPreferenceString(getApplicationContext(), "wio", edCode.getText().toString());
                            dialog.dismiss();
                        }
                    });
                    Toast.makeText(getApplicationContext(),"Wifi On",Toast.LENGTH_SHORT).show();
                }
                if(position==5){
                    dialog_Bind();
                    edCode.setText(Common.getPreferenceString(getApplicationContext(), "wif", ""));
                    btnApply.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String text = "Wifi Off";
                            String code = edCode.getText().toString();
                            dbHelper.open();
                            dbHelper.Insert(text,code);
                            dbHelper.close();
                            Common.setPreferenceString(getApplicationContext(), "wif", edCode.getText().toString());
                            dialog.dismiss();
                        }
                    });
                    Toast.makeText(getApplicationContext(),"Wifi Off",Toast.LENGTH_SHORT).show();
                }
                if(position==6){
                    dialog_Bind();
                    edCode.setText(Common.getPreferenceString(getApplicationContext(), "do", ""));
                    btnApply.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String text = "Data On";
                            String code = edCode.getText().toString();
                            dbHelper.open();
                            dbHelper.Insert(text,code);
                            dbHelper.close();
                            Common.setPreferenceString(getApplicationContext(), "do", edCode.getText().toString());
                            dialog.dismiss();
                        }
                    });
                    Toast.makeText(getApplicationContext(),"Data On",Toast.LENGTH_SHORT).show();
                }
                if(position==7){
                    dialog_Bind();
                    edCode.setText(Common.getPreferenceString(getApplicationContext(), "df", ""));
                    btnApply.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String text = "Data Off";
                            String code = edCode.getText().toString();
                            dbHelper.open();
                            dbHelper.Insert(text,code);
                            dbHelper.close();
                            Common.setPreferenceString(getApplicationContext(), "df", edCode.getText().toString());
                            dialog.dismiss();
                        }
                    });
                    Toast.makeText(getApplicationContext(),"Data Off",Toast.LENGTH_SHORT).show();
                }
                if(position==8){
                    dialog_Bind();
                    edCode.setText(Common.getPreferenceString(getApplicationContext(), "bo", ""));
                    btnApply.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String text = "Bluetooth On";
                            String code = edCode.getText().toString();
                            dbHelper.open();
                            dbHelper.Insert(text,code);
                            dbHelper.close();
                            Common.setPreferenceString(getApplicationContext(), "bo", edCode.getText().toString());
                            dialog.dismiss();
                        }
                    });
                    Toast.makeText(getApplicationContext(),"Bluetooth On",Toast.LENGTH_SHORT).show();
                }
                if(position==9){
                    dialog_Bind();
                    edCode.setText(Common.getPreferenceString(getApplicationContext(), "bf", ""));
                    btnApply.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String text = "Bluetooth Off";
                            String code = edCode.getText().toString();
                            dbHelper.open();
                            dbHelper.Insert(text,code);
                            dbHelper.close();
                            Common.setPreferenceString(getApplicationContext(), "bf", edCode.getText().toString());
                            dialog.dismiss();
                        }
                    });
                    Toast.makeText(getApplicationContext(),"Bluetooth Off",Toast.LENGTH_SHORT).show();
                }
                if(position==10){
                    dialog_Bind();
                    edCode.setText(Common.getPreferenceString(getApplicationContext(), "sos", ""));
                    btnApply.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String text = "SOS Contact";
                            String code = edCode.getText().toString();
                            dbHelper.open();
                            dbHelper.Insert(text,code);
                            dbHelper.close();
                            Common.setPreferenceString(getApplicationContext(), "sos", edCode.getText().toString());
                            dialog.dismiss();
                        }
                    });
                    Toast.makeText(getApplicationContext(),"SOS Contact",Toast.LENGTH_SHORT).show();
                }
                if(position==11){
                    dialog_Bind();
                    edCode.setText(Common.getPreferenceString(getApplicationContext(), "loc", ""));
                    btnApply.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String text = "Location";
                            String code = edCode.getText().toString();
                            dbHelper.open();
                            dbHelper.Insert(text,code);
                            dbHelper.close();
                            Common.setPreferenceString(getApplicationContext(), "loc", edCode.getText().toString());
                            dialog.dismiss();
                        }
                    });
                    Toast.makeText(getApplicationContext(),"Location",Toast.LENGTH_SHORT).show();
                }

                if(position==12){
                    dialog_Bind();
                    edCode.setText(Common.getPreferenceString(getApplicationContext(), "rol", ""));
                    btnApply.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String text = "Ring Out Loud";
                            String code = edCode.getText().toString();
                            dbHelper.open();
                            dbHelper.Insert(text,code);
                            dbHelper.close();
                            Common.setPreferenceString(getApplicationContext(), "rol", edCode.getText().toString());
                            dialog.dismiss();
                        }
                    });
                    Toast.makeText(getApplicationContext(),"Ring Out Loud",Toast.LENGTH_SHORT).show();
                }

                if(position==13){
                    deviceManger = (DevicePolicyManager) getSystemService(Context.DEVICE_POLICY_SERVICE);
                    activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
                    compName = new ComponentName(getApplicationContext(), MyAdmin.class);

                    Intent intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
                    intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, compName);
                    intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION, "Some Description About Your Admin");
                    startActivityForResult(intent, RESULT_ENABLE);

                    dialog_Bind();
                    edCode.setText(Common.getPreferenceString(getApplicationContext(), "lock", ""));
                    btnApply.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String text = "Lock Screen";
                            String code = edCode.getText().toString();
                            dbHelper.open();
                            dbHelper.Insert(text,code);
                            dbHelper.close();
                            Common.setPreferenceString(getApplicationContext(), "lock", edCode.getText().toString());
                            dialog.dismiss();
                        }
                    });
                    Toast.makeText(getApplicationContext(),"Lock Screen",Toast.LENGTH_SHORT).show();
                }
                if(position==14){
                    dialog_Bind_pin();
                    edopin.setText(Common.getPreferenceString(getApplicationContext(), "cp1", ""));
                    ednpin.setText(Common.getPreferenceString(getApplicationContext(), "cp2", ""));
                    btnApply.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String text = "Change PIN";
                            String code = ednpin.getText().toString();
                            dbHelper.open();
                            dbHelper.Insert(text,code);
                            dbHelper.close();
                            Common.setPreferenceString(getApplicationContext(), "pass", ednpin.getText().toString());
                            dialog.dismiss();
                        }
                    });
                    Toast.makeText(getApplicationContext(),"Change PIN",Toast.LENGTH_SHORT).show();
                }
                if(position==15){
                    dialog_Bind();
                    edopin.setText(Common.getPreferenceString(getApplicationContext(), "cr", ""));
                    btnApply.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String text = "Change Recovery";
                            String code = edCode.getText().toString();
                            dbHelper.open();
                            dbHelper.Insert(text,code);
                            dbHelper.close();
                            Common.setPreferenceString(getApplicationContext(), "cr", edCode.getText().toString());
                            dialog.dismiss();
                        }
                    });
                    Toast.makeText(getApplicationContext(),"Change Recovery",Toast.LENGTH_SHORT).show();
                }
                if(position==16){
                    dialog_Bind();
                    TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);


                    if (ActivityCompat.checkSelfPermission(MainActivity.this, android.Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        return;
                    }

                    edCode.setText(telephonyManager.getSimSerialNumber());
                    btnApply.setText("Close");
                    btnApply.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Common.setPreferenceString(getApplicationContext(), "sim", edCode.getText().toString());
                            dialog.dismiss();
                        }
                    });

                   /* edCode.setText(Common.getPreferenceString(getApplicationContext(), "ssk", ""));
                    btnApply.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String text = "SIM Serial Key";
                            String code = edCode.getText().toString();
                            dbHelper.open();
                            dbHelper.Insert(text,code);
                            dbHelper.close();
                            Common.setPreferenceString(getApplicationContext(), "ssk", edCode.getText().toString());
                            dialog.dismiss();
                        }
                    });*/
                    Toast.makeText(getApplicationContext(),"SIM Serial Key",Toast.LENGTH_SHORT).show();
                }
                if(position==17){
                    Intent i = new Intent(MainActivity.this,TrackActivity.class);
                    startActivity(i);
                }

            }

            public void dialog_Bind() {
                inflater = getLayoutInflater();
                alertLayout = inflater.inflate(R.layout.customdialogmodule, null);
                AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
                alert.setView(alertLayout);
                alert.setCancelable(true);
                dialog = alert.create();
                dialog.requestWindowFeature(1);
                //dialog.getWindow().setBackgroundDrawable((Drawable)new ColorDrawable(0));
                dialog.show();
                btnApply = (Button)dialog.findViewById(R.id.btnApply);
                edCode = (EditText) dialog.findViewById(R.id.et_Code);

            }
            public void dialog_Bind_contact() {
                inflater = getLayoutInflater();
                alertLayout = inflater.inflate(R.layout.customedialogcontact, null);
                AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
                alert.setView(alertLayout);
                alert.setCancelable(true);
                dialog = alert.create();
                dialog.requestWindowFeature(1);
                //dialog.getWindow().setBackgroundDrawable((Drawable)new ColorDrawable(0));
                dialog.show();
                btnApply = (Button)dialog.findViewById(R.id.btnApply);
                contact1 = (EditText) dialog.findViewById(R.id.contact1);
                contact2 = (EditText) dialog.findViewById(R.id.contact2);
                contact3 = (EditText) dialog.findViewById(R.id.contact3);
                contact4 = (EditText) dialog.findViewById(R.id.contact4);

            }
            public void dialog_Bind_pin() {
                inflater = getLayoutInflater();
                alertLayout = inflater.inflate(R.layout.customdialogpin, null);
                AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
                alert.setView(alertLayout);
                alert.setCancelable(true);
                dialog = alert.create();
                dialog.requestWindowFeature(1);
                //dialog.getWindow().setBackgroundDrawable((Drawable)new ColorDrawable(0));
                dialog.show();
                btnApply = (Button)dialog.findViewById(R.id.btnApply);
                edopin = (EditText) dialog.findViewById(R.id.et_old_pin);
                ednpin = (EditText) dialog.findViewById(R.id.et_new_pin);

            }


        });

    }


}
