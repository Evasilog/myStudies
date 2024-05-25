package com.example.mystudies;

import androidx.annotation.NonNull;

public class SpinnerItem {
    private String _value;
    private String _label;

    @NonNull
    @Override
    public String toString() {
        return this._label;
    }

    public void setValue(String value){
        this._value = value;
    }

    public String getValue(){
        return this._value;
    }

    public void setLabel(String label){
        this._label = label;
    }

    public String getLabel(){
        return this._label;
    }
}
