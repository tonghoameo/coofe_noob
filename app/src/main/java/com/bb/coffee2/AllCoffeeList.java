package com.bb.coffee2;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bb.coffee2.Adapter.CoffeeAdapter;
import com.bb.coffee2.MVVM.CoffeeViewModel;
import com.bb.coffee2.Model.CoffeeModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AllCoffeeList#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AllCoffeeList extends Fragment implements CoffeeAdapter.GetOneCoffee {

    FirebaseFirestore firebaseFirestore;
    CoffeeAdapter adapter;
    RecyclerView recyclerView;
    CoffeeViewModel coffeeViewModel;
    NavController navController;
    int quantity;
    String tableId="";
    public AllCoffeeList() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AllCoffeeList.
     */
    // TODO: Rename and change types and number of parameters
    public static AllCoffeeList newInstance(String param1, String param2) {
        AllCoffeeList fragment = new AllCoffeeList();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Log.d("TEST", "onComplete: " );

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_all_coffee_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.recViewAll);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new CoffeeAdapter(this);
        navController = Navigation.findNavController(view);
        coffeeViewModel = new ViewModelProvider(getActivity()).get(CoffeeViewModel.class);
        coffeeViewModel.getMutableLiveData().observe(getViewLifecycleOwner(), new Observer<List<CoffeeModel>>() {
            @Override
            public void onChanged(List<CoffeeModel> coffeeModelList) {
                adapter.setCoffeeModelList(coffeeModelList);
                recyclerView.setAdapter(adapter);
            }
        });
        quantity = AllCoffeeListArgs.fromBundle(getArguments()).getQuantity();
        tableId = AllCoffeeListArgs.fromBundle(getArguments()).getTableId();
        firebaseFirestore.collection("Coffees").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete( Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for(DocumentSnapshot ds: task.getResult().getDocuments()){
                        CoffeeModel coffeeModel = ds.toObject(CoffeeModel.class);
                        
                    }
                }
            }
        });
    }

    @Override
    public void clickedCoffee(int pos, List<CoffeeModel> coffeeModels) {
        String coffeeId  = coffeeModels.get(pos).getCoffeeId();
        String coffeename  = coffeeModels.get(pos).getCoffeeName();
        String description  = coffeeModels.get(pos).getDescription();
        String imageUrl = coffeeModels.get(pos).getImageUrl();
        int price = coffeeModels.get(pos).getPrice();

        AllCoffeeListDirections.ActionAllCoffeeListToDetailedFragment action = AllCoffeeListDirections.actionAllCoffeeListToDetailedFragment();
        action.setCoffeename(coffeename);
        action.setDescription(description);
        action.setPrice(price);
        action.setId(coffeeId);
        action.setImageurl(imageUrl);

        navController.navigate(action);
    }

}