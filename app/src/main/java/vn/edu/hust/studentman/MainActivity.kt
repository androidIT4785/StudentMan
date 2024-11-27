package vn.edu.hust.studentman

import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.ContextMenu
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView.AdapterContextMenuInfo
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {
    private lateinit var addNewDialog: Dialog;
    private lateinit var launcher: ActivityResultLauncher<Intent>;
    private lateinit var students: MutableList<StudentModel>;
    private lateinit var studentAdapter: StudentAdapter;

    private var deletedStudent: StudentModel? = null;
    private var editingStudent: StudentModel? = null;
    private var deletedPosition: Int = -1;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        students = mutableListOf(
            StudentModel("Nguyễn Văn An", "SV001"),
            StudentModel("Trần Thị Bảo", "SV002"),
            StudentModel("Lê Hoàng Cường", "SV003"),
            StudentModel("Phạm Thị Dung", "SV004"),
            StudentModel("Đỗ Minh Đức", "SV005"),
            StudentModel("Vũ Thị Hoa", "SV006"),
            StudentModel("Hoàng Văn Hải", "SV007"),
            StudentModel("Bùi Thị Hạnh", "SV008"),
            StudentModel("Đinh Văn Hùng", "SV009"),
            StudentModel("Nguyễn Thị Linh", "SV010"),
            StudentModel("Phạm Văn Long", "SV011"),
            StudentModel("Trần Thị Mai", "SV012"),
            StudentModel("Lê Thị Ngọc", "SV013"),
            StudentModel("Vũ Văn Nam", "SV014"),
            StudentModel("Hoàng Thị Phương", "SV015"),
            StudentModel("Đỗ Văn Quân", "SV016"),
            StudentModel("Nguyễn Thị Thu", "SV017"),
            StudentModel("Trần Văn Tài", "SV018"),
            StudentModel("Phạm Thị Tuyết", "SV019"),
            StudentModel("Lê Văn Vũ", "SV020")
        )

        val value1 = intent.getIntExtra("param3", 0)

        studentAdapter = StudentAdapter(students, this)

        val listView = findViewById<ListView>(R.id.list_view);
        listView.adapter = studentAdapter

        registerForContextMenu(listView);

        //add new
        addNewDialog = Dialog(this)
        addNewDialog.setContentView(R.layout.add_new_dialog)
        addNewDialog.window?.setLayout(
            ActionBar.LayoutParams.MATCH_PARENT,
            ActionBar.LayoutParams.WRAP_CONTENT
        )

        addNewDialog.findViewById<Button>(R.id.button_ok).setOnClickListener {
            val editHoten = addNewDialog.findViewById<EditText>(R.id.edit_hoten)
            val editMssv = addNewDialog.findViewById<EditText>(R.id.edit_mssv)
            val hoten = editHoten.text.toString()
            val mssv = editMssv.text.toString()

            students.add(StudentModel(hoten, mssv));
            studentAdapter.notifyDataSetChanged();

            addNewDialog.dismiss()
        }

        addNewDialog.findViewById<Button>(R.id.button_cancel).setOnClickListener {
            addNewDialog.dismiss()
        }

        launcher =
            this.registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                if (it.resultCode == Activity.RESULT_OK) {
                    val newName = it.data?.getStringExtra("newName")
                    val newId = it.data?.getStringExtra("newId")

                    if (editingStudent == null) {
                        Snackbar.make(
                            findViewById(android.R.id.content),
                            "Invalid student position",
                            Snackbar.LENGTH_LONG
                        ).show()
                        return@registerForActivityResult
                    } else {
                        if (newName != null) {
                            editingStudent!!.studentName = newName
                        }

                        if (newId != null) {
                            editingStudent!!.studentId = newId
                        }

                        studentAdapter.notifyDataSetChanged()
                    }
                }
            }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.appbar_menu, menu)
        return true;
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menuAddNew -> {
                addNewDialog.show()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        menuInflater.inflate(R.menu.student_context_menu, menu);
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        val pos = (item.menuInfo as AdapterContextMenuInfo).position;

        when (item.itemId) {
            R.id.action_edit -> {
                moveToEditActivity(students[pos]);
            }

            R.id.action_delete -> {
                showDeleteDialog(students[pos], pos)
            }
        }
        return super.onContextItemSelected(item)
    }

    private fun moveToEditActivity(student: StudentModel) {
        val intent = Intent(this, EditStudentActivity::class.java);
        intent.putExtra("studentName", student.studentName)
        intent.putExtra("studentId", student.studentId)
        editingStudent = student

        launcher.launch(intent)
    }

    private fun showDeleteDialog(student: StudentModel, position: Int) {
        AlertDialog.Builder(this)
            .setTitle("Xóa sinh viên ${student.studentName}?")
            .setPositiveButton("Xác nhận") { _, _ ->
                deletedStudent = student
                deletedPosition = position

                students.removeAt(position)
                studentAdapter.notifyDataSetChanged()

                val rootView = findViewById<View>(android.R.id.content)
                Snackbar.make(rootView, "Đã xóa sinh viên ${student.studentName}", Snackbar.LENGTH_LONG)
                    .setAction("Hoàn tác") {
                        if (deletedStudent !== null && deletedPosition != -1) {
                            students.add(deletedPosition, deletedStudent!!)
                            studentAdapter.notifyDataSetChanged()

                            deletedStudent = null;
                            deletedPosition = -1;

                            Snackbar.make(rootView, "Đã hoàn tác", Snackbar.LENGTH_LONG)
                                .show()
                        }
                    }
                    .show()
            }
            .setNegativeButton("Hủy", null)
            .show()
    }


}