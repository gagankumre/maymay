package com.example.gagan.maymay

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.support.v4.app.ActivityCompat
import android.view.View
import android.widget.Button
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_post_meme.*
import java.io.ByteArrayOutputStream
import java.text.SimpleDateFormat
import java.util.*
import com.google.firebase.storage.UploadTask
import com.google.firebase.storage.OnProgressListener
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_notification.*
import kotlinx.android.synthetic.main.activity_profile.*


class PostMeme : AppCompatActivity() {

    var mAuth:FirebaseAuth?=null
    var firebase: FirebaseAuth?=null
    var database=FirebaseDatabase.getInstance()
    var myref=database.reference

    var userName:String?=null


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        mAuth= FirebaseAuth.getInstance()
        setContentView(R.layout.activity_post_meme)

        ivMemeCreatePost.setOnClickListener {
            CheckPermission()
        }

        LoadUserName()

        memepostbtn.setOnClickListener{
           //var user = FirebaseAuth.getInstance().getCurrentUser()
            myref.child("Meme").push().setValue(PostMemeToDatabse(textmeme.text.toString(),DownloadUrl,userName!!))

            Toast.makeText(this@PostMeme, "posting meme", Toast.LENGTH_SHORT).show()
            finish()
        }
        homebtn4.setOnClickListener{
           // var intent= Intent(this@PostMeme,Home::class.java)
            //intent.putExtra("key", "Kotlin")
            //startActivity(intent)
            finish()
        }
        profilebtn4.setOnClickListener{
            var intent= Intent(this@PostMeme,activity_profile::class.java)
            startActivity(intent)
        }
        notificationbtn4.setOnClickListener{
            var intent= Intent(this@PostMeme,Notification::class.java)
            startActivity(intent)
        }



    }

    var READIMAGE:Int=253

    fun CheckPermission(){
        if (Build.VERSION.SDK_INT>23){
            if (ActivityCompat.checkSelfPermission(this,android.Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){

               return
            }
        }
        LoadImage()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {

        when(requestCode){
            READIMAGE->{
                if (grantResults[0]== PackageManager.PERMISSION_GRANTED){
                    LoadImage()
                }else{
                    Toast.makeText(applicationContext,"Can't Grant Permission", Toast.LENGTH_SHORT).show()
                }
            }
            else->{
                super.onRequestPermissionsResult(requestCode, permissions, grantResults)
            }
        }
    }

    var PICK_IMAGE_CODE=123

    fun LoadImage(){
        var photoGallery= Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(photoGallery,PICK_IMAGE_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (Build.VERSION.SDK_INT>=23){
            if (data!=null){
                var uri=data.data
                UploadFile(uri)
            }
        }else{
            if (requestCode==PICK_IMAGE_CODE && data!=null && resultCode== Activity.RESULT_OK){

                val selectedImage=data.data
                val filePathCol= arrayOf(MediaStore.Images.Media.DATA)
                var cursor=contentResolver.query(selectedImage,filePathCol,null,null,null)
                cursor.moveToFirst()
                val colIndex=cursor.getColumnIndex(filePathCol[0])
                val picturePath=cursor.getString(colIndex)
                cursor.close()

                UploadImage(BitmapFactory.decodeFile(picturePath))
            }
        }
    }

    var DownloadUrl:String=""

    fun UploadImage(bitmap: Bitmap){

        val storage= FirebaseStorage.getInstance()
        val storageRef=storage.getReferenceFromUrl("gs://maymaycfd1.appspot.com")
        val df= SimpleDateFormat("ddMMyyHHmmss")
        val dateObj= Date()
        val imgPath=SplitString(mAuth!!.currentUser!!.email.toString())+"."+ df.format(dateObj)+ ".jpg"
        val imgRef=storageRef.child("Meme/"+imgPath)
        var baos= ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,baos)
        val data=baos.toByteArray()
        val uploadTask=imgRef.putBytes(data)

        uploadTask.addOnFailureListener{
            Toast.makeText(applicationContext,"Uploading Image Failed", Toast.LENGTH_SHORT).show()
        }.addOnSuccessListener { taskSnapshot ->
                    DownloadUrl=taskSnapshot.downloadUrl!!.toString()
                    if (DownloadUrl!=""){
                        Picasso.with(this).load(DownloadUrl).into(ivMemeCreatePost)
                    }
                }


    }

    fun UploadFile(uri:Uri){
        val storage= FirebaseStorage.getInstance()
        val storageRef=storage.getReferenceFromUrl("gs://maymaycfd1.appspot.com")
        val df= SimpleDateFormat("ddMMyyHHmmss")
        val dateObj= Date()
        val imgPath=SplitString(mAuth!!.currentUser!!.email.toString())+"."+ df.format(dateObj)+ ".jpg"
        val imgRef=storageRef.child("Meme/"+imgPath)
        var uploadTask=imgRef.putFile(uri)

        uploadTask.addOnFailureListener{
            Toast.makeText(applicationContext,"Uploading Image Failed", Toast.LENGTH_SHORT).show()
        }.addOnSuccessListener { taskSnapshot ->
                    DownloadUrl=taskSnapshot.downloadUrl!!.toString()
                    if (DownloadUrl!=""){
                        Picasso.with(this).load(DownloadUrl).into(ivMemeCreatePost)
                    }
                }
    }

    fun SplitString(str:String):String{
        val split=str.split("@")
        return split[0]
    }

    fun LoadUserName(){
        myref.child("User Info").child(mAuth!!.currentUser!!.uid)
                .addValueEventListener(object:ValueEventListener{
                    override fun onCancelled(p0: DatabaseError?) {

                    }

                    override fun onDataChange(p0: DataSnapshot?) {

                        userName=p0!!.child("name").value as String
                    }

                })
    }




}

