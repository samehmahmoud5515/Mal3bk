// Generated code from Butter Knife. Do not modify!
package com.logicdesigns.mohammed.mal3bklast.fragments;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v4.widget.NestedScrollView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.logicdesigns.mohammed.mal3bklast.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class AddClubFragment_ViewBinding implements Unbinder {
  private AddClubFragment target;

  @UiThread
  public AddClubFragment_ViewBinding(AddClubFragment target, View source) {
    this.target = target;

    target.scrollView = Utils.findRequiredViewAsType(source, R.id.scrollViewClub, "field 'scrollView'", NestedScrollView.class);
    target.playNameEditText = Utils.findRequiredViewAsType(source, R.id.playName_et, "field 'playNameEditText'", EditText.class);
    target.price_et = Utils.findRequiredViewAsType(source, R.id.price_et, "field 'price_et'", EditText.class);
    target.address_et = Utils.findRequiredViewAsType(source, R.id.address_et, "field 'address_et'", EditText.class);
    target.selectType = Utils.findRequiredViewAsType(source, R.id.selectType, "field 'selectType'", Button.class);
    target.selectDoshPanio = Utils.findRequiredViewAsType(source, R.id.selectDoshPanio, "field 'selectDoshPanio'", Button.class);
    target.selectground = Utils.findRequiredViewAsType(source, R.id.selectground, "field 'selectground'", Button.class);
    target.select_city = Utils.findRequiredViewAsType(source, R.id.select_city, "field 'select_city'", Button.class);
    target.addPlayground_btn = Utils.findRequiredViewAsType(source, R.id.addPlayground_btn, "field 'addPlayground_btn'", Button.class);
    target.back_btn = Utils.findRequiredViewAsType(source, R.id.back_btn, "field 'back_btn'", Button.class);
    target.details_et = Utils.findRequiredViewAsType(source, R.id.details_et, "field 'details_et'", EditText.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    AddClubFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.scrollView = null;
    target.playNameEditText = null;
    target.price_et = null;
    target.address_et = null;
    target.selectType = null;
    target.selectDoshPanio = null;
    target.selectground = null;
    target.select_city = null;
    target.addPlayground_btn = null;
    target.back_btn = null;
    target.details_et = null;
  }
}
