package com.example.mdc.news;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static com.facebook.FacebookSdk.getApplicationContext;
import static java.lang.System.out;

public class Profile extends Fragment {
    private static final int RESULT_OK = 100;
    ImageView img_editprofile;
    TextView tv_editprofile;
    SharedPreferences sharedPreferences;
    ImageView imagecamera;
    de.hdodenhof.circleimageview.CircleImageView imageprofile;
    private static final int SELECT_PICTURE = 100;
    private static final int PICK_IMAGE_REQUEST = 234;
    private Uri filePath;
    static final int GALLARY_REQUEST_CODE = 213;
    static final int CROP_PICK = 321;
    ProgressDialog pd;
    FirebaseStorage storage = FirebaseStorage.getInstance();
    //creating a storage reference. Replace the below URL with your Firebase storage URL.
    //StorageReference storageRef = storage.getReferenceFromUrl("gs://cricket-b71a9.appspot.com");
    DatabaseReference ref;
    DatabaseReference databaseReference,childref;
    @Override
    public View onCreateView(final LayoutInflater inflater,  ViewGroup container, Bundle savedInstanceState) {
       // FacebookSdk.sdkInitialize(getApplicationContext());
        super.onCreate(savedInstanceState);

        final View rootView = inflater.inflate(R.layout.activity_profile, container, false);
        getActivity().setTitle("Profile");
        ref = FirebaseDatabase.getInstance()
                .getReference().child("Profile")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .child("image");
        img_editprofile = (ImageView) rootView.findViewById(R.id.img_edit);
        tv_editprofile = (TextView) rootView.findViewById(R.id.tv_edit);
        imageprofile = (de.hdodenhof.circleimageview.CircleImageView) rootView.findViewById(R.id.image_profile);
        imagecamera = (ImageView) rootView.findViewById(R.id.image_camera);
        final SharedPreferences sharedPreferences = getActivity().getSharedPreferences(Session.PREFS_NAME, 0);
        try{
            String mypick = sharedPreferences.getString(Session.KEY_PROFILE_PICK, "Image Not Available");
            byte[] decodedString = Base64.decode(mypick, Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);

            //Log.e("mobile:::", String.valueOf(mypick.toString().length()));

            imageprofile.setImageBitmap(decodedByte);
        }catch (Exception e){
            e.printStackTrace();
            imageprofile.setImageResource(R.drawable.profile);
            Log.e("mobile::","drawable");

        }
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
      //  onImageViewClick();
       // onUploadButtonClick();
        imagecamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imagecamera.setClickable(false);
                Intent photoPickerIntent = new Intent(Intent.ACTION_GET_CONTENT);
                photoPickerIntent.setType("image/*");
                photoPickerIntent.putExtra("return-data", true);
                startActivityForResult(photoPickerIntent, GALLARY_REQUEST_CODE);


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
        Log.e("image","image");
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GALLARY_REQUEST_CODE) {
          //  pd.setMessage("Uploading image...");
           // pd.show();

            try {
                Uri uri = data.getData();

                CropImage.activity(uri)
                        .setGuidelines(CropImageView.Guidelines.ON)

                        .setMinCropResultSize(100, 100)
                        .setRequestedSize(400, 400)
                        //.setMaxCropResultSize(500,500)
                        //.setFixAspectRatio(true)
                        .setOutputCompressFormat(Bitmap.CompressFormat.PNG).setOutputCompressQuality(10)

                        .setAspectRatio(1, 1)
                        .start(getActivity());
            } catch (Exception e) {
                e.printStackTrace();
                imagecamera.setClickable(true);
                return;
            }
            Log.e("image", "image");
            //   if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Log.e("image", "image");
            filePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), filePath);
                bitmap.compress(Bitmap.CompressFormat.PNG, 90, out);
                Log.e("image", String.valueOf(bitmap));
                imageprofile.setImageBitmap(bitmap);
                encodeBitmapAndSaveToFirebase(bitmap);
                // uploadFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            // }
        }

    else if(requestCode == CROP_PICK){

        //Uri uri = data.getData();

        Bundle extras = null;

        try{
            extras = data.getExtras();
        }catch (Exception e){
            imagecamera.setClickable(true);
            e.printStackTrace();
            return;

        }
        Bitmap pic2 = extras.getParcelable("data");

        Log.e("bitmap size",pic2.toString().length()+"");
        imageprofile.setImageBitmap(pic2);

        //InputStream is = null;
        //try {
        //    is = getContentResolver().openInputStream(uri);
        //} catch (FileNotFoundException e) {
        //    e.printStackTrace();
        //}
        //Bitmap photo = BitmapFactory.decodeStream(is);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        pic2.compress(Bitmap.CompressFormat.PNG, 10, stream);
        byte[] byteFormat = stream.toByteArray();


        String encodedImage = Base64.encodeToString(byteFormat, Base64.NO_WRAP);




        //StatusCheck statusCheck = new StatusCheck("Online");
        ProfilePick profilePick = new ProfilePick(encodedImage);

       ref.setValue(profilePick);
        Log.e("pick size",encodedImage.length()+"");

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(Session.PREFS_NAME, 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(Session.KEY_PROFILE_PICK, encodedImage);
        editor.commit();


    }

    else if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
        CropImage.ActivityResult result = CropImage.getActivityResult(data);
        if (resultCode == RESULT_OK) {
            Uri resultUri = result.getUri();
            //profilepick.setImageURI(resultUri);
            //Bundle extras = null;

            //try{
            //    extras = data.getExtras();
            //}catch (Exception e){
            //    e.printStackTrace();
            // }
            Bitmap picture = null;
            try {
                picture = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), resultUri);
                //picture = Bitmap.createScaledBitmap(picture,400,400,true);
            } catch (IOException e) {
                e.printStackTrace();
            }
            imageprofile.setImageBitmap(picture);
            //Log.e("bit map length",picture.getByteCount()+"");

            //Bitmap pic = extras.getParcelable("data");
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            picture.compress(Bitmap.CompressFormat.PNG, 50, stream);
            byte[] byteFormat = stream.toByteArray();

            Log.e("bit bytecode map length",byteFormat.length+"");

            String encodedImage = Base64.encodeToString(byteFormat, Base64.NO_WRAP);

            Log.e("bit bytecode map length",encodedImage.length()+"");


            ProfilePick profilePick = new ProfilePick(encodedImage);

            ref.setValue(profilePick);


            SharedPreferences sharedPreferences = getActivity().getSharedPreferences(Session.PREFS_NAME, 0);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(Session.KEY_PROFILE_PICK, encodedImage);
            editor.commit();

            return;


            //cropImageView.setImageUriAsync(resultUri);
        } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
            Exception error = result.getError();
            Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    else{
        Log.e("sory", "problem");
        return;

    }

   imagecamera.setClickable(true);
}




    public void encodeBitmapAndSaveToFirebase(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        String imageEncoded = Base64.encodeToString(baos.toByteArray(), Base64.DEFAULT);

        ref.setValue(imageEncoded);
    }

    protected  void onImageViewClick(){
        imageprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent();
                i.setType("image/*");
                i.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(i, "Select Picture"),SELECT_PICTURE );
            }
        });

    }
  /*  private void uploadFile() {
        //if there is a file to upload
        if (filePath != null) {
            //displaying a progress dialog while upload is going on
            final ProgressDialog progressDialog = new ProgressDialog(getActivity());
            progressDialog.setTitle("Uploading");
            progressDialog.show();

          //  StorageReference riversRef = storage.getReferenceFromUrl("gs://cricket-b71a9.appspot.com");
            ref.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            //if the upload is successfull
                            //hiding the progress dialog
                            progressDialog.dismiss();

                            //and displaying a success toast
                            Toast.makeText(getApplicationContext(), "File Uploaded ", Toast.LENGTH_LONG).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            //if the upload is not successfull
                            //hiding the progress dialog
                            progressDialog.dismiss();

                            //and displaying error message
                            Toast.makeText(getApplicationContext(), exception.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            //calculating progress percentage
                            double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();

                            //displaying percentage in progress dialog
                            progressDialog.setMessage("Uploaded " + ((int) progress) + "%...");
                        }
                    });
        }
        //if there is not any file
        else {
            //you can display an error toast
        }
    }
    /*public void onActivityResult(int requestCode, int resultCode, Intent data){
        if(resultCode==RESULT_OK){
            if(requestCode==SELECT_PICTURE){
                Uri selectedImageUri = data.getData();
                if (null != selectedImageUri) {
                    // Get the path from the Uri
                    String path = getPathFromURI(selectedImageUri);
                    Log.i("IMAGE PATH TAG", "Image Path : " + path);
                    // Set the image in ImageView
                    imageprofile.setImageURI(selectedImageUri);

                }
            }
        }
    }*/
    private String getPathFromURI(Uri contentUri) {
        String res = null;
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = getActivity().getContentResolver().query(contentUri, proj, null, null, null);
        if (cursor.moveToFirst()) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            res = cursor.getString(column_index);
        }
        cursor.close();
        return res;
    }

    /*protected void onUploadButtonClick(){

        imagecamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Creating a reference to the full path of the file. myfileRef now points
                // gs://fir-demo-d7354.appspot.com/myuploadedfile.jpg
                StorageReference myfileRef = storageRef.child("myuploadedfile.jpg");
                imageprofile.setDrawingCacheEnabled(true);
                imageprofile.buildDrawingCache();
                Bitmap bitmap = imageprofile.getDrawingCache();
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                byte[] data = baos.toByteArray();

                UploadTask uploadTask = myfileRef.putBytes(data);
                uploadTask.addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        Toast.makeText(getActivity(), "TASK FAILED", Toast.LENGTH_SHORT).show();
                    }
                }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Toast.makeText(getActivity(), "TASK SUCCEEDED", Toast.LENGTH_SHORT).show();
                        Uri downloadUrl =taskSnapshot.getDownloadUrl();
                        String DOWNLOAD_URL = downloadUrl.getPath();
                        Log.v("DOWNLOAD URL", DOWNLOAD_URL);
                        Toast.makeText(getActivity(), DOWNLOAD_URL, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }
*/

}
