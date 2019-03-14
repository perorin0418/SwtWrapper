package org.net.perorin.swtwrapper;

import org.eclipse.swt.internal.win32.OS;

public class Test {

	public static void main(String[] args) throws HwndNotFoundException {
		Window w = new Window("TransformView");
		System.out.println(w.getTitle().getTitleName());
		for (ChildWindow c : w.getAllChildList()) {
			System.out.println(c.getClassName() + " - " + c.getText() + " = " + c.getHwnd());
		}

		System.out.println(OS.SendMessage(134522L, OS.WM_LBUTTONDOWN, OS.HTCLIENT, 0L));

	}

}
