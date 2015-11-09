package com.asha.nightowllib.handler.impls;

import android.widget.TextView;

import com.asha.nightowllib.NightOwlTable;
import com.asha.nightowllib.handler.annotations.OwlHandle;
import com.asha.nightowllib.handler.annotations.OwlSysStyleable;

/**
 * Created by hzqiujiadi on 15/11/5.
 * hzqiujiadi ashqalcn@gmail.com
 */
@OwlSysStyleable("textAppearance")
@OwlHandle({TextView.class})
public class TextViewHandler extends AbsSkinHandler implements NightOwlTable.OwlTextView {
}
