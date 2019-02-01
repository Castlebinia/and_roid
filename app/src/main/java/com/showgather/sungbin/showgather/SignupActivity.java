package com.showgather.sungbin.showgather;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.ActionCodeSettings;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.showgather.sungbin.showgather.model.UserModel;

public class SignupActivity extends AppCompatActivity {

    private EditText email;
    private EditText name;
    private EditText password;
    private Button siginup;
    private ImageView profile;
    private Uri imageUri;
    private static int PICK_FROM_ALBUM=10;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        FirebaseRemoteConfig mFirebaseRemoteConfig=FirebaseRemoteConfig.getInstance();
        String splash_background = mFirebaseRemoteConfig.getString(getString(R.string.rc_color));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(Color.parseColor(splash_background));
        }

        profile = (ImageView)findViewById(R.id.siginupActivity_image_profile);
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
                startActivityForResult(intent,PICK_FROM_ALBUM);
            }
        });

        email=(EditText)findViewById(R.id.signupActivity_edittext_email);
        name=(EditText)findViewById(R.id.signupActivity_edittext_name);
        password=(EditText)findViewById(R.id.signupActivity_edittext_password);
        siginup=(Button)findViewById(R.id.siginupActivity_button_siginup);
        siginup.setBackgroundColor(Color.parseColor(splash_background));

        siginup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(email.getText().length()==0 || password.getText().length()==0 ||name.getText().length()==0){
                    AlertDialog.Builder builder = new AlertDialog.Builder(SignupActivity.this);
                    Toast.makeText(SignupActivity.this,"공백이 있습니다.",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(imageUri==null){
                    AlertDialog.Builder builder = new AlertDialog.Builder(SignupActivity.this);
                    Toast.makeText(SignupActivity.this,"사진을 등록해주세요.",Toast.LENGTH_SHORT).show();
                    return;
                }
                FirebaseAuth.getInstance()
                        .createUserWithEmailAndPassword(email.getText().toString(),password.getText().toString())
                        .addOnCompleteListener(SignupActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()) {
                                    final String uid = task.getResult().getUser().getUid();
                                    final StorageReference profileImageRef = FirebaseStorage.getInstance().getReference().child("userImages").child(uid);
                                    profileImageRef.putFile(imageUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                                            String imageUrl = profileImageRef.getDownloadUrl().toString();

                                            UserModel userModel = new UserModel();
                                            userModel.userName = name.getText().toString();
                                            userModel.profileImageUrl = imageUrl;

                                            FirebaseDatabase.getInstance().getReference().child("users").child(uid).setValue(userModel);

                                        }
                                    });
                                    Toast.makeText(SignupActivity.this,"회원가입 완료!",Toast.LENGTH_LONG).show();

                                    Intent intent = new Intent(SignupActivity.this,LoginActivity.class);
                                    SignupActivity.this.startActivity(intent);
                                }else {
                                    AlertDialog.Builder builder= new AlertDialog.Builder(SignupActivity.this);
                                    builder.setMessage("잘못 입력하셨습니다.")
                                            .setNegativeButton("다시 시도", null)
                                            .create()
                                            .show();
                                }
                            }
                        });
            }
        });

        }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_FROM_ALBUM && resultCode == RESULT_OK){
            profile.setImageURI(data.getData());    // 가운데 뷰를 바꿈
            imageUri = data.getData(); // 이미지 경로 원본
        }
    }
}
