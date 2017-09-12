package com.sivaram.session7assignment3;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {

    Button btnBrowseGallary;
    Button btnClearImage;
    ImageView ivSelectedImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Reference Button & Image View
        btnBrowseGallary = (Button)findViewById(R.id.btnBrowseGallary);
        btnClearImage = (Button)findViewById(R.id.btnClearImage);
        ivSelectedImage = (ImageView)findViewById(R.id.imgSelectedPicture);

        // Set On CLick Listener to Browse Image which are available within the device.
        btnBrowseGallary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Create an intent and show gallary to browse.
                Intent gallaryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);

                // Filter Only Images / Ignore Videos.
                gallaryIntent.setType("image/*");

                // Start Activity For Result instead start Activity. Result with return image data along with
                // Return result and status.
                startActivityForResult(gallaryIntent,1);
            }
        });

        // Set On Click Listener To Clear Selected Image
        btnClearImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ivSelectedImage.setImageBitmap(null);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK){
            // Start Try Catch Block To Catch File Not Found Exception.
            try {
                // Retrieve Selected Image Details
                Uri selectedImageUri = data.getData();

                // Open Input Stream And Convert Selected Image to Stream
                InputStream imageInputStream = getContentResolver().openInputStream(selectedImageUri);

                // Decode Input Stream object to Image OBject.
                Bitmap selectedImage = BitmapFactory.decodeStream(imageInputStream);

                // Set Selected Image to Image View
                ivSelectedImage.setImageBitmap(selectedImage);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                // Toast If an error occurred.
                Toast.makeText(this, "An error occurred while loading the image.", Toast.LENGTH_LONG).show();
            }
        }
        else
            Toast.makeText(this, "Selection Cancelled by user.", Toast.LENGTH_LONG).show();

    }
}
