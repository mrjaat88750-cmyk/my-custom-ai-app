package com.customai.builder

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val inputPrompt = findViewById<EditText>(R.id.inputPrompt)
        val btnGenerate = findViewById<Button>(R.id.btnGenerate)
        val imagePreview = findViewById<ImageView>(R.id.imagePreview)
        val textStatus = findViewById<TextView>(R.id.textStatus)

        btnGenerate.setOnClickListener {
            val prompt = inputPrompt.text.toString().trim()
            if (prompt.isEmpty()) {
                textStatus.text = "कृपया पहले कोई प्रॉम्प्ट लिखें।"
                return@setOnClickListener
            }

            val bitmap = generateImageFromPrompt(prompt)
            imagePreview.setImageBitmap(bitmap)
            textStatus.text = "इमेज तैयार हुई: $prompt"
        }
    }

    private fun generateImageFromPrompt(prompt: String): Bitmap {
        val width = 512
        val height = 512
        val bmp = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bmp)
        val p = prompt.lowercase()

        val bgColor = when {
            "night" in p || "रात" in p || "dark" in p -> Color.rgb(15, 20, 45)
            "sunset" in p || "लाल" in p || "red" in p -> Color.rgb(180, 55, 45)
            "sky" in p || "नीला" in p || "blue" in p -> Color.rgb(30, 110, 190)
            else -> Color.rgb(28, 32, 42)
        }
        canvas.drawColor(bgColor)

        val paint = Paint(Paint.ANTI_ALIAS_FLAG)
        val rnd = Random(prompt.hashCode())

        for (i in 1..16) {
            paint.color = Color.argb(
                190,
                rnd.nextInt(50, 255),
                rnd.nextInt(50, 255),
                rnd.nextInt(50, 255)
            )
            val cx = rnd.nextFloat() * width
            val cy = rnd.nextFloat() * height
            val radius = rnd.nextFloat() * 85f + 20f

            if ("square" in p || "बॉक्स" in p) {
                canvas.drawRect(cx - radius / 2, cy - radius / 2, cx + radius / 2, cy + radius / 2, paint)
            } else {
                canvas.drawCircle(cx, cy, radius, paint)
            }
        }

        paint.color = Color.WHITE
        paint.textSize = 24f
        canvas.drawText(prompt.take(32), 24f, height - 28f, paint)

        return bmp
    }
}
