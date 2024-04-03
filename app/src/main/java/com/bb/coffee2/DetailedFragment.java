package com.bb.coffee2;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bb.coffee2.Model.CoffeeModel;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DetailedFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DetailedFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    FirebaseFirestore firebaseFirestore;
    NavController navController;
    int quantity = 0;
    Button add, sub,order;
    TextView coffeenameTxtV, coffeedesciptionTxtV, quantityTxtV,orderinfoTxtV;
    ImageView imageView;
    String coffeeid, coffeename, coffeedescription,coffeeimageurl;
    int price = 0, total = 0;
    ProgressBar pb;
    public DetailedFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DetailedFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DetailedFragment newInstance(String param1, String param2) {
        DetailedFragment fragment = new DetailedFragment();
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
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detailed, container, false);
    }

    @Override
    public void onViewCreated( View view,  Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        pb = new ProgressBar(getContext());
        imageView = view.findViewById(R.id.CoffeeDetailImage);
        coffeenameTxtV = view.findViewById(R.id.coffeenamedetail);
        coffeedesciptionTxtV = view.findViewById(R.id.coffeedetaildetail);
        quantityTxtV = view.findViewById(R.id.quantityDETAIL);
        orderinfoTxtV = view.findViewById(R.id.orderINFO);

        add = view.findViewById(R.id.incrementcoffee);
        sub = view.findViewById(R.id.decrementcoffee);
        order = view.findViewById(R.id.orderdetail);

        firebaseFirestore = FirebaseFirestore.getInstance();
        navController = Navigation.findNavController(view);

        coffeename = DetailedFragmentArgs.fromBundle(getArguments()).getCoffeename();
        coffeedescription = DetailedFragmentArgs.fromBundle(getArguments()).getDescription();
        coffeeimageurl = DetailedFragmentArgs.fromBundle(getArguments()).getImageurl();
        price =DetailedFragmentArgs.fromBundle(getArguments()).getPrice();
        coffeeid =DetailedFragmentArgs.fromBundle(getArguments()).getId();

        Glide.with(view.getContext()).load(coffeeimageurl).into(imageView);
        coffeenameTxtV.setText(coffeename + " "+price+"000VND");
        coffeedesciptionTxtV.setText(coffeedescription);
        firebaseFirestore.collection("Coffees").document(coffeeid).addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent( DocumentSnapshot value,  FirebaseFirestoreException error) {
                CoffeeModel coffeeModel = value.toObject(CoffeeModel.class);
                quantity = coffeeModel.getQuantity();
                updateTotal(quantity,quantityTxtV,orderinfoTxtV);

            }
        });
        //update quantity and total
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                quantity++;
                updateTotal(quantity,quantityTxtV,orderinfoTxtV);
                updateQuantity(quantity);

            }
        });
        sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(quantity>0){
                    quantity--;
                    updateTotal(quantity,quantityTxtV,orderinfoTxtV);
                    updateQuantity(quantity);
                }

            }
        });
        // add to cart
        order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addToCart();
            }
        });

    }
    private void addToCart(){
        if(quantity == 0){
            navController.navigate(R.id.action_detailedFragment_to_allCoffeeList);
            Toast.makeText(getContext(), "nothing is order", Toast.LENGTH_SHORT).show();
        }else{
            HashMap<String, Object> carts = new HashMap<>();
            carts.put("coffeename", coffeename);
            carts.put("quantity", quantity);
            carts.put("imageUrl",coffeeimageurl);
            carts.put("totalprice", total);
            carts.put("coffeeid", coffeeid);
            // update cart with bill add more id
            firebaseFirestore.collection("Cart").document().set(carts).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(Task<Void> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(getContext(), "Add to cart success", Toast.LENGTH_SHORT).show();

                        DetailedFragmentDirections.ActionDetailedFragmentToAllCoffeeList action = DetailedFragmentDirections.actionDetailedFragmentToAllCoffeeList();
                        DocumentReference docRef = firebaseFirestore.collection("Cart").document();
                        action.setTableId(docRef.getId());
                        action.setQuantity(quantity);
                        navController.navigate(action);
                    }
                }
            });
        }
    }
    // update in database
    private void updateQuantity(int quantity){
        firebaseFirestore.collection("Coffees").document(coffeeid).update("quantity",quantity).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

            }
        });
    }
    private void updateTotal(int quantity,TextView quantityTxtV, TextView orderinfoTxtV){
        quantityTxtV.setText(String.valueOf(quantity));
        total = price*quantity;
        orderinfoTxtV.setText("Total: "+String.valueOf(total)+"000VND");
    }
}