package com.unitsendtest.m2mobiandroidassessment.generic.view;

import com.airbnb.epoxy.EpoxyDataBindingLayouts;
import com.unitsendtest.m2mobiandroidassessment.R;

@EpoxyDataBindingLayouts(
        value = {
                R.layout.activity_detail,
                R.layout.activity_main,
                R.layout.item_image,
                R.layout.item_comment,
                R.layout.item_header,
        },
        enableDoNotHash = false
)
interface EpoxyLayouts {
}
