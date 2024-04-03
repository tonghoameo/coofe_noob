package com.bb.coffee2.MVVM;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.bb.coffee2.Model.CoffeeModel;

import java.util.List;

public class CoffeeViewModel extends ViewModel implements Repository.CoffeeList {
    MutableLiveData<List<CoffeeModel>> mutableLiveData = new MutableLiveData<List<CoffeeModel>>();
    Repository repository = new Repository(this);
    public CoffeeViewModel(){
        repository.getCoffee();
    }

    public LiveData<List<CoffeeModel>> getMutableLiveData() {
        return mutableLiveData;
    }

    @Override
    public void coffeeLists(List<CoffeeModel> coffeeModelList) {
        mutableLiveData.setValue(coffeeModelList);
    }
}
