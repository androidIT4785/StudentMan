package vn.edu.hust.studentman

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    // Khai báo biến toàn cục nhưng không khởi tạo ngay lập tức
    private lateinit var studentAdapter: StudentAdapter
    private lateinit var students: MutableList<StudentModel>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Khởi tạo danh sách sinh viên
        students = mutableListOf(
            StudentModel("Nguyễn Văn An", "SV001"),
            StudentModel("Trần Thị Bảo", "SV002"),
            StudentModel("Lê Hoàng Cường", "SV003"),
            StudentModel("Phạm Thị Dung", "SV004"),
            StudentModel("Đỗ Minh Đức", "SV005"),
            StudentModel("Vũ Thị Hoa", "SV006"),
        )

        // Khởi tạo adapter
        studentAdapter = StudentAdapter(students) { student, position ->
            // Khôi phục sinh viên đã xóa
            students.add(position, student)
            studentAdapter.notifyItemInserted(position)
        }

        // Cài đặt RecyclerView
        findViewById<RecyclerView>(R.id.recycler_view_students).run {
            adapter = studentAdapter
            layoutManager = LinearLayoutManager(this@MainActivity)
        }

        // Thêm sinh viên mới
        findViewById<Button>(R.id.btn_add_new).setOnClickListener {
            val dialogView = layoutInflater.inflate(R.layout.layout_dialog, null)
            val dialog = androidx.appcompat.app.AlertDialog.Builder(this)
                .setView(dialogView)
                .setTitle("Thêm sinh viên")
                .setPositiveButton("OK") { _, _ ->
                    val name = dialogView.findViewById<EditText>(R.id.edit_hoten).text.toString()
                    val id = dialogView.findViewById<EditText>(R.id.edit_mssv).text.toString()
                    students.add(StudentModel(name, id))
                    studentAdapter.notifyItemInserted(students.size - 1)
                }
                .setNegativeButton("Cancel", null)
                .create()
            dialog.show()
        }
    }
}

