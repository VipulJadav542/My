import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.rk.afterstart.Adapters.RealTimeDataFetchAdapter
import com.rk.afterstart.DataModel.RealTime
import com.rk.afterstart.databinding.ActivityRetriveFromRealTimeBinding
import com.rk.afterstart.databinding.UpdateNoteBinding

class RetriveFromRealTimeFragment : Fragment(), RealTimeDataFetchAdapter.OnItemClickListener {
    private var _binding: ActivityRetriveFromRealTimeBinding? = null
    private val binding get() = _binding!!
    private lateinit var databaseReference: DatabaseReference
    private lateinit var auth: FirebaseAuth
    private lateinit var recyclerView: androidx.recyclerview.widget.RecyclerView
//    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = ActivityRetriveFromRealTimeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        navController=androidx.navigation.Navigation.findNavController(view)
        recyclerView = binding.recyclerView3
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        databaseReference = FirebaseDatabase.getInstance().getReference("Notes")
        val dataList = mutableListOf<RealTime>()

        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                dataList.clear()
                for (noteSnapshot in snapshot.children) {
                    val title = noteSnapshot.child("title").getValue(String::class.java)
                    val description = noteSnapshot.child("description").getValue(String::class.java)
                    val noteId = noteSnapshot.key
                    if (title != null && description != null && noteId != null) {
                        val data = RealTime(noteId, title, description)
                        dataList.add(data)
                    }
                }
//                val adapter = RealTimeDataFetchAdapter(
//                    dataList,
//                    this@RetriveFromRealTimeFragment,
//                    this@RetriveFromRealTimeFragment
//                )
//                recyclerView.adapter = adapter
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e(TAG, "Failed to read data from Firebase.", error.toException())
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun OnDeleteClick(Id: String) {
        val databaseReference = FirebaseDatabase.getInstance().getReference("Notes").child(Id)
        databaseReference.removeValue().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Log.d(TAG, "Record deleted successfully")
            } else {
                Log.e(TAG, "Failed to delete record", task.exception)
            }
        }
    }

//    override fun OnUpdateClick(
//        Id: String,
//        title: String,
//        description: String,
//        email: String,
//        date: String
//    ) {
//        TODO("Not yet implemented")
//    }

    override fun OnUpdateClick(Id: String, currenttitle: String, currentdescription: String,email: String,date: String) {
        val dialogBinding = UpdateNoteBinding.inflate(layoutInflater)
        val dialog = AlertDialog.Builder(requireContext()).setView(dialogBinding.root)
            .setTitle("Update Note")
            .setPositiveButton("Update") { dialog, _ ->
                val newtitle = dialogBinding.updateTitle.text.toString()
                val newdescription = dialogBinding.updateDescription.text.toString()
                UpdateNote(Id, newtitle, newdescription)
                dialog.dismiss()
            }
            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }
            .create()
        dialogBinding.updateTitle.setText(currenttitle)
        dialogBinding.updateDescription.setText(currentdescription)
        dialog.show()
    }

    private fun UpdateNote(id: String, newtitle: String, newdescription: String) {
        val databaseReference = FirebaseDatabase.getInstance().getReference("Notes").child(id)
        val updateNote = RealTime(id, newtitle, newdescription)
        databaseReference.setValue(updateNote).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Log.d(TAG, "Record Updated successfully")
            } else {
                Log.e(TAG, "Failed to Updated record", task.exception)
            }
        }
    }
}
