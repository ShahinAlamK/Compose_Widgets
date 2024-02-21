package com.example.composewidgets.screens

import android.content.ContentResolver
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp

@RequiresApi(Build.VERSION_CODES.P)
@Composable
fun HomeScreen() {

    val context = LocalContext.current
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    val bitMap = remember { mutableStateOf<Bitmap?>(null) }
    val launcher = rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()){uri: Uri? ->
        imageUri = uri
    }

    Column (
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Spacer(modifier = Modifier.height(20.dp))
        Box(
            modifier = Modifier.fillMaxHeight(.3f).background(Color.Gray),
            contentAlignment = Alignment.Center
        ) {
            imageUri?.let {
                if (Build.VERSION.SDK_INT>28){
                    bitMap.value = MediaStore.Images.Media.getBitmap(context.contentResolver,it)
                }else{
                    val sourse = ImageDecoder.createSource(context.contentResolver,it)
                    bitMap.value = ImageDecoder.decodeBitmap(sourse)
                }

                bitMap.value?.let {image->
                    Image(bitmap = image.asImageBitmap(), contentDescription = "")
                }
            }
        }
        
        Spacer(modifier = Modifier.height(20.dp))
        Button(
            shape = MaterialTheme.shapes.small,
            modifier = Modifier
                .height(50.dp)
                .fillMaxWidth(),
            onClick = {
                launcher.launch("image/*")
            }) {
            Text(text = "ImagePicker")
        }
    }

}