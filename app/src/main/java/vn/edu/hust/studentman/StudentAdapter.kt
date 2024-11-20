package vn.edu.hust.studentman

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.appcompat.app.AlertDialog
import com.google.android.material.snackbar.Snackbar

class StudentAdapter(
    private val students: MutableList<StudentModel>,
    private val onUndoDelete: (StudentModel, Int) -> Unit
) : RecyclerView.Adapter<StudentAdapter.StudentViewHolder>() {

    class StudentViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val textStudentName: TextView = itemView.findViewById(R.id.text_student_name)
        val textStudentId: TextView = itemView.findViewById(R.id.text_student_id)
        val imageEdit: ImageView = itemView.findViewById(R.id.image_edit)
        val imageRemove: ImageView = itemView.findViewById(R.id.image_remove)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.layout_student_item, parent, false)
        return StudentViewHolder(itemView)
    }

    override fun getItemCount(): Int = students.size

    override fun onBindViewHolder(holder: StudentViewHolder, position: Int) {
        val student = students[position]

        holder.textStudentName.text = student.studentName
        holder.textStudentId.text = student.studentId

        holder.imageEdit.setOnClickListener {
            val dialogView = LayoutInflater.from(holder.itemView.context).inflate(R.layout.layout_dialog, null)
            val dialog = AlertDialog.Builder(holder.itemView.context)
                .setView(dialogView)
                .setTitle("Chỉnh sửa sinh viên")
                .setPositiveButton("OK") { _, _ ->
                    val name = dialogView.findViewById<EditText>(R.id.edit_hoten).text.toString()
                    val id = dialogView.findViewById<EditText>(R.id.edit_mssv).text.toString()

                    students[position] = StudentModel(name, id)
                    notifyItemChanged(position)
                }
                .setNegativeButton("Cancel", null)
                .create()

            dialogView.findViewById<EditText>(R.id.edit_hoten).setText(student.studentName)
            dialogView.findViewById<EditText>(R.id.edit_mssv).setText(student.studentId)
            dialog.show()
        }

        holder.imageRemove.setOnClickListener {
            val context = holder.itemView.context
            val removedStudent = students[position]
            val removedPosition = position

            // Hiển thị hộp thoại xác nhận
            AlertDialog.Builder(context)
                .setTitle("Xóa sinh viên")
                .setMessage("Bạn có chắc chắn muốn xóa sinh viên ${removedStudent.studentName} không?")
                .setPositiveButton("Xóa") { _, _ ->

                    // Xóa sinh viên khỏi danh sách
                    students.removeAt(position)
                    notifyItemRemoved(position)

                    // Hiển thị Snackbar
                    Snackbar.make(holder.itemView, "Đã xóa ${removedStudent.studentName}", Snackbar.LENGTH_LONG)
                        .setAction("Hoàn tác") {
                            // Hoàn tác hành động xóa
                            onUndoDelete(removedStudent, removedPosition)
                        }
                        .show()
                }
                .setNegativeButton("Hủy", null)
                .create()
                .show()
        }
    }
}

