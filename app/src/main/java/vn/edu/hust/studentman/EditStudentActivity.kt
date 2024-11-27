package vn.edu.hust.studentman

import android.app.Activity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class EditStudentActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)

        val editHoten = findViewById<EditText>(R.id.edit_hoten)
        val editMssv = findViewById<EditText>(R.id.edit_mssv)
        val btnOk = findViewById<Button>(R.id.button_ok)
        val btnCancel = findViewById<Button>(R.id.button_cancel)

        val name = intent.getStringExtra("studentName")
        val id = intent.getStringExtra("studentId")

        if (name == null || id == null) {
            Toast.makeText(this, "Student name or ID is missing!", Toast.LENGTH_SHORT).show()
            setResult(Activity.RESULT_CANCELED)
            finish()
            return
        } else {
            editHoten.setText(name)
            editMssv.setText(id)
        }

        btnOk.setOnClickListener {
            val newName = editHoten.text.toString()
            val newId = editMssv.text.toString()

            if (newName.isEmpty() || newId.isEmpty()) {
                Toast.makeText(this, "Name and ID cannot be empty!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            intent.putExtra("newName", newName)
            intent.putExtra("newId", newId)
            setResult(Activity.RESULT_OK, intent)

            finish()
        }

        btnCancel.setOnClickListener {
            Toast.makeText(this, "Canceled", Toast.LENGTH_SHORT).show()
            setResult(Activity.RESULT_CANCELED)
            finish()
        }
    }
}
