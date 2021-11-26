package com.example.school.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.school.R
import com.example.school.adapter.DashboardAdapter
import com.example.school.api.school.ApiSchool
import com.example.school.models.Discipline
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TeamsFragment : Fragment() {
    private lateinit var recyclerViewTurmas :RecyclerView
    private lateinit var dashBoardAdapter: DashboardAdapter
    lateinit var btn_criar_disciplina: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_teams, container, false)
    }

    @Suppress("DEPRECATION")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val context = view.context

        btn_criar_disciplina = view.findViewById(R.id.btn_criar_disciplina)

        recyclerViewTurmas = view.findViewById(R.id.recycler_teams)!!
        dashBoardAdapter = DashboardAdapter(context)
        //layout of recyclerview, grid two columns
        recyclerViewTurmas.layoutManager = GridLayoutManager(context, 2)
        //LinearLayoutManager(view.context, LinearLayoutManager.VERTICAL, false)
        //* Definindo a Adapter da RV(RecycleView)
        recyclerViewTurmas.adapter = dashBoardAdapter

        btn_criar_disciplina.setOnClickListener {
            val view = View.inflate(context, R.layout.dialog_displine, null)

            val builder = AlertDialog.Builder(context)
            builder.setView(view)

            val dialog = builder.create()
            dialog.show()

            dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        }

        //call the shared preferences school and get jwt
        val sharedPreferences = context.getSharedPreferences("school", 0)
        val jwt = sharedPreferences.getString("JWT", "teste de chamada saida vazia")

        //call the api for populate disciplines of studant
        val remote = ApiSchool.SchoolEndPoint().dashboardService()
        val call: Call<List<Discipline>> = remote.listDisciplines("Bearer $jwt")

        //aply a request async and get the response
        call.enqueue(object : Callback<List<Discipline>> {
            override fun onResponse(call: Call<List<Discipline>>, response: Response<List<Discipline>>) {
                /*Log.i("RESPONSE body", response.body().toString())
                Log.i("RESPONSE", response.message().toString())
                Log.i("RESPONSE", response.code().toString())
                Log.i("RESPONSE", response.errorBody().toString())
                Log.i("RESPONSE", response.isSuccessful.toString())
                Log.i("RESPONSE", response.headers().toString())
                Log.i("RESPONSE", response.raw().toString())*/

                if(response.code() == 200){
                    dashBoardAdapter.updateListasDisciplina(response.body()!!)
                }
            }

            override fun onFailure(call: Call<List<Discipline>>, t: Throwable) {
                /*Log.i("REQUEST", "FAIL")*/
            }
        })
    }

}