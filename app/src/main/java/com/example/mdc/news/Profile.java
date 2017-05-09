package com.example.mdc.news;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import static com.facebook.FacebookSdk.getApplicationContext;
import static java.lang.System.out;

public class Profile extends Fragment {
    private static final int RESULT_OK = 100;
    ImageView img_editprofile;
    TextView tv_editprofile,tv_editedname,tv_editlive;
    SharedPreferences sharedPreferences;
    ImageView imagecamera,nameedit,changepswd;
    boolean isImageFitToScreen;
    private FirebaseAuth firebaseAuth;
    de.hdodenhof.circleimageview.CircleImageView imageprofile;
    private static final int SELECT_PICTURE = 100;
    private static final int PICK_IMAGE_REQUEST = 234;
    private Uri filePath;
    static final int GALLARY_REQUEST_CODE = 213;
    static final int CROP_PICK = 321;
    ProgressDialog pd;
    String mypick;
    ArrayList<UserProfile> userProfiles =new ArrayList<>();
    FirebaseStorage storage = FirebaseStorage.getInstance();
    //creating a storage reference. Replace the below URL with your Firebase storage URL.
    //StorageReference storageRef = storage.getReferenceFromUrl("gs://cricket-b71a9.appspot.com");
    DatabaseReference ref,ref1;
    DatabaseReference databaseReference,childref;
    FirebaseUser user;
    @Override
    public View onCreateView(final LayoutInflater inflater,  ViewGroup container, Bundle savedInstanceState) {
       // FacebookSdk.sdkInitialize(getApplicationContext());
        super.onCreate(savedInstanceState);

        final View rootView = inflater.inflate(R.layout.activity_profile, container, false);
        getActivity().setTitle("Profile");
        pd = new ProgressDialog(getActivity());

        img_editprofile = (ImageView) rootView.findViewById(R.id.img_edit);
        tv_editprofile = (TextView) rootView.findViewById(R.id.tv_edit);
        imageprofile = (de.hdodenhof.circleimageview.CircleImageView) rootView.findViewById(R.id.image_profile);
        imagecamera = (ImageView) rootView.findViewById(R.id.image_camera);
        nameedit = (ImageView) rootView.findViewById(R.id.namechange);
        tv_editedname = (TextView) rootView.findViewById(R.id.tv_name);
        changepswd = (ImageView) rootView.findViewById(R.id.img_changepswd);
        tv_editlive = (TextView) rootView.findViewById(R.id.tv_lives);
        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();

        changepswd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ChangePassword.class);
                startActivity(intent);
            }
        });
        //if the user is not logged in
        //that means current user will return null
        if (firebaseAuth.getCurrentUser() == null) {
            //closing this activity
            getActivity().finish();
            //starting login activity
            startActivity(new Intent(getActivity(), LoginActivity.class));
        }
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Profile");
        final SharedPreferences sharedPreferences = getActivity().getSharedPreferences(Session.PREFS_NAME, 0);
        String name = sharedPreferences.getString(Session.KEY_NAME, "Edit your Name");
      tv_editedname.setText(name);
        try{
            String mypick = sharedPreferences.getString(Session.KEY_PROFILE_PICK, "Image Not Available");
            Log.e("image","image");
            byte[] decodedString = Base64.decode(mypick, Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            imageprofile.setImageBitmap(decodedByte);
        }catch (Exception e){
            e.printStackTrace();
            imageprofile.setImageResource(R.drawable.profile2);
            Log.e("mobile::","drawable");

        }
        ref = FirebaseDatabase.getInstance()
                .getReference().child("Profile")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .child("name");
              Log.e("profileref", String.valueOf(ref));
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                Log.e("profilesnapshot", String.valueOf(snapshot));
              //  for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                String name = (String) snapshot.getValue();
                  //  UserProfile userProfile = snapshot.getValue(UserProfile.class);
                    String string = "Name: " + name;
                    Log.e("name", String.valueOf(name));
                    tv_editedname.setText(name);
               // }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getMessage());
            }
        });
        ref1 = FirebaseDatabase.getInstance()
                .getReference().child("Profile")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .child("city");
        ref1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                Log.e("profilesnapshot", String.valueOf(snapshot));
                //  for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                String name = (String) snapshot.getValue();
                //  UserProfile userProfile = snapshot.getValue(UserProfile.class);
                String string = "Name: " + name;
                Log.e("name", String.valueOf(name));
                tv_editlive.setText(name);
                // }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getMessage());
            }
        });
        img_editprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),ProfileEdit.class));
            }
        });
        tv_editprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),ProfileEdit.class));
            }
        });

        nameedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),ProfileNameEdit.class);
                startActivity(intent);
            }
        });
        mypick = sharedPreferences.getString(Session.KEY_PROFILE_PICK, "Image Not Available");
        imageprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Profilepickdisplay.class);
                startActivity(intent);



                if(isImageFitToScreen) {
                    // isImageFitToScreen=false;
                    //profilepick.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                    //profilepick.setAdjustViewBounds(true);
                }else{
                    //isImageFitToScreen=true;
                    // profilepick.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
                    //profilepick.setScaleType(ImageView.ScaleType.CENTER);
                }
            }
        });
        imagecamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             imagecamera.setClickable(false);
               // Intent photoPickerIntent = new Intent(Intent.ACTION_GET_CONTENT);
               // photoPickerIntent.setType("image/*");
               // photoPickerIntent.putExtra("return-data", true);
               // startActivityForResult(photoPickerIntent, GALLARY_REQUEST_CODE);

               showFileChooser();
            }
        });
        return rootView;
    }
    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");

        intent.setAction(Intent.ACTION_GET_CONTENT);

        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    //handling the image chooser activity result
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.e("image", "image");
        super.onActivityResult(requestCode, resultCode, data);
        if ( data != null && data.getData() != null) {
            Log.e("image", "image");

          filePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), filePath);

             /*   CropImage.activity(filePath)
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .setMinCropResultSize(100,100)
                        .setRequestedSize(400,400)
                        //.setMaxCropResultSize(500,500)
                        //.setFixAspectRatio(true)
                        .setOutputCompressFormat(Bitmap.CompressFormat.PNG).setOutputCompressQuality(10)
                        .setAspectRatio(1,1)
                        .start(getActivity());*/
                bitmap.compress(Bitmap.CompressFormat.JPEG, 10, out);
                imageprofile.setImageBitmap(bitmap);
                encodeBitmapAndSaveToFirebase(bitmap);


            } catch (IOException e) {
                e.printStackTrace();
            }
            imagecamera.setClickable(true);
            Log.e("gallery", "gallery");
        }
    }

    public void encodeBitmapAndSaveToFirebase(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
    bitmap.compress(Bitmap.CompressFormat.JPEG, 10, stream);
        byte[] byteFormat = stream.toByteArray();
        String imageEncoded = Base64.encodeToString(byteFormat, Base64.NO_WRAP);
        databaseReference.child(user.getUid()).child("image").setValue(imageEncoded);
       /* ref = FirebaseDatabase.getInstance()
                .getReference().child("Profile")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .child("image");
         Log.e("ref", String.valueOf(ref));
        ref.setValue(imageEncoded);*/
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(Session.PREFS_NAME, 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(Session.KEY_PROFILE_PICK, imageEncoded);
        editor.commit();
    }
    public static Bitmap decodeFromFirebaseBase64(String image) throws IOException {
        byte[] decodedByteArray = android.util.Base64.decode(image, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(decodedByteArray, 0, decodedByteArray.length);
    }

}
