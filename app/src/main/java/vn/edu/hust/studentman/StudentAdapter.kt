package vn.edu.hust.studentman

import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.ActionBar
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar

class StudentAdapter(
  val students: MutableList<StudentModel>,
  val context: Context
): BaseAdapter() {

  override fun getCount(): Int = students.size;

  override fun getItem(position: Int): StudentModel = students[position]

  override fun getItemId(position: Int): Long = position.toLong();

  override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
    val view: View = convertView ?: LayoutInflater.from(context).inflate(R.layout.layout_student_item, parent, false);

    val studentName = view.findViewById<TextView>(R.id.text_student_name)
    val studentId = view.findViewById<TextView>(R.id.text_student_id)

    val student = students[position];
    studentName.text = student.studentName;
    studentId.text = student.studentId;

    return view;
  }
}