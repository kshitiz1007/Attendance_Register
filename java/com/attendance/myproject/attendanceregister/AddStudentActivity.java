package com.attendance.myproject.attendanceregister;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class AddStudentActivity extends AppCompatActivity {
    private EditText et_user_id,et_name,et_roll_no,et_dept,et_phone,et_email,et_password;
    ProgressDialog pd;
    Button b5;
    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;
    CheckBox cb1,cb2,cb3,cb4,cb5,cb6,cb7,cb8;
    FirebaseDatabase firebaseDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_student);
        et_user_id=(EditText)findViewById(R.id.editText5);
        et_name=(EditText)findViewById(R.id.editText6);
        et_roll_no=(EditText)findViewById(R.id.editText7);
        et_dept=(EditText)findViewById(R.id.editText8);
        et_phone=(EditText)findViewById(R.id.editText9);
        et_email=(EditText)findViewById(R.id.editText10);
        et_password=(EditText)findViewById(R.id.editText11);
        b5=(Button)findViewById(R.id.button5);

        firebaseAuth=FirebaseAuth.getInstance();
        pd=new ProgressDialog(this);
        if(firebaseAuth.getCurrentUser()!=null){
            Toast.makeText(AddStudentActivity.this, "Already signIn", Toast.LENGTH_SHORT).show();
            firebaseAuth.signOut();
        }


        cb1=(CheckBox)findViewById(R.id.checkBox1);
        cb2=(CheckBox)findViewById(R.id.checkBox2);
        cb3=(CheckBox)findViewById(R.id.checkBox3);
        cb4=(CheckBox)findViewById(R.id.checkBox4);
        cb5=(CheckBox)findViewById(R.id.checkBox5);
        cb6=(CheckBox)findViewById(R.id.checkBox6);
        cb7=(CheckBox)findViewById(R.id.checkBox7);
        cb8=(CheckBox)findViewById(R.id.checkBox8);

    }

    public void fnRegister(View view) {
        Log.e("Entering ","In fn");

            String userid=et_user_id.getText().toString().trim();
            String name=et_name.getText().toString().trim();
            String rollno=et_roll_no.getText().toString().trim();
            String dept=et_dept.getText().toString().trim();
            String phone=et_phone.getText().toString().trim();
            String email=et_email.getText().toString().trim();
            String password=et_password.getText().toString().trim();

            //checking checkboxes

            ArrayList<Integer> subject=new ArrayList<Integer>(8);
        for(int i=0;i<8;i++)
            subject.add(0);
            if(!cb1.isChecked())
                subject.add(0,-1);
            if(!cb2.isChecked())
                subject.add(1,-1);
            if(!cb3.isChecked())
                subject.add(2,-1);
            if(!cb4.isChecked())
                subject.add(3,-1);
            if(!cb5.isChecked())
                subject.add(4,-1);
            if(!cb6.isChecked())
                subject.add(5,-1);
            if(!cb7.isChecked())
                subject.add(6,-1);
            if(!cb8.isChecked())
                subject.add(7,-1);
            int sum=0;
            for(int i=0;i<8;i++)
                sum=sum+subject.get(i);
            if(sum==-8){
                Toast.makeText(AddStudentActivity.this, "Please Select subjects", Toast.LENGTH_SHORT).show();
                return ;
            }
            String arr[]=new String[]{ userid,name,rollno,dept,phone,email,password };
            for(int i=0;i<7;i++){
                if(TextUtils.isEmpty(arr[i])){
                    Toast.makeText(AddStudentActivity.this, "Block cannot be empty", Toast.LENGTH_SHORT).show();
                    return ;
                }
            }
            if(password.length()<6){
                Toast.makeText(AddStudentActivity.this, "Password should contain min. 6 characters", Toast.LENGTH_SHORT).show();
                return ;
            }

            Log.e("Entering ", "In fn2");
            Toast.makeText(AddStudentActivity.this, "Validation Successful", Toast.LENGTH_SHORT).show();

            pd.setMessage("Registering User...");
            pd.setCancelable(false);
            pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            pd.show();

            Log.e("Entering ", "In fn3");
          try{
            final StudentInformation stdinfo=new StudentInformation(name,rollno,dept,phone,email,password,subject);
            firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(AddStudentActivity.this, "User Registered Successfully", Toast.LENGTH_SHORT).show();
                        saveInformation(stdinfo);
                        pd.dismiss();
                    } else {
                        Toast.makeText(AddStudentActivity.this, "Unable to register", Toast.LENGTH_SHORT).show();
                        Log.e("Exception is", task.getException().toString());
                        pd.dismiss();
                    }
                }
            });
        }catch(Exception e){
            Log.e("Exception is ",e.toString());
        }


    }
    public void saveInformation(StudentInformation stdinfo){

        try {
            FirebaseUser user = firebaseAuth.getCurrentUser();
            databaseReference = FirebaseDatabase.getInstance().getReference();
            databaseReference.child(user.getUid()).setValue(stdinfo);
            //Toast.makeText(AddStudentActivity.this, "Information Stored", Toast.LENGTH_SHORT).show();
        }catch (Exception e){
            Log.e("Exception is",e.toString());
        }

        firebaseAuth.signOut();
        //Toast.makeText(AddStudentActivity.this, "Signed Out", Toast.LENGTH_SHORT).show();
        //Log.e("Signed","out");

    }
}
