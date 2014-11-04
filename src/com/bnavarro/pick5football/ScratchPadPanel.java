package com.bnavarro.pick5football;

import java.util.Stack;

import android.view.View;
import android.widget.ListView;

public class ScratchPadPanel {
	
	//Editor
	//ItemList
	private LinedEditText scratchPad;
	private ListView scratchPadList;
	private Stack<View> stack;
	
	public ScratchPadPanel (){
		stack = new Stack<View> ();
		stack.push(scratchPad);
		stack.push(scratchPadList);
	}
}
